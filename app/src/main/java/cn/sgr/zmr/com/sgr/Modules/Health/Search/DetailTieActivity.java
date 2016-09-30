package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;

public class DetailTieActivity extends BaseActivity {
    @BindView(R.id.wv_help)
    WebView wv_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tie);

        ButterKnife.bind(this);

        wv_help.setWebChromeClient(new WebChromeClient());
        wv_help.getSettings().setJavaScriptEnabled(true);
        wv_help.getSettings().setAllowFileAccess(true);
        wv_help.getSettings().setPluginState(WebSettings.PluginState.ON);

          /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
        wv_help.setWebChromeClient(new WebViewClient());
        wv_help.loadUrl(bundle.getString(UtilKey.TIE_KEY));


    }


    private class WebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            showProgressDialog();
            if (newProgress == 100) {
//                cancelProgressDialog();
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
