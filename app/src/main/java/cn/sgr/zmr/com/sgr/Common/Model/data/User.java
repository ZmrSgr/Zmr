package cn.sgr.zmr.com.sgr.Common.Model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by 沈国荣 on 2016/8/19 0019.
 */
@Table(name = "Baby")
public class User extends Model {
    @Column(name = "uid")//用户id
    public  String uid;


    @Column(name = "tid")//第三方（唯一标识符）id
    public  String tid;

    @Column(name = "password")//用户密码
    public  String password;

    @Column(name = "phone")//电话号码
    public  String phone;


    @Column(name = "avatar")//头像
    public  String avatar;

}
