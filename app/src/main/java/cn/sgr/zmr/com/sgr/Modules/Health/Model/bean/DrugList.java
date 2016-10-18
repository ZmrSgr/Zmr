package cn.sgr.zmr.com.sgr.Modules.Health.Model.bean;

/**
 * Created by 沈国荣 on 2016/10/12 0012.
 */
public class DrugList {
    String itemname;
    String pack_pic;
    String info;
    String company;
    String url;

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPack_pic() {
        return pack_pic;
    }

    public void setPack_pic(String pack_pic) {
        this.pack_pic = pack_pic;
    }
}
