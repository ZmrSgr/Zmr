package cn.sgr.zmr.com.sgr.Modules.Home.Location.NearByDrug;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.DrugAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Location.CommonMapActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.Drug;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.View.recycleView.XRecyclerView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class DrugFragment extends BaseFragment implements DrugContract.View,DrugAdapter.OnRecyclerViewListener{

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
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drug_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        mPresenter.getDrugList(getActivity().getIntent().getStringExtra(UtilKey.DRUG_LAT), getActivity().getIntent().getStringExtra(UtilKey.DRUG_LNG));
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.nearby_medice));
        top_view_back.setVisibility(View.VISIBLE);
        // 设置 刷新的方向
        mXRecyclerView.setDirection(SwipyRefreshLayoutDirection.BOTH);
        // 是否自动加载
        mXRecyclerView.setAutoLoadEnable(false);
        adapter = new DrugAdapter(getActivity());
        mXRecyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewListener(this);
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
    public void showDrugList(List<Drug> drugStoreList) {
        adapter.addDatas(drugStoreList);
    }
    @Override
    public void ShowMoreList() {

    }
    @Override
    public void setPresenter(DrugContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getActivity().getIntent();
        // 获取数据
        Bundle bundle = getActivity().getIntent().getExtras();
        String  lat=  bundle.getString(UtilKey.DRUG_LAT);
        String  lng=  bundle.getString(UtilKey.DRUG_LNG);
        if(lat!=null){//表示在上一个界面定位成功
            mPresenter.getDrugList(lat,lng);
        }else{//重新定位，在获取数据，在显示
        }
    }
//   列表选择监听
    @Override
    public void onItemClick(Drug data, View v) {
        Intent mIntent = new Intent(getActivity(),CommonMapActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(UtilKey.DRUG_DATA_KEY,data);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }
    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
