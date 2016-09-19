package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddHistory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bean.entity.Baby;
import com.bean.entity.Treat;

import java.util.List;

import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class AddHistoryPresenter implements AddHistoryContract.Presenter {

    @NonNull
    private final AddHistoryContract.View registerView;
    private Context context;
    private DaoCacheManage daoManage;

    public AddHistoryPresenter(Context context,@NonNull AddHistoryContract.View registerView) {
        this.registerView = registerView;
        this.registerView.setPresenter(this);
        this.context = context;
        this.daoManage=  new DaoCacheManage(context);
    }
    @Override
    public void addBabys(List<Baby> babys) {

    }

    @Override
    public void nextActivity(int postiong) {

    }

    @Override
    public void saveTreat(Treat treat) {
        daoManage.InsertTreat(treat);
    }

    @Override
    public void start() {

    }
}
