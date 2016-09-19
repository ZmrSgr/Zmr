package cn.sgr.zmr.com.sgr.Modules.Home;

import android.app.FragmentManager;

import com.bean.entity.Baby;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class HomeContract {

    interface View extends BaseView<Presenter> {

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        void clearData();//清楚数据

        void setDate(Baby baby);//设置用户数据

        void initBlue();//初始化蓝牙

        boolean isActive();//目的是为了解决内存泄漏
    }

    interface Presenter extends BasePresenter {

    }
}
