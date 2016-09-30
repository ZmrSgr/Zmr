package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.app.FragmentManager;

import com.bean.entity.Baby;
import com.bean.entity.SearchRecent;

import java.util.List;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.SearchResult;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public interface SearchContract {



    interface View extends BaseView<Presenter> {


        boolean isActive();//目的是为了解决内存泄漏

        void showProgressDialog();//显示进度条

        void cancelProgressDialogs();//隐藏进度条

        void showSearchRecent(List<SearchRecent> datas);//

        void showSearchResult(SearchResult result);

        void showRecentView();//显示最近的listview

        void dismissRecentView();//隐藏最近的listView


    }

    interface Presenter extends BasePresenter {
        void AddSearchRecent(SearchRecent data);//插入最近搜索数据
        void getSearchRecent();//获得最近输入列表
        void delSearchRecent(SearchRecent data);//删除一条最近搜索记录
        void SearchAsk(String keyword);//搜索数据
    }

}
