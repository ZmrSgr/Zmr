package cn.sgr.zmr.com.sgr.Modules.My.More.RetsetPwd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.R;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class RetsetPwdFragment extends BaseFragment implements RetsetPwdContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.et_currypwd)
    EditText et_currypwd;

    @BindView(R.id.et_new_pwd)
    EditText et_new_pwd;

    @BindView(R.id.et_c_newpwd)
    EditText et_c_newpwd;

    @BindView(R.id.btn_save)
    Button btn_save;


    private RetsetPwdContract.Presenter mPresenter;

    //单例 模式
    public static RetsetPwdFragment newInstance() {
        return new RetsetPwdFragment();
    }
    //   构造方法
    public RetsetPwdFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resetpwd_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.set_retsetpwd));
        top_view_back.setVisibility(View.VISIBLE);
    }

    //监听按钮
    @OnClick({R.id.top_view_back,R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                getActivity().finish();
                break;
            case R.id.btn_save:
                String now_password = et_currypwd.getText().toString().trim();
                String new_password = et_new_pwd.getText().toString().trim();
                String re_new_password = et_c_newpwd.getText().toString().trim();

                if ("".equals(now_password)) {
                    Toast.makeText(getActivity(),getString(R.string.input_password),Toast.LENGTH_SHORT).show();
                    et_currypwd.requestFocus();
                    et_currypwd.setText("");
                }
                else if (now_password.length() < 6 || now_password.length() > 20) {
                    Toast.makeText(getActivity(),getString(R.string.password_wrong_format_hint),Toast.LENGTH_SHORT).show();
                    et_currypwd.requestFocus();
                } else if ("".equals(new_password)) {
                    Toast.makeText(getActivity(),getString(R.string.input_new_password),Toast.LENGTH_SHORT).show();
                    et_new_pwd.requestFocus();
                    et_new_pwd.setText("");
                }else if (new_password.length() < 6 || new_password.length() > 20) {
                    Toast.makeText(getActivity(),getString(R.string.new_password_wrong_format_hint),Toast.LENGTH_SHORT).show();
                    et_new_pwd.requestFocus();
                }else if (!new_password.equals(re_new_password)) {
                    Toast.makeText(getActivity(),getString(R.string.two_passwords_differ_hint),Toast.LENGTH_SHORT).show();
                    et_c_newpwd.requestFocus();
                }else{
                    Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }


    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(RetsetPwdContract.Presenter presenter) {

    }
}
