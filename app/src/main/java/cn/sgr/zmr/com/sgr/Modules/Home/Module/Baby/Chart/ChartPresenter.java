package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bean.entity.Baby;
import com.bean.entity.Treat;

import java.util.ArrayList;

import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class ChartPresenter implements ChartContract.Presenter {
    private Context context;
    @NonNull
    private final ChartContract.View registerView;
    private DaoCacheManage daoManage;

    public ChartPresenter(Context context, @NonNull ChartContract.View registerView) {
        this.context = context;
        this.registerView = registerView;
        this. registerView.setPresenter(this);
        this.daoManage=  new DaoCacheManage(context);
    }

    @Override
    public void getBabys(Baby babys) {

    }

    @Override
    public void getAllChart() {
//        registerView.showChart(getData(24, 2));
    }

    @Override
    public void nextActivity(int postiong) {

    }

    @Override
    public void delBaby(int position) {

    }

    @Override
    public void getHistory() {
        ArrayList<Treat> items = new ArrayList<>();

       /* for (int i = 0; i < 10; i++) {
            items.add(new EventDatas("even" + i, "测试数据"));
        }*/
        registerView.showHistory(daoManage.FindAll());

    }

    @Override
    public void start() {
//        getAllChart();
        getHistory();
    }



}
