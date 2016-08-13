package cn.sgr.zmr.com.sgr.Modules.Home.Activity;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.R;

public class AddBaby_Activity extends Activity {
    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.btn_save)
    Button btn_save;

    @BindView(R.id.et_babysex)
    EditText et_babysex;

    @BindView(R.id.et_babyhigh)
    EditText et_babyhigh;

    @BindView(R.id.et_babyweight)
    EditText et_babyweight;

    @BindView(R.id.et_babyage)
    EditText et_babyage;

    @BindView(R.id.et_babyname)
    EditText et_babyname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);
        ButterKnife.bind(this);
        intiView();
    }
    @OnClick({R.id.top_view_back, R.id.iv_right,R.id.et_babyweight,R.id.et_babyage,R.id.et_babyhigh,R.id.et_babysex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                finish();
            case R.id.btn_save:
                Toast.makeText(AddBaby_Activity.this,"添加成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.et_babyweight:
                CloseKeyBoard(et_babyweight);
                break;

            case R.id.et_babyage:
                CloseKeyBoard(et_babyage);

                break;

            case R.id.et_babyhigh:
                CloseKeyBoard(et_babyhigh);
                break;

            case R.id.et_babysex:
                CloseKeyBoard(et_babysex);
                break;
        }
    }

    private void intiView() {
        top_view_title.setText(getResources().getString(R.string.add_baby));
        top_view_back.setVisibility(View.VISIBLE);

    }
    // 关闭键盘
    private void CloseKeyBoard(EditText edit) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }
}
