package cn.sgr.zmr.com.sgr.Modules.Home.Location.NearByStore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Health.Search.DetailTieActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.StoreAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.Store;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.StoreResult;
import cn.sgr.zmr.com.sgr.Modules.My.More.Contract.ContractActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.Disclaimer.DisclaimerActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.Feedback.FeedbackActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.recycleView.XRecyclerView;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class StoreFragment extends BaseFragment implements StoreContract.View ,StoreAdapter.OnRecyclerViewListener{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.xrecycleview)
    XRecyclerView mXRecyclerView;

    StoreAdapter mStoreAdapter;

    private StoreContract.Presenter mPresenter;

    //单例 模式
    public static StoreFragment newInstance() {
        return new StoreFragment();
    }

    //   构造方法
    public StoreFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
//        String.valueOf(getActivity().getIntent().getDoubleExtra(UtilKey.STORE_LAT, 0))
//        String.valueOf(getActivity().getIntent().getDoubleExtra(UtilKey.STORE_LNG, 0))
        mPresenter.getStoreList("", "");
        return view;
    }

    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.nearby_store));
        top_view_back.setVisibility(View.VISIBLE);

        mStoreAdapter = new StoreAdapter(getActivity());
        mXRecyclerView.setAdapter(mStoreAdapter);
        mStoreAdapter.setOnRecyclerViewListener(this);
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
    public void showNearByStore(List<StoreResult.HospitalBean> hospitalList) {
        mStoreAdapter.addDatas(hospitalList);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void setPresenter(StoreContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onItemClick(StoreResult.HospitalBean data, View v) {

        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailTieActivity.class);
        intent.putExtra(UtilKey.TIE_KEY, data.getUrl());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
