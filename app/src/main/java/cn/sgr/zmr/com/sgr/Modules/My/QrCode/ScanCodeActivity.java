package cn.sgr.zmr.com.sgr.Modules.My.QrCode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;

public class ScanCodeActivity extends BaseActivity {
    @BindView(R.id.top_view_title)
    TextView top_view_title;
    @BindView(R.id.top_view_back)
    ImageView top_view_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);
        initView();

    }
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.connected_child));
        top_view_back.setVisibility(View.VISIBLE);
        startActivityForResult(new Intent(this, CaptureActivity.class),0);//扫描二维码
    }

    @OnClick({R.id.top_view_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String result=bundle.getString("result");
                Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
            }
        }

    }



}
