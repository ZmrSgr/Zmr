package cn.sgr.zmr.com.sgr.Modules.Health.Model;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class Result<T> {
    public static final int RESULT_OK = 200;



    public String msg;


    public String status;


    public int code;


    public T data;
}
