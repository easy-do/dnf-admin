package plus.easydo.dnf.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author laoyu
 * @version 1.0
 * @description TODO
 * @date 2023/11/11
 */

public class PvfReader {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\yuzhanfeng\\Desktop\\Script.pvf";
//        fp = open(path,'rb')
        File file = FileUtil.file(path);
        BufferedReader reader = FileUtil.getReader(file, CharsetUtil.defaultCharsetName());
        //读四个字节 获取uuid的长度
        char[] uuidChar = new char[4];
        reader.read(uuidChar);
        int length = uuidChar.length;
        int result = 0;

//        for (int i = 0; i < length; i++) {
//            result += Character.toChars(uuidChar[i]) * (int) Math.pow(26, length - 1 - i);
//        }

//        self.uuid_len = struct.unpack('i',fp.read(4))[0]

//        self.uuid = fp.read(self.uuid_len)
//        self.PVFversion = struct.unpack('i',fp.read(4))[0]
//        self.dirTreeLength = struct.unpack('i',fp.read(4))[0] #长度
//        self.dirTreeCrc32 = struct.unpack('I',fp.read(4))[0]
//        self.numFilesInDirTree:int = struct.unpack('I',fp.read(4))[0]
//        self.filePackIndexShift = fp.tell() + self.dirTreeLength
//        self.headerLength = fp.tell()
    }


}
