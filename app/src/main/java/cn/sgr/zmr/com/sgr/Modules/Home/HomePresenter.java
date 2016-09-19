package cn.sgr.zmr.com.sgr.Modules.Home;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class HomePresenter implements HomeContract.Presenter {


    @NonNull
    private final HomeContract.View registerView;
    Context context;
    private DaoCacheManage daoManage;
    public HomePresenter(Context contexts, @NonNull HomeContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        this.daoManage=  new DaoCacheManage(contexts);
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {
        if (daoManage.getCurryBaby() == null) {
            registerView.clearData();
        }else{
            registerView.setDate(daoManage.getCurryBaby());
        }


    }
}
