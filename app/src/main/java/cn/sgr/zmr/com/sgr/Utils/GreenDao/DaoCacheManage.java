package cn.sgr.zmr.com.sgr.Utils.GreenDao;

import android.content.Context;

import com.bean.dao.BabyDao;
import com.bean.dao.ChartDao;
import com.bean.dao.SearchRecentDao;
import com.bean.dao.TreatDao;
import com.bean.dao.UserDao;
import com.bean.entity.Baby;
import com.bean.entity.Chart;
import com.bean.entity.SearchRecent;
import com.bean.entity.Treat;
import com.bean.entity.User;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Base.MyApplication;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by 沈国荣 on 2016/9/14 0014.
 */
public class DaoCacheManage {
    //TAG
    private static final String TAG = DaoCacheManage.class.getSimpleName();
    private DaoManager daoManager;
    Context contexts;
    //构造方法
    public DaoCacheManage(Context context) {
        if (daoManager == null) {
            daoManager=  MyApplication.getGreenDao();
        }
            contexts=context;
    }
    //更新和保存当前用户
    public boolean updateUser(User user) {
        boolean flag = false;
//        user.setId(null);//避免出现重复的主键报错
        List<User> userList = new ArrayList<User>();
        QueryBuilder<User> queryBuilder = daoManager.getDaoSession().queryBuilder(User.class);
        userList =queryBuilder.where(UserDao.Properties.Uid.eq(user.getUid())).list();
                if(userList.size()>0){//存在就更新
                    try {
                        daoManager.getDaoSession().update(user);
                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{//不存在就插入
                    flag = daoManager.getDaoSession().insert(user) != -1 ? true : false;
                    return flag;
                }
        return flag;
    }
    /**
     * 查询单个用户
     *
     *
     * @return
     */
    public User FindUserByUid(String uid) {
        //查询构建器
        QueryBuilder<User> queryBuilder = daoManager.getDaoSession().queryBuilder(User.class);
        List<User> list =queryBuilder.where(UserDao.Properties.Uid.eq(uid)).limit(1).list();

        return list.get(0);
    }
    //删除宝宝
    public boolean DeleteUser(User user){
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    //设备绑定宝宝
    public void setCurryBaby(Baby  baby,String DeviceName){
        disConnect();
        baby.setIsconnect(true);
        baby.setDeviceName(DeviceName);
        daoManager.getDaoSession().update(baby);
    }
    //获得当前连接的宝宝
    public Baby getCurryBaby(){
        //查询构建器
        QueryBuilder<Baby> queryBuilder = daoManager.getDaoSession().queryBuilder(Baby.class);
        List<Baby> list =queryBuilder.where(BabyDao.Properties.Isconnect.eq(true)).limit(1).list();

        return list.size()>0?list.get(0):null;
    }
    //取消绑定
    public void disConnect(){
        final List<Baby> list=  daoManager.getDaoSession().loadAll(Baby.class);
        daoManager.getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    Baby baby=null;
                    baby= list.get(i);
                    baby.setIsconnect(false);
                    daoManager.getDaoSession().update(baby);
                }
            }
        });
    }

    //宝宝的列表
    public List<Baby> getBabys(){
        List<Baby> babys = new ArrayList<Baby>();//登录的父亲id为uid，没有登录的话父亲id为“-1”
        if (UserInfo.getInstance(contexts).hasSignIn()) {
            //查询构建器
            QueryBuilder<Baby> queryBuilder = daoManager.getDaoSession().queryBuilder(Baby.class);
            babys =queryBuilder.where(BabyDao.Properties.Fid.eq(UserInfo.getInstance(contexts).getMyUserId())).list();
        }else{
            QueryBuilder<Baby> queryBuilder = daoManager.getDaoSession().queryBuilder(Baby.class);
            babys =queryBuilder.where(BabyDao.Properties.Fid.eq("-1")).list();
        }
        return babys;
    }
    //更新baby和插入
    public boolean updateBaby(Baby baby,boolean isOnline) {
        boolean flag = false;
//        baby.setId(null);
        if (isOnline) {//已经登录
            //查询构建器
            List<Baby> babyList = new ArrayList<Baby>();
            QueryBuilder<Baby> queryBuilder = daoManager.getDaoSession().queryBuilder(Baby.class);
            if(baby.getBid()!=null){
                babyList =queryBuilder.where(BabyDao.Properties.Bid.eq(baby.getBid())).list();
            }
            if(babyList.size()>0){//表示已经存在，那么更新
                daoManager.getDaoSession().update(baby);
                flag = true;
            }else{//表示不存在，那么插入
                flag = daoManager.getDaoSession().insert(baby) != -1 ? true : false;
            }
        }else{//如果没有登录


            System.out.println("fid"+baby.getFid());
            if (baby.getFid()==null||baby.getFid().equals("")) {//不存在就插入

                baby.setFid("-1");
                flag = daoManager.getDaoSession().insert(baby) != -1 ? true : false;


            }else{
                System.out.println("更新");
                //存在就更新
                try {
                    daoManager.getDaoSession().update(baby);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return flag;
    }
    //删除宝宝
    public boolean DeleteBaby(Baby baby){
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(baby);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////



    //添加温度表
    public boolean InsertChart(Chart chart){
        boolean flag = false;
        flag = daoManager.getDaoSession().insert(chart) != -1 ? true : false;
        return flag;
    }
    //删除温度表
    public boolean DeleteChartByDay(String day){
        return false;
    }
    //删除全部温度表
    public boolean DeleteAllChart(){
        boolean flag = false;
        try {
            daoManager.getDaoSession().deleteAll(Chart.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
    //查看某一天的记录表
    public List<Chart> FindChartByDay(String day){
        List<Chart> charts = new ArrayList<Chart>();
        QueryBuilder<Chart> queryBuilder = daoManager.getDaoSession().queryBuilder(Chart.class);
        charts =queryBuilder.where(ChartDao.Properties.Date.eq(day)).list();
        return charts;

    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////



    //查看某一天的治疗记录
    public List<Treat>FindTreatByDay(String day){

        List<Treat> treats = new ArrayList<Treat>();
        QueryBuilder<Treat> queryBuilder = daoManager.getDaoSession().queryBuilder(Treat.class);
        treats =queryBuilder.where(TreatDao.Properties.Date.eq(day)).list();
        return treats;
    }
    //插入一条治疗记录
    public boolean InsertTreat(Treat treat){

        boolean flag = false;
        flag = daoManager.getDaoSession().insert(treat) != -1 ? true : false;
        return flag;
    }
    //删除一条记录
    public boolean DeleteTreate(Treat treat){

        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(treat);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }
    //修改某一条治疗记录
    public boolean UpdateTreate(Treat treat){

        boolean flag = false;
        try {
            daoManager.getDaoSession().update(treat);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    //查找一条治疗记录
    public Treat FindTreatByTid(String tid){
        return null;
    }

    public List<Treat> FindAll(){

        return daoManager.getDaoSession().loadAll(Treat.class);
    }

///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
    /**
     * 全部查询
     *
     * @return
     */
    public List<SearchRecent> listAllSearchRecent() {
        return Utils.orderDes( daoManager.getDaoSession().loadAll(SearchRecent.class));
    }

    /**
     * 删除
     *
     * @param SearchRecent
     * @return
     */
    public boolean deleteSearch(SearchRecent data) {
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(data);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }


    /**
     * 对数据库中student表的插入操作
     *
     * @param SearchRecent
     * @return
     */
    public boolean insertSearch(SearchRecent data) {
        boolean flag = false;
        //查询构建器
        QueryBuilder<SearchRecent> queryBuilder = daoManager.getDaoSession().queryBuilder(SearchRecent.class);

        List<SearchRecent> list = queryBuilder.where(SearchRecentDao.Properties.Title.ge(data.getTitle())).list();
        if (list != null&&list.size()>0) {
            flag=true;
        }else{
            flag = daoManager.getDaoSession().insert(data) != -1 ? true : false;
        }

        return flag;
    }




}
