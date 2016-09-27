package cn.sgr.zmr.com.sgr.Modules.My.More;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class MorePresenter implements MoreContract.Presenter {


    @NonNull
    private final MoreContract.View registerView;
    Context context;
    public MorePresenter(Context contexts,@NonNull MoreContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
