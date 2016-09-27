package cn.sgr.zmr.com.sgr.Modules.My.QrCode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.xys.libzxing.zxing.encoding.EncodingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;

public class MyQrCodeActivity extends BaseActivity {
    @BindView(R.id.top_view_title)
    TextView top_view_title;
    @BindView(R.id.top_view_back)
    ImageView top_view_back;
    @BindView(R.id.img_shouw)
    ImageView img_shouw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_qr_code_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        top_view_title.setText(getResources().getString(R.string.my_qr_code));
        top_view_back.setVisibility(View.VISIBLE);
        make();
    }
    @OnClick({R.id.top_view_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                finish();
                break;

        }
    }
    //生成二维码 可以设置Logo
    public void make() {
        Bitmap qrCode = EncodingUtils.createQRCode("1", 500, 500,null);
        img_shouw.setImageBitmap(qrCode);

    }
}
