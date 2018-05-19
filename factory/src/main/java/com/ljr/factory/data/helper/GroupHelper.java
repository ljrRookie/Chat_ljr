package com.ljr.factory.data.helper;

import com.ljr.common.factory.data.DataSource;
import com.ljr.factory.R;
import com.ljr.factory.model.card.GroupCard;
import com.ljr.factory.model.card.UserCard;
import com.ljr.factory.model.db.Group;
import com.ljr.factory.presenter.search.SearchGroupPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 林佳荣 on 2018/5/5.
 * Github：https://github.com/ljrRookie
 * Function ：对群的一个简单的辅助工具类
 */

public class GroupHelper {

    public static void search(String content, final DataSource.Callback<List<GroupCard>> callback) {
        List<GroupCard> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GroupCard groupCard = new GroupCard("测试群" + i, "", null);
            data.add(groupCard);
        }
        if(data!=null){
            callback.onDataLoaded(data);
        }else{
            callback.onDataNotAvailable(R.string.data_network_error);
        }

     /*   RemoteService service = Network.remote();
        Call<RspModel<List<GroupCard>>> call = service.groupSearch(name);

        call.enqueue(new Callback<RspModel<List<GroupCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupCard>>> call, Response<RspModel<List<GroupCard>>> response) {
                RspModel<List<GroupCard>> rspModel = response.body();
                if (rspModel.success()) {
                    // 返回数据
                    callback.onDataLoaded(rspModel.getResult());
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        // 把当前的调度者返回
        return call;*/
    }

    public static Group find(String groupId) {
        return null;
    }

    public static Group findFromLocal(String groupId) {
        return null;
    }
}
