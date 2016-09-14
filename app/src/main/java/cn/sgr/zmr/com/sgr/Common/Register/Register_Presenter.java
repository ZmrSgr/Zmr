package cn.sgr.zmr.com.sgr.Common.Register;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bean.entity.User;

import cn.sgr.zmr.com.sgr.Common.Model.imp.CommonImp;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class Register_Presenter implements Register_Contract.Presenter {
    @NonNull
    private final CommonImp loginModel;

    @NonNull
    private final Register_Contract.View registerView;
    Context context;

    public Register_Presenter(Context contexts,@NonNull Register_Contract.View registerView) {
        this.loginModel = new CommonImp();
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }


    @Override
    public void saveUser(User user) {

    }

    @Override
    public void doRegister() {

    }

    @Override
    public void start() {

    }
}
