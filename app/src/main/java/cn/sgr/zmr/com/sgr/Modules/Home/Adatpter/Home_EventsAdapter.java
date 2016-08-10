package cn.sgr.zmr.com.sgr.Modules.Home.Adatpter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alorma.timeline.TimelineView;

import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Home.Model.EventDatas;
import cn.sgr.zmr.com.sgr.R;

public class Home_EventsAdapter extends ArrayAdapter<EventDatas> {
    private final LayoutInflater layoutInflater;
    private   Context context;

    public Home_EventsAdapter(Context contexts, List<EventDatas> objects) {
        super(contexts, 0, objects);
        context=contexts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.chart_item_main, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.text = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.timeline = (TimelineView) convertView.findViewById(R.id.timeline);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        EventDatas events = getItem(position);

        viewHolder.text.setText(events.getName());
        viewHolder.timeline.setTimelineType(events.getType());
        viewHolder.timeline.setLineColor( ContextCompat.getColor(context, R.color.them_text));
        viewHolder.timeline.setIndicatorColor( ContextCompat.getColor(context, R.color.them_bg));
        viewHolder.timeline.setTimelineAlignment(events.getAlignment());

        return convertView;
    }

    static class ViewHolderItem {
        TextView text;
        TimelineView timeline;
    }
}
