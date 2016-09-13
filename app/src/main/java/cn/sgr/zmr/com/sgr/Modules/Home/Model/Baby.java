package cn.sgr.zmr.com.sgr.Modules.Home.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import cn.sgr.zmr.com.sgr.Common.Model.data.User;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
@Table(name = "Baby")
public class Baby extends Model {

    //宝宝id
    @Column(name = "bid")
    public  String bid;
    //父亲id
    @Column(name = "fid")
    public  String fid;

    @Column(name = "name")
    public  String name;

    @Column(name = "age")
    public  String age;

    @Column(name = "sex")
    public  String sex;

    @Column(name = "weight")
    public  String weight;

    @Column(name = "hight")
    public  String hight;

    @Column(name = "deviceName")
    public  String deviceName;

    @Column(name = "img")
    public  String img;

    public Baby(String name, String age, String sex, String weight, String hight, String deviceName) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.weight = weight;
        this.hight = hight;
        this.deviceName = deviceName;
    }
    //更新和保存baby
    public Baby update(Baby baby,boolean isOnline) {
        Baby userExisit;
        if(isOnline){//表示用户已经登录了
            userExisit  = new Select().from(Baby.class)
                    .where("bid= ? ", baby.bid).executeSingle();
        }else{//表示本地
            userExisit  = new Select().from(Baby.class)
                    .where("id= ? ", baby.getId()).executeSingle();
        }
        // 不存
        if (userExisit == null) {
            userExisit = this;
        }else{
            if(img!=null)
                userExisit.img = img;
            if(deviceName!=null)
                userExisit.deviceName = deviceName;
            if(hight!=null)
                userExisit.hight = hight;
            if(weight!=null)
                userExisit.weight=weight;
            if(sex!=null)
                userExisit.sex = sex;

            if(age!=null)
                userExisit.age = age;

            if(name!=null)
                userExisit.name = name;

            if(fid!=null)
                userExisit.fid = fid;

            if(bid!=null)
                userExisit.bid = bid;
        }
        userExisit.save();
        return userExisit;
    }
    //删除宝宝
    public void DeleteBaby(String id){
        new Delete().from(Baby.class).where("id = \"" + id + "\"")
                .executeSingle();

    }
}
