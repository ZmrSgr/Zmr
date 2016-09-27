package cn.sgr.zmr.com.sgr.Modules.Home.Module.AlarmWay;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class AlarmWayPresenter implements AlarmWayContract.Presenter {


    @NonNull
    private final AlarmWayContract.View registerView;
    Context context;
    public AlarmWayPresenter(Context contexts, @NonNull AlarmWayContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
