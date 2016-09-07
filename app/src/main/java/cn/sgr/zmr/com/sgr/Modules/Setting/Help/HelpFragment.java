package cn.sgr.zmr.com.sgr.Modules.Setting.Help;

import android.os.Bundle;
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
import cn.sgr.zmr.com.sgr.R;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class HelpFragment extends BaseFragment implements HelpContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    private HelpContract.Presenter mPresenter;

    //单例 模式
    public static HelpFragment newInstance() {
        return new HelpFragment();
    }
    //   构造方法
    public HelpFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.help_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.set_help));
        top_view_back.setVisibility(View.VISIBLE);
    }

    //监听按钮
    @OnClick({R.id.top_view_back,R.id.rel_reset_pwd,R.id.rel_update,R.id.rel_feedback,R.id.rel_about_us,R.id.rel_settings_disclaimer,R.id.btn_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                getActivity().finish();
                break;
        }
    }


    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(HelpContract.Presenter presenter) {

    }
}
