package plus.easydo.dnf.util.pvf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description laoyu  借鉴于于 https://tieba.baidu.com/p/8243869840?pid=146754067658&cid=#146754067658
 * @date 2023/11/11
 */
@Slf4j
public class PvfReader {

    private PvfReader() {
    }

    /**
     * 读取pvf文件
     *
     * @param pvfPath pvfPath
     * @return java.util.Map<java.lang.Integer,java.lang.String>
     * @author laoyu
     * @date 2023/11/12
     */
    public static PvfData reader(String pvfPath) throws IOException {
        PvfData pvfData = initPvfData(pvfPath);
        String baseDir = "stackable";
        PvfFileListData stackable = pvfData.getPvfFileListMap().get(baseDir + "/" + "stackable.lst");
        Map<Integer, String> stackableRes = readLstFile(stackable, pvfData, baseDir);
        baseDir = "equipment";
        PvfFileListData equipment = pvfData.getPvfFileListMap().get(baseDir + "/" + "equipment.lst");
        Map<Integer, String> equipmentRes = readLstFile(equipment, pvfData, baseDir);
        stackableRes.putAll(equipmentRes);
        pvfData.setItemMap(stackableRes);
        return pvfData;
    }


    /**
     * 读取lst文件
     *
     * @param equipment equipment
     * @param pvfData pvfData
     * @param baseDir baseDir
     * @return java.util.Map<java.lang.Integer,java.lang.String>
     * @author laoyu
     * @date 2023/11/12
     */
    private static Map<Integer,String> readLstFile(PvfFileListData equipment, PvfData pvfData, String baseDir) {
        byte[] content = equipment.getDecodeFileContent();
        ByteBuffer byteBuffer = ByteBuffer.wrap(content);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        //先读取两位
        byteBuffer.getShort();
        Map<Integer,String> resultMap = new HashMap<>();
        while (byteBuffer.hasRemaining()) {
            if (byteBuffer.remaining() >= 5) {
                byte be = byteBuffer.get();
                int itemId = byteBuffer.getInt();
                //字典对应的文字
                String filePathStr = getFilePathStr(pvfData, itemId);
                if (CharSequenceUtil.isNotBlank(filePathStr)) {
                    filePathStr = baseDir + "/" + filePathStr;
                    PvfFileListData file = pvfData.getPvfFileListMap().get(filePathStr);
                    log.info("readLstFile filePathStr:{}", filePathStr);
                    if (Objects.nonNull(file)) {
                        String res = readFile(file, pvfData);
                        resultMap.put(itemId,res);
                    }
                }
            } else {
                break;
            }
        }
        return resultMap;
    }

    /**
     * 读取普通文件
     *
     * @param file file
     * @param pvfData pvfData
     * @return java.lang.String
     * @author laoyu
     * @date 2023/11/12
     */
    public static String readFile(PvfFileListData file, PvfData pvfData) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(file.getDecodeFileContent());
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#PVF_File\r");
        //先读取两位
        byteBuffer.getShort();
        while (byteBuffer.hasRemaining()) {
            if (byteBuffer.remaining() >= 5) {
                byte be = byteBuffer.get();
                int fe = byteBuffer.getInt();
                String str = null;
                List<PvfData.StringTableData> stringTableDataList = pvfData.getStringTableDataList();
                if (fe >= 0 && fe < stringTableDataList.size()) {
                    PvfData.StringTableData stringTableData = stringTableDataList.get(fe);
                    str = stringTableData.getContext();
                }
                switch (be) {
                    case 2 -> stringBuilder.append(fe).append(" ");
                    case 5 -> {
                        if (CharSequenceUtil.isNotBlank(str)) {
                            stringBuilder.append("\r\n").append(str).append("\r\n");
                        }
                    }
                    case 7 -> {
                        if (CharSequenceUtil.isNotBlank(str)) {
                            stringBuilder.append("`").append(str).append("` ");
                        }
                    }
                    default -> stringBuilder.append("\r").append(be).append("\r");
                }
            } else {
                break;
            }
        }
       return stringBuilder.toString();
    }

    /**
     * 从字典获取文件路径
     *
     * @param pvfData pvfData
     * @param alt alt
     * @return java.lang.String
     * @author laoyu
     * @date 2023/11/12
     */
    public static String getFilePathStr(PvfData pvfData, int alt) {
        if (alt >= 0 && alt < pvfData.getStringTableDataList().size()) {
            PvfData.StringTableData stringTableData = pvfData.getStringTableDataList().get(alt);
            if (Objects.nonNull(stringTableData)) {
                return stringTableData.getContext();
            }
        }
        return null;
    }


    /**
     * 初始化pvf数据
     *
     * @param pvfPath pvfPath
     * @return plus.easydo.dnf.util.pvf.PvfData
     * @author laoyu
     * @date 2023/11/12
     */
    public static PvfData initPvfData(String pvfPath) throws IOException {
        File pvfFile = FileUtil.file(pvfPath);
        return initPvfData(pvfFile);
    }

    /**
     * 初始化pvf数据
     *
     * @param pvfFile pvfFile
     * @return plus.easydo.dnf.util.pvf.PvfData
     * @author laoyu
     * @date 2023/11/12
     */
    public static PvfData initPvfData(File pvfFile) throws IOException {
        //创建一个数组用来存读出来的文件内容
        byte[] pvfByte = new byte[(int) pvfFile.length()];
        //拿到流 进行读
        BufferedInputStream in = FileUtil.getInputStream(pvfFile);
        in.read(pvfByte, 0, pvfByte.length);
        in.close();
        //byte转ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(pvfByte);
        //小端序字节顺序
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        //获取uuid的长度？
        int uuidLen = byteBuffer.getInt();
        //读取uuid
        byte[] uuidBuff = new byte[uuidLen];
        byteBuffer.get(uuidBuff, 0, uuidBuff.length);
        String uuid = new String(uuidBuff);
        //版本号
        int version = byteBuffer.getInt();
        //文件路径数据的大小
        int alignedIndexHeaderSize = byteBuffer.getInt();
        //解密的密钥
        int indexHeaderCrc = byteBuffer.getInt();
        //文件数量
        int indexSize = byteBuffer.getInt();
        //用于存储文件路径数据
        byte[] indexHeaderData = new byte[alignedIndexHeaderSize];
        byteBuffer.get(indexHeaderData, 0, indexHeaderData.length);
        PvfData pvfData = PvfData.builder()
                .pvfByte(pvfByte)
                .uuidLen(uuidLen)
                .uuid(uuid)
                .version(version)
                .alignedIndexHeaderSize(alignedIndexHeaderSize)
                .indexHeaderCrc(indexHeaderCrc)
                .indexSize(indexSize)
                .indexHeaderData(indexHeaderData)
                .build();
        pvfData.readPvfFileData();
        pvfData.readNStringList();
        return pvfData;
    }


}
