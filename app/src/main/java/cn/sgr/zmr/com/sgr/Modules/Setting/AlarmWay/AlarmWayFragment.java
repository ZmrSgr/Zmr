package cn.sgr.zmr.com.sgr.Modules.Setting.AlarmWay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.R;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class AlarmWayFragment extends BaseFragment implements AlarmWayContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    private AlarmWayContract.Presenter mPresenter;

    @BindView(R.id.jtb_fever)
    JellyToggleButton   jtb_fever;

    @BindView(R.id.jtb_lost)
    JellyToggleButton   jtb_lost;

    //单例 模式
    public static AlarmWayFragment newInstance() {
        return new AlarmWayFragment();
    }
    //   构造方法
    public AlarmWayFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarmway_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.set_type));
        top_view_back.setVisibility(View.VISIBLE);

        jtb_lost.setLeftBackgroundColor(  getResources().getColor(R.color.them_bg));
        jtb_lost.setRightBackgroundColor(  getResources().getColor(R.color.them_bg));
        jtb_fever.setLeftBackgroundColor(   getResources().getColor(R.color.them_bg));
        jtb_fever.setRightBackgroundColor(   getResources().getColor(R.color.them_bg));
        jtb_lost.setTextSize(40);
        jtb_fever.setTextSize(40);
        jtb_fever.setTextColor(  getResources().getColor(R.color.them_bg));
        jtb_lost.setTextColor(  getResources().getColor(R.color.them_bg));
        jtb_fever.setTextMarginLeft(20);
        jtb_fever.setTextMarginRight(20);
        jtb_lost.setTextMarginLeft(20);
        jtb_lost.setTextMarginRight(20);

        jtb_fever.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {


                }
                if (state.equals(State.RIGHT)) {

                }

            }
        });

        jtb_lost.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {

                    Toast.makeText(getActivity(), "Left!", Toast.LENGTH_SHORT).show();
                }
                if (state.equals(State.RIGHT)) {
                    Toast.makeText(getActivity(), "RIGHT!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //监听按钮
    @OnClick({R.id.top_view_back})
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
    public void setPresenter(AlarmWayContract.Presenter presenter) {

    }
}
