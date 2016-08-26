package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddHistory;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBabyContract;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class AddHistoryPresenter implements AddHistoryContract.Presenter {

    @NonNull
    private final AddHistoryContract.View registerView;
    private Context context;

    public AddHistoryPresenter(Context context,@NonNull AddHistoryContract.View registerView) {
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
