package cn.sgr.zmr.com.sgr.Modules.My.More.Contract;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class ContractPresenter implements ContractMoreContract.Presenter {


    @NonNull
    private final ContractMoreContract.View registerView;
    Context context;
    public ContractPresenter(Context contexts, @NonNull ContractMoreContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
