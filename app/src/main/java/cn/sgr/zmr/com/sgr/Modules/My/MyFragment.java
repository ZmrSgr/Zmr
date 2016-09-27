package cn.sgr.zmr.com.sgr.Modules.My;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Location.LocationActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.AlarmWay.AlarmWayActivity;
import cn.sgr.zmr.com.sgr.Modules.My.Help.HelpActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.MoreActivity;
import cn.sgr.zmr.com.sgr.Modules.My.MyChild.MyChildActivity;
import cn.sgr.zmr.com.sgr.Modules.My.Profile.ProfileActivity;
import cn.sgr.zmr.com.sgr.Modules.My.QrCode.MyQrCodeActivity;
import cn.sgr.zmr.com.sgr.Modules.My.QrCode.ScanCodeActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class MyFragment extends BaseFragment {
    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.rel_my)
    View   rel_my;

    @BindView(R.id.rel_child)
    View   rel_child;

    @BindView(R.id.connected_child)
    View   connected_child;

    @BindView(R.id.rel_qr_code)
    View   rel_qr_code;

    @BindView(R.id.rel_help)
    View   rel_help;

    @BindView(R.id.rel_more)
    View   rel_more;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    private void initView() {
        top_view_title.setText(getString(R.string.my));
        top_view_back.setVisibility(View.GONE);
    }
    @OnClick({R.id.rel_more,R.id.rel_help,R.id.rel_qr_code,R.id.connected_child,R.id.rel_child,R.id.rel_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_more:
                Utils.toNextActivity(getActivity(), MoreActivity.class);
                break;
            case R.id.rel_help:
                Utils.toNextActivity(getActivity(), HelpActivity.class);;
                break;
            case R.id.rel_qr_code:
                Utils.toNextActivity(getActivity(), MyQrCodeActivity.class);
                break;
            case R.id.connected_child:
                Utils.toNextActivity(getActivity(), ScanCodeActivity.class);
                break;
            case R.id.rel_child:
                Utils.toNextActivity(getActivity(), MyChildActivity.class);
                break;
            case R.id.rel_my:
                Utils.toNextActivity(getActivity(), ProfileActivity.class);
                break;
        }
    }


}
