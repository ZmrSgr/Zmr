package cn.sgr.zmr.com.sgr.Modules.Health.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.entity.Baby;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Search;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.SearchResult;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.Tie;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.util.GlideCircleTransform;
import cn.sgr.zmr.com.sgr.View.MyDialog;
import cn.sgr.zmr.com.sgr.View.RoundImageView;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class Tie_Adapter extends BaseRecyclerAdapter<Tie>  {

    private Context context;
    private DaoCacheManage daoManage;

    public Tie_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_tie, null);
/*//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
//        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int position,  Tie data) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
       /* boolean isZhangJiaWei = newEntity.getContentSourceName().equals("张佳玮的博客");
        if(hasImage) {
            mHolder.  mNewsImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(0))
                    .placeholder(R.mipmap.placeholder_biger)
                    .into(mNewsImage);
        }*/
        mHolder. profileImage.setImageResource(R.drawable.ic_launcher);
        mHolder.  author.setText(data.getAuthor());
        mHolder. mNwsTitle.setText(data.getTitle());
//        mHolder. showTime = new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyy年MM月dd日");
        mHolder. mNewsTime.setText(data.getDateline());
        mHolder. description.setText(data.getContent());
        mHolder.newsNum.setText(data.getNum()+"个回答");
        Glide.with(context).load(data.getPhoto()).centerCrop().transform(new GlideCircleTransform(context)).into(mHolder.profileImage);

    }

/*
    public void applyData(List<Baby> msgs) {
        datas.clear();
        datas.addAll(msgs);
        notifyDataSetChanged();
    }*/

/*

    public void removeData(int position) {
        this.daoManage=  new DaoCacheManage(context);
        daoManage.DeleteBaby(datas.get(position));
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,datas.size());
    }
*/

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {



        @BindView(R.id.newsImage)
        ImageView mNewsImage;

        @BindView(R.id.newsTitle)
        TextView mNwsTitle;

        @BindView(R.id.newsTime) TextView mNewsTime;

        @BindView(R.id.description) TextView description;

        @BindView(R.id.profile_image)
        ImageView profileImage;

        @BindView(R.id.author)
        TextView author;

        @BindView(R.id.newsNum)
        TextView newsNum;


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
