package cn.sgr.zmr.com.sgr.Modules.Home.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
@Table(name = "Baby")
public class Baby extends Model {

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
}
