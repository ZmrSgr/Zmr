package cn.sgr.zmr.com.sgr.Modules.Home.Activity;

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
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Config;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.RoundImageView;

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

    RoundImageView add_babay_iv;


    OptionsPickerView sexOptions;
    OptionsPickerView highOptions;
    OptionsPickerView weightOptions;
    TimePickerView pvTime;

    private ArrayList<String> Sexoptions1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> Sexoptions2Items = new ArrayList<>();

    private ArrayList<String> Highoptions1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> Highoptions2Items = new ArrayList<>();

    private ArrayList<String> Weightoptions1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> Weightoptions2Items = new ArrayList<>();

    private Dialog mDialog;
    private File mFileDir;
    private File mFile;
    private String mFileName = "";
    private final int REQUEST_CAMERA = 1;
    private final int REQUEST_PHOTO = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);
        ButterKnife.bind(this);
        intiView();
        initViewData();
    }
    //初始化控件数据
    private void initViewData() {




//        性别
        Sexoptions1Items.add("男");
        Sexoptions1Items.add("女");

//        身高
        for (int i = 0; i < 20; i++) {
            ArrayList<String> options2Items_high=new ArrayList<>();
            Highoptions1Items.add(i+"");
            for (int i1 = 0; i1 < 10; i1++) {
                options2Items_high.add(i1+"");
            }
            Highoptions2Items.add(options2Items_high);
        }

//       体重
        for (int i2 = 2; i2 < 70; i2++) {
            ArrayList<String> options2Items_weigh=new ArrayList<>();
            Weightoptions1Items.add(i2+"");
            for (int i3 = 0; i3 < 10; i3++) {
                options2Items_weigh.add(i3+"");
            }
            Weightoptions2Items.add(options2Items_weigh);
        }

    }

//弹出选择照片的入口
    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.photo_dialog, null);
        mDialog = new Dialog(this, R.style.dialog);
        mDialog.setContentView(view);

        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
        LinearLayout requsetCameraLayout = (LinearLayout) view
                .findViewById(R.id.register_camera);
        LinearLayout requestPhotoLayout = (LinearLayout) view
                .findViewById(R.id.register_photo);

        requsetCameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
                if (mFileDir == null) {
                    mFileDir = new File(Config.FILEPATH + "img/");
                    if (!mFileDir.exists()) {
                        mFileDir.mkdirs();
                    }
                }
                mFileName = Config.FILEPATH + "img/" + "temp.jpg";
                mFile = new File(mFileName);
                Uri imageuri = Uri.fromFile(mFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
                intent.putExtra("return-data", false);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });

        requestPhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
                Intent picture = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, REQUEST_PHOTO);

            }
        });
    }

    @OnClick({R.id.top_view_back, R.id.iv_right,R.id.et_babyweight,R.id.et_babyage,R.id.et_babyhigh,R.id.et_babysex,R.id.btn_save,R.id.add_babay_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_babay_iv:
                showDialog();
                break;

            case R.id.top_view_back:
                finish();
                break;
            case R.id.btn_save:
                Toast.makeText(AddBaby_Activity.this,"添加成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.et_babyweight:
                CloseKeyBoard(et_babyweight);
                weightOptions = new OptionsPickerView(this);
                //三级联动效果
                weightOptions.setPicker(Weightoptions1Items, Weightoptions2Items, true);
                //设置选择的三级单位
                weightOptions.setLabels(".", "kg");
                weightOptions.setTitle("体重（kg）");
                weightOptions.setCyclic(false, true, true);
                //设置默认选中的三级项目
                //监听确定选择按钮
                weightOptions.setSelectOptions(1, 1);
                weightOptions.show();
                weightOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        //返回的分别是三个级别的选中位置
                        String tx = Weightoptions1Items.get(options1)+"."
                                + Weightoptions2Items.get(options1).get(option2)+"kg";
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        et_babyweight.setText(tx);
//                        vMasker.setVisibility(View.GONE);
                    }
                });



                break;

            case R.id.et_babyage:
                CloseKeyBoard(et_babyage);
                pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
                //控制时间范围
                Calendar calendar = Calendar.getInstance();
                pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦

                pvTime.setTime(new Date());
                pvTime.setCyclic(false);
                pvTime.setCancelable(true);
                pvTime.show();
                //时间选择后回调
                pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                    @Override
                    public void onTimeSelect(Date date) {
                        et_babyage.setText(Utils.getTime(date));
                    }
                });

                break;

            case R.id.et_babyhigh:
                CloseKeyBoard(et_babyhigh);

                highOptions = new OptionsPickerView(this);
                //三级联动效果
                highOptions.setPicker(Highoptions1Items, Highoptions2Items, true);
                //设置选择的三级单位
                highOptions.setLabels("", "cm");
                highOptions.setTitle("身高");
                highOptions.setCyclic(false, true, true);
                //设置默认选中的三级项目
                //监听确定选择按钮
                highOptions.setSelectOptions(1, 1);
                highOptions.show();
                highOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        //返回的分别是三个级别的选中位置
                        String tx = Highoptions1Items.get(options1)
                                + Highoptions2Items.get(options1).get(option2)+"cm";
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        et_babyhigh.setText(tx);
//                        vMasker.setVisibility(View.GONE);
                    }
                });

                break;

            case R.id.et_babysex:
                CloseKeyBoard(et_babysex);
                sexOptions = new OptionsPickerView(this);
                //三级联动效果
                sexOptions.setPicker(Sexoptions1Items);
                //设置选择的三级单位

                sexOptions.setTitle("性别");
                sexOptions.setCyclic(false, true, true);
                //设置默认选中的三级项目
                //监听确定选择按钮
                sexOptions.setSelectOptions(1, 1);
                sexOptions.show();
                sexOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        //返回的分别是三个级别的选中位置
                        String tx = Sexoptions1Items.get(options1)
                              ;
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        et_babysex.setText(tx);
//                        vMasker.setVisibility(View.GONE);
                    }
                });
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
