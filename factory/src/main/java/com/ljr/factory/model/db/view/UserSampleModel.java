package com.ljr.factory.model.db.view;


import com.ljr.common.factory.model.Author;

/**
 * 用户基础信息的Model，可以和数据库进行查询
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class UserSampleModel implements Author {
    public UserSampleModel(String id, String name, String portrait) {
        this.id = id;
        this.name = name;
        this.portrait = portrait;
    }

    public String id;

    public String name;

    public String portrait;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPortrait() {
        return portrait;
    }


    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
