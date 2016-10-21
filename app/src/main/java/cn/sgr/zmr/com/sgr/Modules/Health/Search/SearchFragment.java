package cn.sgr.zmr.com.sgr.Modules.Health.Search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.entity.SearchRecent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Health.Adapter.SearchRecentAdapter;
import cn.sgr.zmr.com.sgr.Modules.Health.Adapter.Tie_Adapter;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.DoctorList;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.DrugList;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.SearchResult;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.Tie;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.View.TextViewExpandableAnimation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class SearchFragment<T> extends BaseFragment implements SearchContract.View,Tie_Adapter.OnRecyclerViewListener<T>{
    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.ivDeleteText)
    ImageView ivDeleteText;


    @BindView(R.id.btnSearch)
    TextView btnSearch;


    @BindView(R.id.recent_list)
    ListView recent_list;



    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    SearchContract.Presenter mPresenter;
    SearchRecentAdapter searchRecentAdapter;

    @BindView(R.id.rv_tie)
    RecyclerView mNewsListView;


    TextViewExpandableAnimation recycle_title_text;


    Tie_Adapter tie_adapter;

    boolean isvoice =false;


    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "xiaoyan";

    private String piece;

    private boolean isPause=false;



    //单例 模式
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
    //   构造方法
    public SearchFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }
    //主界面语音传过来的文字
    private void initData() {

        if (getActivity(). getIntent().getExtras() != null
                &&getActivity(). getIntent().getExtras()
                .containsKey(UtilKey.VOICE_KEY)) {

            Intent intent =getActivity(). getIntent();
            // 获取数据
            Bundle bundle = getActivity().getIntent().getExtras();
            System.out.println("跳转过来的内容"+bundle.getString(UtilKey.VOICE_KEY));
            etSearch.setText(bundle.getString(UtilKey.VOICE_KEY));
            mPresenter.SearchAsk(bundle.getString(UtilKey.VOICE_KEY));

        }else{

        }



    }

    private void initView() {

        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), mTtsInitListener);



       /* mNewsListView.addOnScrollListener(new RecyclerViewLoadMoreListener(linearLayoutManager, this, 0));
        mNewsListView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );*/

//        top_view_title.setText(getResources().getString(R.string.health_search));
//        top_view_back.setVisibility(View.VISIBLE);
        etSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
//            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(getActivity(),"初始化失败,错误码："+code,Toast.LENGTH_LONG).show();
//                showTip("初始化失败,错误码："+code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };


    @OnClick({R.id.ivDeleteText,R.id.top_view_back,R.id.btnSearch,R.id.etSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDeleteText:
                etSearch.setText("");
                break;
            case R.id.top_view_back:
                getActivity().finish();
                break;
            case R.id.btnSearch:
                if(etSearch.getText().toString().equals("")){
                    Toast.makeText(getActivity(),R.string.please_input_content,Toast.LENGTH_SHORT).show();
                }else{
                    mPresenter.SearchAsk(etSearch.getText().toString());
                }

                break;

            case R.id.etSearch:
               showRecentView();
                break;
        }
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(getFragmentManager());

    }

    @Override
    public void cancelProgressDialogs() {
        cancelProgressDialog();
    }

    @Override
    public void showSearchRecent(final List<SearchRecent> datas) {

        if (datas != null) {


            searchRecentAdapter=new SearchRecentAdapter(getActivity(),datas);
            recent_list.setAdapter(searchRecentAdapter);
            searchRecentAdapter.notifyDataSetChanged();
            recent_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPresenter.SearchAsk(datas.get(position).getTitle());
                }
            });
        }

    }

    @Override
    public void showSearchResult(SearchResult result) {

        Gson gson = new Gson();



        if (result != null) {
            mNewsListView.setHasFixedSize(true);
            mNewsListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


            tie_adapter=new Tie_Adapter(getActivity());
            gson.toJson(result.getList());
            System.out.println("getList"+result.getList());
            if(result.getType().equals("0")||result.getType().equals("1")||result.getType().equals("5")){

           //把它变成json 在转变回对象
                List<Tie> resultTie=gson.fromJson(gson.toJson(result.getList()), new TypeToken<List<Tie>>() {
                }.getType());
            tie_adapter.addDatas(resultTie);

            }else if(result.getType().equals("3")||result.getType().equals("6")||result.getType().equals("7")){

                List<DoctorList> resultDoctor=gson.fromJson(gson.toJson(result.getList()), new TypeToken<List<DoctorList>>() {
                }.getType());
            tie_adapter.addDatas(resultDoctor);

            }else if(result.getType().equals("2")){

                List<DrugList> resultDrug=gson.fromJson(gson.toJson(result.getList()), new TypeToken<List<DrugList>>() {
                }.getType());
                tie_adapter.addDatas(resultDrug);

            }else if(result.getType().equals("4")){
                Toast.makeText(getActivity(), R.string.search_nothing,Toast.LENGTH_LONG).show();
                return;
            }

            tie_adapter.setOnRecyclerViewListener(this);
            mNewsListView.setAdapter(tie_adapter);
            mNewsListView.setVerticalScrollBarEnabled(false);
            if(result.getPiece()!=null&&!result.getPiece().equals("")){
                View header = LayoutInflater.from(getActivity()).inflate(R.layout.header,mNewsListView, false);
                tie_adapter.setHeaderView(header);
                recycle_title_text=(TextViewExpandableAnimation)header.findViewById(R.id.tv_expand);
                recycle_title_text.setText("    "+Html.fromHtml(result.getPiece()));
                piece=Html.fromHtml(result.getPiece()).toString();
                recycle_title_text.setVoiceClickListener(new TextViewExpandableAnimation.VoiceClickListener() {
                    @Override
                    public void ImageVice(View v) {
                        if(isvoice){
                            recycle_title_text.setDrawableVoiceImage(R.drawable.wsdk_drawable_rg_ic_voice_off);
                            mTts.stopSpeaking();
                            isvoice=false;
                        }else{
                            isvoice=true;
                            recycle_title_text.setDrawableVoiceImage(R.drawable.wsdk_drawable_rg_ic_voice_on);

                            // 移动数据分析，收集开始合成事件
                            FlowerCollector.onEvent(getActivity(), "tts_play");

                            String text = piece;
                            // 设置参数
                            setParam();
                            int code = mTts.startSpeaking(text, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);

                            if (code != ErrorCode.SUCCESS) {
                                if(code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
                                    //未安装则跳转到提示安装页面
//                                mInstaller.install();
                                    showTip("未安装则跳转到提示安装页面" + code);
                                }else {
                                    showTip("语音合成失败,错误码: " + code);
                                }
                            }

                        }

                    }
                });
            }


        }

    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {

            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
           /* // 合成进度
            mPercentForBuffering = percent;
            showTip(String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));*/
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
         /*   mPercentForPlaying = percent;
            showTip(String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));*/
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                showTip("播放完成");
            } else if (error != null) {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };


    /**
     * 参数设置
     * @param
     * @return
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH,  "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME,"50");
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE,"3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }

    private void showTip(final String str) {
     Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRecentView() {
        recent_list.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissRecentView() {
        recent_list.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }



    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


   @Override
    public void onItemClick(T data, View v) {

       /*         Intent intent = new Intent();
                intent.setClass(getActivity(), DetailTieActivity.class);
                intent.putExtra(UtilKey.TIE_KEY, Tempresult.getList().get(position).getUrl());
                startActivity(intent);*/
       if(data instanceof Tie){//type为 0 1 5 帖子
           Tie dataTie=(Tie)data;
           Intent intent = new Intent();
           intent.setClass(getActivity(), DetailTieActivity.class);
           intent.putExtra(UtilKey.TIE_KEY, dataTie.getUrl());
           startActivity(intent);

       }else if(data instanceof DrugList){//药物列表  2
           DrugList dataDrug=(DrugList)data;
           Intent intent = new Intent();
           intent.setClass(getActivity(), DetailTieActivity.class);
           intent.putExtra(UtilKey.TIE_KEY, dataDrug.getUrl());
           startActivity(intent);

       }else if(data instanceof DoctorList){//医生医院列表  367
           DoctorList dataDoctor=(DoctorList)data;
           Intent intent = new Intent();
           intent.setClass(getActivity(), DetailTieActivity.class);
           intent.putExtra(UtilKey.TIE_KEY, dataDoctor.getUrl());
           startActivity(intent);
       }

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        mTts.stopSpeaking();
        // 退出时释放连接
        mTts.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mTts!=null){
            if(mTts.isSpeaking()) {
                mTts.pauseSpeaking();
                isPause=true;
            }
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        if(mTts!=null){
            if (isPause) {
                mTts.resumeSpeaking();
                isPause=false;
            }

        }

    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
