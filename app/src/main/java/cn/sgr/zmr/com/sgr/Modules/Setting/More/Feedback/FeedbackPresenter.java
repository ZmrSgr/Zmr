package cn.sgr.zmr.com.sgr.Modules.Setting.More.Feedback;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class FeedbackPresenter implements FeedbackContract.Presenter {


    @NonNull
    private final FeedbackContract.View registerView;
    Context context;
    public FeedbackPresenter(Context contexts, @NonNull FeedbackContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {

    }
}
