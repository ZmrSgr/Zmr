package cn.sgr.zmr.com.sgr.Modules.Setting.AlarmWay;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.R;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class AlarmWayFragment extends BaseFragment implements AlarmWayContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    private AlarmWayContract.Presenter mPresenter;

    @BindView(R.id.jtb_fever)
    JellyToggleButton   jtb_fever;

    @BindView(R.id.jtb_lost)
    JellyToggleButton   jtb_lost;

    @BindView(R.id.ring_lv)
    ListView listView;//铃声列表

    private MainAdapter mAdapter;

    Ringtone rTone;
    private Vibrator vibrator;

    //单例 模式
    public static AlarmWayFragment newInstance() {
        return new AlarmWayFragment();
    }
    //   构造方法
    public AlarmWayFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarmway_fragment, container, false);

        // 震动效果的系统服务
        vibrator = (Vibrator)getActivity(). getSystemService(Context.VIBRATOR_SERVICE);

        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.set_type));
        top_view_back.setVisibility(View.VISIBLE);

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
        jtb_lost.setChecked(Setting.getInstance(getActivity()).IsVocie());
        if(Setting.getInstance(getActivity()).IsVocie()){
            listView.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.GONE);
        }
        jtb_fever.setChecked(Setting.getInstance(getActivity()).IsShock());
        jtb_fever.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    Toast.makeText(getActivity(),getString(R.string.close_shock),Toast.LENGTH_SHORT).show();
                    Setting.getInstance(getActivity()).setShock("0");

                }
                if (state.equals(State.RIGHT)) {
                    Toast.makeText(getActivity(),getString(R.string.open_shock),Toast.LENGTH_SHORT).show();
                    Setting.getInstance(getActivity()).setShock("1");
                    Animation shake = AnimationUtils.loadAnimation(
                            getActivity(), R.anim.shake);
                    jtb_fever.startAnimation(shake);

                    /*
                     * 震动的方式
                     */
                    // vibrator.vibrate(2000);//振动两秒

                    // 下边是可以使震动有规律的震动   -1：表示不重复 0：循环的震动
//                    long[] pattern = { 200, 2000, 2000, 200, 200, 200 };
//                    vibrator.vibrate(1000);


                }

            }
        });

        jtb_lost.setOnStateChangeListener(
                new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {

                    StopVice();
                    listView.setVisibility(View.GONE);
                    Setting.getInstance(getActivity()).setVoice("0");
                }
                if (state.equals(State.RIGHT)) {
                    Setting.getInstance(getActivity()).setVoice("1");
                    listView.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "RIGHT!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mAdapter = new MainAdapter(getActivity(),0);
        listView.setAdapter(mAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(mOnItemClickListener);
        		/*清空map*/
        mAdapter.map.clear();
        // mAdapter.map.put(position, 1);
			/*将所点击的位置记录在map中*/
        mAdapter.map.put(Setting.getInstance(getActivity()).getVoicePosition(), true);
			/*刷新数据*/
        mAdapter.notifyDataSetChanged();



    }

    /*listView的按钮点击事件*/
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            MainAdapter.ViewHolder mHolder = new MainAdapter.ViewHolder(parent);
			/*设置Imageview不可被点击*/
            mHolder.iv.setClickable(false);
			/*清空map*/
            mAdapter.map.clear();
            // mAdapter.map.put(position, 1);
			/*将所点击的位置记录在map中*/
            mAdapter.map.put(position, true);
			/*刷新数据*/
            mAdapter.notifyDataSetChanged();
            Setting.getInstance(getActivity()).setVoicePosition(position-1);

			/*判断位置不为0则播放的条目为position-1*/
            if (position != 0) {
                try {

                    RingtoneManager rm = new RingtoneManager(getActivity());
                    rm.setType(RingtoneManager.TYPE_ALARM);
                    rm.getCursor();
                    rm.getRingtone(position - 1).play();


                    rTone=rm.getRingtone(position - 1);
                    rTone.play();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


			/*position为0是跟随系统，先得到系统所使用的铃声，然后播放*/
            if (position == 0) {

                Uri uri = RingtoneManager.getActualDefaultRingtoneUri(
                      getActivity(), RingtoneManager.TYPE_ALARM);



                rTone=RingtoneManager.getRingtone( getActivity(), uri);
                rTone.play();
            }

        }

    };


    //监听按钮
    @OnClick({R.id.top_view_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                StopVice();
                getActivity().finish();
                break;
        }
    }


    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(AlarmWayContract.Presenter presenter) {

    }

    private  void StopVice(){
        if(rTone!=null&&rTone.isPlaying()){

            rTone.stop();
        }
    }

    @Override
    public void onPause() {
        StopVice();
        super.onPause();
    }
}
