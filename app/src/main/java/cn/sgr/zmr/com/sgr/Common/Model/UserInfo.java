package cn.sgr.zmr.com.sgr.Common.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.bean.entity.User;

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
    private static final String KEY_USER_SID = "sid";
    private static final String KEY_USER_NICKNAME = "nickname";
    private static final String KEY_USER_AVATAR = "avatar";

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
        editor.putString(KEY_USER_SID,"");
        editor.putString(KEY_USER_NICKNAME, "");
        editor.putString(KEY_USER_AVATAR,"");
    }
    public void saveUserInfo(User user) {
        editor.putString(KEY_USER_PHONE, user.getPhone()).commit();
//        editor.putString(KEY_USER_PWD, user.getPassword()).commit();
        editor.putString(KEY_USER_USERID, user.getUid().toString()).commit();
        if( user.getThird_id()!=null&& !user.getThird_id().isEmpty()){//只有登录注册的时候才会生成这个sid
            editor.putString(KEY_USER_CLIENTID, user.getThird_id()).commit();
        }


        editor.putString(KEY_USER_NICKNAME, user.getNickname()).commit();
        editor.putString(KEY_USER_AVATAR, user.getAvatar()).commit();
    }

    //用户id
    public String getMyUserId() {
        return Account.getString(KEY_USER_USERID, "");
    }
   //已经登录
    public String geThird_id() {
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
    public void setSid(String sid){
        if( sid!=null&& !sid.isEmpty()){//只有登录注册的时候才会生成这个sid
            editor.putString(KEY_USER_SID,sid).commit();
        }
    }

    public String geSid() {
        return Account.getString(KEY_USER_SID, "");
    }
    public String getNickName() {
        return Account.getString(KEY_USER_NICKNAME, "");
    }
    public String getAvatar() {
        return Account.getString(KEY_USER_AVATAR, "");
    }
}
