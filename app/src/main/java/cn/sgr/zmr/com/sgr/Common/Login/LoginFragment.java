package cn.sgr.zmr.com.sgr.Common.Login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tauth.AuthActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.MainActivity;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Activity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

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

    private UMShareAPI mShareAPI = null;


    private LoginContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        /** init auth api**/
        mShareAPI = UMShareAPI.get(getActivity());
        return view;
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
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
                intent1.setClass(getActivity(), Register_Activity.class);
                startActivity(intent1);

                break;

            case R.id.login_weibo:
               ;
                mShareAPI.doOauthVerify(getActivity(),  SHARE_MEDIA.SINA, umAuthListener);

                break;

            case R.id.login_qq:
                mShareAPI.doOauthVerify(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
                break;

            case R.id.login_weixin:
                mShareAPI.doOauthVerify(getActivity(),  SHARE_MEDIA.WEIXIN, umAuthListener);
                break;

            case R.id.top_view_back:
                getActivity().finish();
                break;
            case R.id.btn_login:

                String  mobile = loing_et_mobile.getText().toString();
                String  password = login_et_password.getText().toString();
                if ("".equals(mobile)) {

                    Toast.makeText(getActivity(), getString(R.string.please_input_mobile_phone),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else if ("".equals(password)) {
                    Toast.makeText(getActivity(), getString(R.string.please_input_password),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                }else if (mobile.length() < 11 || !Utils.isMobile(mobile)){
                    Toast.makeText(getActivity(), getString(R.string.wrong_mobile_phone),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else if (password.length() < 6 || password.length() > 20) {
                    Toast.makeText(getActivity(), getString(R.string.please_enter_correct_password_format),Toast.LENGTH_SHORT).show();
                    loing_et_mobile.requestFocus();
                } else {
                  //presenter 处理
                    Toast.makeText(getActivity(),getString(R.string.login_success),Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    
    @Override
    public void showFailureLogin() {
           Toast.makeText(getActivity(),R.string.login_failure,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessLogin() {
        Utils.toNextActivity(getActivity(),MainActivity.class);

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void showProgressDialog(FragmentManager manager) {
        showProgressDialog(getActivity().getFragmentManager());
    }

    @Override
    public void cancelProgressDialog() {
        cancelProgressDialog();
    }

    /** auth callback interface**/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getActivity(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            Log.d("user info","user info:"+data.toString());
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getActivity(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getActivity(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
