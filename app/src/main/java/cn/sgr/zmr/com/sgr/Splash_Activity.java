package cn.sgr.zmr.com.sgr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class Splash_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View startView = View.inflate(this, R.layout.activity_splash, null);
        setContentView(startView);


        // 渐变
         AlphaAnimation aa = new AlphaAnimation(1f, 1.0f);
         aa.setDuration(2500);
         startView.setAnimation(aa);
         aa.setAnimationListener(new Animation.AnimationListener() {

         @Override
         public void onAnimationStart(Animation animation) {
         // TODO Auto-generated method stub
         }

         @Override
         public void onAnimationRepeat(Animation animation) {
         // TODO Auto-generated method stub
         }

         @Override
         public void onAnimationEnd(Animation animation) {
         // TODO Auto-generated method stub
         redirectto();
         }
         });
    }
    private void redirectto() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // overridePendingTransition(R.anim.push_right_in,
        // R.anim.push_right_out);
        finish();
    }

}
