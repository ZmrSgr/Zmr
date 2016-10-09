package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bean.entity.Baby;
import com.bean.entity.SearchRecent;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Health.Model.HealthModel;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Result;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Search;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.SearchResult;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import okhttp3.Call;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class SearchPresenter implements SearchContract.Presenter{

    @NonNull
    private final SearchContract.View registerView;


    private Context context;
    private DaoCacheManage daoManage;

    public SearchPresenter(Context contexts, @NonNull SearchContract.View registerView) {
        this.registerView = registerView;
        this.registerView.setPresenter(this);
        this.daoManage=  new DaoCacheManage(contexts);
        this.context=contexts;
    }


    @Override
    public void start() {
        getSearchRecent();
    }


    @Override
    public void AddSearchRecent(SearchRecent data) {
       daoManage.insertSearch(data);
    }

    @Override
    public void getSearchRecent() {
        registerView.showSearchRecent(daoManage.listAllSearchRecent());

    }

    @Override
    public void delSearchRecent(SearchRecent data) {
        daoManage.deleteSearch(data);
    }

    @Override
    public void SearchAsk(String keyword) {
     //保存到最近搜索中
        SearchRecent searchRecent=new SearchRecent();
        searchRecent.setTitle(keyword);
        AddSearchRecent(searchRecent);
        //访问网络获取结果
        registerView.showProgressDialog();
        HealthModel healthmodel=new HealthModel();
        healthmodel.getSearchList(context, keyword, new HttpRequestCallback<Result<SearchResult>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }
            @Override
            public void onResponse(Result<SearchResult> user) {
                registerView.dismissRecentView();
                if(user.code==200){//表示获取到数据
                    registerView.showSearchResult(user.data);
                }else{
                    Toast.makeText(context, R.string.search_nothing,Toast.LENGTH_LONG).show();
                }

                registerView.cancelProgressDialogs();


            }

            @Override
            public void onFailure(Call call, HttpException e) {
                registerView.cancelProgressDialogs();
            }
        });


    }
}
