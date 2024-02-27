package plus.easydo.dnf.util;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 * @description 装备物品读取
 * @date 2023/11/11
 */

@Slf4j
public class ItemReaderUtil {

    private static final String STACKABLE_NAME = "stackable";
    private static final String STACKABLE_LIST_NAME = "stackable.lst";
    private static final String EQUIPMENT_NAME = "equipment";
    private static final String EQUIPMENT_LIST_NAME = "equipment.lst";

    private ItemReaderUtil() {
    }

    public static List<JSONObject> reader(MultipartFile file) throws IOException {
        String tempPath = FileUtil.getTmpDirPath();
        InputStream in = null;
        BufferedOutputStream op = null;
        try {
            //先保存文件
            log.info("ItemReaderUtil.reader: 保存文件到本地");
            in = file.getInputStream();
            File tmpFile = FileUtil.file(tempPath + file.getName());
            op = FileUtil.getOutputStream(tmpFile);
            IoUtil.copy(in, op);
            //解压文件
            log.info("ItemReaderUtil.reader: 解压文件");
            unPackage7Z(tempPath, tmpFile.getPath());
            List<JSONObject> resultList = new ArrayList<>();
            log.info("ItemReaderUtil.reader: 开始解析物品信息");
            //读取道具列表
            //先判断是否存在对应的文件夹
            readerForPath(tempPath + STACKABLE_NAME, resultList, STACKABLE_LIST_NAME);
            //读取装备列表
            readerForPath(tempPath + EQUIPMENT_NAME, resultList, EQUIPMENT_LIST_NAME);
            log.info("ItemReaderUtil.reader: 解析完成");
            return resultList;
        } finally {
            if(in != null){
                in.close();
            }
            if(op != null){
                op.close();
            }
        }

    }

    private static void readerForPath(String parentPath, List<JSONObject> resultList, String listFileName) throws IOException {
        if (FileUtil.isDirectory(parentPath)) {
            //判断是否存在列表文件
            boolean listFile = FileUtil.isFile(parentPath + File.separator + listFileName);
            if (listFile) {
                BufferedReader reader = null;
                try {
                    log.info("ItemReaderUtil.reader: 检测到列表文件,开始读取{}", listFileName);
                    //读取列表文件
                    File stackableListFile = FileUtil.file(parentPath + File.separator + listFileName);
                    reader = FileUtil.getReader(stackableListFile, CharsetUtil.parse(CharsetUtil.UTF_8));
                    String line1;
                    String line2;
                    line1 = reader.readLine();
                    line2 = reader.readLine();
                    while (line1 != null && line2 != null) {
                        if (line1.contains("#PVF_File") || CharSequenceUtil.isBlank(line2)) {
                            //什么都不做
                        } else {
                            try {
                                //读取每个物品文件
                                log.info("ItemReaderUtil.reader: 读取到物品文件，解析信息{},{}", line1, line2);
                                JSONObject res = reader(parentPath + File.separator + line2.replace("`",""));
                                res.set("itemId", line1);
                                resultList.add(res);
                            }catch (Exception exception){
                                log.error("ItemReaderUtil.reader: 读取到物品文件{}失败，跳过 {}", line2, exception.getMessage());
                            }
                        }
                        line1 = reader.readLine();
                        line2 = reader.readLine();
                        log.info("ItemReaderUtil.reader: 解析物品文件完成，继续下一个,{},{}", line1, line2);
                    }
                }finally {
                    reader.close();
                    log.info("ItemReaderUtil.reader: 解析完成，删除所有解压的文件");
                    FileUtil.del(parentPath);
                }
            }
        }
    }

    /**
     * 解压7z文件
     *
     * @param tempPath tempPath
     * @param filePath filePath
     * @author laoyu
     * @date 2023/11/11
     */
    public static void unPackage7Z(String tempPath, String filePath) throws IOException {
        log.info("ItemReaderUtil.reader: 开始解压文件,请耐心等待");
        File file = FileUtil.file(filePath);
        try {
            SevenZFile sevenZFile = new SevenZFile(file);
            SevenZArchiveEntry entry;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                //如果是文件夹
                if (entry.isDirectory()) {
                    FileUtil.mkdir(tempPath + entry.getName());
                } else {
                    File outputFile = FileUtil.file(tempPath + entry.getName());
                    BufferedOutputStream op = FileUtil.getOutputStream(outputFile);
                    InputStream in = sevenZFile.getInputStream(entry);
                    IoUtil.copy(in, op);
                    op.close();
                    in.close();
                }
            }
        }finally {
            FileUtil.del(file);
            log.info("ItemReaderUtil.reader: 解压完毕删除缓存文件");
        }
    }


    /**
     * 读取单个文件
     *
     * @param path path
     * @return cn.hutool.json.JSONObject
     * @author laoyu
     * @date 2023/11/11
     */
    public static JSONObject reader(String path) throws IOException {
        File file = FileUtil.file(path);
        JSONObject jsonObject = JSONUtil.createObj();
        BufferedReader reader = FileUtil.getReader(file,CharsetUtil.parse(CharsetUtil.UTF_8));
        String line;
        line = reader.readLine();
        String currentKey = null;
        while (line != null) {
            currentKey = getString(jsonObject, currentKey, line);
            line = reader.readLine();
        }
        reader.close();
        return jsonObject;
    }

    /**
     * 读取单个文件
     *
     * @param fileContext fileContext
     * @return cn.hutool.json.JSONObject
     * @author laoyu
     * @date 2023/11/11
     */
    public static JSONObject readerForStr(String fileContext) {
        JSONObject jsonObject = JSONUtil.createObj();
        List<String> lines = CharSequenceUtil.split(fileContext, "\n");
        String currentKey = null;
        for (String line :lines) {
            currentKey = getString(jsonObject, currentKey, line);
        }
        return jsonObject;
    }

    private static String getString(JSONObject jsonObject, String currentKey, String line) {
        line = line.replace("\t", "").replace("\r","");
        if (line.contains("[/")) {
            // 什么都不做
        } else if (line.startsWith("[") || line.endsWith("]")) {
            currentKey = line.replace("[", "").replace("]", "").replace("`", "");
        } else {
            if (CharSequenceUtil.isNotBlank(line)) {
                jsonObject.set(currentKey, line.replace("`", "").trim());
            }
        }
        return currentKey;
    }
}
