package cn.sgr.zmr.com.sgr.Modules.Health.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.entity.SearchRecent;

import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;


public class SearchRecentAdapter extends BaseAdapter {
	 private Context ct;
	 List<SearchRecent> datas=new ArrayList<SearchRecent>();
	private DaoCacheManage daoManage;
	
	public SearchRecentAdapter(Context ct, List<SearchRecent> data) {
		super();
		  this.ct = ct;
	        this.datas = data;
		this.daoManage=  new DaoCacheManage(ct);
	    
	}


    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ChatListItem view = (ChatListItem) convertView;
	        if (convertView == null) {
	            view = new ChatListItem(parent.getContext());
	        }
	        view.setData(position);
	        return view;
	}

	
	 public class ChatListItem extends RelativeLayout {
	        private TextView textName;
	        private ImageView imgDel;
	        public ChatListItem(Context ct) {
	            super(ct);
	            inflate(getContext(), R.layout.searchrecent_item, this);
	            this.textName = (TextView) findViewById(R.id.recent_tv_name);
	            this.imgDel = (ImageView) findViewById(R.id.recent_iv_del);
	        }

	        public void setData(final int  index) {
				final SearchRecent data = datas.get(index);
				textName.setText(data.getTitle());
				imgDel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						    daoManage.deleteSearch(data);
							datas.remove(index);
							notifyDataSetChanged();

						
					}
				});
	          
	            
	        }
	    }

}
