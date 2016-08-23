package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddHistory;

import android.app.FragmentManager;

import java.util.List;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Chart;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public interface AddHistoryContract {
    interface View extends BaseView<Presenter> {


        boolean isActive();//目的是为了解决内存泄漏

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        void showBaby( List<Baby> babys );//展示baby列表

        void showChart(List<Chart> charts);//显示表格数据

        void showHistory();//显示物理治疗历史数据

        void nextActivity(String id);//跳转宝宝详细信息，并且编辑

        void nextDayChart(Chart chart);//下一天的表格信息

        void preDayChart(Chart chart);//前一天的表格信息

        void curryDayChart(Chart chart);//当前时间的表格信息
    }

    interface Presenter extends BasePresenter {
        void getBabys(Baby babys);//获得宝宝信息
        void getAllChart(List<Chart> charts);//获得全部表格信息
        void nextActivity(int postiong);
        void delBaby(int position);//删除宝宝


    }

}
