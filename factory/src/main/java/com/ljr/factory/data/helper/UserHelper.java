package com.ljr.factory.data.helper;


import com.ljr.common.factory.data.DataSource;
import com.ljr.factory.Factory;
import com.ljr.factory.R;
import com.ljr.factory.model.api.RspModel;
import com.ljr.factory.model.api.user.UserUpdateModel;
import com.ljr.factory.model.card.UserCard;
import com.ljr.factory.model.db.User;
import com.ljr.factory.model.db.User_Table;
import com.ljr.factory.net.NetWork;
import com.ljr.factory.net.RemoteService;
import com.ljr.factory.presenter.contact.FollowPresenter;
import com.ljr.factory.presenter.user.UpdateInfoPresenter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;

/**
 * Created by 林佳荣 on 2018/5/7.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class UserHelper {


    public static List<UserCard> getSearchUser() {
        List<UserCard> data = new ArrayList<>();
        UserCard userCard = new UserCard();
        userCard.setId("2");
        userCard.setName("ljr");
        userCard.setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525779042979&di=8eb6e95cd5d352c12b3da9a232f0ef72&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201504%2F10%2F20150410H1256_QAULP.jpeg");
        userCard.setFollow(true);
        userCard.setDesc("android 开发");
        data.add(userCard);
        UserCard user1 = new UserCard();
        user1.setId("3");
        user1.setName("广交学子");
        user1.setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525779123407&di=2042f86c4666a49e3f918d73bcb2aea7&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F11%2F20151211133408_XyLeV.jpeg");
        user1.setFollow(true);
        user1.setDesc("广东交通职业技术学院");

        UserCard user2 = new UserCard();
        user2.setId("4");
        user2.setName("蜘蛛侠");
        user2.setPortrait("http://f.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=38aa7da33f01213fcf6646d861d71ae7/3c6d55fbb2fb4316d898330026a4462308f7d3c1.jpg");
        user2.setFollow(true);
        user2.setDesc("爬来爬去，飞来飞去");

        UserCard user3 = new UserCard();
        user3.setId("5");
        user3.setName("琴女");
        user3.setPortrait("http://dynamic-image.yesky.com/600x-/uploadImages/upload/20141120/qdyum2m0yqfpng.png");
        user3.setFollow(true);
        user3.setDesc("英雄联盟 -- 奶妈");

        UserCard user4 = new UserCard();
        user4.setId("6");
        user4.setName("表姐");
        user4.setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525779617307&di=6c64ffa5ed7c902b23b5eaf1633ac3ea&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Fup%2F2017-11%2F2017112812040936427.jpg");
        user4.setFollow(true);
        user4.setDesc("管好自己的嘴巴");

        UserCard user5 = new UserCard();
        user5.setId("7");
        user5.setName("网红姐姐");
        user5.setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525779682388&di=98d9e1e80a2e07e9714e41c04bc9ce44&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Fup%2F2017-12%2F2017122810242483341.jpg");
        user5.setFollow(true);
        user5.setDesc("每天都是微笑日");
        UserCard user6 = new UserCard();
        user6.setId("8");
        user6.setName("非主流");
        user6.setPortrait("http://p1.4499.cn/touxiang/UploadPic/2014-7/3/2014070318131624354.jpg");
        user6.setFollow(false);
        user6.setDesc("洗剪吹，洗剪吹");
        UserCard user7 = new UserCard();
        user7.setId("9");
        user7.setName("靓女");
        user7.setPortrait("http://img4.duitang.com/uploads/item/201602/07/20160207151120_zj5ES.jpeg");
        user7.setFollow(false);
        user7.setDesc("洗剪吹，洗剪吹");
        data.add(user1);
        data.add(user2);
        data.add(user3);
        data.add(user4);
        data.add(user5);
        data.add(user6);
        data.add(user7);
        return data;
    }

    // 搜索的方法
    public static Call search(String name, final DataSource.Callback<List<UserCard>> callback) {
        RemoteService service = NetWork.remote();
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);
        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()) {
                    // 返回数据
                    callback.onDataLoaded(rspModel.getResult());
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        // 把当前的调度者返回
        return call;
    }

    public static void follow(String id, final DataSource.Callback<UserCard> callback) {
        RemoteService service = NetWork.remote();
        Call<RspModel<UserCard>> call = service.userFollow(id);

        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    // 唤起进行保存的操作
                    Factory.getUserCenter().dispatch(userCard);
                    // 返回数据
                    callback.onDataLoaded(userCard);
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
    // 更新用户信息的操作，异步的
    public static void update(UserUpdateModel model, final DataSource.Callback<UserCard> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = NetWork.remote();
        // 得到一个Call
        Call<RspModel<UserCard>> call = service.userUpdate(model);
        // 网络请求
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    // 唤起进行保存的操作
                    Factory.getUserCenter().dispatch(userCard);
                    // 返回成功
                    callback.onDataLoaded(userCard);
                } else {
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    /**
     * 搜索一个用户，优先本地缓存，
     * 没有用然后再从网络拉取
     */
    public static User search(String id) {
        User user = findFromLocal(id);
        if (user == null) {
            return findFromNet(id);
        }
        return user;
    }

    public static User searchFirstOfNet(String id) {
        User user = findFromNet(id);
        if (user == null) {
            return findFromLocal(id);
        }
        return user;
    }
    // 从本地查询一个用户的信息
    public static User findFromLocal(String id) {
        return SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }
    // 从网络查询某用户的信息
    private static User findFromNet(String id) {
        RemoteService remoteService = NetWork.remote();
        try {
            Response<RspModel<UserCard>> response = remoteService.userFind(id).execute();
            UserCard card = response.body().getResult();
            if (card != null) {
                User user = card.build();
                // 数据库的存储并通知
                Factory.getUserCenter().dispatch(card);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 刷新联系人的操作，不需要Callback，直接存储到数据库，
    // 并通过数据库观察者进行通知界面更新，
    // 界面更新的时候进行对比，然后差异更新
    public static void refreshContacts() {
        RemoteService service = NetWork.remote();
        service.userContacts()
                .enqueue(new Callback<RspModel<List<UserCard>>>() {
                    @Override
                    public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                        RspModel<List<UserCard>> rspModel = response.body();
                        if (rspModel.success()) {
                            // 拿到集合
                            List<UserCard> cards = rspModel.getResult();
                            if (cards == null || cards.size() == 0)
                                return;

                            UserCard[] cards1 = cards.toArray(new UserCard[0]);
                            // CollectionUtil.toArray(cards, UserCard.class);

                            Factory.getUserCenter().dispatch(cards1);

                        } else {
                            Factory.decodeRspCode(rspModel, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                        // nothing
                    }
                });
    }
}
