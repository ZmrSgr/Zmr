package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;

public class DetailTieActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     *
     * 该界面是浏览器功能
     * */
    private WebView m_webview = null;
    private String m_url = null;
    private ProgressDialog m_progressDlg = null;
    private Handler m_handler = null; // 用于下载线程与UI间的通讯

    private final int HIDE = 0;
    private final int SHOW = 1;

    private final int RESULT_OK = 1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.browser);

        initWebView();
        initHandler();
        initDlg();
        Intent intent = getIntent();
        // 获取数据
        Bundle bundle = this.getIntent().getExtras();
        loadUrl(m_webview,bundle.getString(UtilKey.TIE_KEY));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK && m_webview.canGoBack()) {
            m_webview.goBack();
            Log.d("MAIN", "ONKEYDOWN1");
            return true;
        } else {
            this.finish();
            Log.d("MAIN", "ONKEYDOWN2");
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initHandler() {
        m_handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case HIDE:
                        m_progressDlg.hide();
                        break;
                    case SHOW:
                        m_progressDlg.show();
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }

        };
    }

    private void initWebView() {
        m_webview = (WebView) findViewById(R.id.web_engine);

        // 设置多点触控放点
        m_webview.getSettings().setSupportZoom(true);
        m_webview.getSettings().setBuiltInZoomControls(true);
        m_webview.getSettings().setUseWideViewPort(true);
        m_webview.getSettings().setLoadWithOverviewMode(true);
        m_webview.getSettings().setJavaScriptEnabled(true);
        m_webview.setWebViewClient(new WebViewClient() {
            // 用WebView载入页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadUrl(view, url);
                return true;
            }

        });

        m_webview.setWebChromeClient(new WebChromeClient() {
            // 载入进度改变而被触发
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    m_handler.sendEmptyMessage(HIDE);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    private void initDlg() {
        m_progressDlg = new ProgressDialog(this);
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_progressDlg.setMessage("页面跳转中...");
    }

    private void loadUrl(final WebView view, final String url) {
        m_handler.sendEmptyMessage(SHOW);
        view.loadUrl(url);
		/*new Thread() {
			@Override
			public void run() {

			}
		}.start();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (resultCode) {
            case RESULT_OK:
                m_url = data.getStringExtra(UtilKey.TIE_KEY);
                loadUrl(m_webview, m_url);
                break;
            default:
                break;
        }
    }


}