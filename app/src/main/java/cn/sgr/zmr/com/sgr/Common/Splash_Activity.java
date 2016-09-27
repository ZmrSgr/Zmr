package cn.sgr.zmr.com.sgr.Common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.RoundImageView;

public class Splash_Activity extends Activity {
    @BindView(R.id.iv_center)
    RoundImageView iv_center;

    @BindView(R.id.rl)
    RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View startViews = View.inflate(this, R.layout.splash_activity, null);
        setContentView(startViews);
        ButterKnife.bind(this);
      // 渐变
         AlphaAnimation aa = new AlphaAnimation(1f, 1.0f);
         aa.setDuration(2500);
         startViews.setAnimation(aa);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);

        aa.setAnimationListener(new Animation.AnimationListener() {

         @Override
         public void onAnimationStart(Animation animation) {

             //创建一个AnimationSet对象，参数为Boolean型，

             //true表示使用Animation的interpolator，false则是使用自己的

             AnimationSet animationSet = new AnimationSet(true);

             //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明

             AlphaAnimation alphaAnimation = new AlphaAnimation( 0, 1);

             //设置动画执行的时间

             alphaAnimation.setDuration(2000);

             //参数1：从哪个旋转角度开始

             //参数2：转到什么角度

             //后4个参数用于设置围绕着旋转的圆的圆心在哪里

             //参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标

             //参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴

             //参数5：确定y轴坐标的类型

             //参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
             RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                     Animation.RELATIVE_TO_SELF,0.5f,
                     Animation.RELATIVE_TO_SELF,0.5f);
             rotateAnimation.setDuration(2000);
             animationSet.addAnimation(alphaAnimation);
             animationSet.addAnimation(rotateAnimation);
             iv_center.startAnimation(animationSet);

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
        Utils.toNextActivity(this,MainActivity.class);
        finish();
    }

}
