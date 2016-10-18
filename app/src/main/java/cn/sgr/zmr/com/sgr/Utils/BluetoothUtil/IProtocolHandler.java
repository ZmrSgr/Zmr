package cn.sgr.zmr.com.sgr.Utils.BluetoothUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/8.
 */
public interface IProtocolHandler {

    // reset the protocol handler the state machine
    void reset();

    /*
       process the receiving data (original data from BLE) and merge it with previous data , util
       gathered one complete frame.
       And then, decode the frame and output the command data (application data).

       parameter:
         data : the ble received data
         return : the command data. if didn't gather one complete frame return null.
     */
    ArrayList<byte[]> mergePacket(final byte[] data);

    /*
       Code the command data to a frame data.
     */
    byte[] sendData(final byte[] cmdData);
}
