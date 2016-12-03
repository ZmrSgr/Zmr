package cn.sgr.zmr.com.sgr.Common.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bean.entity.User;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Common.FindPwd.FindPwdActivity;
import cn.sgr.zmr.com.sgr.Common.MainActivity;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Common.Model.UserResult;
import cn.sgr.zmr.com.sgr.Common.Model.imp.CommonImp;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Activity;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.Result;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.SearchResult;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import okhttp3.Call;
/*
*
*  //qq 保存的是openid ，微信保存的是unionid，微博保存的是uid
* */
public class LoginActivity extends BaseActivity{
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

    @BindView(R.id.tv_reset_password)
    TextView tv_reset_password;

    String third_id,nicke_name,avatar;
    private UMShareAPI mShareAPI = null;
    private CommonImp commonModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        mShareAPI = UMShareAPI.get(this);
        commonModel=new CommonImp();
        initView();
    }
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.login));
        top_view_back.setVisibility(View.VISIBLE);

    }
    @OnClick({R.id.tv_signup,R.id.top_view_back,R.id.login_weibo,R.id.login_qq,R.id.login_weixin,R.id.tv_reset_password ,R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signup:
                Intent intent1=new Intent();
                intent1.setClass(LoginActivity.this, Register_Activity.class);
                startActivity(intent1);
                break;
            case R.id.login_weibo:

                mShareAPI.doOauthVerify(LoginActivity.this,  SHARE_MEDIA.SINA, umAuthListener);

                break;

            case R.id.login_qq:

                mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;

            case R.id.login_weixin:

                mShareAPI.doOauthVerify(LoginActivity.this,  SHARE_MEDIA.WEIXIN, umAuthListener);
                break;

            case R.id.tv_reset_password:
               Utils.toNextActivity(this, FindPwdActivity.class);
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
                    login(mobile,password);
                }
                break;
        }
    }
   //登录
    private void login(String mobile,String password){
        User user=new User();
        user.setPassword(password);
        user.setPhone(mobile);
        commonModel.Login(LoginActivity.this, user, new HttpRequestCallback<Result<UserResult>>() {
            @Override
            public void onStart() {
                showProgressDialog();

            }
            @Override
            public void onFinish() {
                cancelProgressDialog();
            }
            @Override
            public void onResponse(Result<UserResult> userResult) {
                if(userResult.msg.equals("1")){//正常访问
                    UserInfo.getInstance(LoginActivity.this).setSid(userResult.data.sid);//保存sid
                    AfterLogin(userResult.data.user);
                }else{
                    Toast.makeText(LoginActivity.this,userResult.desc,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call call, HttpException e) {
                System.out.println("onFailure");
            }
        });

    }
    //第三方登录
    private void Third_login(String third_id,String nicke_name,String avatar){
        commonModel.Third_Login(LoginActivity.this,third_id,nicke_name,avatar , new HttpRequestCallback<Result<UserResult>>() {
            @Override
            public void onStart() {
                showProgressDialog();

            }

            @Override
            public void onFinish() {
                cancelProgressDialog();
            }

            @Override
            public void onResponse(Result<UserResult> userResult) {
                if(userResult.msg.equals("1")){//正常访问
                    UserInfo.getInstance(LoginActivity.this).setSid(userResult.data.sid);//保存sid
                    AfterLogin(userResult.data.user);
                }else{
                    Toast.makeText(LoginActivity.this,userResult.desc,Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call call, HttpException e) {
                System.out.println("onFailure");
            }
        });
    }
    private void AfterLogin(User user) {
       //保存登录信息
        UserInfo.getInstance(this).saveUserInfo(user);
//        new DaoCacheManage(this).updateUser(user);
        Utils.toNextActivity(this,MainActivity.class);
        finish();
    }
    /** auth callback interface**/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if(action==UMAuthListener.ACTION_GET_PROFILE){//获取用户资料
                if(platform==SHARE_MEDIA.QQ){
                    nicke_name=data.get("screen_name");
                    third_id=data.get("openid");
                    avatar=data.get("profile_image_url");
                }else if(platform==SHARE_MEDIA.WEIXIN){
                    nicke_name=data.get("screen_name");
                    third_id=data.get("unionid");
                    avatar=data.get("profile_image_url");

                }else if(platform==SHARE_MEDIA.SINA){
                    nicke_name=data.get("screen_name");
                    third_id=data.get("uid");
                    avatar=data.get("profile_image_url");
                }
                Third_login(third_id,nicke_name,avatar);//登录保存信息

            }else if(action==UMAuthListener.ACTION_AUTHORIZE){
                mShareAPI.getPlatformInfo(LoginActivity.this,platform, umAuthListener);
            }
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
}
