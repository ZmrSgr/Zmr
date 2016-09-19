package cn.sgr.zmr.com.sgr.Modules.Home.Adatpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bean.entity.Baby;
import com.bean.entity.Treat;

import java.util.List;

import cn.sgr.zmr.com.sgr.R;

/**
 * Author: shenguorong
 * Email:
 * Date:
 */
public class AddHistoryAdapter extends RecyclerView.Adapter {
    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = AddHistoryAdapter.class.getSimpleName();
    private List<Treat> list;
    private Context context;

    public AddHistoryAdapter(Context contexts,List<Treat> list) {
        this.list = list;
        this.context=contexts;
    }

    public void applyData(List<Treat> msgs) {
        list.clear();
        list.addAll(msgs);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chart_item_main, null);
//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        Treat events =  list.get(i);
        holder.wuli.setText(events.getPhysics());
        holder.time.setText(events.getTime());
        holder.yao.setText(events.getMedicine());
        holder. add_history_temp.setText(events.getTemperature());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public View rootView;
        public TextView time,wuli,yao,add_history_temp;
        public ItemViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.add_history_time);
            wuli = (TextView) itemView.findViewById(R.id.add_history_wuli);
            yao = (TextView)itemView.findViewById(R.id.add_history_yao);
            add_history_temp= (TextView)itemView.findViewById(R.id.add_history_temp);

//            rootView.setOnClickListener(this);
//            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(this.getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(this.getPosition());
            }
            return false;
        }
    }

}
