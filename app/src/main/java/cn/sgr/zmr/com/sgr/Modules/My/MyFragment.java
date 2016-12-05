package cn.sgr.zmr.com.sgr.Modules.My;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.Login.LoginActivity;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Contract;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Fragment;
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
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.util.GlideCircleTransform;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.RoundImageView;

import static com.google.common.base.Preconditions.checkNotNull;

public class MyFragment extends BaseFragment implements MyContract.View {
    @BindView(R.id.iv_right)
    ImageView iv_right;


    @BindView(R.id.top_view_back)
    ImageView top_view_back;


    @BindView(R.id.top_view_title)
    TextView top_view_title;


    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;


    @BindView(R.id.nickname)
    TextView nickname;

    @BindView(R.id.phone)
    TextView Tvphone;




    @BindView(R.id.rel_my)
    View   rel_my;

    @BindView(R.id.rel_child)
    View   rel_child;


    @BindView(R.id.rel_help)
    View   rel_help;

    @BindView(R.id.rel_more)
    View   rel_more;


    private MyContract.Presenter mPresenter;
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
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.drawable.ic_qrcode);
    }
    @OnClick({R.id.rel_more,R.id.rel_help,R.id.rel_child,R.id.rel_my,R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_more:
                Utils.toNextActivity(getActivity(), MoreActivity.class);
                break;
            case R.id.rel_help:
                Utils.toNextActivity(getActivity(), HelpActivity.class);;
                break;
          case R.id.iv_right:
                Utils.toNextActivity(getActivity(), MyQrCodeActivity.class);
                break;
           /*
            case R.id.connected_child:
                Utils.toNextActivity(getActivity(), ScanCodeActivity.class);
                break;*/
            case R.id.rel_child:
                Utils.toNextActivity(getActivity(), MyChildActivity.class);
                break;
            case R.id.rel_my:
                if (!UserInfo.getInstance(getActivity()).hasSignIn()) {//没有登录
                    Utils.toNextActivity(getActivity(), LoginActivity.class);
                }else{
                    Utils.toNextActivity(getActivity(), ProfileActivity.class);
                }

                break;
        }
    }

    //单例 模式
    public static MyFragment newInstance() {
        return new MyFragment();
    }
    //   构造方法
    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void showUserInfo(Context context,String nickName, String phone, String avatar) {
        if (!UserInfo.getInstance(getActivity()).hasSignIn()) {//没有登录
            nickname.setText(getString(R.string.set_unlogin));
            Tvphone.setVisibility(View.GONE);
        }else{

            if(phone!=null&&phone.length()<3){
                Tvphone.setVisibility(View.GONE);
            }else{
                Tvphone.setVisibility(View.VISIBLE);
                Tvphone.setText(phone);
            }
            Glide.with(getActivity()).load(avatar).error(R.drawable.no_avatar).dontAnimate().thumbnail(0.1f).transform(new GlideCircleTransform(getActivity())).into(iv_avatar);
//            Glide.with(context).load(avatar).into(iv_avatar);
            nickname.setText(nickName);
        }

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(MyContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
