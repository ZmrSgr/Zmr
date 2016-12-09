package cn.sgr.zmr.com.sgr.Utils.util;

import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;

import cn.sgr.zmr.com.sgr.R;

/**
 * Created by Weiping Huang at 23:44 on 16/11/21
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */
public class BuilderManager {

    private static int[] imageResources = new int[]{
            R.drawable.bat,
            R.drawable.bear,
            R.drawable.bee,
            R.drawable.butterfly,

    };

    private static int[] textResources = new int[]{

            R.string.my_baby,
            R.string.synchronize_yun,
            R.string.set_alarm,
            R.string.set_decive,

    };


    private static int imageResourceIndex = 0;
    private static int textResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    static int getTextResource() {
        if (textResourceIndex >= textResources.length) textResourceIndex = 0;
        return textResources[textResourceIndex++];
    }

    static SimpleCircleButton.Builder getSimpleCircleButtonBuilder() {
        return new SimpleCircleButton.Builder()
                .normalImageRes(getImageResource());
    }

   public  static TextInsideCircleButton.Builder getTextInsideCircleButtonBuilder() {
        return new TextInsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(getTextResource());
    }
    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }
}
