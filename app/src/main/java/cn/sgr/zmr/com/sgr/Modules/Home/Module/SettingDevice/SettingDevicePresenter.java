package cn.sgr.zmr.com.sgr.Modules.Home.Module.SettingDevice;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class SettingDevicePresenter implements SettingDeviceContract.Presenter {


    @NonNull
    private final SettingDeviceContract.View registerView;
    Context context;
    public SettingDevicePresenter(Context contexts, @NonNull SettingDeviceContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
