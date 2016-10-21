package cn.sgr.zmr.com.sgr.Modules.Home.Model.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */

public class StoreResult {

    /**
     * areaCode : 440400
     * ad : [{"hospitalId":1,"hospitalName":"有问必答网医务室(3)","realname":"蒋军","departmentShow":"内科-消化内科","goodAt":"癫痫病","avatar":"http://net.120askimages.com/ztc_kw/face/0/a87ff679a2f3e71d9181a67b7542122c.jpg","url":"http://webim.120ask.com/client.html?id=0g3h0ca2nlwg3h0d&broswer=3g&fromflat=app"}]
     * hospital : [{"id":1849,"hospitalName":"珠海市平沙医院","address":"广东省珠海市金湾区平沙二路87号","distance":11.315226,"latitude":22.107214,"longitude":113.1895,"url":"http://m.120ask.com/hospital/detail?keyword=珠海市平沙医院&lat=22.107214&lng=113.1895"},{"id":22867,"hospitalName":"平沙迎雁社区卫生服务站","address":"珠海金湾区平沙迎雁社区卫生服务站迎雁东路168号","distance":12.859142,"latitude":22.113527,"longitude":113.202913,"url":"http://m.120ask.com/hospital/detail?keyword=平沙迎雁社区卫生服务站&lat=22.113527&lng=113.202913"},{"id":5086,"hospitalName":"台山市麻风病医院","address":"广东省江门市台山县","distance":20.931416,"latitude":21.88619,"longitude":113.049635,"url":"http://m.120ask.com/hospital/detail?keyword=台山市麻风病医院&lat=21.88619&lng=113.049635"},{"id":1846,"hospitalName":"珠海市斗门区侨立中医院","address":"广东省珠海市斗门区连桥新路65号","distance":23.202393,"latitude":22.216637,"longitude":113.247982,"url":"http://m.120ask.com/hospital/detail?keyword=珠海市斗门区侨立中医院&lat=22.216637&lng=113.247982"},{"id":1845,"hospitalName":"遵义医学院第五附属医院","address":"广东省珠海市斗门区港霞东路268号","distance":27.387138,"latitude":22.217253,"longitude":113.300586,"url":"http://m.120ask.com/hospital/detail?keyword=遵义医学院第五附属医院&lat=22.217253&lng=113.300586"},{"id":1847,"hospitalName":"珠海市斗门区妇幼保健院","address":"广东省珠海市斗门区港霞路76号","distance":27.43681,"latitude":22.216905,"longitude":113.301461,"url":"http://m.120ask.com/hospital/detail?keyword=珠海市斗门区妇幼保健院&lat=22.216905&lng=113.301461"},{"id":23655,"hospitalName":"三灶社区居民委员会社区卫生服务站","address":"珠海市金湾区三灶镇高椅街86号（三灶社区公共服务站旁）","distance":27.820559,"latitude":22.053061,"longitude":113.355966,"url":"http://infoacq.120ask.com/ia/info/operationpage.jsp?id=23655"},{"id":1848,"hospitalName":"珠海市红旗医院","address":"广东省珠海市红旗镇藤山一路一号","distance":29.444021,"latitude":22.149176,"longitude":113.35974,"url":"http://m.120ask.com/hospital/detail?keyword=珠海市红旗医院&lat=22.149176&lng=113.35974"},{"id":17380,"hospitalName":"珠海市金湾区妇幼保健院","address":"广东省珠海市金湾区金海岸海华新村20-21号","distance":31.468315,"latitude":22.074859,"longitude":113.392014,"url":"http://m.120ask.com/hospital/detail?keyword=珠海市金湾区妇幼保健院&lat=22.074859&lng=113.392014"},{"id":21560,"hospitalName":"珠海源春林中西医结合医院","address":"广东省珠海市金湾区 三灶镇金海岸华阳路508号","distance":31.533447,"latitude":22.075237,"longitude":113.39264,"url":"http://infoacq.120ask.com/ia/info/operationpage.jsp?id=21560"},{"id":21539,"hospitalName":"健康云公司医务室","address":"珠海市金湾区三灶镇机场东路288号","distance":31.931587,"latitude":22.083962,"longitude":113.396227,"url":"http://infoacq.120ask.com/ia/info/operationpage.jsp?id=21539"},{"id":21571,"hospitalName":"120健康网医师协会","address":"广东省珠海市金湾区机场路","distance":32.04341,"latitude":22.08454,"longitude":113.397284,"url":"http://m.120ask.com/hospital/detail?keyword=120健康网医师协会&lat=22.08454&lng=113.397284"},{"id":5082,"hospitalName":"台山市人民医院","address":"广东省江门市台山市台城环北大道80号","distance":33.96879,"latitude":22.252997,"longitude":112.822529,"url":"http://m.120ask.com/hospital/detail?keyword=台山市人民医院&lat=22.252997&lng=112.822529"},{"id":5083,"hospitalName":"台山市中医院","address":"广东省江门市台山市台城镇台西路196号","distance":34.06339,"latitude":22.232575,"longitude":112.80643,"url":"http://m.120ask.com/hospital/detail?keyword=台山市中医院&lat=22.232575&lng=112.80643"},{"id":1990,"hospitalName":"江门市新会区麻风病院","address":"广东省江门市新会区垄门镇苍山村","distance":35.720303,"latitude":22.388215,"longitude":113.034751,"url":"http://m.120ask.com/hospital/detail?keyword=江门市新会区麻风病院&lat=22.388215&lng=113.034751"},{"id":5084,"hospitalName":"台山市妇幼保健院","address":"广东省江门台山市台城镇环北大道74号","distance":36.359684,"latitude":22.258888,"longitude":112.798507,"url":"http://m.120ask.com/hospital/detail?keyword=台山市妇幼保健院&lat=22.258888&lng=112.798507"},{"id":5089,"hospitalName":"开平市荻海医院","address":"广东省开平市荻海中和后街13号","distance":36.758366,"latitude":22.257718,"longitude":112.792923,"url":"http://m.120ask.com/hospital/detail?keyword=开平市荻海医院&lat=22.257718&lng=112.792923"},{"id":5085,"hospitalName":"台山市工人医院","address":"广东省江门市台山市龙舟路12号","distance":36.99105,"latitude":22.256714,"longitude":112.789458,"url":"http://m.120ask.com/hospital/detail?keyword=台山市工人医院&lat=22.256714&lng=112.789458"},{"id":31806,"hospitalName":"中山市神湾医院","address":"广东省中山市神湾镇沙岗街15号","distance":39.034397,"latitude":22.303218,"longitude":113.370749,"url":"http://m.120ask.com/hospital/detail?keyword=中山市神湾医院&lat=22.303218&lng=113.370749"},{"id":29910,"hospitalName":"中山市板芙镇四联村卫生站","address":"广东省中山市板芙镇四联村委会","distance":44.154644,"latitude":22.421133,"longitude":113.288366,"url":"http://m.120ask.com/hospital/detail?keyword=中山市板芙镇四联村卫生站&lat=22.421133&lng=113.288366"}]
     */

    private String areaCode;
    /**
     * hospitalId : 1
     * hospitalName : 有问必答网医务室(3)
     * realname : 蒋军
     * departmentShow : 内科-消化内科
     * goodAt : 癫痫病
     * avatar : http://net.120askimages.com/ztc_kw/face/0/a87ff679a2f3e71d9181a67b7542122c.jpg
     * url : http://webim.120ask.com/client.html?id=0g3h0ca2nlwg3h0d&broswer=3g&fromflat=app
     */

    private List<AdBean> ad;
    /**
     * id : 1849
     * hospitalName : 珠海市平沙医院
     * address : 广东省珠海市金湾区平沙二路87号
     * distance : 11.315226
     * latitude : 22.107214
     * longitude : 113.1895
     * url : http://m.120ask.com/hospital/detail?keyword=珠海市平沙医院&lat=22.107214&lng=113.1895
     */

    private List<HospitalBean> hospital;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public List<AdBean> getAd() {
        return ad;
    }

    public void setAd(List<AdBean> ad) {
        this.ad = ad;
    }

    public List<HospitalBean> getHospital() {
        return hospital;
    }

    public void setHospital(List<HospitalBean> hospital) {
        this.hospital = hospital;
    }

    public static class AdBean {
        private int hospitalId;
        private String hospitalName;
        private String realname;
        private String departmentShow;
        private String goodAt;
        private String avatar;
        private String url;

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getDepartmentShow() {
            return departmentShow;
        }

        public void setDepartmentShow(String departmentShow) {
            this.departmentShow = departmentShow;
        }

        public String getGoodAt() {
            return goodAt;
        }

        public void setGoodAt(String goodAt) {
            this.goodAt = goodAt;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class HospitalBean {
        private int id;
        private String hospitalName;
        private String address;
        private double distance;
        private double latitude;
        private double longitude;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
