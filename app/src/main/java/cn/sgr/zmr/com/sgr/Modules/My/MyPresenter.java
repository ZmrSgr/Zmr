package cn.sgr.zmr.com.sgr.Modules.My;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class MyPresenter implements MyContract.Presenter {


    @NonNull
    private final MyContract.View registerView;
    Context context;
    public MyPresenter(Context contexts, @NonNull MyContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {
        registerView.showUserInfo(context,UserInfo.getInstance(context).getNickName(),UserInfo.getInstance(context).getMyPhone(),UserInfo.getInstance(context).getAvatar());
    }
}
