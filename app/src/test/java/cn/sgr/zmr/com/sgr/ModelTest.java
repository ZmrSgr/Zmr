package cn.sgr.zmr.com.sgr;

import android.content.Context;

import com.bean.entity.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cn.sgr.zmr.com.sgr.Common.Model.imp.CommonImp;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.Result;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import okhttp3.Call;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by 沈国荣 on 2016/11/29 0029.
 */

public class ModelTest {
    @Mock
    private Context mContext;


    @Before
    public void setupModelTest() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

    }
    @Test
    public void LoginModelTest() {
        User user=new User();
        user.setPassword("123456");
        user.setPhone("13602284381");
        CommonImp commonModel=new CommonImp();
        commonModel.Login(mContext, user, new HttpRequestCallback<Result<User>>() {
            @Override
            public void onStart() {
                System.out.println("开始");
            }

            @Override
            public void onFinish() {
                System.out.println("结束");
            }

            @Override
            public void onResponse(Result<User> userResult) {
                System.out.println("onResponse");

            }

            @Override
            public void onFailure(Call call, HttpException e) {
                System.out.println("失败");
            }
        });
    }



}
