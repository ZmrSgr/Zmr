package cn.sgr.zmr.com.sgr.Modules.Home.Adatpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class Baby_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Baby> datas;
    private Context context;
    private DaoCacheManage daoManage;
    private String DeviceName;

    public Baby_Adapter( Context ct,List<Baby> list) {
                this.context=ct;
                this.datas = list;
            }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_item, null);
/*//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
//        view.setLayoutParams(lp);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.babay_tv_name.setText(datas.get(position).getName());
        if(datas.get(position).getSex()!=null&&datas.get(position).getSex().equals("男")){
            mHolder.babay_tv_sex.setImageResource(R.drawable.baby_boy);
        }else{
            mHolder.babay_tv_sex.setImageResource(R.drawable.baby_girl);
        }

        mHolder.babay_tv_device.setText(datas.get(position).getDeviceName());
        mHolder. babay_tv_age.setText(datas.get(position).getAge());
        mHolder.babay_tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MyDialog dialog = new MyDialog(context,context.getResources().getString(R.string.is_del_baby),null,null);
                dialog.show();
                dialog.positive.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
//                                removeData(position);
                                if(!TextUtils.isEmpty(DeviceName)){
                                    boundBaby(position, DeviceName);
                                    dialog.dismiss();
                                }
                            }
                        });
                dialog.negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

//               final MyDialog dialog = new MyDialog(context,context.getResources().getString(R.string.is_del_baby),null,null);
//                dialog.show();
//                dialog.positive.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                removeData(position);
//                                dialog.dismiss();
//                            }
//                        });
//                dialog.negative.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
            }
        });
    }

    public void setDeviceName(String DeviceName){
        this.DeviceName = DeviceName;
    }

    private void boundBaby(int position,String DeviceName) {
        this.daoManage = new DaoCacheManage(context);
        daoManage.setCurryBaby(datas.get(position), DeviceName);

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

//    public void removeData(int position) {
//        this.daoManage=  new DaoCacheManage(context);
//        daoManage.DeleteBaby(datas.get(position));
//        datas.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position,datas.size());
//    }



    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        @BindView(R.id.babay_iv_head)
        ImageView babay_iv_head;;

        @BindView(R.id.babay_tv_name)
        TextView babay_tv_name;;
      @BindView(R.id.babay_tv_sex)
        ImageView babay_tv_sex;

        @BindView(R.id.babay_tv_age)
        TextView babay_tv_age;

        @BindView(R.id.babay_tv_device)
        TextView babay_tv_device;
        @BindView(R.id.babay_tv_del)
        TextView babay_tv_del;
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
            void onItemClick(int position,View v);
            boolean onItemLongClick(int position);
        }

        private OnRecyclerViewListener onRecyclerViewListener;

        public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
            this.onRecyclerViewListener = onRecyclerViewListener;
        }
}
