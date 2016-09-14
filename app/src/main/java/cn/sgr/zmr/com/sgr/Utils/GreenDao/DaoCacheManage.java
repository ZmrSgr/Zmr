package cn.sgr.zmr.com.sgr.Utils.GreenDao;

import android.content.Context;

import com.bean.dao.BabyDao;
import com.bean.dao.UserDao;
import com.bean.entity.Baby;
import com.bean.entity.User;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
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
        daoManager = DaoManager.getInstance();
        daoManager.initManager(context);
        contexts=context;
    }
    //更新和保存当前用户
    public boolean updateUser(User user) {
        boolean flag = false;
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
    //宝宝的列表
    public List<Baby> getBabys(){
        List<Baby> babys = new ArrayList<Baby>();//登录的父亲id为uid，没有登录的话父亲id为“1”
        if (UserInfo.getInstance(contexts).hasSignIn()) {
            //查询构建器
            QueryBuilder<Baby> queryBuilder = daoManager.getDaoSession().queryBuilder(Baby.class);
            babys =queryBuilder.where(BabyDao.Properties.Fid.eq(UserInfo.getInstance(contexts).getMyUserId())).list();
        }else{
            QueryBuilder<Baby> queryBuilder = daoManager.getDaoSession().queryBuilder(Baby.class);
            babys =queryBuilder.where(BabyDao.Properties.Fid.eq("1")).list();
        }


        return babys;
    }
    //更新baby和插入
    public boolean updateBaby(Baby baby,boolean isOnline) {
        boolean flag = false;
        if (isOnline) {//已经登录
            //查询构建器
            List<Baby> babyList = new ArrayList<Baby>();
            QueryBuilder<Baby> queryBuilder = daoManager.getDaoSession().queryBuilder(Baby.class);
            babyList =queryBuilder.where(BabyDao.Properties.Bid.eq(baby.getBid())).list();
            if(babyList.size()>0){//表示已经存在，那么更新
                daoManager.getDaoSession().update(baby);
                flag = true;
            }else{//表示不存在，那么插入
                flag = daoManager.getDaoSession().insert(baby) != -1 ? true : false;
            }



        }else{//如果没有登录
            Baby selectbaby= daoManager.getDaoSession().load(Baby.class, baby.getId());
            if (baby == null) {//不存在就插入

                flag = daoManager.getDaoSession().insert(baby) != -1 ? true : false;

            }else{//存在就更新
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

}
