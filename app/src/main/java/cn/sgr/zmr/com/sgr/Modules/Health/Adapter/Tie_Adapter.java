package cn.sgr.zmr.com.sgr.Modules.Health.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.entity.Baby;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.View.MyDialog;
import cn.sgr.zmr.com.sgr.View.RoundImageView;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class Tie_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Baby> datas;
    private Context context;
    private DaoCacheManage daoManage;

    public Tie_Adapter(Context ct, List<Baby> list) {
                this.context=ct;
                this.datas = list;
            }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_tie, null);
/*//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
//        view.setLayoutParams(lp);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        boolean isZhangJiaWei = newEntity.getContentSourceName().equals("张佳玮的博客");
        if(hasImage) {
            mHolder.  mNewsImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(0))
                    .placeholder(R.mipmap.placeholder_biger)
                    .into(mNewsImage);
        }
        mHolder. profileImage.setImageResource(isZhangJiaWei ? R.mipmap.zhangjiawei : R.mipmap.suqun);
        mHolder.  author.setText(isZhangJiaWei ? "张佳玮  " : "苏群  ");
        mHolder. mNwsTitle.setText(newEntity.getTitle());
        mHolder. showTime = new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyy年MM月dd日");
        mHolder. mNewsTime.setText(showTime);
        mHolder. description.setText(newEntity.getDescription() + "......");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public void applyData(List<Baby> msgs) {
        datas.clear();
        datas.addAll(msgs);
        notifyDataSetChanged();
    }


    public void removeData(int position) {
        this.daoManage=  new DaoCacheManage(context);
        daoManage.DeleteBaby(datas.get(position));
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,datas.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {


        @BindView(R.id.newsImage)
        ImageView mNewsImage;
        @BindView(R.id.newsTitle)
        TextView mNwsTitle;
        @BindView(R.id.newsTime) TextView mNewsTime;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.profile_image)
        RoundImageView profileImage;
        @BindView(R.id.author) TextView author;

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
