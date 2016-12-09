package cn.sgr.zmr.com.sgr.Modules.Home.Model.bean;

/**
 * Created by 沈国荣 on 2016/10/10 0010.
 *
 * 药店信息
 */
public class Drug implements  java.io.Serializable{
/*
    "durgstoreName": "珠海市金鼎恒祥学康医药堂", //药店名称
            "address": "珠海市唐家湾镇金鼎北京师范大学珠海分校海华苑6栋1号铺B区", // 药店地址
            "latitude": 22.347721, // 药店纬度
            "longitude": 113.530487, // 药店经度
            "distance": 3.5314624, // 与当前用户相距距离
            "telephone": "6128350" // 电话
*/

    String url;
    String durgstoreName;
    String address;
    String latitude;
    String longitude;
    String distance;
    String telephone;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDurgstoreName() {
        return durgstoreName;
    }

    public void setDurgstoreName(String durgstoreName) {
        this.durgstoreName = durgstoreName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
