package cn.sgr.zmr.com.sgr.Common.Register;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.bean.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.MainActivity;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class Register_Fragment extends BaseFragment implements Register_Contract.View  {


    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.btn_signup_complete)
    Button btn_signup_complete;

    @BindView(R.id.et_mobile)
    EditText mMobile;

    @BindView(R.id.et_password)
    EditText mPassword;

    @BindView(R.id.et_password_again)
    EditText mPasswordAgain;

    @BindView(R.id.et_verify_code)
    EditText mVerifyCode;

    @BindView(R.id.et_nickname)
    EditText et_nickname;






    @BindView(R.id.btn_get_verify_code_again)
    Button mGetVerifyCodeAgain;

    String mobile;//电话号码
    private TimeCount mTime;//倒计时控件

    private Register_Contract.Presenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();

        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.signup));
        top_view_back.setVisibility(View.VISIBLE);
    }
    
    
    //监听按钮
    @OnClick({R.id.btn_signup_complete,R.id.top_view_back,R.id.btn_get_verify_code_again })
    public void onClick(View view) {


        mobile = mMobile.getText().toString();
        switch (view.getId()) {

            case R.id.btn_signup_complete:
                String password = mPassword.getText().toString();
                String password_again = mPasswordAgain.getText().toString();
                String verify_code = mVerifyCode.getText().toString().trim();
                String nickName = et_nickname.getText().toString().trim();
                if ("".equals(mobile)) {
                    Toast.makeText(getActivity(), getString(R.string.please_input_mobile_phone),Toast.LENGTH_SHORT).show();
                    mMobile.requestFocus();
                } else if("".equals(nickName)){
                    Toast.makeText(getActivity(), getString(R.string.please_input_nickname),Toast.LENGTH_SHORT).show();
                    et_nickname.requestFocus();
                }
                else if (mobile.length() < 11) {

                    Toast.makeText(getActivity(), getString(R.string.wrong_mobile_phone),Toast.LENGTH_SHORT).show();
                    mMobile.requestFocus();
                } else if ("".equals(verify_code)) {
                    Toast.makeText(getActivity(), getString(R.string.please_input_verify_code),Toast.LENGTH_SHORT).show();
                    mVerifyCode.setText("");
                    mVerifyCode.requestFocus();
                } else if (verify_code.length() < 6) {
                    Toast.makeText(getActivity(), getString(R.string.please_input_six),Toast.LENGTH_SHORT).show();

                    mVerifyCode.requestFocus();
                } else if ("".equals(password)) {
                    Toast.makeText(getActivity(), getString(R.string.please_input_password),Toast.LENGTH_SHORT).show();
                    mPassword.requestFocus();
                } else if (password.length() < 6 || password.length() > 20) {
                    Toast.makeText(getActivity(), getString(R.string.password_wrong_format_hint),Toast.LENGTH_SHORT).show();
                    mPassword.requestFocus();
                } else if (!password.equals(password_again)) {
                    Toast.makeText(getActivity(), getString(R.string.two_passwords_differ_hint),Toast.LENGTH_SHORT).show();
                    mPasswordAgain.requestFocus();
                } else {// 注册成功
                    User user=new User();
                    user.setPassword(password);
                    user.setPhone(mobile);
                    user.setNickname(nickName);

                    mPresenter.doRegister(user);
                }
                break;

            case R.id.top_view_back:
                getActivity().finish();
                break;

            case R.id.btn_get_verify_code_again:


                if ("".equals(mobile)) {
                    Toast.makeText(getActivity(), getString(R.string.please_input_mobile_phone),Toast.LENGTH_SHORT).show();

                    mMobile.requestFocus();
                } else if (mobile.length() < 11 || !Utils.isMobile(mobile)) {
                    Toast.makeText(getActivity(), getString(R.string.wrong_mobile_phone),Toast.LENGTH_SHORT).show();
                    mMobile.requestFocus();
                } else {// 这个地方获取短信验证码并且发送
                    Toast.makeText(getActivity(), getString(R.string.verify_have_send),Toast.LENGTH_SHORT).show();
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

    //单例 模式
    public static Register_Fragment newInstance() {
        return new Register_Fragment();
    }
    //   构造方法
    public Register_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void showProgressDialog() {
        super.showProgressDialog(getFragmentManager());
    }

    @Override
    public void cancelProgressDialog() {
        super.cancelProgressDialog();
    }

    @Override
    public void showFailureLogin() {

    }

    @Override
    public void showSuccessLogin(User user) {
        UserInfo.getInstance(getActivity()).saveUserInfo(user);
        Utils.toNextActivity(getActivity(),MainActivity.class);
        getActivity().finish();

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull Register_Contract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }
}
