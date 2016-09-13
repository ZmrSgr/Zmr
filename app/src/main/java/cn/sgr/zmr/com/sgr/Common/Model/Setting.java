package cn.sgr.zmr.com.sgr.Common.Model;

import android.content.Context;
import android.content.SharedPreferences;

import cn.sgr.zmr.com.sgr.R;

/**
 * Created by 沈国荣 on 2016/9/12 0012.
 */
public class Setting {

    private static Setting setting;
    public SharedPreferences Account;
    public SharedPreferences.Editor editor;


    private static final String KEY_SETTING = "key_setting";//是否防丢失，false，表示不防丢失
    private static final String KEY_IS_LOSE = "is_lose";//是否防丢失，0，表示不防丢失
    private static final String KEY_ALARM = "alarm";//0表示不报警，1表示报警
    private static final String KEY_ALARM_IS_VOICE = "alarm_voice";//0表示不用语音，1表示语音

    private static final String KEY_ALARM_POSITION_VOICE = "alarm_voice_position";//闹钟声音的位置
    private static final String KEY_ALARM_IS_SHOCK = "alarm_shock";//0表示不报警，1表示报警
    private static final String KEY_ALARM_TEMP = "alarm_temp";//报警温度

    private Setting(Context ct) {
        Account = ct.getSharedPreferences(KEY_SETTING, 0);
        editor = Account.edit();
    }

    public static Setting getInstance(Context ct) {
        if (setting == null) {
            setting = new Setting(ct);
        }
        return setting;
    }

    //默认设置
    public void DefaultSetting() {
        editor.putString(KEY_IS_LOSE, "0").commit();//不防丢失
        editor.putString(KEY_ALARM, "0").commit();//不报警
        editor.putString(KEY_ALARM_IS_VOICE, "0").commit();
        editor.putString(KEY_ALARM_IS_SHOCK, "0").commit();
        editor.putString(KEY_ALARM_TEMP, "37.6").commit();
        editor.putInt(KEY_ALARM_POSITION_VOICE, 0).commit();
    }

   //获得声音位置
    public int getVoicePosition() {
        return Account.getInt(KEY_ALARM_POSITION_VOICE, 0);
    }
    //设置声音位置
    public void setVoicePosition(int position) {
        editor.putInt(KEY_ALARM_POSITION_VOICE, position).commit();
    }


    public String getTemp() {
        return Account.getString(KEY_ALARM_TEMP, "37.6");
    }
   //设置温度
    public void setTemp(String temp) {
        editor.putString(KEY_ALARM_TEMP, temp).commit();
    }

    public void setShock(String isShock) {
        editor.putString(KEY_ALARM_IS_SHOCK, isShock).commit();
    }

    public void setVoice(String voice) {
        editor.putString(KEY_ALARM_IS_VOICE, voice).commit();
    }

    public void setAlarm(String Alarm) {
        editor.putString(KEY_ALARM, Alarm).commit();
    }

    public void setLose(String lose) {
        editor.putString(KEY_IS_LOSE, lose).commit();
    }

    public boolean IsLose() {
        return  Account.getString(KEY_IS_LOSE, "0").equals("1");
    }
    public boolean IsAlarm() {
        return  Account.getString(KEY_ALARM, "0").equals("1");
    }
    public boolean IsShock() {
        return  Account.getString(KEY_ALARM_IS_SHOCK, "0").equals("1");
    }
    public boolean IsVocie() {
        return  Account.getString(KEY_ALARM_IS_VOICE, "0").equals("1");
    }



}
