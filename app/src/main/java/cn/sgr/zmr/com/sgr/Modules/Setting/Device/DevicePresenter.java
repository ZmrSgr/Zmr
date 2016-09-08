package cn.sgr.zmr.com.sgr.Modules.Setting.Device;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class DevicePresenter implements DeviceContract.Presenter {


    @NonNull
    private final DeviceContract.View registerView;
    Context context;
    public DevicePresenter(Context contexts, @NonNull DeviceContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
