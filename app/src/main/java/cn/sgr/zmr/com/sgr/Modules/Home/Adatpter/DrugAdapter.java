package cn.sgr.zmr.com.sgr.Modules.Home.Adatpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Modules.Health.Adapter.BaseRecyclerAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.Drug;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class DrugAdapter extends BaseRecyclerAdapter<Drug> {

    private Context context;
    private DaoCacheManage daoManage;

    public DrugAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drugstore, null);
/*//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
//        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int position, Drug data) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        if(data != null){
            if(!TextUtils.isEmpty(data.getDurgstoreName())){
                mHolder.tvStorename.setText(data.getDurgstoreName());
            }
            if(!TextUtils.isEmpty(data.getAddress())){
                mHolder.tvAddress.setText(data.getAddress());
            }
            if(!TextUtils.isEmpty(data.getDistance())){
                DecimalFormat df = new DecimalFormat("#.#");

                Double d = Double.parseDouble(data.getDistance());

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_storename_item_drugstore)
        TextView tvStorename;
        @BindView(R.id.tv_address_item_drugstore)
        TextView tvAddress;
        @BindView(R.id.tv_distance_item_drugstore)
        TextView tvDistance;
        @BindView(R.id.item_head)
        RelativeLayout itemHead;
        @BindView(R.id.device_list_view)
        LinearLayout deviceListView;

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
