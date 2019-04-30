package one.chchy.autocardpatch.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: 程辉
 * @Description: 通用工具，存档非特定静态方法
 * @Date: 14:56 2017/11/3
 */
public class GeneraUtil {

    public static ByteBuffer combine(ByteBuffer byteBuffer1, ByteBuffer byteBuffer2){
        if (byteBuffer1 != null && byteBuffer1.position() != 0) {
            byteBuffer1.flip();
        }

        if (byteBuffer2 != null && byteBuffer2.position() != 0) {
            byteBuffer2.flip();
        }

        if (byteBuffer1 == null && byteBuffer2 != null){
            return byteBuffer2;
        }

        if (byteBuffer1 != null && byteBuffer2 == null){
            return byteBuffer1;
        }

        ByteBuffer buf3 = ByteBuffer.allocate(byteBuffer1.limit() + byteBuffer2.limit());
        byte[] bytes1 = new byte[byteBuffer1.limit()];
        byte[] bytes2 = new byte[byteBuffer2.limit()];
        byteBuffer1.get(bytes1);
        byteBuffer2.get(bytes2);
        buf3.put(bytes1);
        buf3.put(bytes2);

        return buf3;
    }

    public static ByteBuffer bytes2ByteBuffer(byte[]... bytes){

        int capacity = 0;

        for (byte[] arr : bytes){
            capacity += arr.length;
        }

        if (capacity < 0){
            capacity = 0;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);;

        for (byte[] arr : bytes){
            byteBuffer.put(arr);
        }

        byteBuffer.flip();

        return byteBuffer;
    }

    /**
     * 将字符串转换为Point类型
     * @param strpoint 需要转换的字符串
     * @return 返回Point
     * @throws Exception 转换失败异常
     */
    public static Point strToPoint(String strpoint) throws Exception{
        String[] strps = strpoint.trim().split("X");

        if (strps.length < 1){
            throw new Exception("转换失败");
        }

        int xPoint = Integer.valueOf(strps[0]);
        int yPoint = Integer.valueOf(strps[1]);

        Point point = new Point(xPoint, yPoint);

        return point;
    }

    public static byte[] bytesToMD5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bSrcStr = bytes;
        md.update(bSrcStr);

        byte[] digest = md.digest();

        return digest;
    }

    /**
     * 获取文件的MD5码
     * @param inputStream 输入流
     * @return 返回MD5码字符串
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static byte[] FileToMD5Bytes(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        InputStream in = inputStream;
        byte[] content = new byte[4096];
        int numRead = 0;

        while ((numRead = in.read(content)) > 0){
            md.update(content, 0, numRead);
        }

        in.close();
        byte[] digest = md.digest();
//        String result = new String(Hex.encodeHex(digest));

        return digest;
    }

    public static byte[] copyBytes(byte[] srcBytes, int off, int length){
        byte[] cpyBytes = new byte[length];

        for (int i = 0, count = srcBytes.length - off; i < count && i < length; ++i){
            cpyBytes[i] = srcBytes[off + i];
        }

        return cpyBytes;
    }

    /**
     * 获取文件后缀名
     * @param origin 文件路径信息
     * @return 返回后缀
     */
    public static String getSuffix(String origin){
        return GeneraUtil.getSuffix(origin, ".");
    }

    /**
     * 获取文件后缀，指定分隔符
     * @param origin 文件路径信息
     * @param separate 分隔符字符串
     * @return 返回文件后缀
     */
    public static String getSuffix(String origin, String separate){
        int lastIndex = origin.lastIndexOf(separate);

        if (lastIndex < 0){
            return "";
        }

        String suffix = origin.substring(lastIndex + 1);

        return suffix;
    }

    /**
     * 去除文件名后缀
     * @param origin 文件路径
     * @return 返回无后缀的字符串
     */
    public static String getNoSuffix(String origin){
        int lastIndex = origin.lastIndexOf(".");

        if (lastIndex < 0){
            return origin;
        }

        return origin.substring(0, lastIndex);
    }

    /**
     * 将原始图片按照drawSize,以宽度为标准进行比例缩放
     * @param srcImage 原始的图片
     * @param drawSize 绘制尺寸
     * @return 返回新创建的图片
     */
    public static BufferedImage transformLongImage(BufferedImage srcImage, Point drawSize){
        int newWidth = drawSize.x;

        int newHeight = newWidth * srcImage.getHeight() / srcImage.getWidth();

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, srcImage.getType());
        Graphics graphics = newImage.getGraphics();
        graphics.setColor(new Color(255, 255, 255));
        graphics.fillRect(0, 0, newWidth, newHeight);

        graphics.drawImage(srcImage, 0, 0, newWidth, newHeight, 0, 0, srcImage.getWidth(), srcImage.getHeight(), null);

        return newImage;
    }

    /**
     * 字节数组转16进制字符串
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b){
        return GeneraUtil.byte2HexStr(b, 0, b.length);
    }

    /**
     * 将字节数组转换为16进制字符串
     * @param b 字节数组
     * @param off 偏移
     * @param len 长度
     * @return
     */
    public static String byte2HexStr(byte[] b, int off, int len){
        String hexStr = "";
        String byteStr = "";

        for (int n = off, count = off + len; n < count; ++n){
            byteStr = (Integer.toHexString(b[n] & 0xff));
            if (byteStr.length() == 1){
                hexStr = hexStr + "0" + byteStr;
            }
            else{
                hexStr = hexStr + byteStr;
            }
        }

        return hexStr.toLowerCase();
    }


//    public static void main(String[] argc){
//        byte value = (byte)(0xff & Integer.valueOf("80", 16));
//
//        System.out.println(value);
//    }

    /**
     * byte[] 转 int，低位在前，高位在后
     * @param bs
     * @return
     */
    public static int byteArray2Int(byte... bs){
        byte[] byteArray = new byte[4];

        for (int i = 0; i < bs.length && i < 4; ++i){
            byteArray[i] = bs[i];
        }

        return byteArray[0] & 0xff |
                (byteArray[1] & 0xff) << 8 |
                (byteArray[2] & 0xff) << 16 |
                (byteArray[3] & 0xff) << 24;
    }

    public static byte[] Int2ByteArray(int iVal){
        byte[] bVals = new byte[4];

        bVals[0] = (byte)((iVal >> 24) & 0xff);
        bVals[1] = (byte)((iVal >> 16) & 0xff);
        bVals[2] = (byte)((iVal >> 8) & 0xff);
        bVals[3] = (byte)((iVal >> 0) & 0xff);

        return bVals;
    }

    public static byte[] long2ByteArray(long lVal){
        byte[] bVals = new byte[8];

        bVals[0] = (byte)((lVal >> 56) & 0xff);
        bVals[1] = (byte)((lVal >> 48) & 0xff);
        bVals[2] = (byte)((lVal >> 40) & 0xff);
        bVals[3] = (byte)((lVal >> 32) & 0xff);
        bVals[4] = (byte)((lVal >> 24) & 0xff);
        bVals[5] = (byte)((lVal >> 16) & 0xff);
        bVals[6] = (byte)((lVal >> 8) & 0xff);
        bVals[7] = (byte)((lVal >> 0) & 0xff);

        return bVals;
    }
}
