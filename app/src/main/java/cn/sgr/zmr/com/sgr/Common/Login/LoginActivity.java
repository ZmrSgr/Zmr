package cn.sgr.zmr.com.sgr.Common.Login;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Common.MainActivity;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Activity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginActivity extends BaseActivity implements LoginContract.View{
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

    @BindView(R.id.login_qq)
    ImageView login_qq;

    @BindView(R.id.login_weixin)
    ImageView login_weixin;

    @BindView(R.id.login_weibo)
    ImageView login_weibo;



    private LoginContract.Presenter mPresenter;

    private UMShareAPI mShareAPI = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        mShareAPI = UMShareAPI.get(this);
        initView();
        new LoginPresenter(this);

    }

    private void initView() {
        top_view_title.setText(getResources().getString(R.string.login));
        top_view_back.setVisibility(View.VISIBLE);

    }


    @OnClick({R.id.tv_signup,R.id.top_view_back,R.id.login_weibo,R.id.login_qq,R.id.login_weixin })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signup:
                Intent intent1=new Intent();
                intent1.setClass(LoginActivity.this, Register_Activity.class);
                startActivity(intent1);

                break;

            case R.id.login_weibo:
                ;
                mShareAPI.doOauthVerify(LoginActivity.this,  SHARE_MEDIA.SINA, umAuthListener);

                break;

            case R.id.login_qq:
                mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;

            case R.id.login_weixin:
                mShareAPI.doOauthVerify(LoginActivity.this,  SHARE_MEDIA.WEIXIN, umAuthListener);
                break;

            case R.id.top_view_back:
                LoginActivity.this.finish();
                break;
            case R.id.btn_login:

                String  mobile = loing_et_mobile.getText().toString();
                String  password = login_et_password.getText().toString();
                if ("".equals(mobile)) {

                    Toast.makeText(LoginActivity.this, getString(R.string.please_input_mobile_phone),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else if ("".equals(password)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.please_input_password),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                }else if (mobile.length() < 11 || !Utils.isMobile(mobile)){
                    Toast.makeText(LoginActivity.this, getString(R.string.wrong_mobile_phone),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else if (password.length() < 6 || password.length() > 20) {
                    Toast.makeText(LoginActivity.this, getString(R.string.please_enter_correct_password_format),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else {
                    //presenter 处理
                    Toast.makeText(LoginActivity.this,getString(R.string.login_success),Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    /** auth callback interface**/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LoginActivity.this, "Authorize succeed", Toast.LENGTH_SHORT).show();
            Log.d("user info","user info:"+data.toString());
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this, "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( LoginActivity.this, "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    //初始化   presenter 和 fregment（view）

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void showFailureLogin() {
        Toast.makeText(LoginActivity.this,R.string.login_failure,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessLogin() {
        Utils.toNextActivity(LoginActivity.this,MainActivity.class);

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }
    @Override
    public void showProgressDialog(FragmentManager manager) {
        showProgressDialog(getFragmentManager());
    }

    @Override
    public void cancelProgressDialog() {
        cancelProgressDialog();
    }

}
