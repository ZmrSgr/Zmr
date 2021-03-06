package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart;

import android.app.FragmentManager;

import com.bean.entity.Baby;
import com.bean.entity.Chart;
import com.bean.entity.Treat;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public interface ChartContract {

    interface View extends BaseView<Presenter> {
            boolean isActive();//目的是为了解决内存泄漏

            void showProgressDialog(FragmentManager manager);//显示进度条

            void cancelProgressDialog();//隐藏进度条

            void showBaby( List<Baby> babys );//展示baby列表

            void showChart( LineData data);//显示表格数据

            void showHistory( List<Treat> items);//显示物理治疗历史数据

            void nextActivity(String id);//跳转宝宝详细信息，并且编辑

            void nextDayChart(Chart chart);//下一天的表格信息

            void preDayChart(Chart chart);//前一天的表格信息

            void curryDayChart(Chart chart);//当前时间的表格信息
        }

        interface Presenter extends BasePresenter {
            void getBabys(Baby babys);//获得宝宝信息
            void getAllChart();//获得全部表格信息
            void nextActivity(int postiong);
            void delBaby(int position);//删除宝宝
            void getHistory();//获取物理治疗数据


        }
}
