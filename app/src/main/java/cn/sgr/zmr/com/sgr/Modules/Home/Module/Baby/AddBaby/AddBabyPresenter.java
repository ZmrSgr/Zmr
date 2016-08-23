package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyContract;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class AddBabyPresenter implements AddBabyContract.Presenter {

    @NonNull
    private final AddBabyContract.View registerView;
    private Context context;

    public AddBabyPresenter(Context context,@NonNull AddBabyContract.View registerView) {
        this.registerView = registerView;
        this.registerView.setPresenter(this);
        this.context = context;
    }

    @Override
    public void addBabys(List<Baby> babys) {

    }

    @Override
    public void nextActivity(int postiong) {

    }

    @Override
    public void start() {

    }
}
