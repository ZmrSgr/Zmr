package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddHistory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBabyFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBabyPresenter;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class AddHisoryActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_history_fragment);
        ButterKnife.bind(this);
        initPV();
    }
    //初始化presenter和view
    private void initPV() {

        AddHistoryFragment babyFragment = (AddHistoryFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (babyFragment == null) {
            babyFragment = babyFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),babyFragment, R.id.contentFrame);
        }
        // Create the presenter
        new AddHistoryPresenter(this,babyFragment);
    }


//时间选择
    private void showTime(){


    }

}
