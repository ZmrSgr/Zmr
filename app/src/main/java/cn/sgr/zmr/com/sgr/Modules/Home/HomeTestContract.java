package cn.sgr.zmr.com.sgr.Modules.Home;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ColorRes;
import android.view.View.OnClickListener;

import com.bean.entity.Baby;

import java.util.Date;
import java.util.List;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;
import cn.sgr.zmr.com.sgr.View.MsgDialog;

/**
 * Created by zuky on 2016/9/7 0007.
 */
public class HomeTestContract {

    interface View extends BaseView<Presenter> {

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        void showUserInfo(Context context, String nickName, String phone, String avatar);//显示用户信息

        boolean isActive();//目的是为了解决内存泄漏
    }

    interface Presenter extends BasePresenter {



    }
}
