package cn.sgr.zmr.com.sgr.Modules.Home.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Baby_Adapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.MyDecoration;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class BabyActivity extends Activity {
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

    @BindView(R.id.device_list)
   RecyclerView recyclerView;

    Baby_Adapter adapter;

    private List<Baby> babyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby);
        ButterKnife.bind(this);
        initVew();


    }

    private void initVew() {
        top_view_title.setText(getResources().getString(R.string.my_baby));
        top_view_back.setVisibility(View.VISIBLE);
        top_view_left_text.setVisibility(View.GONE);
        top_view_right_text.setVisibility(View.GONE);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.drawable.btn_add_baby);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MyDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        initData();
        adapter = new Baby_Adapter(this,babyList);
        adapter.setOnRecyclerViewListener(new Baby_Adapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position, View v) {
                Utils.toNextActivity(BabyActivity.this,Chart.class);
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
                finish();
            case R.id.iv_right:
                Utils.toNextActivity(BabyActivity.this,AddBaby_Activity.class);
                break;
        }
    }

    private void initData() {
        for (int i=0; i < 5; ++i) {
            Baby baby = new Baby("小宝"+i,"两个月","男","5kg","45cm","设备名称");
            babyList.add(baby);
        }
    }
}
