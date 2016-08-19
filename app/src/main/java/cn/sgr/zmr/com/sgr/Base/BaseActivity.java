package cn.sgr.zmr.com.sgr.Base;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.View.MyLoadingView;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class BaseActivity extends Activity {
    MyLoadingView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mProgressDialog = DialogUtil.createProgressDialog(this);
    }

    /**
     * 显示加载对话框
     */
    public void showProgressDialog() {
        if (mView == null) {
            mView=new MyLoadingView();
        }

        mView.show(getFragmentManager(), "");

    }

    /**
     * 取消加载对话框
     */
    public void cancelProgressDialog() {
        if (mView != null && mView.isVisible()) {
            mView.dismiss();
        }
    }

}
