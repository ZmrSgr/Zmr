package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyPresenter;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Config;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.RoundImageView;

public class AddBaby_Activity extends BaseActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        ButterKnife.bind(this);
        initPV();

    }
    //初始化presenter和view
    private void initPV() {
        AddBabyFragment babyFragment = (AddBabyFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (babyFragment == null) {
            babyFragment = babyFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),babyFragment, R.id.contentFrame);
        }
        // Create the presenter
        new AddBabyPresenter(this,babyFragment);
    }

}
