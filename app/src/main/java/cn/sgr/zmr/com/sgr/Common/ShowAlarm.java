package cn.sgr.zmr.com.sgr.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;

import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;

public class ShowAlarm extends Activity {
	String title;
	String Neutral;
	TimeAway mTime;
	private Vibrator vibrator;//震动
	Ringtone rTone;//声音

	Ringtone rpeatTone;//声音
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        // Turn on the screen unless we are being launched from the AlarmAlert
        // subclass.
        if (!getIntent().getBooleanExtra("screen_off", false)) {
            win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
		String type= getIntent().getStringExtra(UtilKey.ALARM_KEY_ALARM);

		//      震动

		if(Setting.getInstance(this).IsShock()){
			// 震动效果的系统服务
			vibrator = (Vibrator)this. getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 1000, 121000};
			vibrator.vibrate(pattern,0);
		}
//		声音
		if(Setting.getInstance(this).IsVocie()){
			int position=Setting.getInstance(this).getVoicePosition();


					/*position为0是跟随系统，先得到系统所使用的铃声，然后播放*/

				RingtoneManager rm = new RingtoneManager(this);
				rm.setType(RingtoneManager.TYPE_ALARM);
				rm.getCursor();
//				rm.getRingtone(position).play();
				rTone=rm.getRingtone(position);
				rTone.play();




		}


		if(type!=null&&type.equals("0")){
		    title=getResources().getString(R.string.temp_over)+ Setting.getInstance(this).getTemp().toString()+"℃"+ getResources().getString(R.string.after_tip);
			Neutral=getResources().getString(R.string.close_tip);
		}else{
			title=getResources().getString(R.string.child_away) +getResources().getString(R.string.after_tip);
			Neutral=getResources().getString(R.string.close_away);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(title)
		       .setCancelable(false)
               .setNeutralButton(Neutral, new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   Setting.getInstance(ShowAlarm.this).setAlarm("0");
					   ShowAlarm.this.finish();
					   if(mTime!=null){
						   mTime.onFinish();
					   }
					   ShowAlarm.this. runOnUiThread(new Runnable() {
						   @Override
						   public void run() {
							   if(rTone!=null){
								   rTone.stop();
								   rTone=null;
							   }

							   if(rpeatTone!=null){
								   rpeatTone.stop();
								   rpeatTone=null;
							   }

							   if(vibrator!=null){
								   vibrator.cancel();
								   vibrator=null;
							   }
						   }
					   });
				   }
			   })
		       .setPositiveButton(getResources().getString(R.string.know), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   ShowAlarm.this.finish();
					   if(mTime!=null){
						   mTime.onFinish();
					   }
					   ShowAlarm.this. runOnUiThread(new Runnable() {
						   @Override
						   public void run() {
							   if(vibrator!=null){
								   vibrator.cancel();
								   vibrator=null;
							   }

							   if(rpeatTone!=null){
								   rpeatTone.stop();
								   rpeatTone=null;
							   }
							   if(rTone!=null){
								   rTone.stop();
								   rTone=null;
								   System.out.println("停止播放");
							   }
						   }
					   });

		           }
		       });

		AlertDialog alert = builder.create();
		alert.show();


		//计算十分钟之后闹钟继续闹
		mTime = new TimeAway(600000, 1000);// 构造CountDownTimer对象
		mTime.start();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		if(rTone!=null){
			rTone.stop();
			rTone=null;
		}
		if(rpeatTone!=null){
			rpeatTone.stop();
			rpeatTone=null;
		}

	}
	/* 定义一个倒计时的内部类   计算多久没有应答*/
	class TimeAway extends CountDownTimer {
		public TimeAway(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}
		@Override
		public void onFinish() {// 计时完毕时触发
			if(Setting.getInstance(ShowAlarm.this).IsShock()){
				// 震动效果的系统服务
				if(vibrator!=null){
					vibrator = (Vibrator)ShowAlarm.this. getSystemService(Context.VIBRATOR_SERVICE);
					long[] pattern = { 1000, 121000};//持续2分钟
					vibrator.vibrate(pattern,0);
				}

			}
			if(Setting.getInstance(ShowAlarm.this).IsVocie()){

				if(rTone!=null) {
					int position = Setting.getInstance(ShowAlarm.this).getVoicePosition();
					if (position == 1) {
						Uri uri = RingtoneManager.getActualDefaultRingtoneUri(
								ShowAlarm.this, RingtoneManager.TYPE_ALARM);
						rpeatTone = RingtoneManager.getRingtone(ShowAlarm.this, uri);
						rpeatTone.play();
					} else {
						RingtoneManager rm = new RingtoneManager(ShowAlarm.this);
						rm.setType(RingtoneManager.TYPE_ALARM);
						rm.getCursor();
//					rm.getRingtone(position).play();
						rpeatTone = rm.getRingtone(position);
						rpeatTone.play();
					}

				}

			}
		}
		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
		}
	}
	

}
