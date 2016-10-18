package cn.sgr.zmr.com.sgr.Utils.BluetoothUtil;

import cn.sgr.zmr.com.sgr.R;

/**
 * Created by Administrator on 2016/3/8.
 */

public abstract class ICmdModel {

    static public int cmdIdFromBytes(final byte[] cmd, int startPos) {
        int id = (int) cmd[startPos] & 0xFF;
        id <<= 8;
        id += (int) cmd[startPos + 1] & 0xFF;
        return id;
    }

    protected byte[] allocCmdBytes(int paramSize) {
        byte[] cmd = new byte[2 + paramSize];
        int id = this.getCmdModelID();
        cmd[0] = (byte) ((id >> 8) & 0xFF);
        cmd[1] = (byte) ((id >> 0) & 0xFF);
        return cmd;
    }

    public abstract int getCmdModelID();

    /*
       Set the model useing cmd bytes which received.
       parameter:
         data : the received cmd byte stream.
     */
    public void fromCmdBytes(final byte[] cmd) {
        assert (false);
    }

    /*
       translate the model to cmd byte stream.
     */
    public byte[] toCmdBytes() {
        assert (false);
        return null;
    }


    //请求体温
    public static class CmTxRequestTemperature extends ICmdModel {
        public final static int ID = 0x1901;

        @Override
        public int getCmdModelID() {
            return ID;
        }

        @Override
        public byte[] toCmdBytes() {
            return allocCmdBytes(0);
        }
    }

    //请求体温开
    public static class CmTxRequestTemperatureOn extends ICmdModel {
        public final static int ID = 0x1902;
        final static int ON = 1;

        @Override
        public int getCmdModelID() {
            return ID;
        }

        @Override
        public byte[] toCmdBytes() {
            byte[] cmd = allocCmdBytes(1);
            ProtocolUtil.littleEndianIntToBytes(cmd, 2, 1, ON);
            return cmd;
        }
    }

    //请求体温开
    public static class CmTxRequestTemperatureOff extends ICmdModel {
        public final static int ID = 0x1902;
        final static int OFF = 2;

        @Override
        public int getCmdModelID() {
            return ID;
        }

        @Override
        public byte[] toCmdBytes() {
            byte[] cmd = allocCmdBytes(1);
            ProtocolUtil.littleEndianIntToBytes(cmd, 2, 1, OFF);
            return cmd;
        }
    }

    //解析收到的体温值
    public static class CmRxTemperature extends ICmdModel {
        public final static int ID = 0x1981;
        public int mPercent;
        public int mMode;

        @Override
        public int getCmdModelID() {
            return ID;
        }

        @Override
        public void fromCmdBytes(byte[] cmd) {
            mMode = ProtocolUtil.littleEndianBytesToInt(cmd, 2, 1);
            mPercent = ProtocolUtil.littleEndianBytesToInt(cmd, 3, 4);
            System.out.println();
        }

//        + "°c"

        @Override
        public String toString() {
            return String.valueOf((double) mPercent / 100) ;
        }
    }

    //请求电量信息
    public static class CmTxRequestBatteryInfo extends ICmdModel {
        public final static int ID = 0x1602;

        @Override
        public int getCmdModelID() {
            return ID;
        }

        @Override
        public byte[] toCmdBytes() {
            return allocCmdBytes(0);
        }
    }

    //解析收到的电量值
    public static class CmRxBatteryInfo extends ICmdModel {
        public final static int ID = 0x1681;
        public final static int MODE_CHARGING = 1;
        public final static int MODE_FULL = 2;
        public int mPercent;
        public int mMode;

        @Override
        public int getCmdModelID() {
            return ID;
        }

        @Override
        public void fromCmdBytes(byte[] cmd) {
            mMode = ProtocolUtil.littleEndianBytesToInt(cmd, 2, 1);
            mPercent = ProtocolUtil.littleEndianBytesToInt(cmd, 3, 1);
        }

        @Override
        public String toString() {
            String mode;
            switch (mMode) {
                case MODE_CHARGING:
                    mode ="";
                    break;
                case MODE_FULL:
                    mode = "";
                    break;
                default:
                    mode = "";
            }
            return String.format("%d%% %s", mPercent, mode);
        }
    }

}

