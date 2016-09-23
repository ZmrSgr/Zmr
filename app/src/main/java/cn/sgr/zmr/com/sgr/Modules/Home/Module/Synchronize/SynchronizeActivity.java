package cn.sgr.zmr.com.sgr.Modules.Home.Module.Synchronize;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.Modules.Setting.AlarmWay.AlarmWayActivity;
import cn.sgr.zmr.com.sgr.Modules.Setting.Device.DeviceActivity;
import cn.sgr.zmr.com.sgr.Modules.Setting.Help.HelpActivity;
import cn.sgr.zmr.com.sgr.Modules.Setting.More.MoreActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class SynchronizeActivity extends BaseActivity {
    @BindView(R.id.auto)
    JellyToggleButton   auto;

    @BindView(R.id.wifi)
    JellyToggleButton   wifi;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;


    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.btn_submit)
    Button btn_submit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {
        top_view_title.setText(getString(R.string.synchronize_yun));
        wifi.setLeftBackgroundColor(getResources().getColor(R.color.them_bg));
        wifi.setRightBackgroundColor(getResources().getColor(R.color.them_bg));
        auto.setLeftBackgroundColor(getResources().getColor(R.color.them_bg));
        auto.setRightBackgroundColor(getResources().getColor(R.color.them_bg));
        wifi.setTextSize(40);
        auto.setTextSize(40);
        auto.setTextColor(getResources().getColor(R.color.them_bg));
        wifi.setTextColor(getResources().getColor(R.color.them_bg));
        auto.setTextMarginLeft(20);
        auto.setTextMarginRight(20);
        wifi.setTextMarginLeft(20);
        wifi.setTextMarginRight(20);

        wifi.setChecked(Setting.getInstance(this).IsWifi());
        auto.setChecked(Setting.getInstance(this).IsAuto());
        auto.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    Setting.getInstance(SynchronizeActivity.this).setSYNCHRONIZE_AUTO("0");

                }
                if (state.equals(State.RIGHT)) {
                    Setting.getInstance(SynchronizeActivity.this).setSYNCHRONIZE_AUTO("1");
                }

            }
        });

        wifi.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    Setting.getInstance(SynchronizeActivity.this).setSYNCHRONIZE_WIFI("0");

                }
                if (state.equals(State.RIGHT)) {
                    Setting.getInstance(SynchronizeActivity.this).setSYNCHRONIZE_WIFI("1");
                }

            }
        });
    }
    @OnClick({R.id.top_view_back,R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Toast.makeText(SynchronizeActivity.this,"提交",Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_view_back:
               finish();
                break;
        }
    }
}
