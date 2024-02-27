package plus.easydo.dnf.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * @author laoyu
 * @version 1.0
 * @description zlib工具
 * @date 2023/12/27
 */

public class ZLibUtils {

    // 压缩直接数组
    public static byte[] compress(byte[] data) {
        byte[] output = new byte[0];
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }

    // 压缩 字节数组到输出流
    public static void compress(byte[] data, OutputStream os) {
        DeflaterOutputStream dos = new DeflaterOutputStream(os);
        try {
            dos.write(data, 0, data.length);
            dos.finish();
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 解压缩 字节数组
    public static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        try {
            byte[] result = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(result);
                outputStream.write(result, 0, count);
            }
            output = outputStream.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inflater.end();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    // 解压缩 字节数组
    public static String decompress_str(byte[] data) {
        byte[] output = new byte[0];
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buf);
                outputStream.write(buf, 0, count);
            }
            output = outputStream.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inflater.end();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    // 解压缩 输入流 到字节数组
    public static byte[] decompress(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        try {
            int i = 1024;
            byte[] buf = new byte[i];
            while ((i = iis.read(buf, 0, i)) > 0) {
                outputStream.write(buf, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
