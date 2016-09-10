package cn.sgr.zmr.com.sgr.Common.Model;

import android.content.Context;
import android.content.SharedPreferences;

import cn.sgr.zmr.com.sgr.R;

/**
 * Created by 沈国荣 on 2016/9/10 0010.
 */
public class UserInfo {

    private static UserInfo sSpfHelper;
    public SharedPreferences Account;
    public SharedPreferences.Editor editor;
    private static final String KEY_USER_INFO = "userinfo";
    private static final String KEY_USER_PHONE = "phone";
    private static final String KEY_USER_PWD = "pwd";
    private static final String KEY_USER_USERID = "userid";
    private static final String KEY_USER_CLIENTID = "clientid";

    private UserInfo(Context ct) {
        Account = ct.getSharedPreferences(ct.getString(R.string.app_name), 0);
        editor = Account.edit();
    }

    public static UserInfo getInstance(Context ct) {
        if (sSpfHelper == null) {
            sSpfHelper = new UserInfo(ct);
        }
        return sSpfHelper;
    }

    public void clearUserInfo() {
        editor.putString(KEY_USER_PHONE, "").commit();
        editor.putString(KEY_USER_PWD, "").commit();
        editor.putString(KEY_USER_USERID, "").commit();
        editor.putString(KEY_USER_CLIENTID, "").commit();
    }

    public void saveUserInfo(String phone, String pwd, String userId, String clientId) {
        editor.putString(KEY_USER_PHONE, phone).commit();
        editor.putString(KEY_USER_PWD, pwd).commit();
        editor.putString(KEY_USER_USERID, userId).commit();
        editor.putString(KEY_USER_CLIENTID, clientId).commit();
    }

    public String getMyUserId() {
        return Account.getString(KEY_USER_USERID, "");
    }
   //已经登录
    public String getMyClientId() {
        return Account.getString(KEY_USER_CLIENTID, "");
    }

    public boolean hasSignIn() {
        return getMyUserId() != null && getMyUserId().length() > 0;
    }

    public String getMyPhone() {
        return Account.getString(KEY_USER_PHONE, "");
    }

    public String getMyPwd() {
        return Account.getString(KEY_USER_PWD, "");
    }
}
