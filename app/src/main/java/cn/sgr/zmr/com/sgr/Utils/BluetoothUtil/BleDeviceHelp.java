package cn.sgr.zmr.com.sgr.Utils.BluetoothUtil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

/**
 * Created by Administrator on 2016/4/6.
 */
public class BleDeviceHelp {
    private Context context;
    private String TAG = "Ble address";

    // ble device uuid
    public static final UUID CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID LEx_BAND_SERVICE_UUID = UUID.fromString("0000fc20-0000-1000-8000-00805f9b34fb");
    public static final UUID LEx_RX_CHAR_UUID = UUID.fromString("0000fc21-0000-1000-8000-00805f9b34fb");
    public static final UUID LEx_TX_CHAR_UUID = UUID.fromString("0000fc22-0000-1000-8000-00805f9b34fb");

    private UUID mBandServiceUUID = LEx_BAND_SERVICE_UUID;
    private UUID mRxCharUUID = LEx_RX_CHAR_UUID;
    private UUID mTxCharUUID = LEx_TX_CHAR_UUID;

    public String mBluetoothDeviceAddress = null;
    private BluetoothGatt mBluetoothGatt = null;
    private Queue<byte[]> mSendBuffer = new LinkedList<byte[]>();
    private int repeatConnTime = 5;//重连5次

    public final static boolean mAutoConnEnable = true; // 是否开启回连功能。开启后，如果出现异常断开，那么APP会尝试去连接设备。

    private BleReceiver bleReceiver;
    private IProtocolHandler mProtocolHandler = null;

    public BleDeviceHelp(Context context, BleReceiver bleReceiver) {
        this.context = context;
        this.bleReceiver = bleReceiver;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void enableTXNotification() {
        if (mBluetoothGatt == null) {
            Log.w(TAG, "enableTXNotification: mBluetoothGatt == null");
            return;
        }

        BluetoothGattCharacteristic TxChar = null;
        BluetoothGattService BandService = mBluetoothGatt.getService(mBandServiceUUID);
        if (BandService == null) {
            Log.w(TAG, "enableTXNotification Tx service not found!");

            return;
        } else {
            TxChar = BandService.getCharacteristic(mTxCharUUID);
        }

        if (TxChar == null) {
            Log.w(TAG, "Tx charateristic not found!");

            return;
        }
        mBluetoothGatt.setCharacteristicNotification(TxChar, true);

        BluetoothGattDescriptor descriptor = TxChar.getDescriptor(CCCD);
        if (descriptor != null) {
            boolean retval;
            retval = descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            Log.d(TAG, "descriptor.setValue = " + retval);

            retval = mBluetoothGatt.writeDescriptor(descriptor);
            Log.d(TAG, "gatt.writeDescriptor = " + retval);
        }

    }

    private void writeRXCharacteristic(byte[] value) {
        if (mBluetoothGatt == null) return;

        BluetoothGattService RxService = mBluetoothGatt.getService(mBandServiceUUID);
        if (RxService == null) {
            Log.w(TAG, "Rx service not found!");

            return;
        }
        BluetoothGattCharacteristic RxChar = RxService.getCharacteristic(mRxCharUUID);
        if (RxChar == null) {
            Log.w(TAG, "Rx charateristic not found!");

            return;
        }
        RxChar.setValue(value);
        boolean status = mBluetoothGatt.writeCharacteristic(RxChar);

        Log.d(TAG, "write TXchar - status=" + status);

    }

    public boolean sendCmd(ICmdModel cmd) {
        return sendCmd(cmd.toCmdBytes());
    }

    private boolean sendCmd(byte[] cmd) {

        if (mConnectionState == CON_STATE_CLOSED) return false;

        if (getConnectionStatus() != CON_STATE_CONNECTED && mConnectionState != CON_STATE_ENABLING) {
            Log.e(TAG, "sendCmd: Not already for sending.");
            return false;
        }

        if (mProtocolHandler != null) {
//            Log.i(TAG, "SendCmd: " + IWxProtocol.HexConver.byte2HexStr(cmd, cmd.length));

            byte[] out = mProtocolHandler.sendData(cmd);
            bufferedSend(out);

        } else {
            Log.e(TAG, "send Cmd error. protocol handler is null.");
            bufferedSend(cmd);
        }
        return true;
    }

    void bufferedSend(byte[] dat) {

        int max = dat.length;
        final int FRAME_SIZE = 20;
        if (max > FRAME_SIZE) {
            int i = 0;
            byte[] tmp = null;
            for (; i < (max - FRAME_SIZE); i += FRAME_SIZE) {
                tmp = new byte[FRAME_SIZE];

                System.arraycopy(dat, i, tmp, 0, FRAME_SIZE);
                mSendBuffer.offer(tmp);
            }

            tmp = new byte[max - i];
            System.arraycopy(dat, i, tmp, 0, max - i);
            mSendBuffer.offer(tmp);
            tmp = null;
        } else {
            mSendBuffer.offer(dat);
        }

        //if (triggleSend) {
        //    Log.w(TAG, "BufferedSendData: Triggle.");
        bufferedSendNext();
        //}
    }

    void bufferedSendNext() {
        if (mSendBuffer.size() > 0) {
            byte[] tmp = mSendBuffer.peek();
            writeRXCharacteristic(tmp);
        }
    }

    public boolean disconnect() {

        if (mConnectionState == CON_STATE_CLOSED) return false;

        if (mBluetoothGatt == null) {
            Log.w(TAG, "disconnect(): BluetoothAdapter not initialized");

            return false;
        }
        mBluetoothGatt.disconnect();
        mBluetoothGatt.close();
        mAutoConnState = false;
        changeConnectionStatus(CON_STATE_DISCONNECTED);
        mBluetoothGatt.close();
        return true;
    }

    public void close() {

        mSendBuffer.clear();

        if (mBluetoothGatt == null) {
            return;
        }
        Log.w(TAG, "mBluetoothGatt closed..." + mBluetoothDeviceAddress);

        if (CON_STATE_DISCONNECTED == getConnectionStatus()) {
            refreshDeviceCache(mBluetoothGatt, true);
        }
        mBluetoothGatt.disconnect();
        mBluetoothGatt.close();
        mAutoConnState = false;
        mBandServiceUUID = LEx_BAND_SERVICE_UUID;
        mRxCharUUID = LEx_RX_CHAR_UUID;
        mTxCharUUID = LEx_TX_CHAR_UUID;

    }

    /**
     * Clears the device cache. After uploading new firmware the DFU target will have other services than before.
     *
     * @param gatt  the GATT device to be refreshed
     * @param force <code>true</code> to force the refresh
     */
    private void refreshDeviceCache(final BluetoothGatt gatt, final boolean force) {
        /*
         * If the device is bonded this is up to the Service Changed characteristic to notify Android that the services has changed.
		 * There is no need for this trick in that case.
		 * If not bonded, the Android should not keep the services cached when the Service Changed characteristic is present in the target device database.
		 * However, due to the Android bug (still exists in Android 5.0.1), it is keeping them anyway and the only way to clear services is by using this hidden refresh method.
		 */
        if (force || gatt.getDevice().getBondState() == BluetoothDevice.BOND_NONE) {

            Log.d(TAG, "refreshDeviceCache: gatt.refresh() (hidden)");
            /*
             * There is a refresh() method in BluetoothGatt class but for now it's hidden. We will call it using reflections.
			 */
            try {
                final Method refresh = gatt.getClass().getMethod("refresh");
                if (refresh != null) {
                    final boolean success = (Boolean) refresh.invoke(gatt);
                    Log.d(TAG, "refreshDeviceCache: Refreshing result: " + success);

                }
            } catch (Exception e) {
                Log.w(TAG, "refreshDeviceCache: An exception occurred while refreshing device", e);

            }
        }

    }

    public boolean connect() {


        if (context == null) {
            return false;
        }

        if (mBluetoothDeviceAddress == null || mBluetoothDeviceAddress.compareTo("") == 0) {
            return false;
        }

//        close();//clean All resourse;
        disconnect();
        mAutoConnState = mAutoConnEnable;
        if (reConnection()) return false;

        Log.d(TAG, "Trying to create a new connection.");

        return true;
    }

    private boolean reConnection() {
        BluetoothManager bleManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bleAdapter = bleManager.getAdapter();
        BluetoothDevice device = bleAdapter.getRemoteDevice(mBluetoothDeviceAddress);
        if (device == null) {
            return true;
        }
        TAG = device.getName();

        changeConnectionStatus(CON_STATE_CONNECTING);
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.


        if (myGattCallBack == null) {
            myGattCallBack = new MyGattCallBack();
        }
        mBluetoothGatt = device.connectGatt(context, false, myGattCallBack);
        return false;
    }

    public boolean mAutoConnState = true;


    private MyGattCallBack myGattCallBack = null;

    private class MyGattCallBack extends BluetoothGattCallback {
        ////////////////////////////////////////////////////////////////////////////////////////////////callbacks for gatt
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {//已经连接
                if (mBluetoothGatt.equals(gatt)) {
                    changeConnectionStatus(CON_STATE_DISCONNECTED);
                    // default to use LEx protocol
                    mBandServiceUUID = LEx_BAND_SERVICE_UUID;
                    mRxCharUUID = LEx_RX_CHAR_UUID;
                    mTxCharUUID = LEx_TX_CHAR_UUID;
                    Log.i(TAG, "Connected to GATT server. Attempting to start service discovery:" +
                            mBluetoothGatt.discoverServices() + "...." + mBluetoothGatt.getDevice().getName());

                } else {
                    Log.w(TAG, "Unexpect connect status, GATT:" + gatt + " Dev:" + gatt.getDevice().getName());
                }

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {//断开连接
                if (mBluetoothGatt.equals(gatt)) {
                    changeConnectionStatus(CON_STATE_DISCONNECTED);
                    Log.i(TAG, "Disconnected from GATT server..." + gatt.getDevice().getName());

                    if (mAutoConnState) {
                        gatt.disconnect();
                        gatt.close();
                        try {
                            Thread.sleep(2000);
                            if (repeatConnTime >= 0) {
                                reConnection();
                                repeatConnTime--;
                            } else {
                                close();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        changeConnectionStatus(CON_STATE_RECONNECTING);
                        Log.d(TAG, "trying to reconnect device..." + mBluetoothGatt.getDevice().getName());
                    } else {
                        refreshDeviceCache(mBluetoothGatt, true);
                    }
                } else {
                    Log.w(TAG, "Unexpect disconnect status, GATT:" + gatt + " Dev:" + gatt.getDevice().getName());
                }
            } else if (newState == BluetoothProfile.STATE_CONNECTING) {//正在连接
                Log.i(TAG, "Connecting: " + gatt.getDevice().getName());

            } else {
                Log.w(TAG, "Unkown ConnectStateChanged: " + gatt);
            }
        }


        /**
         * 服务被发现
         *
         * @param gatt
         * @param status
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (mBluetoothGatt.equals(gatt)) {
//                    Log.w(TAG, "onServicesDiscovered = " + mBluetoothGatt.getDevice().getName());

                    changeConnectionStatus(CON_STATE_ENABLING);
                    mProtocolHandler = new LExProtocol();
                    enableTXNotification();
                } else {
                    Log.w(TAG, "Unexpect service discovering. gatt:" + gatt + " Dev:" + gatt.getDevice().getName());
                }
            } else {
                Log.w(TAG, "onServicesDiscovered received status: " + status);

            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.w(TAG, "onCharacteristicRead: " + characteristic + "..." + gatt.getDevice().getName());
            }
        }


        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {

            if (!mBluetoothGatt.equals(gatt)) {
                Log.w(TAG, "onCharacteristicChanged(): Unexpect data. dev:" + mBluetoothGatt.getDevice().getName());
            }

            Log.w(TAG, "onCharacteristicChanged(): uuid:" + characteristic.getUuid() + gatt.getDevice().getName());
            if (characteristic.getUuid().equals(LEx_TX_CHAR_UUID)) {
                byte[] data = characteristic.getValue();

                ArrayList<byte[]> cmds = mProtocolHandler.mergePacket(data);
                if (cmds != null) {
                    for (byte[] cmd : cmds) {
                        bleReceiver.getCmdData(cmd);
                        getRssiVal();

                    }
                }
            } else {
                Log.w(TAG, "onCharacteristicChanged(): uuid:" + characteristic.getUuid() + gatt.getDevice().getName());
            }
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            if(rssi<-92)
            bleReceiver.isDistantDisconnect();
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);

            if (descriptor.getUuid().equals(CCCD)) {
                Log.d(TAG, "onDescriptorWrite status: " + status + "..." + gatt.getDevice().getName());
                if (descriptor.getValue().equals(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)) {
                    changeConnectionStatus(CON_STATE_CONNECTED);
                } else {
                    Log.w(TAG, "onDescriptorWrite error.It seems that enable notify fail..." + gatt.getDevice().getName());
                    disconnect();
                }
            } else {

                Log.w(TAG, "Unexpect onDescriptorWrite : " + descriptor + "..." + gatt.getDevice().getName());
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);

            if (!mBluetoothGatt.equals(gatt)) {
                Log.w(TAG, "onCharacteristicWrite(): Unexpect data. dev:" + characteristic);
                return;
            }

            if (characteristic.getUuid().equals(LEx_RX_CHAR_UUID)) {
                mSendBuffer.poll();
                bufferedSendNext();
            } else {
                Log.w(gatt.getDevice().getName(), "onCharacteristicChanged(): uuid:" + characteristic.getUuid());
            }
        }
    }

    private void changeConnectionStatus(int newStatus) {

        if (mConnectionState != newStatus) {
            mConnectionState = newStatus;
        }
        bleReceiver.getConnState(mConnectionState);
    }

    private int getConnectionStatus() {
        switch (mConnectionState) {
            case CON_STATE_DISCONNECTED:
                return CON_STATE_DISCONNECTED;

            case CON_STATE_CONNECTED:
                return CON_STATE_CONNECTED;

            case CON_STATE_CONNECTING:
            case CON_STATE_RECONNECTING:
            case CON_STATE_DISCOVERING:
            case CON_STATE_ENABLING:
                return CON_STATE_CONNECTING;
        }
        return mConnectionState;
    }

    public interface BleReceiver {
        void getConnState(int mConnectionState);

        void getCmdData(byte[] characteristic);

        void isDistantDisconnect();
    }

    public boolean getRssiVal() {
        if (mBluetoothGatt == null)
            return false;
        return mBluetoothGatt.readRemoteRssi();

    }

    protected int mConnectionState = CON_STATE_DISCONNECTED;
    public static final int CON_STATE_RECONNECTING = 1;//重新连接状态
    public static final int CON_STATE_DISCOVERING = 3;// 发现状态
    public static final int CON_STATE_ENABLING = 4;//启用状态
    public static final int CON_STATE_DISCONNECTED = 0; //disconnect 断开连接状态
    public static final int CON_STATE_CONNECTING = 2;   //connecting 连接中状态
    public static final int CON_STATE_CONNECTED = 5;    //connected  己连接状态
    public static final int CON_STATE_FATAL_ERROR = 6;  //fatal error 严重错误
    public static final int CON_STATE_CLOSED = 7;        //device closed. never can be used anymore.
}
