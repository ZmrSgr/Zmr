package cn.sgr.zmr.com.sgr.Modules.Health.Model.bean;

/**
 * Created by 沈国荣 on 2016/9/27 0027.
 */
public class Search {
    String kw;//关键字
    String page;//页码
    String t;//t==limit 时 仅仅返回5条数据，其他默认返回10条数据
    String test;//浏览数据
    String is_yuyin;//1语音2文字0其他
    String version;//813及以上，必须传
    String latitude;
    String longitude;

    public String getKw() {
        return kw;
    }

    public String getPage() {
        return page;
    }

    public String getT() {
        return t;
    }

    public String getIs_yuyin() {
        return is_yuyin;
    }

    public String getTest() {
        return test;
    }

    public String getVersion() {
        return version;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setIs_yuyin(String is_yuyin) {
        this.is_yuyin = is_yuyin;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void setT(String t) {
        this.t = t;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
