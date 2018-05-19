package com.ljr.factory.data.helper;

import com.ljr.factory.model.db.Group;
import com.ljr.factory.model.db.GroupMember;
import com.ljr.factory.model.db.Group_Table;
import com.ljr.factory.model.db.Message;
import com.ljr.factory.model.db.Session;
import com.ljr.factory.model.db.User;
import com.ljr.factory.model.db.model.AppDatabase;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 林佳荣 on 2018/4/24.
 * Github：https://github.com/ljrRookie
 * Function ：数据库的辅助类
 */

public class DbHelper {
    private static final DbHelper instance;
    static {
        instance = new DbHelper();
    }
    private DbHelper() {
    }

    /**
     * 观察者的集合
     * Class<?>:观察的表
     * Set<Changedlistener>:每一个表对应的观察者有很多
     */
    private final Map<Class<?>, Set<ChangedListener>> changedListeners = new HashMap<>();

    /**
     * 从所有的监听者中，获取某一个表的所有监听者
     * @param modelClass
     * @param <Model>
     * @return
     */
    private final <Model extends BaseModel> Set<ChangedListener> getListeners(Class<Model> modelClass){
        if(changedListeners.containsKey(modelClass)){
            return changedListeners.get(modelClass);
        }
        return null;
    }

    /**
     * 添加一个监听
     * @param tClass 对某个表的关注
     * @param listener  监听者
     * @param <Model> 表的泛型
     */
    public static <Model extends BaseModel> void addChangeListener(final Class<Model> tClass,ChangedListener<Model> listener){
        Set<ChangedListener> changedListeners = instance.getListeners(tClass);
        if (changedListeners == null) {
            // 初始化某一类型的容器
            changedListeners = new HashSet<>();
            // 添加到中的Map
            instance.changedListeners.put(tClass, changedListeners);
        }
        changedListeners.add(listener);

    }
    /**
     * 删除某一个表的某一个监听器
     * @param tClass   表
     * @param listener 监听器
     * @param <Model>  表的范型
     */
    public static <Model extends BaseModel> void removeChangedListener(final Class<Model> tClass,
                                                                       ChangedListener<Model> listener) {
        Set<ChangedListener> changedListeners = instance.getListeners(tClass);
        if (changedListeners == null) {
            // 容器本身为null，代表根本就没有
            return;
        }
        // 从容器中删除你这个监听者
        changedListeners.remove(listener);
    }
    /**
     * 新增或者修改的统一方法
     * @param tClass  传递一个Class信息
     * @param models  这个Class对应的实例的数组
     * @param <Model> 这个实例的范型，限定条件是BaseModel
     */
    public static <Model extends BaseModel> void save(final Class<Model> tClass,
                                                      final Model... models) {
        if (models == null || models.length == 0)
            return;

        // 当前数据库的一个管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        // 提交一个事物
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // 执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tClass);
                // 保存
                adapter.saveAll(Arrays.asList(models));
                // 唤起通知
                instance.notifySave(tClass, models);
            }
        }).build().execute();
    }


    public static <Model extends BaseModel> void delete(final Class<Model> tClass,final Model...models){
        if(models == null || models.length == 0){
            return;
        }
        //当前数据库的一个管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tClass);
                adapter.deleteAll(Arrays.asList(models));
                instance.notifyDelete(tClass,models);
            }
        }).build().execute();


    }

    /**
     * 进行通知调用
     *
     * @param tClass  通知的类型
     * @param models  通知的Model数组
     * @param <Model> 这个实例的范型，限定条件是BaseModel
     */
    private final <Model extends BaseModel> void notifySave(final Class<Model> tClass,
                                                            final Model... models) {
        // 找监听器
        final Set<ChangedListener> listeners = getListeners(tClass);
        if (listeners != null && listeners.size() > 0) {
            // 通用的通知
            for (ChangedListener<Model> listener : listeners) {
                listener.onDataSave(models);
             }
        }

       // 列外情况
        if (GroupMember.class.equals(tClass)) {
            // 群成员变更，需要通知对应群信息更新
            updateGroup((GroupMember[]) models);
        } else if (Message.class.equals(tClass)) {
            // 消息变化，应该通知会话列表更新
            updateSession((Message[]) models);
        }
    }
    /**
     * 从消息列表中，筛选出对应的会话，并对会话进行更新
     *
     * @param messages Message列表
     */
    private void updateSession(Message... messages) {
        // 标示一个Session的唯一性
        final Set<Session.Identify> identifies = new HashSet<>();
        for (Message message : messages) {
            Session.Identify identify = Session.createSessionIdentify(message);
            identifies.add(identify);
        }

        // 异步的数据库查询，并异步的发起二次通知
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Session> adapter = FlowManager.getModelAdapter(Session.class);
                Session[] sessions = new Session[identifies.size()];

                int index = 0;
                for (Session.Identify identify : identifies) {
                    Session session = SessionHelper.findFromLocal(identify.id);

                    if (session == null) {
                        // 第一次聊天，创建一个你和对方的一个会话
                        session = new Session(identify);
                    }

                    // 把会话，刷新到当前Message的最新状态
                    session.refreshToNow();
                    // 数据存储
                    adapter.save(session);
                    // 添加到集合
                    sessions[index++] = session;
                }

                // 调用直接进行一次通知分发
                instance.notifySave(Session.class, sessions);

            }
        }).build().execute();
    }
    /**
     * 从成员中找出成员对应的群，并对群进行更新
     *
     * @param members 群成员列表
     */
    private void updateGroup(GroupMember...members) {
        // 不重复集合
        final Set<String> groupIds = new HashSet<>();
        for (GroupMember member : members) {
            // 添加群Id
            groupIds.add(member.getGroup().getId());
        }

        // 异步的数据库查询，并异步的发起二次通知
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // 找到需要通知的群
                List<Group> groups = SQLite.select()
                        .from(Group.class)
                        .where(Group_Table.id.in(groupIds))
                        .queryList();

                // 调用直接进行一次通知分发
                instance.notifySave(Group.class, groups.toArray(new Group[0]));

            }
        }).build().execute();
    }

    /**
     * 进行通知调用
     *
     * @param tClass  通知的类型
     * @param models  通知的Model数组
     * @param <Model> 这个实例的范型，限定条件是BaseModel
     */
    @SuppressWarnings("unchecked")
    private final <Model extends BaseModel> void notifyDelete(final Class<Model> tClass,
                                                              final Model... models) {
        // 找监听器
        final Set<ChangedListener> listeners = getListeners(tClass);
        if (listeners != null && listeners.size() > 0) {

            // 通用的通知
            for (ChangedListener<Model> listener : listeners) {
                listener.onDataDelete(models);
            }
        }

        // 列外情况
        if (GroupMember.class.equals(tClass)) {
            // 群成员变更，需要通知对应群信息更新
            updateGroup((GroupMember[]) models);
        } else if (Message.class.equals(tClass)) {
            // 消息变化，应该通知会话列表更新
            updateSession((Message[]) models);
        }
    }

    /**
     * 通知监听器
     */
    @SuppressWarnings({"unused", "unchecked"})
    public interface ChangedListener<Data extends BaseModel> {
        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }

/*    *//**
     * 模拟数据
     *//*
    public static  void loadUser(){
        User user = new User();
        user.setId("2");
        user.setName("ljr");
        user.setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525779042979&di=8eb6e95cd5d352c12b3da9a232f0ef72&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201504%2F10%2F20150410H1256_QAULP.jpeg");
        user.setFollow(true);
        user.setDesc("android 开发");
        DbHelper.save(User.class, user);
        User user1 = new User();
        user1.setId("3");
        user1.setName("广交学子");
        user1.setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525779123407&di=2042f86c4666a49e3f918d73bcb2aea7&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F11%2F20151211133408_XyLeV.jpeg");
        user1.setFollow(true);
        user1.setDesc("广东交通职业技术学院");
        DbHelper.save(User.class, user1);
        User user2 = new User();
        user2.setId("4");
        user2.setName("蜘蛛侠");
        user2.setPortrait("http://f.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=38aa7da33f01213fcf6646d861d71ae7/3c6d55fbb2fb4316d898330026a4462308f7d3c1.jpg");
        user2.setFollow(true);
        user2.setDesc("爬来爬去，飞来飞去");
        DbHelper.save(User.class, user2);
        User user3 = new User();
        user3.setId("5");
        user3.setName("琴女");
        user3.setPortrait("http://dynamic-image.yesky.com/600x-/uploadImages/upload/20141120/qdyum2m0yqfpng.png");
        user3.setFollow(true);
        user3.setDesc("英雄联盟 -- 奶妈");
        DbHelper.save(User.class, user3);
        User user4 = new User();
        user4.setId("6");
        user4.setName("表姐");
        user4.setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525779617307&di=6c64ffa5ed7c902b23b5eaf1633ac3ea&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Fup%2F2017-11%2F2017112812040936427.jpg");
        user4.setFollow(true);
        user4.setDesc("管好自己的嘴巴");
        DbHelper.save(User.class, user4);
        User user5 = new User();
        user5.setId("7");
        user5.setName("网红姐姐");
        user5.setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525779682388&di=98d9e1e80a2e07e9714e41c04bc9ce44&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Fup%2F2017-12%2F2017122810242483341.jpg");
        user5.setFollow(true);
        user5.setDesc("每天都是微笑日");
        DbHelper.save(User.class, user5);
    }*/
}
