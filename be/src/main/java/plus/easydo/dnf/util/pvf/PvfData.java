package plus.easydo.dnf.util.pvf;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author laoyu
 * @version 1.0
 * @description pvf文件对象
 * @date 2023/11/12
 */

@Data
@Builder
@Slf4j
public class PvfData extends PvfCrc {

    private byte[] pvfByte;

    private int uuidLen;

    private String uuid;

    private int version;

    private int alignedIndexHeaderSize;

    private int indexHeaderCrc;

    private int indexSize;

    private byte[] indexHeaderData;

    private List<PvfFileListData> pvfFileListData;

    private List<StringTableData> stringTableDataList;

    private Map<String, PvfFileListData> pvfFileListMap;

    private Map<Integer,Map<String,String>> nStringMap;

    private Map<Integer,String> itemMap;

    /**
     * 初始化pvf的文件列表数据
     *
     * @author laoyu
     * @date 2023/11/12
     */
    public void readPvfFileData(){
        //解密头文件获取目录数据
        crcDecode(indexHeaderData,indexHeaderCrc);
        List<PvfFileListData> pvfFileList = new ArrayList<>();
        int currentPos = 0;
        // +56 是指读取pvf头部信息时共读取了56字节长度
        int startPost = alignedIndexHeaderSize +56;
        for (int i = 0; i < indexSize; i++) {
            PvfFileListData pvfFileListData = new PvfFileListData();
            int fileLength = pvfFileListData.readTree(indexHeaderData, currentPos, pvfByte,startPost);
            if(CharSequenceUtil.isNotBlank(pvfFileListData.getFilePath())){
                pvfFileList.add(pvfFileListData);
            }
            currentPos += fileLength;
            //如果是字符串字典则直接初始化
            if("stringtable.bin".equals(pvfFileListData.getFilePath())){
                initStringTable(pvfFileListData.getDecodeFileContent());
            }
        }
        this.pvfFileListMap = pvfFileList.stream().collect(Collectors.toMap(PvfFileListData::getFilePath,s->s));
    }

    /**
     * 初始化字典
     *
     * @param fileContext fileContext
     * @author laoyu
     * @date 2023/11/12
     */
    private void initStringTable(byte[] fileContext) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(fileContext);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int strLength = byteBuffer.getInt();
        this.stringTableDataList = new ArrayList<>();
        int start = byteBuffer.getInt();
        for (int i = 0; i < strLength; i++) {
            int end = byteBuffer.getInt();
            StringTableData stringTableData = new StringTableData();
            stringTableData.setStart(start);
            stringTableData.setEnd(end);
            byte[] strByte = new byte[end-start];
            byteBuffer.mark();
            byteBuffer.position(start+4);
            byteBuffer.get(strByte);
            byteBuffer.reset();
            String context = new String(strByte, Charset.forName("Big5")).trim();
            stringTableData.setContext(context);
            stringTableDataList.add(stringTableData);
            start = end;
        }
    }

    /**
     * 读取 n_string.lst文件
     *
     * @author laoyu
     * @date 2023/11/12
     */
    public void readNStringList(){
        PvfFileListData nStringData = pvfFileListMap.get("n_string.lst");
        byte[] fileContext = nStringData.getDecodeFileContent();
        ByteBuffer byteBuffer = ByteBuffer.wrap(fileContext);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        short shortValue = byteBuffer.getShort();
        this.nStringMap = new HashMap<>();
        while (byteBuffer.hasRemaining()){
            if(byteBuffer.remaining() -10>=0){
                //每一行10个字节，如果不够则不处理
                byte b = byteBuffer.get();//2
                int index = byteBuffer.getInt(); //每行前面的编号
                byte b1 = byteBuffer.get();//7
                int anInt = byteBuffer.getInt();// 在stringtable.bin字符串字典中的位置
                if(anInt >= 0 && anInt <= stringTableDataList.size()){
                    //kor.str的位置
                    String strPath = stringTableDataList.get(anInt).getContext().toLowerCase();
                    log.info("read kor.str:{}",strPath);
                    PvfFileListData strKor = pvfFileListMap.get(strPath);
                    String contextStr = strKor.getContextStr();
                    if(CharSequenceUtil.isNotBlank(contextStr)){
                        String[] split = contextStr.split("\n");
                        Map<String,String> korMap = new HashMap<>();
                        for (int i = 0; i < split.length; i++) {
                            String line = split[i];
                            String[] row = line.split(">");
                            if(row.length == 2){
                                korMap.put(row[0],row[1]);
                            }
                        }
                        nStringMap.put(index,korMap);
                    }else {
                        break;
                    }
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    static class  StringTableData {
        private int start;
        private int end;
        private String context;
    }


}
