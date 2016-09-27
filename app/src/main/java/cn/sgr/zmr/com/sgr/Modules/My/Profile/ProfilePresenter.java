package cn.sgr.zmr.com.sgr.Modules.My.Profile;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class ProfilePresenter implements ProfileContract.Presenter {


    @NonNull
    private final ProfileContract.View registerView;
    Context context;
    public ProfilePresenter(Context contexts, @NonNull ProfileContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
