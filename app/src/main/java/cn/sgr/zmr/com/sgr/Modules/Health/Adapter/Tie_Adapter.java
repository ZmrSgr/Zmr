package cn.sgr.zmr.com.sgr.Modules.Health.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.DoctorList;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.DrugList;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.Tie;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.util.GlideCircleTransform;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class Tie_Adapter<T> extends BaseRecyclerAdapter<T>  {

    private Context context;
    private DaoCacheManage daoManage;
    private ArrayList<T> mDatas = new ArrayList<>();

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
    public void onBind(RecyclerView.ViewHolder viewHolder, int position,  T dataResult) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mDatas.add(dataResult);
       /* boolean isZhangJiaWei = newEntity.getContentSourceName().equals("张佳玮的博客");
        if(hasImage) {
            mHolder.  mNewsImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(0))
                    .placeholder(R.mipmap.placeholder_biger)
                    .into(mNewsImage);
        }*/


        if(dataResult instanceof Tie){//type为 0 1 5 帖子
            Tie data=(Tie)dataResult;
            mHolder. profileImage.setImageResource(R.drawable.ic_launcher);
            mHolder.  author.setText(data.getAuthor());
            mHolder. mNwsTitle.setText(data.getTitle());
//        mHolder. showTime = new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyy年MM月dd日");
            mHolder. mNewsTime.setText(data.getDateline());
            mHolder. description.setText(data.getContent());
            mHolder.newsNum.setText(data.getNum()+"个回答");
            Glide.with(context).load(data.getPhoto()).centerCrop().transform(new GlideCircleTransform(context)).into(mHolder.profileImage);
        }else if(dataResult instanceof DrugList){//药物列表  2
            DrugList data=(DrugList)dataResult;
            mHolder. profileImage.setImageResource(R.drawable.ic_launcher);
            mHolder.  author.setText(data.getCompany());
            mHolder. mNwsTitle.setText(data.getItemname());
//        mHolder. showTime = new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyy年MM月dd日");
            mHolder. mNewsTime.setVisibility(View.GONE);
            mHolder. newsNum.setVisibility(View.GONE);
            mHolder. description.setText(data.getInfo());
//            mHolder.newsNum.setText(data.getNum()+"个回答");
            Glide.with(context).load(data.getPack_pic()).centerCrop().transform(new GlideCircleTransform(context)).into(mHolder.profileImage);
        }else if(dataResult instanceof DoctorList){//医生医院列表  367
            DoctorList data=(DoctorList)dataResult;
            mHolder. profileImage.setImageResource(R.drawable.ic_launcher);
            mHolder.  author.setText(data.getHos_name());
            mHolder. mNwsTitle.setText(data.getRealname());
//        mHolder. showTime = new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyy年MM月dd日");
//            mHolder. mNewsTime.setText(data.getDateline());
            mHolder. description.setText(data.getGood_at());
//            mHolder.newsNum.setText(data.getNum()+"个回答");
            Glide.with(context).load(data.getFace()).centerCrop().transform(new GlideCircleTransform(context)).into(mHolder.profileImage);
            mHolder. mNewsTime.setVisibility(View.GONE);
            mHolder. newsNum.setVisibility(View.GONE);
        }



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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



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
                onRecyclerViewListener.onItemClick(mDatas.get(this.getPosition()),v);
            }
        }
    }


        public  interface OnRecyclerViewListener<T>{
            void  onItemClick(T data, View v);
            boolean onItemLongClick(int position);
        }

        private OnRecyclerViewListener onRecyclerViewListener;

        public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
            this.onRecyclerViewListener = onRecyclerViewListener;
        }
}
