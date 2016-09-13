package cn.sgr.zmr.com.sgr.Common.FindPwd;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class FindPwdPresenter implements FindPwdContract.Presenter {


    @NonNull
    private final FindPwdContract.View registerView;
    Context context;
    public FindPwdPresenter(Context contexts, @NonNull FindPwdContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
