package cn.sgr.zmr.com.sgr.Modules.My.Profile.EditName;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import cn.sgr.zmr.com.sgr.Common.Login.LoginActivity;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyActivity;
import cn.sgr.zmr.com.sgr.Modules.My.More.RetsetPwd.RetsetPwdActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class EditNameActivity extends BaseActivity{
    @BindView(R.id.btn_save)
    Button btn_save;

    @BindView(R.id.et_my_name)
    EditText et_my_name;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_name_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        top_view_title.setText(getString(R.string.my_edit_name));
    }
    //监听按钮
    @OnClick({R.id.top_view_back,R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
               finish();
                break;
            case R.id.btn_save:
                Toast.makeText(this,"提交",Toast.LENGTH_SHORT).show();
                break;



        }
    }
}
