package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.HealthModel;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Result;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Search;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.SearchResult;
import cn.sgr.zmr.com.sgr.Modules.My.More.MoreFragment;
import cn.sgr.zmr.com.sgr.Modules.My.More.MorePresenter;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import okhttp3.Call;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        initPV();


    }

    //初始化presenter和view
    private void initPV() {
        SearchFragment searchFragment =
                (SearchFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (searchFragment == null) {
            searchFragment = searchFragment.newInstance();


  /*          if (getIntent().getExtras() != null
                    &&getIntent().getExtras()
                    .containsKey(UtilKey.TIE_KEY)) {

                Intent intent = getIntent();
                // 获取数据
                Bundle bundle = getIntent().getExtras();
                System.out.println("跳转过来的内容"+bundle.getString(UtilKey.TIE_KEY));

                mPresenter.SearchAsk(bundle.getString(UtilKey.TIE_KEY));

            }else{

            }
*/


            Utils.addFragmentToActivity(getFragmentManager(),searchFragment, R.id.contentFrame);
        }
        // Create the presenter
        new SearchPresenter(this,searchFragment);

    }
}
