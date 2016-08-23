package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.AddHistoryAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Chart;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.EventDatas;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBaby_Activity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddHistory.AddHisoryActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.View.SublimePickerFragment;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class ChartFragment extends Fragment implements ChartContract.View{


    // 高温线下标
    private final int HIGH = 0;

    private final int LOW = 1;

    @BindView(R.id.charts)
    LineChart mChart;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.iv_right)
    ImageView iv_right;


    @BindView(R.id.btn_up)
    ImageView btn_up;

    //时间轴
    @BindView(R.id.chart_list)
    RecyclerView chart_list;

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;


    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout layout;

    @BindView(R.id.lin_bottom)
    TextView lin_bottom;

    @BindView(R.id.time_chart)
    TextView time_chart;

    @BindView(R.id.chart_time)
    View chart_time;


    @BindView(R.id.time_left)
    ImageView time_left;

    @BindView(R.id.time_right)
    ImageView time_right;

    @BindView(R.id.chart_user_rel)
    View chart_user_rel;

    Calendar ca;//得到一个Calendar的实例
    SimpleDateFormat sf;



    ChartContract.Presenter mPresenter;
    //单例 模式
    public static  ChartFragment newInstance() {
        return new ChartFragment();
    }
    //   构造方法
    public ChartFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_fragment, container, false);
        ButterKnife.bind(this, view);
        intitView();
        
        return view;
    }


    private void intitView() {
        top_view_title.setText("体温历史记录");
        top_view_back.setVisibility(View.VISIBLE);
        top_view_left_text.setVisibility(View.GONE);
        iv_right.setImageResource(R.drawable.btn_share_friend);
        iv_right.setVisibility(View.VISIBLE);
        top_view_right_text.setVisibility(View.GONE);
        //时间轴
        chart_list.setHasFixedSize(true);
        chart_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<EventDatas> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            items.add(new EventDatas("even" + i, "测试数据"));
        }
        chart_list.setAdapter(new AddHistoryAdapter(getActivity(), items));

        //图标
        initialChart(mChart);
        addLineDataSet(mChart);
        //初始化时间
        ca = Calendar.getInstance();//得到一个Calendar的实例
        sf = new SimpleDateFormat("yyyy-M-d");
        time_chart.setText(sf.format(ca.getTime()));
        //向上滑动
        layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
        layout.setAnchorPoint(0.3f);
        layout.setDragView(lin_bottom);
        layout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset < 0.1) {
                    btn_up.setImageResource(R.drawable.btn_down);
                    chart_time.setVisibility(View.GONE);

                } else {
                    btn_up.setImageResource(R.drawable.btn_up);
                    chart_time.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPanelExpanded(View panel) {
            }
            @Override
            public void onPanelCollapsed(View panel) {
            }

            @Override
            public void onPanelAnchored(View panel) {
            }
        });
    }


    @OnClick({R.id.top_view_back, R.id.iv_right, R.id.lin_bottom, R.id.time_chart, R.id.time_left, R.id.time_right,R.id.chart_user_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chart_user_rel:
                Utils.toNextActivity(getActivity(),AddBaby_Activity.class);
                break;

            case R.id.top_view_back:
                getActivity().finish();
                break;

            case R.id.iv_right:
                Toast.makeText(getActivity(), "分享", Toast.LENGTH_LONG).show();
                break;

            case R.id.lin_bottom:
                Utils.toNextActivity(getActivity(),AddHisoryActivity.class);
                break;

            case R.id.time_right:
                time_chart.setText(getDate(time_chart.getText().toString(), +1));
                break;

            case R.id.time_left:
                time_chart.setText(getDate(time_chart.getText().toString(), -1));
                break;

            case R.id.time_chart:
                // DialogFragment to host SublimePicker
                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(mFragmentCallback);

                // Options
                Pair<Boolean, SublimeOptions> optionsPair = getOptions();

                if (!optionsPair.first) { // If options are not valid
                    Toast.makeText(getActivity(), "No pickers activated",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Valid options
                Bundle bundle = new Bundle();
                bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
                pickerFrag.setArguments(bundle);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getActivity().getFragmentManager(), "SUBLIME_PICKER");
                break;
        }
    }

    /**
     * 获取日期
     *
     * @param date 当前日期
     * @param type 少于0为减一日，大于0为加一日
     * @return
     */
    private String getDate(String date, int type) {

        int[] the_data = Utils.StringtoInt(date);
        if (the_data.length < 3) {
            return (sf.format(ca.getTime()));
        }

        ca.set(the_data[0], the_data[1] - 1, the_data[2]);//月份是从0开始的，所以11表示12月
        Date now = ca.getTime();
        if (type < 0) {
            ca.add(Calendar.DATE, -1); //日数减1
        } else {
            ca.add(Calendar.DATE, +1); //日数+1
        }
        Date lastMonth = ca.getTime(); //结果

//	Log.d("xiaofu", "now"+sf.format(now)+"lastMonth"+sf.format(lastMonth));
        return (sf.format(lastMonth));
    }

    // 时间选择的样式
    Pair<Boolean, SublimeOptions> getOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;
        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;
        displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        options.setDisplayOptions(displayOptions);
        // Enable/disable the date range selection feature
        options.setCanPickDateRange(true);

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }

    // 初始化图表
    private void initialChart(LineChart mChart) {
        mChart.setDescription(" ");
        mChart.setNoDataTextDescription("暂时尚无数据");
        mChart.setTouchEnabled(true);
        // 可拖曳
        mChart.setDragEnabled(true);
        // 可缩放
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setPinchZoom(true);
        // 设置图表的背景颜色
//        mChart.setBackgroundColor(0xfff5f5f5);
        // 图表的注解(只有当数据集存在时候才生效)
        Legend l = mChart.getLegend();

        // 可以修改图表注解部分的位置
        // l.setPosition(LegendPosition.LEFT_OF_CHART);

        // 线性，也可是圆
        l.setForm(Legend.LegendForm.LINE);

        // 颜色
        l.setTextColor(ContextCompat.getColor(getActivity(), R.color.them_bg));
        // x坐标轴
        XAxis xl = mChart.getXAxis();
//        xl.setTextColor(0xff00897b);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMaxValue(24f);
        xl.setAxisMinValue(0f);


        // 几个x坐标轴之间才绘制？
//        xl.setSpaceBetweenLabels(5);
//        xl.set
        // 如果false，那么x坐标轴将不可见
        xl.setEnabled(true);

        // 将X坐标轴放置在底部，默认是在顶部。
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        // 图表左边的y坐标轴线
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(0xff37474f);

//        // 最大值
//        leftAxis.setAxisMaxValue(45f);

        // 最小值
        leftAxis.setAxisMinValue(35f);

        // 不一定要从0开始
        leftAxis.setStartAtZero(false);

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        // 不显示图表的右边y坐标轴线
        rightAxis.setEnabled(false);
    }

    // 为LineChart增加LineDataSet
    private void addLineDataSet(LineChart mChart) {
        LineData data = getData(24, 2);
        mChart.setData(data);
    }

    private LineData getData(int count, float range) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 37;
            yVals.add(new Entry(i, val));
        }
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "时间体温");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        set1.setLineWidth(1.75f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(ContextCompat.getColor(getActivity(), R.color.them_bg));
        set1.setCircleColor(ContextCompat.getColor(getActivity(), R.color.them_bg));//圆圈的颜色
        set1.setLabel("时间体温");
//        set1.setHighLightColor(Color.RED);
        set1.setDrawValues(false);
        // create a data object with the datasets
        LineData data = new LineData(set1);
        return data;
    }
    //时间选择监听返回
    SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
        }
        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            time_chart.setText((String.valueOf(selectedDate.getStartDate().get(Calendar.YEAR)) + "-" + String.valueOf(selectedDate.getStartDate().get(Calendar.MONTH) + 1) + "-"
                    + String.valueOf(selectedDate.getStartDate().get(Calendar.DAY_OF_MONTH))).trim());
        }
    };
    

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgressDialog(FragmentManager manager) {

    }

    @Override
    public void cancelProgressDialog() {

    }

    @Override
    public void showBaby(List<Baby> babys) {

    }

    @Override
    public void showChart(List<Chart> charts) {

    }

    @Override
    public void showHistory() {

    }

    @Override
    public void nextActivity(String id) {

    }

    @Override
    public void nextDayChart(Chart chart) {

    }

    @Override
    public void preDayChart(Chart chart) {

    }

    @Override
    public void curryDayChart(Chart chart) {

    }

    @Override
    public void setPresenter(ChartContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }
}
