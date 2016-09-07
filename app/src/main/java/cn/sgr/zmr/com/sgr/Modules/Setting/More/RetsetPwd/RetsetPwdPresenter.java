package cn.sgr.zmr.com.sgr.Modules.Setting.More.RetsetPwd;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class RetsetPwdPresenter implements RetsetPwdContract.Presenter {


    @NonNull
    private final RetsetPwdContract.View registerView;
    Context context;
    public RetsetPwdPresenter(Context contexts, @NonNull RetsetPwdContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
