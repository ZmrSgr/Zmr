package cn.sgr.zmr.com.sgr.Modules.Messages.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.sgr.zmr.com.sgr.R;

/**
 * Created by Administrator on 2016/9/24 0024.
 */
public class GridAdapter extends BaseAdapter {
    List<Map<String, Object>> list;
    Context context;
    LayoutInflater inflater;

    public GridAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HanldView hand;
        if (convertView == null) {
            hand = new HanldView();
            convertView = inflater.inflate(R.layout.grid_itme, null);
            hand.icon = (ImageView) convertView.findViewById(R.id.main_grid_icon);
            hand.title = (TextView) convertView.findViewById(R.id.main_grid_title);
            convertView.setTag(hand);
        } else {
            hand = (HanldView) convertView.getTag();
        }

        hand.icon.setBackgroundResource((Integer) list.get(position).get("icon"));
        hand.title.setText((String) list.get(position).get("title"));
        return convertView;
    }

    class HanldView {
        ImageView icon;
        TextView title;
    }
}