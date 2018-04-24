package com.ljr.factory.data.helper;

import com.ljr.factory.presenter.model.db.model.AppDatabase;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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

      /*  // 列外情况
        if (GroupMember.class.equals(tClass)) {
            // 群成员变更，需要通知对应群信息更新
            updateGroup((GroupMember[]) models);
        } else if (Message.class.equals(tClass)) {
            // 消息变化，应该通知会话列表更新
            updateSession((Message[]) models);
        }*/
    }
    /**
     * 通知监听器
     */
    @SuppressWarnings({"unused", "unchecked"})
    public interface ChangedListener<Data extends BaseModel> {
        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }
}
