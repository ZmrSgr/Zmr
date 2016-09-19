package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.entity.Baby;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Baby_Adapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBaby_Activity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.MyDecoration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class BabyFragment extends BaseFragment implements BabyContract.View{
    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.empty_tip)
    TextView empty_tip;



    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.iv_right)
    ImageView iv_right;

    @BindView(R.id.device_list)
    RecyclerView recyclerView;

    Baby_Adapter adapter;

    BabyContract.Presenter mPresenter;

    List<Baby> babys=new ArrayList<>();
    //单例 模式
    public static BabyFragment newInstance() {
        return new BabyFragment();
    }
    //   构造方法
    public BabyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baby_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        top_view_title.setText(getResources().getString(R.string.my_baby));
        top_view_back.setVisibility(View.VISIBLE);
        top_view_left_text.setVisibility(View.GONE);
        top_view_right_text.setVisibility(View.GONE);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.drawable.btn_add_baby);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MyDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Baby_Adapter(getActivity(),new ArrayList<Baby>());
        adapter.setOnRecyclerViewListener(new Baby_Adapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent mIntent = new Intent(getActivity(),ChartActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(UtilKey.BABY_KEY,babys.get(position));
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @OnClick({R.id.top_view_back, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
               getActivity().finish();
                break;
            case R.id.iv_right:
                Utils.toNextActivity(getActivity(),AddBaby_Activity.class);
                break;
        }
    }

    @Override
    public void showProgressDialog(FragmentManager manager) {
        super.showProgressDialog(manager);
    }

    @Override
    public void cancelProgressDialog() {
        super.cancelProgressDialog();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showBaby(List<Baby> babys) {
        this.babys=babys;
        if(babys.size()>0){
            empty_tip.setVisibility(View.GONE);
        }else{
            empty_tip.setVisibility(View.VISIBLE);
        }
        adapter.applyData(babys);

    }

    @Override
    public void delBaby(int position) {

    }

    @Override
    public void nextActivity(String address) {

    }

    @Override
    public void setPresenter(BabyContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
