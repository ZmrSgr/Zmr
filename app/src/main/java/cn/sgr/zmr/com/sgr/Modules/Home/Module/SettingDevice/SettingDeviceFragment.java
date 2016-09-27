package cn.sgr.zmr.com.sgr.Modules.Home.Module.SettingDevice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.Modules.Home.Location.LocationActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Synchronize.SynchronizeActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.AlarmWay.AlarmWayActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Device.DeviceActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class SettingDeviceFragment extends BaseFragment implements SettingDeviceContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;



    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.rel_fever_temp)
    RelativeLayout rel_fever_temp;


    @BindView(R.id.jtb_fever)
    JellyToggleButton jtb_fever;

    @BindView(R.id.jtb_lost)
    JellyToggleButton   jtb_lost;

    @BindView(R.id.line)
    View   line;

    @BindView(R.id.line1)
    View   line1;

    @BindView(R.id.rel_my_baby)
    View   rel_my_baby;

    @BindView(R.id.item_settings_device)
    View   item_settings_device;

    @BindView(R.id.rel_fever_way)
    View   rel_fever_way;

    @BindView(R.id.rel_location)
    View   rel_location;

    @BindView(R.id.text_tem)
    TextView   text_tem;

    @BindView(R.id.text_way)
    TextView   text_way;





    OptionsPickerView weightOptions;
    private ArrayList<String> Weightoptions1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> Weightoptions2Items = new ArrayList<>();


    private SettingDeviceContract.Presenter mPresenter;

    //单例 模式
    public static SettingDeviceFragment newInstance() {
        return new SettingDeviceFragment();
    }
    //   构造方法
    public SettingDeviceFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getString(R.string.bottom_set));
        jtb_lost.setLeftBackgroundColor(  getResources().getColor(R.color.them_bg));
        jtb_lost.setRightBackgroundColor(  getResources().getColor(R.color.them_bg));
        jtb_fever.setLeftBackgroundColor(   getResources().getColor(R.color.them_bg));
        jtb_fever.setRightBackgroundColor(   getResources().getColor(R.color.them_bg));
        jtb_lost.setTextSize(40);
        jtb_fever.setTextSize(40);
        jtb_fever.setTextColor(  getResources().getColor(R.color.them_bg));
        jtb_lost.setTextColor(  getResources().getColor(R.color.them_bg));
        jtb_fever.setTextMarginLeft(20);
        jtb_fever.setTextMarginRight(20);
        jtb_lost.setTextMarginLeft(20);
        jtb_lost.setTextMarginRight(20);

        rel_fever_temp.setVisibility(View.GONE);
        rel_fever_way.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        line1.setVisibility(View.GONE);

        text_tem.setText(Setting.getInstance(getActivity()).getTemp());
        jtb_lost.setChecked(Setting.getInstance(getActivity()).IsLose());
        jtb_fever.setChecked(Setting.getInstance(getActivity()).IsAlarm());
        jtb_fever.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    Setting.getInstance(getActivity()).setAlarm("0");
                    rel_fever_temp.setVisibility(View.GONE);
                    rel_fever_way.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    line1.setVisibility(View.GONE);
                }
                if (state.equals(State.RIGHT)) {
                    Setting.getInstance(getActivity()).setAlarm("1");
                    rel_fever_temp.setVisibility(View.VISIBLE);
                    rel_fever_way.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    line1.setVisibility(View.VISIBLE);
                }

            }
        });

        jtb_lost.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    Setting.getInstance(getActivity()).setLose("0");
                    Toast.makeText(getActivity(), getString(R.string.close_lose), Toast.LENGTH_SHORT).show();
                }
                if (state.equals(State.RIGHT)) {
                    Setting.getInstance(getActivity()).setLose("1");
                    Toast.makeText(getActivity(), getString(R.string.open_lose), Toast.LENGTH_SHORT).show();
                }

            }
        });


        //       体重
        for (int i2 = 32; i2 < 45; i2++) {
            ArrayList<String> options2Items_weigh=new ArrayList<>();
            Weightoptions1Items.add(i2+"");
            for (int i3 = 0; i3 < 10; i3++) {
                options2Items_weigh.add(i3+"");
            }
            Weightoptions2Items.add(options2Items_weigh);
        }


    }

    @OnClick({R.id.rel_synchronize_yun,R.id.item_settings_device,R.id.rel_fever_way,R.id.rel_fever_temp,R.id.rel_my_baby,R.id.rel_location,R.id.top_view_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_synchronize_yun:
                Utils.toNextActivity(getActivity(), SynchronizeActivity.class);
                break;
            case R.id.item_settings_device:
                Utils.toNextActivity(getActivity(), DeviceActivity.class);;
                break;
            case R.id.rel_fever_temp:
                setTemp();
                break;

            case R.id.rel_fever_way:
                Utils.toNextActivity(getActivity(), AlarmWayActivity.class);
                break;

            case R.id.rel_my_baby:
                Utils.toNextActivity(getActivity(), BabyActivity.class);
                break;
            case R.id.rel_location:
                Utils.toNextActivity(getActivity(), LocationActivity.class);
                break;
            case R.id.top_view_back:
               getActivity().finish();
                break;
        }
    }

    private void setTemp() {
        weightOptions = new OptionsPickerView(getActivity());
        //三级联动效果
        weightOptions.setPicker(Weightoptions1Items, Weightoptions2Items, true);
        //设置选择的三级单位
        weightOptions.setLabels(".", "℃");
        weightOptions.setTitle("体温（℃）");
        weightOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        weightOptions.setSelectOptions(1, 1);
        weightOptions.show();
        weightOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = Weightoptions1Items.get(options1)+"." + Weightoptions2Items.get(options1).get(option2)+"℃";
                text_tem.setText(tx);
                Setting.getInstance(getActivity()).setTemp(tx);

            }
        });

    }



    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(SettingDeviceContract.Presenter presenter) {

    }
}
