package cn.sgr.zmr.com.sgr.Modules.My.MyChild;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bean.entity.Baby;
import com.bean.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class MyChildPresenter implements MyChildContract.Presenter {


    @NonNull
    private final MyChildContract.View registerView;
    Context context;
    private List<User> userList = new ArrayList<>();
    public MyChildPresenter(Context contexts, @NonNull MyChildContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {
        userList.clear();
        User user=new User();
        user.setNickname("测试账号");
        userList.add(user);
        registerView.showBaby(userList);


    }
}
