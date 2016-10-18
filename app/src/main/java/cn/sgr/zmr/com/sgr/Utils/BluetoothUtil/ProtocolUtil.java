package cn.sgr.zmr.com.sgr.Utils.BluetoothUtil;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/3/8.
 */
public class ProtocolUtil {
    public static int littleEndianBytesToInt(final byte[] dat, int startPos, int sizeOfInt) {
        int val = 0;

        if (sizeOfInt <= 1) {
            val = (int) dat[startPos] & 0xff;
            return val;
        }

        for (int i = sizeOfInt; i != 0; i--) {
            int tmp = (int) dat[startPos + i - 1] & 0xff;
            val <<= 8;
            val += tmp;
        }

        return val;
    }

    public static long littleEndianBytesToLong(final byte[] dat, int startPos, int sizeOfInt) {
        long val = 0;

        if (sizeOfInt <= 1) {
            val = (int) dat[startPos] & 0xff;
            return val;
        }

        for (int i = sizeOfInt; i != 0; i--) {
            int tmp = (int) dat[startPos + i - 1] & 0xff;
            val <<= 8;
            val += tmp;
        }

        return val;
    }

    public static void littleEndianIntToBytes(byte[] dat, int startPos, int sizeOfInt, int value) {
        int val = 0;

        if (sizeOfInt <= 1) {
            dat[startPos] = (byte) (value & 0xff);
            return;
        }

        for (int i = 0; i < sizeOfInt; i++) {
            dat[startPos + i] = (byte) (value & 0xff);
            value >>= 8;
        }
    }

    public static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);

        return bb.array();
    }

    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }
}
