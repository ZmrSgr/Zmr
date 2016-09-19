package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bean.entity.Baby;

import java.util.List;

import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class AddBabyPresenter implements AddBabyContract.Presenter {

    @NonNull
    private final AddBabyContract.View registerView;
    private Context context;
    private DaoCacheManage daoManage;

    public AddBabyPresenter(Context context,@NonNull AddBabyContract.View registerView) {
        this.registerView = registerView;
        this.registerView.setPresenter(this);
        this.context = context;
        this.daoManage=  new DaoCacheManage(context);
    }

    @Override
    public void SaveBaby(Baby babys, boolean isOnline) {
        daoManage.updateBaby(babys,isOnline);
    }

    @Override
    public void nextActivity(int postiong) {

    }

    @Override
    public void start() {

    }
}
