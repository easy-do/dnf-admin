package plus.easydo.dnf.util.pvf;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * @author laoyu
 * @version 1.0
 * @description pvf文件信息
 * @date 2023/11/12
 */

@Data
@NoArgsConstructor
public class PvfFileListData extends PvfCrc {
    private int fileNumber;
    private int cre32;
    private int relativeOffset;
    private int fileLength;
    private long longFileLength;
    private byte[] fileContext;
    private String filePath;
    private boolean isCrcDecode;

    public int readTree(byte[] indexHeaderData, int currentPos, byte[] pvfByte, int startPos) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(indexHeaderData);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.position(currentPos);
        this.fileNumber = byteBuffer.getInt();
        int filePathLength = byteBuffer.getInt();
        byte[] filePathByte = new byte[filePathLength];
        byteBuffer.get(filePathByte);
        int fileLength = byteBuffer.getInt();
        this.cre32 = byteBuffer.getInt();
        this.relativeOffset = byteBuffer.getInt();
        if (fileLength > 0) {
            this.longFileLength = (fileLength + 3) & 4294967292L;
            this.fileContext = new byte[(int) longFileLength];
            //copy
            System.arraycopy(pvfByte, startPos + relativeOffset, fileContext, 0, fileLength);
            this.filePath = new String(filePathByte, Charset.forName("CP949"));
            if (CharSequenceUtil.isNotBlank(filePath)) {
                this.filePath = this.filePath.toLowerCase().replace("\\", "/");
            }
        }
        return filePathLength + 20;
    }

    public byte[] getDecodeFileContent() {
        if (!isCrcDecode) {
            crcDecode(this.fileContext, this.cre32);
        }
        return this.fileContext;
    }

    public String getContextStr() {
        return new String(getDecodeFileContent(), Charset.forName("Big5")).trim();
    }

}
