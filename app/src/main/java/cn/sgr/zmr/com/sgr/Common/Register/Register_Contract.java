package cn.sgr.zmr.com.sgr.Common.Register;

import android.app.FragmentManager;

import com.bean.entity.User;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class Register_Contract {

    interface View extends BaseView<Presenter> {

        void showFailureLogin();//登录失败

        void showProgressDialog();//显示进度条

        void cancelProgressDialog();//隐藏进度条

        void showSuccessLogin(User user);//登录成功

        boolean isActive();//目的是为了解决内存泄漏
    }

    interface Presenter extends BasePresenter {

        void saveUser(User user);//保存或者更新用户

        void doRegister(User user);//注册操作


    }
}
