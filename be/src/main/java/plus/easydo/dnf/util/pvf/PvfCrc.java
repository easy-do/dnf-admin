package plus.easydo.dnf.util.pvf;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author laoyu
 * @version 1.0
 * @description TODO
 * @date 2023/11/12
 */

public class PvfCrc {

    /**
     * 解密数据
     *
     * @param data data
     * @param crc32 crc32
     * @author laoyu
     * @date 2023/11/11
     */
    public void crcDecode(byte[] data, int crc32) {
        long num = 2175242257L;
        ByteBuffer buff = ByteBuffer.wrap(data);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        long a = num ^ crc32;
        for (int i = 0; i < data.length; i += 4) {
            buff.mark();
            int anInt = buff.getInt();
            int val = (int) (anInt ^ a);
            buff.reset();
            int str = val >>> 6 | (int) ((long) val << (32 - 6));
            buff.putInt(str);
        }
    }

}
