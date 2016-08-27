package cn.sgr.zmr.com.sgr.Modules.Setting.Device;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.MyItemRecyclerViewAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.DummyContent;
import cn.sgr.zmr.com.sgr.Modules.Home.View.LoadMoreRecyclerView;
import cn.sgr.zmr.com.sgr.R;

public class DeviceActivity extends BaseActivity {
    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;

    @BindView(R.id.device_list)
    LoadMoreRecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.iv_right)
    ImageView iv_right;


    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        top_view_title.setText("设备信息");
        top_view_back.setVisibility(View.VISIBLE);
        top_view_left_text.setVisibility(View.GONE);
        top_view_right_text.setVisibility(View.GONE);


        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setColorSchemeColors(  getResources().getColor(R.color.them_bg));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                page = 0;
                myItemRecyclerViewAdapter.setData(DummyContent.generyData(page));
                recyclerView.setAutoLoadMoreEnable(DummyContent.hasMore(page));
                myItemRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(DummyContent.generyData(page));
        recyclerView.setAdapter(myItemRecyclerViewAdapter);
        recyclerView.setAutoLoadMoreEnable(true);
        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        myItemRecyclerViewAdapter.addDatas(DummyContent.generyData(++page));
                        recyclerView.notifyMoreFinish(DummyContent.hasMore(page));
                    }
                }, 2000);
            }
        });
        myItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.top_view_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                finish();
                break;
        }
    }
}
