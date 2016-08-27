package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBaby_Activity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddHistory.AddHisoryActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.View.SublimePickerFragment;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class ChartActivity extends BaseActivity {

    @BindView(R.id.top_view_back)
    ImageView top_view_back;
    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.iv_right)
    ImageView iv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_activity_chart);
        ButterKnife.bind(this);
        initView();
        initPV();

    }

    private void initView() {
        top_view_title.setText("体温历史记录");
        top_view_back.setVisibility(View.VISIBLE);
        top_view_left_text.setVisibility(View.GONE);
        iv_right.setImageResource(R.drawable.btn_share_friend);
        iv_right.setVisibility(View.VISIBLE);
        top_view_right_text.setVisibility(View.GONE);
    }


    @OnClick({R.id.top_view_back, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.top_view_back:
                finish();
                break;

            case R.id.iv_right:
                new ShareAction(this)
                        .setDisplayList(SHARE_MEDIA.SINA,
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                                SHARE_MEDIA.WEIXIN,
                                SHARE_MEDIA.WEIXIN_CIRCLE,
                                SHARE_MEDIA.WEIXIN_FAVORITE)
                        .withTitle(this.getResources().getString(R.string.app_name))
                        .withText("——来自智梦人")
                        .withMedia(
                                new UMImage(this,
                                        "http://dev.umeng.com/images/tab2_1.png"))
                        .withTargetUrl("https://wsq.umeng.com/")
                        .setCallback(umShareListener).open();
                break;

        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {


            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(ChartActivity.this, platform + " 收藏成功啦",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ChartActivity.this, platform + " 分享成功啦",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ChartActivity.this, platform + " 分享失败啦",
                    Toast.LENGTH_SHORT).show();
            if (t != null) {
                System.out.println("分享失败"+t.getMessage());
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ChartActivity.this, platform + " 分享取消了",
                    Toast.LENGTH_SHORT).show();
        }
    };

    //初始化presenter和view
    private void initPV() {
        ChartFragment chartFragment = (ChartFragment)getFragmentManager().findFragmentById(R.id.contentFrame);
        if (chartFragment == null) {
            chartFragment = chartFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),chartFragment, R.id.contentFrame);
        }
        // Create the presenter
        new ChartPresenter(this,chartFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}