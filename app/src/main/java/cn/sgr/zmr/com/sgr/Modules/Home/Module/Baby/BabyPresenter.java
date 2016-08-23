package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Device.DeviceListContract;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class BabyPresenter implements BabyContract.Presenter{

    @NonNull
    private final BabyContract.View registerView;

    private List<Baby> babyList = new ArrayList<>();
    private Context context;

    public BabyPresenter(Context contexts, @NonNull BabyContract.View registerView) {
        this.registerView = registerView;
        this.registerView.setPresenter(this);
        this.context=contexts;
    }

    @Override
    public void getBabys(List<Baby> babys) {
        this.registerView.showBaby(babys);
    }

    @Override
    public void nextActivity(int postiong) {

    }

    @Override
    public void delBaby(int position) {

    }

    @Override
    public void start() {
        initData();
        getBabys(babyList);

    }
    private void initData() {
        babyList.clear();
        for (int i=0; i < 5; ++i) {
            Baby baby = new Baby("小宝"+i,"两个月","男","5kg","45cm","设备名称");
            babyList.add(baby);
        }
    }


}
