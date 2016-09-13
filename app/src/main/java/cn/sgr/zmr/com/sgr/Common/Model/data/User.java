package cn.sgr.zmr.com.sgr.Common.Model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;

/**
 * Created by 沈国荣 on 2016/8/19 0019.
 */
@Table(name = "User")
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

    @Column(name = "babys")
    public ArrayList<Baby> babys = new ArrayList<Baby>();

    //宝宝的列表
    public List<Baby> Babys(String fid){
        List<Baby> babys = new ArrayList<Baby>();
        List<Baby> babyList = new Select().from(Baby.class).where("fid = \""+ fid+"\"").execute();
        return babyList;
    }

    //更新当前用户
    public User update() {
        User userExisit = new Select().from(User.class)
                .where("uid = ? ", uid).executeSingle();

        // 不存�?
        if (userExisit == null) {
            userExisit = this;
        }else{
            if(uid!=null)
                userExisit.uid = uid;
            if(tid!=null)
                userExisit.tid = tid;
            if(password!=null)
                userExisit.password = password;
            if(phone!=null)
                userExisit.phone=phone;
            if(babys!=null)
                userExisit.babys = babys;
        }

        userExisit.save();
        return userExisit;
    }


}
