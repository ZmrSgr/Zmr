package cn.sgr.zmr.com.sgr.Common.Register;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Common.Login.LoginFragment;
import cn.sgr.zmr.com.sgr.Common.Login.LoginPresenter;
import cn.sgr.zmr.com.sgr.Common.MainActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class Register_Activity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        initPV();
    }

    //初始化presenter和view
    private void initPV() {
        Register_Fragment registerFragment =
                (Register_Fragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (registerFragment == null) {
            registerFragment = registerFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),registerFragment, R.id.contentFrame);
        }
        // Create the presenter
        new Register_Presenter(this,registerFragment);

    }

}
