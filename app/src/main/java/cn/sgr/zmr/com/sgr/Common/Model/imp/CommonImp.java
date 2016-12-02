package cn.sgr.zmr.com.sgr.Common.Model.imp;

import android.content.Context;

import com.bean.entity.User;

import java.security.NoSuchAlgorithmException;

import cn.sgr.zmr.com.sgr.Base.BaseModel;
import cn.sgr.zmr.com.sgr.Common.Model.interfaces.CommonInterface;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.http.RequestParams;
import cn.sgr.zmr.com.sgr.Utils.util.Constant;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class CommonImp extends BaseModel implements CommonInterface{
    public CommonImp() {
    }


    //登录操作
    @Override
    public void Login(Context context, User user, HttpRequestCallback callback) {
        RequestParams params = new RequestParams();
        params.put("mobile_phone" ,user.getPhone());
        try {
            params.put("password" , Utils.Mymd5(user.getPassword().toString()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("context"+context);
        sendPostRequest(context,Constant.LOGIN, params, callback);
    }
    // 注册操作
    @Override
    public void Register(Context context, User user, HttpRequestCallback callback) {
        RequestParams params = new RequestParams();
        params.put("mobile_phone" ,user.getPhone());
        try {
            params.put("password" , Utils.Mymd5(user.getPassword().toString()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        params.put("nickname" ,user.getNickname());
        params.put("platform" ,"android");
        sendPostRequest(context,Constant.REGISTER, params, callback);

    }

    @Override
    public void Third_Login(Context context, String third_id, String nickname, String avatar, HttpRequestCallback callback) {
        RequestParams params = new RequestParams();
        params.put("third_id" ,third_id);
        params.put("nickname" ,nickname);
        params.put("avatar" ,avatar);
        params.put("platform" ,"android");
        sendPostRequest(context,Constant.THIRD_LOGIN, params, callback);
    }

    //保存到或者更新本地的数据库
    private  void SaveUser(Context context,User user){
        new DaoCacheManage(context).updateUser(user);

    }

}
