package cn.sgr.zmr.com.sgr.Modules.My.MyChild;

import android.app.FragmentManager;

import com.bean.entity.Baby;
import com.bean.entity.User;

import java.util.List;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class MyChildContract {

    interface View extends BaseView<Presenter> {

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        boolean isActive();//目的是为了解决内存泄漏

        void showBaby( List<User> users );//展示用户列表
    }

    interface Presenter extends BasePresenter {

    }
}
