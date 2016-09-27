package cn.sgr.zmr.com.sgr.Modules.Messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.R;

public class MessageFragment extends BaseFragment {
    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.main_grid)
    GridView main_grid;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    private GridAdapter gridAdapter;

    int[] icon = { R.drawable.icon_growth, R.drawable.icon_jizhang, R.drawable.icon_quanzi,
           };

    String[] title = { "成长记录", "记账", "圈子"};
    private List<Map<String, Object>> list;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);

        ButterKnife.bind(this, view);
        intView();

        return view;

    }
    private void intView() {
        top_view_title.setText("消息中心");
        top_view_back.setVisibility(View.GONE);
        top_view_right_text.setVisibility(View.GONE);


        list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", icon[i]);
            map.put("title", title[i]);
            list.add(map);
        }
        gridAdapter = new GridAdapter(getActivity(), list);
        main_grid.setAdapter(gridAdapter);
        main_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
