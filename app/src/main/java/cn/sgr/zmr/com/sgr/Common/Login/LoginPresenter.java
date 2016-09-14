package cn.sgr.zmr.com.sgr.Common.Login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bean.entity.User;

import cn.sgr.zmr.com.sgr.Common.Model.imp.CommonImp;

/**
 * Created by 沈国荣 on 2016/8/19 0019.
 */
public class LoginPresenter implements LoginContract.Presenter {


    @NonNull
    private final CommonImp loginModel;

    @NonNull
    private final LoginContract.View loginView;
    Context context;
    public LoginPresenter(LoginContract.View loginViews) {
        this.loginModel = new CommonImp();
        this.loginView = loginViews;
        loginView.setPresenter(this);
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void doLogin() {

    }

    @Override
    public void start() {

    }
}
