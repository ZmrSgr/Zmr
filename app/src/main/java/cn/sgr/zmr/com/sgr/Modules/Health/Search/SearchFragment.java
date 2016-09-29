package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.entity.Baby;
import com.bean.entity.SearchRecent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Health.Adapter.SearchRecentAdapter;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.HealthModel;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Result;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Search;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.SearchResult;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Baby_Adapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBaby_Activity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.MyDecoration;
import okhttp3.Call;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class SearchFragment extends BaseFragment implements SearchContract.View{
    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.ivDeleteText)
    ImageView ivDeleteText;


    @BindView(R.id.btnSearch)
    TextView btnSearch;


    @BindView(R.id.recent_list)
    ListView recent_list;



    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    SearchContract.Presenter mPresenter;
    SearchRecentAdapter searchRecentAdapter;

    @BindView(R.id.rv_tie)
    RecyclerView mNewsListView;


    //单例 模式
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
    //   构造方法
    public SearchFragment() {
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
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mNewsListView.getContext());
        mNewsListView.setLayoutManager(linearLayoutManager);

        mLoadAdapter=new BlogAdapter(getActivity(),mNewsListEntity);
        mNewsListView.setAdapter(mLoadAdapter);
        mNewsListView.setVerticalScrollBarEnabled(false);
       /* mNewsListView.addOnScrollListener(new RecyclerViewLoadMoreListener(linearLayoutManager, this, 0));
        mNewsListView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );*/

//        top_view_title.setText(getResources().getString(R.string.health_search));
//        top_view_back.setVisibility(View.VISIBLE);
        etSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    @OnClick({R.id.ivDeleteText,R.id.top_view_back,R.id.btnSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDeleteText:
                etSearch.setText("");
                break;
            case R.id.top_view_back:
                getActivity().finish();
                break;
            case R.id.btnSearch:
                mPresenter.SearchAsk(etSearch.getText().toString());
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
    public void showSearchRecent(List<SearchRecent> datas) {

        if (datas != null) {
            System.out.println("datas"+datas.size());
            searchRecentAdapter=new SearchRecentAdapter(getActivity(),datas);
            recent_list.setAdapter(searchRecentAdapter);
            searchRecentAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }



    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
