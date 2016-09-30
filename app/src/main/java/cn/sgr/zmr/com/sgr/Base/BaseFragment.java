package cn.sgr.zmr.com.sgr.Base;






import android.app.Fragment;
import android.app.FragmentManager;

import cn.sgr.zmr.com.sgr.View.MyLoadingView;

public class BaseFragment extends Fragment {

    MyLoadingView mView;

    /**
     * 显示加载对话框
     */
    public void showProgressDialog(FragmentManager manager) {
        if (mView == null) {
            mView=new MyLoadingView();
        }

        mView.show(manager, "");

    }

    /**
     * 取消加载对话框
     */
    public void cancelProgressDialog() {

        if (mView != null ) {
            System.out.println("隐藏进度条");
            mView.dismiss();

        }
    }


}
