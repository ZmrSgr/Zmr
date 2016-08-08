package cn.sgr.zmr.com.sgr.Base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class BaseActivity extends Activity {
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mProgressDialog = DialogUtil.createProgressDialog(this);
    }

    /**
     * 显示加载对话框
     */
    public void showProgressDialog() {
        cancelProgressDialog();
        mProgressDialog.show();
    }

    /**
     * 取消加载对话框
     */
    public void cancelProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
