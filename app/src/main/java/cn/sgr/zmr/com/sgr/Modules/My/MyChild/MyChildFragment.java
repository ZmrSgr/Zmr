package cn.sgr.zmr.com.sgr.Modules.My.MyChild;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.entity.Baby;
import com.bean.entity.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Baby_Adapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBaby_Activity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.Contract.ContractActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.Disclaimer.DisclaimerActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.Feedback.FeedbackActivity;
import cn.sgr.zmr.com.sgr.Modules.My.MyChild.BabyOfChild.BabyOfChildActivity;
import cn.sgr.zmr.com.sgr.Modules.My.MyChild.adapter.User_Adapter;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.MyDecoration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class MyChildFragment extends BaseFragment implements MyChildContract.View{

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;


    @BindView(R.id.top_view_back)
    ImageView top_view_back;

/*    @BindView(R.id.iv_right)
    ImageView iv_right;*/

    @BindView(R.id.device_list)
    RecyclerView recyclerView;

    User_Adapter adapter;

    List<User> users=new ArrayList<>();



    private MyChildContract.Presenter mPresenter;

    //单例 模式
    public static MyChildFragment newInstance() {
        return new MyChildFragment();
    }
    //   构造方法
    public MyChildFragment() {
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
        View view = inflater.inflate(R.layout.mychild_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件

        private void initView() {
            top_view_title.setText(getResources().getString(R.string.my_child));
            top_view_back.setVisibility(View.VISIBLE);
            top_view_left_text.setVisibility(View.GONE);
            top_view_right_text.setVisibility(View.GONE);


            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new MyDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new User_Adapter(getActivity(),new ArrayList<User>());
            adapter.setOnRecyclerViewListener(new User_Adapter.OnRecyclerViewListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent mIntent = new Intent(getActivity(),BabyOfChildActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable(UtilKey.BABY_KEY,users.get(position));
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
    public void showBaby(List<User> users) {
        this.users=users;
        adapter.applyData(users);
    }

    @Override
    public void setPresenter(MyChildContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
