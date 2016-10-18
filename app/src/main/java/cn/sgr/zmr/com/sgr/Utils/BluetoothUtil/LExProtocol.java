package cn.sgr.zmr.com.sgr.Utils.BluetoothUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/13.
 */
public class LExProtocol implements IProtocolHandler {
    private int sIdx = 0;
    private int sLen = 0;
    private byte[] sBuf = new byte[256];
    private byte[] sCmd = new byte[128];
    private byte[] sOrg = new byte[128];

    @Override
    public void reset() {
        sIdx = 0;
        sLen = 0;
    }

    @Override
    public ArrayList<byte[]> mergePacket(byte[] data) {
        ArrayList<byte[]> cmds = null;
        int i;
        int len = data.length;
        for (i = 0; i < len; i++) {
            byte ch = data[i];

            if (((int) ch & 0xff) > 0x80) {
                sIdx = 0;
                sLen = (int) ch & 0x7F;
                sLen += 2;
            }
            sBuf[sIdx++] = ch;
            if (sIdx >= sLen && sLen > 2) {
                if (checkSumByte(sBuf, 2, sLen - 2) == sBuf[1]) {
                    int cmdLen = 0;

                    System.arraycopy(sBuf, 2, sOrg, 0, sLen - 2);
                    cmdLen = unpack_base127(sCmd, sOrg, sLen - 2);

                    byte[] cmd = new byte[cmdLen];
                    System.arraycopy(sCmd, 0, cmd, 0, cmdLen);
                    if (cmds == null) {
                        cmds = new ArrayList<byte[]>();
                    }

                    cmds.add(cmd);
                    reset();
                } else {
                    //printf("OnRcvIWxFrame(): recived err frame.\r\n");
                }
            }
        }
        return cmds;
    }

    @Override
    public byte[] sendData(byte[] cmdData) {
        if (cmdData.length > (127 / 8 * 7)) {
            return new byte[0];
        }

        byte[] ptCmdData127 = new byte[128];

        byte packLen = (byte) pack_base127(ptCmdData127, cmdData, cmdData.length);

        byte[] ptBandFrame = new byte[packLen + 2];
        ptBandFrame[0] = (byte) (0x80 | packLen);
        ptBandFrame[1] = checkSumByte(ptCmdData127, 0, packLen);

        System.arraycopy(ptCmdData127, 0, ptBandFrame, 2, packLen);

        return ptBandFrame;
    }

    byte checkSumByte(final byte[] cmd, int startPos, int len) {
        byte checkSum = 0;
        for (int i = 0; i < len; i++) checkSum += cmd[startPos + i];
        checkSum = (byte) (((~checkSum) + 1) & 0x7F);

        return checkSum;
    }

    int pack_base127(byte[] pBuffer, final byte[] pData, int size) {
        int count = 0; // 打包后的字节数
        int posToSave = 0;
        int posToProc = 0;

        while (size > 0) {
            int msb = 0; // 收集最高高位

            int posToSaveMSB = posToSave++;  // byte* pToSaveMSB = pBuffer++;
            int toProcCount = size > 7 ? 7 : size;
            size -= toProcCount;
            count += (toProcCount + 1); // 要包含msb字节
            for (; toProcCount != 0; toProcCount--) {
                int ch = (int) pData[posToProc++] & 0xff;//byte ch = *pData++;
                msb |= ch & 0x80;
                pBuffer[posToSave++] = (byte) (ch & 0x7F);//*pBuffer++ = ch & 0x7F;
                msb >>= 1;
            }
            pBuffer[posToSaveMSB] = (byte) (msb & 0x7f);//*pToSaveMSB = msb; // 保存最高位
        }

        return count;
    }

    int unpack_base127(byte[] pData, final byte[] pBuffer, int size) {
        int count = 0; // 完成解包后的字节数
        int posBufferForDataTail;//const byte* pBufferForDataTail;
        int posDataTail;//byte* pDataTail;
        int posBuffer = 0;
        int posData = 0;
        int toProcCount;

        while (size > 0) {
            int msb = (int) pBuffer[posBuffer++] & 0xff;//byte msb = *pBuffer++;
            size -= 1; // 要排除msb字节
            toProcCount = size > 7 ? 7 : size;
            size -= toProcCount;
            count += toProcCount;
            posBufferForDataTail = posBuffer + toProcCount; //pBufferForDataTail = pBuffer + toProcCount;
            posDataTail = posData + toProcCount; //pDataTail = pData + toProcCount;
            posData += toProcCount; //pData += toProcCount;
            posBuffer += toProcCount; //pBuffer += toProcCount;
            for (; toProcCount != 0; toProcCount--) {
                int dat = (int) pBuffer[--posBufferForDataTail] & 0xff; //byte dat = *--pBufferForDataTail;

                msb <<= 1;
                pData[--posDataTail] = (byte) (dat | (msb & 0x80)); //*--pDataTail = dat | (msb & 0x80);
            }
        }

        return count;
    }


}

