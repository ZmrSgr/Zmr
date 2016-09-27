package cn.sgr.zmr.com.sgr.Modules.My.More.Disclaimer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.R;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class DisclaimerFragment extends BaseFragment implements DisclaimerContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.wv_help)
    WebView wv_help;


    private DisclaimerContract.Presenter mPresenter;

    //单例 模式
    public static DisclaimerFragment newInstance() {
        return new DisclaimerFragment();
    }
    //   构造方法
    public DisclaimerFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.disclaimer_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.set_disclaimer));
        top_view_back.setVisibility(View.VISIBLE);
        wv_help.setWebChromeClient(new WebChromeClient());
        wv_help.getSettings().setJavaScriptEnabled(true);
        wv_help.getSettings().setAllowFileAccess(true);
        wv_help.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv_help.loadUrl("http://app.120.net/");
    }

    //监听按钮
    @OnClick({R.id.top_view_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                getActivity().finish();
                break;

        }
    }


    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(DisclaimerContract.Presenter presenter) {

    }
}
