package cn.sgr.zmr.com.sgr.Utils.util;

import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sgr.zmr.com.sgr.Utils.http.RequestParams;
import okhttp3.FormBody;

/**
 * 参数加密工具类
 * Created by Administrator on 2016/10/19.
 */
public class EncryptUtil {
    public static String getEncryptParam(@Nullable RequestParams requestParams) {
        List<Map<String, String>> paramvalues = new ArrayList<>();
        Map<String, String> temp = new HashMap<String, String>();
        StringBuffer allParam = new StringBuffer();
        String encryptParam = "";

        FormBody params = requestParams.toParams();

        for (int i = 0; i < params.size(); i++) {
            Map<String, String> mParam = new HashMap<String, String>();
            mParam.put("name", params.name(i));
            mParam.put("value", params.value(i));
            paramvalues.add(mParam);
        }
        // 用简单的冒泡排序来对参数进行排序用于加密
        for (int i = 0; i < paramvalues.size(); i++) {
            for (int j = i + 1; j < paramvalues.size(); j++) {
                if (paramvalues.get(i).get("name").compareTo(paramvalues.get(j).get("name")) > 0) {
                    temp = paramvalues.get(i);
                    paramvalues.set(i, paramvalues.get(j));
                    paramvalues.set(j, temp);
                }
            }
        }
//        Map<String, String> secretParam = new HashMap<String, String>();
        String secret = "";
        for (int i = 0; i < paramvalues.size(); i++) {
            // 参数os的值为1或2时，明文后添加相应的字符串
            // os 参数的类型：IOS(1); Android(2)
            if (paramvalues.get(i).get("name").equals("os") && paramvalues.get(i).get("value").equals("1")) {
//                secretParam.put("name", "sign");
//                secretParam.put("value", "G+)YO!ZUD(");
                secret = "G+)YO!ZUD(";
            } else if (paramvalues.get(i).get("name").equals("os") && paramvalues.get(i).get("value").equals("2")) {
//                secretParam.put("name", "sign");
//                secretParam.put("value", "+8K98FF_TU");
                secret = "+8K98FF_TU";
            }
            allParam.append(paramvalues.get(i).get("name")+ "="+ paramvalues.get(i).get("value") + "&");
        }
        String str = allParam.toString() + secret;
        encryptParam = getMD5(str);// 明文添加完secret后，加密字符串，获得最后一个参数sign

        return encryptParam;
    }

    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
