package cn.sgr.zmr.com.sgr.Common.Register;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bean.entity.User;

import cn.sgr.zmr.com.sgr.Common.Login.LoginActivity;
import cn.sgr.zmr.com.sgr.Common.MainActivity;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Common.Model.UserResult;
import cn.sgr.zmr.com.sgr.Common.Model.imp.CommonImp;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.Result;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class Register_Presenter implements Register_Contract.Presenter {
    @NonNull
    private  CommonImp registModel;

    @NonNull
    private final Register_Contract.View registerView;
    Context context;

    public Register_Presenter(Context contexts,@NonNull Register_Contract.View registerView) {

        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }
    @Override
    public void saveUser(User user) {

    }

    @Override
    public void doRegister(User user) {
        registModel = new CommonImp();
        registModel.Register(context, user, new HttpRequestCallback<Result<UserResult>>() {
            @Override
            public void onStart() {
                registerView.showProgressDialog();
            }

            @Override
            public void onFinish() {
                registerView.cancelProgressDialog();
            }

            @Override
            public void onResponse(Result<UserResult> userResult) {
                if(userResult.msg.equals("1")){//正常访问
                    UserInfo.getInstance(context).setSid(userResult.data.sid);//保存sid
                    registerView.showSuccessLogin(userResult.data.user);
                }else{
                    Toast.makeText(context,userResult.desc,Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call call, HttpException e) {
//                Toast.makeText(context,"注册失败！服务器错误",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void start() {

    }
}
