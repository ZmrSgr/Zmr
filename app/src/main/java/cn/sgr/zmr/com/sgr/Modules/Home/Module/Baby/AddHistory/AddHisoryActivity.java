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
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class AddHisoryActivity extends Activity {
    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.btn_save)
    Button btn_save;

    @BindView(R.id.et_baby_time)
   EditText et_baby_time;

    @BindView(R.id.et_baby_yao)
    EditText et_baby_yao;

    @BindView(R.id.et_baby_wuli)
    EditText et_baby_wuli;

    @BindView(R.id.et_baby_tem)
    EditText et_baby_tem;

    OptionsPickerView highOptions;
    TimePickerView pvTime;

    private ArrayList<String> Highoptions1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> Highoptions2Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hisory);
        ButterKnife.bind(this);
        initView();
    }


    @OnClick({R.id.top_view_back, R.id.btn_save,R.id.et_baby_tem,R.id.et_baby_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                break;

            case R.id.et_baby_time:
                CloseKeyBoard(et_baby_time);
                pvTime = new TimePickerView(this, TimePickerView.Type.MONTH_DAY_HOUR_MIN);
                //控制时间范围
                Calendar calendar = Calendar.getInstance();
//                pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
//                pvTime.setr
                pvTime.setTime(new Date());
                pvTime.setCyclic(false);
                pvTime.setCancelable(true);
                pvTime.show();
                //时间选择后回调
                pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                    @Override
                    public void onTimeSelect(Date date) {
                        et_baby_time.setText(Utils.getTime_hm(date));
                    }
                });

                break;

            case R.id.top_view_back:
                finish();
                break;

            case R.id.et_baby_tem:
                CloseKeyBoard(et_baby_tem);

                highOptions = new OptionsPickerView(this);
                //三级联动效果
                highOptions.setPicker(Highoptions1Items, Highoptions2Items, true);
                //设置选择的三级单位
                highOptions.setLabels(".", getResources().getString(R.string.shishi_unit));
                highOptions.setTitle( getResources().getString(R.string.baby_fever_tem));
                highOptions.setCyclic(false, true, true);
                //设置默认选中的三级项目
                //监听确定选择按钮
                highOptions.setSelectOptions(1, 1);
                highOptions.show();
                highOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        //返回的分别是三个级别的选中位置
                        String tx = Highoptions1Items.get(options1)+"."
                                + Highoptions2Items.get(options1).get(option2)+ getResources().getString(R.string.shishi_unit);
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        et_baby_tem.setText(tx);
//                        vMasker.setVisibility(View.GONE);
                    }
                });

                break;
        }
    }

    private void initView() {
        top_view_title.setText(getResources().getString(R.string.baby_add_history));
        top_view_back.setVisibility(View.VISIBLE);


        //        身高
        for (int i = 35; i < 45; i++) {
            ArrayList<String> options2Items_high=new ArrayList<>();
            Highoptions1Items.add(i+"");
            for (int i1 = 0; i1 < 10; i1++) {
                options2Items_high.add(i1+"");
            }
            Highoptions2Items.add(options2Items_high);
        }

    }

    // 关闭键盘
    private void CloseKeyBoard(EditText edit) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

//时间选择
    private void showTime(){


    }

}
