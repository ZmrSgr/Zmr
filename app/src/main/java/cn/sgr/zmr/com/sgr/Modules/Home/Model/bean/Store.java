package cn.sgr.zmr.com.sgr.Modules.Home.Model.bean;

/**
 * Created by 沈国荣 on 2016/10/10 0010.
 *
 * 医院信息
 */
public class Store {

 /*   "id": 20149, // 医院ID
            "hospitalName": "家庭医院", // 医院名称
            "address": "中山市坦洲镇汇翠山庄", // 医院地址
            "distance": 9.051189, // 相距距离，单位：km
            "latitude": 22.270868, // 医院所在地理位置的纬度
            "longitude": 113.490817, // 医院所在地理位置的经度
            "url": "http://m.120ask.com/hospital/detail?keyword=家庭医院" // 医院详情跳转url*/

    String id;
    String hospitalName;
    String address;
    String distance;
    String latitude;
    String longitude;
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
