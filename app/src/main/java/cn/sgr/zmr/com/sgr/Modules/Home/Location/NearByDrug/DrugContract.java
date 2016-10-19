package cn.sgr.zmr.com.sgr.Modules.Home.Location.NearByDrug;

import android.app.FragmentManager;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class DrugContract {

    interface View extends BaseView<Presenter> {

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        boolean isActive();//目的是为了解决内存泄漏

        void showDrugList();//更新药物列表

        void ShowMoreList();//显示更多的药物列表
    }

    interface Presenter extends BasePresenter {
        void getDrugList(  String lat, String lng);
        void getMoreDrugList(  String lat, String lng);

        @Override
        void start();//开始加载数据。

    }
}
