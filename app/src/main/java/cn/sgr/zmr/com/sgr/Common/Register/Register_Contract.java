package cn.sgr.zmr.com.sgr.Common.Register;

import android.app.FragmentManager;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;
import cn.sgr.zmr.com.sgr.Common.Model.data.User;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class Register_Contract {

    interface View extends BaseView<Presenter> {

        void showFailureLogin();//登录失败

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        void showSuccessLogin();//登录成功

        boolean isActive();//目的是为了解决内存泄漏
    }

    interface Presenter extends BasePresenter {

        void saveUser(User user);//保存或者更新用户

        void doRegister();//注册操作


    }
}
