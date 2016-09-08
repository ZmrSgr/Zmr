package cn.sgr.zmr.com.sgr.Modules.Setting.More;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.Login.LoginActivity;
import cn.sgr.zmr.com.sgr.Common.MainActivity;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Contract;
import cn.sgr.zmr.com.sgr.Modules.Setting.More.Contract.ContractActivity;
import cn.sgr.zmr.com.sgr.Modules.Setting.More.Disclaimer.DisclaimerActivity;
import cn.sgr.zmr.com.sgr.Modules.Setting.More.Feedback.FeedbackActivity;
import cn.sgr.zmr.com.sgr.Modules.Setting.More.RetsetPwd.RetsetPwdActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class MoreFragment extends BaseFragment implements MoreContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.rel_reset_pwd)
    View   rel_reset_pwd;

    @BindView(R.id.rel_update)
    View   rel_update;

    @BindView(R.id.rel_feedback)
    View   rel_feedback;

    @BindView(R.id.rel_about_us)
    View   rel_about_us;

    @BindView(R.id.rel_settings_disclaimer)
    View   rel_settings_disclaimer;


    @BindView(R.id.btn_login_out)
    Button btn_login_out;

    private MoreContract.Presenter mPresenter;

    //单例 模式
    public static MoreFragment newInstance() {
        return new MoreFragment();
    }
    //   构造方法
    public MoreFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.set_more));
        top_view_back.setVisibility(View.VISIBLE);
    }

    //监听按钮
    @OnClick({R.id.top_view_back,R.id.rel_reset_pwd,R.id.rel_update,R.id.rel_feedback,R.id.rel_about_us,R.id.rel_settings_disclaimer,R.id.btn_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                getActivity().finish();
                break;

            case R.id.rel_reset_pwd:
                Utils.toNextActivity(getActivity(), RetsetPwdActivity.class);
                break;
            case R.id.rel_update:
                showProgressDialog(getFragmentManager());
                new Handler().postDelayed(new Runnable() {//延时0.5秒显示
                    public void run() {
                       cancelProgressDialog();
                    }
                }, 1000);

                Toast.makeText(getActivity(),"软件更新",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rel_feedback:
                Utils.toNextActivity(getActivity(), FeedbackActivity.class);
                break;
            case R.id.rel_about_us:
                Utils.toNextActivity(getActivity(), ContractActivity.class);
                break;
            case R.id.rel_settings_disclaimer:
                Utils.toNextActivity(getActivity(), DisclaimerActivity.class);
                break;
            case R.id.btn_login_out:
                Utils.toNextActivity(getActivity(), LoginActivity.class);
                break;



        }
    }


    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(MoreContract.Presenter presenter) {

    }
}
