

package cn.sgr.zmr.com.sgr.View;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.sgr.zmr.com.sgr.R;


public class MyDialog {

	public Dialog mDialog;
	public TextView dialog_message;
	public TextView positive;
	public TextView negative;

	public MyDialog(Context context, String message,String yes,String no) {

		LayoutInflater inflater = LayoutInflater.from(context);

		View view = inflater.inflate(R.layout.dialog_layout, null);

		mDialog = new Dialog(context, R.style.dialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(true);
		dialog_message = (TextView) view.findViewById(R.id.dialog_message);
		dialog_message.setText(message);
		positive = (TextView) view.findViewById(R.id.yes);
		negative = (TextView) view.findViewById(R.id.no);
		if(yes!=null){//默认
			positive.setText(context.getResources().getString(R.string.confirm));
			negative.setText(context.getResources().getString(R.string.cancel));
		}
		
	}
	
	public void show() {
		mDialog.show();
	}
	
	public void dismiss() {
		mDialog.dismiss();
	}
	public void setCancelable(Boolean bool) {
		mDialog.setCancelable(bool);
	}

}
