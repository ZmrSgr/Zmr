
package cn.sgr.zmr.com.sgr.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import cn.sgr.zmr.com.sgr.R;


public class DemoView extends LinearLayout {
    /**
     * TAG for logging
     */
    private static final String TAG = "HomeUserView";

    public DemoView(Context context) {
        super(context);

        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout mainV = (LinearLayout) inflater.inflate(R.layout.view_user_info, this);

        //TODO init view
    }

}
