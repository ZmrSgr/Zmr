package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bean.entity.Baby;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;

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
    }
    private void initData() {
        babyList.clear();
        babyList= new DaoCacheManage(context).getBabys();
        getBabys(babyList);
    }


}
