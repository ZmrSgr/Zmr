package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoMaker {

    public static void main(String[] args) {
        //生成数据库的实体类,还有版本号

        Schema schema = new Schema(4, "com.bean.entity");
        addUser(schema);
        addBaby(schema);
        BabyTemperature(schema);
        BabyTreat(schema);
        SearchRecent(schema);

        //指定dao
        schema.setDefaultJavaPackageDao("com.bean.dao");
        try {
            //指定路径
            new DaoGenerator().generateAll(schema, "F:\\WorkStation\\Zmr\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据库的表  用户
     *
     * @param schema
     */
    public static void addUser(Schema schema) {


        //创建数据库的表
        Entity entity = schema.addEntity("User");
        //序列化
        entity.implementsSerializable();
        //主键 是int类型
        entity.addIdProperty().autoincrement();
//        用户id
        entity.addStringProperty("uid");
//        第三方id
        entity.addStringProperty("third_id");
//        密码
        entity.addStringProperty("password");
//        电话
        entity.addStringProperty("phone");
//        头像
        entity.addStringProperty("avatar");
        //昵称
        entity.addStringProperty("nickname");

        //sid
        entity.addStringProperty("sid");

        // 性别1为女 2为男
        entity.addStringProperty("gender");


    }
    /**
     * 创建数据库的表  宝宝
     *
     * @param schema
     */
    public static void addBaby(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("Baby");

        entity.implementsSerializable();
        //主键 是int类型
        entity.addIdProperty().autoincrement();
//        宝宝id
        entity.addStringProperty("bid");
//        父亲id（用户id）
        entity.addStringProperty("fid");
//        宝宝名称
        entity.addStringProperty("name");
//        宝宝年龄
        entity.addStringProperty("age");
        entity.addStringProperty("sex");
//        体重
        entity.addStringProperty("weight");
//        高度
        entity.addStringProperty("hight");
//        蓝牙设备名称
        entity.addStringProperty("deviceName");
//        头像
        entity.addStringProperty("img");
        entity.addBooleanProperty("isconnect");
    }

    /**
     * 创建数据库的表  宝宝的体温记录
     *
     * @param schema
     */
    public static void BabyTemperature(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("Chart");
        //主键 是int类型
        entity.addIdProperty().autoincrement();
//        表格id
        entity.addStringProperty("cid");
//       （用户id）
        entity.addStringProperty("uid");
//        宝宝id
        entity.addStringProperty("bid");
//        温度
        entity.addStringProperty("temperature");
//        时间
        entity.addStringProperty("time");
        //        日期
        entity.addStringProperty("date");
    }
    /**
     * 创建数据库的表  宝宝的治疗记录
     *
     * @param schema
     */
    public static void BabyTreat(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("Treat");
        //主键 是int类型
        entity.addIdProperty().autoincrement();
//        治疗记录的id
        entity.addStringProperty("tid");
//       （用户id）
        entity.addStringProperty("uid");
//        宝宝id
        entity.addStringProperty("bid");
//        温度
        entity.addStringProperty("temperature");
//        时间
        entity.addStringProperty("time");
//        日期
        entity.addStringProperty("date");
//       物理治疗
        entity.addStringProperty("physics");
//        用药
        entity.addStringProperty("medicine");
    }

    /**
     * 创建数据库的表  最近搜索记录
     *
     * @param schema
     */
    public static void SearchRecent(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("SearchRecent");
        //主键 是int类型
        entity.addIdProperty().autoincrement();
//        治疗记录的内容
        entity.addStringProperty("title");
//       备用
        entity.addStringProperty("other");

    }
}
