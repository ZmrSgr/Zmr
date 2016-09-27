package cn.sgr.zmr.com.sgr.Modules.My.MyChild.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.entity.Baby;
import com.bean.entity.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.View.MyDialog;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class User_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> datas;
    private Context context;
    private DaoCacheManage daoManage;

    public User_Adapter(Context ct, List<User> list) {
                this.context=ct;
                this.datas = list;
            }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, null);
/*//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
//        view.setLayoutParams(lp);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.user_tv_name.setText(datas.get(position).getNickname());
        mHolder.user_tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               final MyDialog dialog = new MyDialog(context,context.getResources().getString(R.string.is_del_baby),null,null);
                dialog.show();
                dialog.positive.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                removeData(position);
                                dialog.dismiss();
                            }
                        });
                dialog.negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public void applyData(List<User> msgs) {
        datas.clear();
        datas.addAll(msgs);
        notifyDataSetChanged();
    }


    public void removeData(int position) {
        this.daoManage=  new DaoCacheManage(context);

        daoManage.DeleteUser(datas.get(position));
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,datas.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        @BindView(R.id.user_iv_head)
        ImageView user_iv_head;;
        @BindView(R.id.user_tv_name)
        TextView user_tv_name;;
        @BindView(R.id.user_tv_del)
        TextView user_tv_del;
//        public DummyContent.DummyItem mItem;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(this.getPosition(),v);
            }
        }
    }


        public static interface OnRecyclerViewListener {
            void onItemClick(int position, View v);
            boolean onItemLongClick(int position);
        }

        private OnRecyclerViewListener onRecyclerViewListener;

        public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
            this.onRecyclerViewListener = onRecyclerViewListener;
        }
}
