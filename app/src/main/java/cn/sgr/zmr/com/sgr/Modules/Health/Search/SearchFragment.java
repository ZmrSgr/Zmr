package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import cn.sgr.zmr.com.sgr.Modules.Health.Adapter.Tie_Adapter;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.HealthModel;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Result;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Search;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.SearchResult;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Tie;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Baby_Adapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBaby_Activity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.MyDecoration;
import cn.sgr.zmr.com.sgr.View.RecyclerViewHeader;
import cn.sgr.zmr.com.sgr.View.TextViewExpandableAnimation;
import okhttp3.Call;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class SearchFragment extends BaseFragment implements SearchContract.View,Tie_Adapter.OnRecyclerViewListener{
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


    TextViewExpandableAnimation recycle_title_text;


    Tie_Adapter tie_adapter;

    boolean isvoice =false;

    SearchResult Tempresult;



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



    @OnClick({R.id.ivDeleteText,R.id.top_view_back,R.id.btnSearch,R.id.etSearch})
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

            case R.id.etSearch:
               showRecentView();
                break;
        }
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(getFragmentManager());

    }

    @Override
    public void cancelProgressDialogs() {
        cancelProgressDialog();
    }

    @Override
    public void showSearchRecent(final List<SearchRecent> datas) {

        if (datas != null) {


            searchRecentAdapter=new SearchRecentAdapter(getActivity(),datas);
            recent_list.setAdapter(searchRecentAdapter);
            searchRecentAdapter.notifyDataSetChanged();
            recent_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPresenter.SearchAsk(datas.get(position).getTitle());
                }
            });
        }

    }

    @Override
    public void showSearchResult(SearchResult result) {
        Tempresult=result;
        if (result != null) {
            mNewsListView.setHasFixedSize(true);
            mNewsListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            tie_adapter=new Tie_Adapter();
            mNewsListView.setAdapter(tie_adapter);

            mNewsListView.setVerticalScrollBarEnabled(false);
            tie_adapter.addDatas(result.getList());
            tie_adapter.setOnRecyclerViewListener(this);
            View header = LayoutInflater.from(getActivity()).inflate(R.layout.header,mNewsListView, false);
            tie_adapter.setHeaderView(header);

            recycle_title_text=(TextViewExpandableAnimation)header.findViewById(R.id.tv_expand);
            recycle_title_text.setText("    "+Html.fromHtml(result.getPiece()));
            recycle_title_text.setVoiceClickListener(new TextViewExpandableAnimation.VoiceClickListener() {
                @Override
                public void ImageVice(View v) {
                    if(isvoice){
                        recycle_title_text.setDrawableVoiceImage(R.drawable.wsdk_drawable_rg_ic_voice_off);
                    }else{
                        isvoice=true;
                        recycle_title_text.setDrawableVoiceImage(R.drawable.wsdk_drawable_rg_ic_voice_on);
                    }

                }
            });
        }


    }

    @Override
    public void showRecentView() {
        recent_list.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissRecentView() {
        recent_list.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }



    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void onItemClick(int position, View v) {
        System.out.println("dianji");
        if(Tempresult!=null){
            if(Tempresult.getList()!=null){
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailTieActivity.class);
                intent.putExtra(UtilKey.TIE_KEY, Tempresult.getList().get(position).getUrl());
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
