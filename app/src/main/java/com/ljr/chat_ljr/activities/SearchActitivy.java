package com.ljr.chat_ljr.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ljr.chat_ljr.R;
import com.ljr.chat_ljr.frags.search.SearchGroupFragment;
import com.ljr.chat_ljr.frags.search.SearchUserFragment;
import com.ljr.common.app.BaseFragment;
import com.ljr.common.app.ToolbarActivity;

/**
 * Created by 林佳荣 on 2018/5/7.
 * Github：https://github.com/ljrRookie
 * Function ：搜索页面
 */

public class SearchActitivy extends ToolbarActivity {
    private static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int TYPE_USER = 1; // 搜索人
    public static final int TYPE_GROUP = 2; // 搜索群
    // 具体需要显示的类型
    private int type;
    private SearchFragment mSearchFragment;
    public static void show(Context context , int type){
        Intent intent = new Intent(context, SearchActitivy.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(EXTRA_TYPE);
        // 是搜索人或者搜索群
        return type == TYPE_USER || type == TYPE_GROUP;
    }
    /**
     * 搜索的Fragment必须继承的接口
     */
    public interface SearchFragment {
        void search(String content);
    }
    @Override
    protected void initWidget() {
        super.initWidget();
        // 显示对应的Fragment
        BaseFragment fragment = null;
        if(type == TYPE_USER){
            SearchUserFragment searchUserFragment = new SearchUserFragment();
            fragment = searchUserFragment;
            mSearchFragment = searchUserFragment;
        }else{
            SearchGroupFragment searchGroupFragment = new SearchGroupFragment();
            fragment = searchGroupFragment;
            mSearchFragment = searchGroupFragment;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, fragment)
                .commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 初始化菜单
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        // 找到搜索菜单
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            // 拿到一个搜索管理器
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            // 添加搜索监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // 当点击了提交按钮的时候
                    search(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    // 当文字改变的时候，咱们不会及时搜索，只在为null的情况下进行搜索
                    if (TextUtils.isEmpty(s)) {
                        search("");
                        return true;
                    }
                    return false;
                }
            });


        }
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * 搜索的发起点
     *
     * @param query 搜索的文字
     */
    private void search(String query) {
        if (mSearchFragment == null)
            return;
        mSearchFragment.search(query);
    }

}
