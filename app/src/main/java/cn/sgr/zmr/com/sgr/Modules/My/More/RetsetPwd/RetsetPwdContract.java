package cn.sgr.zmr.com.sgr.Modules.My.More.RetsetPwd;

import android.app.FragmentManager;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class RetsetPwdContract {

    interface View extends BaseView<Presenter> {

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        boolean isActive();//目的是为了解决内存泄漏
    }

    interface Presenter extends BasePresenter {

    }
}
