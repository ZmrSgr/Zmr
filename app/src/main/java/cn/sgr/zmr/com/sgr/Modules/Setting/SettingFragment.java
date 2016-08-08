package cn.sgr.zmr.com.sgr.Modules.Setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.R;

public class SettingFragment extends BaseFragment {
    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.rel_fever_temp)
    RelativeLayout rel_fever_temp;


    @BindView(R.id.jtb_fever)
    JellyToggleButton   jtb_fever;

    @BindView(R.id.jtb_lost)
    JellyToggleButton   jtb_lost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        intView();
        return view;

    }

    private void intView() {
        top_view_title.setText("设置");
        top_view_back.setVisibility(View.GONE);
        top_view_right_text.setVisibility(View.GONE);
        jtb_lost.setLeftBackgroundColor( ContextCompat.getColor(getActivity(), R.color.them_bg));
        jtb_lost.setRightBackgroundColor( ContextCompat.getColor(getActivity(), R.color.them_bg));
        jtb_fever.setLeftBackgroundColor( ContextCompat.getColor(getActivity(), R.color.them_bg));
        jtb_fever.setRightBackgroundColor( ContextCompat.getColor(getActivity(), R.color.them_bg));
        jtb_lost.setTextSize(40);
        jtb_fever.setTextSize(40);
        jtb_fever.setTextColor( ContextCompat.getColor(getActivity(),R.color.withe));
        jtb_lost.setTextColor( ContextCompat.getColor(getActivity(),R.color.withe));
        jtb_fever.setTextMarginLeft(20);
        jtb_fever.setTextMarginRight(20);

        jtb_fever.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    rel_fever_temp.setVisibility(View.VISIBLE);

                }
                if (state.equals(State.RIGHT)) {
                    rel_fever_temp.setVisibility(View.GONE);
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

}
