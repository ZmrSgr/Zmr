package cn.sgr.zmr.com.sgr.Modules.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.R;

public class HomeFragment extends BaseFragment {


    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.home_temp)
    JellyToggleButton home_temp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        intView();
        return view;
    }
    private void intView() {
        top_view_title.setText("体温检测");
        top_view_back.setVisibility(View.GONE);
        top_view_left_text.setVisibility(View.VISIBLE);
        top_view_left_text.setText("设备");
        top_view_right_text.setVisibility(View.VISIBLE);
        top_view_right_text.setText("电子病历");


        home_temp.setLeftBackgroundColor( ContextCompat.getColor(getActivity(), R.color.them_bg));
        home_temp.setRightBackgroundColor( ContextCompat.getColor(getActivity(), R.color.them_bg));

        home_temp.setTextSize(40);
        home_temp.setTextColor( ContextCompat.getColor(getActivity(),R.color.withe));
        home_temp.setTextMarginLeft(20);
        home_temp.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {


                }
                if (state.equals(State.RIGHT)) {

                }

            }
        });

    }
}
