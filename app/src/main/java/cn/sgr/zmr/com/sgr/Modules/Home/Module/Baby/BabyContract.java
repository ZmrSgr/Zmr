package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby;

import android.app.FragmentManager;

import java.util.List;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public interface BabyContract {



    interface View extends BaseView<Presenter> {


        boolean isActive();//目的是为了解决内存泄漏

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        void showBaby( List<Baby> babys );//展示baby列表

        void delBaby(int position);//删除某个宝宝

        void nextActivity(String address);//跳转宝宝详细信息
    }

    interface Presenter extends BasePresenter {
        void getBabys( List<Baby> babys);//获得宝宝列表
        void nextActivity(int postiong);
        void delBaby(int position);//删除宝宝


    }

}
