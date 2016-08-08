package cn.sgr.zmr.com.sgr.Modules.Messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.R;

public class MessageFragment extends BaseFragment {
    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);

        ButterKnife.bind(this, view);
        intView();

        return view;

    }
    private void intView() {
        top_view_title.setText("消息中心");
        top_view_back.setVisibility(View.GONE);

        top_view_right_text.setVisibility(View.GONE);
    }
}
