package cn.sgr.zmr.com.sgr.Common.Register;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Common.MainActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class Register_Activity extends Activity {
    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.btn_signup_complete)
    Button btn_signup_complete;

    @BindView(R.id.et_mobile)
    EditText mMobile;

    @BindView(R.id.et_verify_code)
     EditText mVerifyCode;

    @BindView(R.id.btn_get_verify_code_again)
     Button mGetVerifyCodeAgain;

    String mobile;//电话号码
    private TimeCount mTime;//倒计时控件



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.signup));
        top_view_back.setVisibility(View.VISIBLE);

    }


    @OnClick({R.id.btn_signup_complete,R.id.top_view_back,R.id.btn_get_verify_code_again })
    public void onClick(View view) {


        mobile = mMobile.getText().toString();
        switch (view.getId()) {



            case R.id.btn_signup_complete:

                String verify_code = mVerifyCode.getText().toString().trim();
                if ("".equals(mobile)) {
                    Toast.makeText(this, getString(R.string.please_input_mobile_phone),Toast.LENGTH_SHORT).show();
                    mMobile.requestFocus();
                } else if (mobile.length() < 11) {

                    Toast.makeText(this, getString(R.string.wrong_mobile_phone),Toast.LENGTH_SHORT).show();
                    mMobile.requestFocus();
                } else if ("".equals(verify_code)) {
                    Toast.makeText(this, getString(R.string.please_input_verify_code),Toast.LENGTH_SHORT).show();
                    mVerifyCode.setText("");
                    mVerifyCode.requestFocus();
                } else if (verify_code.length() < 6) {
                    Toast.makeText(this, getString(R.string.please_input_six),Toast.LENGTH_SHORT).show();

                    mVerifyCode.requestFocus();
                }
                else {
                    Toast.makeText(this, getString(R.string.complete_signup),Toast.LENGTH_SHORT).show();
                    Utils.toNextActivity(this,MainActivity.class);
                }

                break;

            case R.id.top_view_back:
                finish();
                break;

            case R.id.btn_get_verify_code_again:


                if ("".equals(mobile)) {
                    Toast.makeText(this, getString(R.string.please_input_mobile_phone),Toast.LENGTH_SHORT).show();

                    mMobile.requestFocus();
                } else if (mobile.length() < 11 || !Utils.isMobile(mobile)) {
                    Toast.makeText(this, getString(R.string.wrong_mobile_phone),Toast.LENGTH_SHORT).show();
                    mMobile.requestFocus();
                } else {// 这个地方获取短信验证码并且发送
                    Toast.makeText(this, getString(R.string.verify_have_send),Toast.LENGTH_SHORT).show();
                    mTime = new TimeCount(60000, 1000);// 构造CountDownTimer对象
                    mTime.start();
                    mVerifyCode.requestFocus();
                }


                break;

        }
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mGetVerifyCodeAgain
                    .setText(getString(R.string.get_verify_code_again));
            mGetVerifyCodeAgain.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mGetVerifyCodeAgain.setClickable(false);
            mGetVerifyCodeAgain.setText(millisUntilFinished / 1000
                    + getString(R.string.resend_after));
        }
    }

}
