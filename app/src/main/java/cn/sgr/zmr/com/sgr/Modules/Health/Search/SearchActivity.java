package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.os.Bundle;

import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

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
