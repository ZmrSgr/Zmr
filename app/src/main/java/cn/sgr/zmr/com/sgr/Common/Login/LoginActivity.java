package cn.sgr.zmr.com.sgr.Common.Login;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import cn.sgr.zmr.com.sgr.Common.Register.Register_Activity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class LoginActivity extends Activity {
    @BindView(R.id.tv_signup)
    TextView tv_signup;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.loing_et_mobile)
    EditText loing_et_mobile;

    @BindView(R.id.login_et_password)
    EditText login_et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        top_view_title.setText(getResources().getString(R.string.login));
        top_view_back.setVisibility(View.VISIBLE);

    }



    @OnClick({R.id.tv_signup,R.id.top_view_back })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signup:
                Intent intent1=new Intent();
                intent1.setClass(this, Register_Activity.class);
                startActivity(intent1);
                break;

            case R.id.top_view_back:
               finish();
                break;
            case R.id.btn_login:

                String  mobile = loing_et_mobile.getText().toString();
                String  password = login_et_password.getText().toString();
                if ("".equals(mobile)) {

                    Toast.makeText(this, getString(R.string.please_input_mobile_phone),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else if ("".equals(password)) {
                    Toast.makeText(this, getString(R.string.please_input_password),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                }else if (mobile.length() < 11 || !Utils.isMobile(mobile)){
                    Toast.makeText(this, getString(R.string.wrong_mobile_phone),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else if (password.length() < 6 || password.length() > 20) {
                    Toast.makeText(this, getString(R.string.please_enter_correct_password_format),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else {
                    Utils.toNextActivity(this,MainActivity.class);
                    Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
