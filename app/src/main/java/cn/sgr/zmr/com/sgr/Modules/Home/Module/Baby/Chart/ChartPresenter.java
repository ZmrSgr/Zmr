package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Chart;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyContract;

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
    }

    @Override
    public void getBabys(Baby babys) {

    }

    @Override
    public void getAllChart(List<Chart> charts) {

    }

    @Override
    public void nextActivity(int postiong) {

    }

    @Override
    public void delBaby(int position) {

    }

    @Override
    public void start() {

    }
}
