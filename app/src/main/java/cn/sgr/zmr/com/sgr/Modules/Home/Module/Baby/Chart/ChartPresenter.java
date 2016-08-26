package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Chart;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.EventDatas;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyContract;
import cn.sgr.zmr.com.sgr.R;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class ChartPresenter implements ChartContract.Presenter {
    private Context context;
    @NonNull
    private final ChartContract.View registerView;

    public ChartPresenter(Context context, @NonNull ChartContract.View registerView) {
        this.context = context;
        this.registerView = registerView;
        this. registerView.setPresenter(this);
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
        ArrayList<EventDatas> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            items.add(new EventDatas("even" + i, "测试数据"));
        }
        registerView.showHistory(items);

    }

    @Override
    public void start() {
//        getAllChart();
        getHistory();
    }



}
