package cn.sgr.zmr.com.sgr.Modules.Home.Location.NearByDrug;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.DrugAdapter;
import cn.sgr.zmr.com.sgr.Modules.My.More.Contract.ContractActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.Disclaimer.DisclaimerActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.Feedback.FeedbackActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.recycleView.XRecyclerView;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class DrugFragment extends BaseFragment implements DrugContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.xrecycleview)
    XRecyclerView mXRecyclerView;

    private DrugAdapter adapter;

    private DrugContract.Presenter mPresenter;

    //单例 模式
    public static DrugFragment newInstance() {
        return new DrugFragment();
    }
    //   构造方法
    public DrugFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drug_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.nearby_medice));
        top_view_back.setVisibility(View.VISIBLE);

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
    public void setPresenter(DrugContract.Presenter presenter) {

    }
}
