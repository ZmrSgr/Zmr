package cn.sgr.zmr.com.sgr.Modules.Home.Adatpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Modules.Health.Adapter.BaseRecyclerAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.StoreResult;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class StoreAdapter extends BaseRecyclerAdapter<StoreResult.HospitalBean> {

    private Context context;
    private DaoCacheManage daoManage;
    private List<StoreResult.HospitalBean> mDatas;

    public StoreAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, null);
/*//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
//        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int position, StoreResult.HospitalBean data) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        if(data != null){
            if(!TextUtils.isEmpty(data.getHospitalName())){
                mHolder.tvHospitalName.setText(data.getHospitalName());
            }

            if(!TextUtils.isEmpty(data.getAddress())){
                mHolder.tvAddress.setText(data.getAddress());
            }

            if(!TextUtils.isEmpty(String.valueOf(data.getDistance()))){

                DecimalFormat df = new DecimalFormat("#.#");

                Double d = data.getDistance();

                mHolder.tvDistance.setText(df.format(d) + "公里");
            }
        }
    }

    public interface OnRecyclerViewListener {
        void onItemClick(int position, View v);

        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_hospitalname_item_store)
        TextView tvHospitalName;
        @BindView(R.id.tv_address_item_store)
        TextView tvAddress;
        @BindView(R.id.tv_distance_item_store)
        TextView tvDistance;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(this.getPosition(), v);
            }
        }
    }
}
