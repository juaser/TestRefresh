package com.example.zxl.testrefresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Description: 带列表类型的Activity
 * Created by zxl on 2016/11/1016.
 */

public abstract class BaseListActivity extends FragmentActivity {

    public SwipeRefreshLayout mSwipeRefreshLayout;
    public RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;//recyclerView 的线性布局管理器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baselist);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        setRefreshEnable(false);
        initView();
        initData();
    }

    public abstract void initView();

    public abstract void initData();

    /**
     * 设置下拉刷新是否生效
     */
    public void setRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    /**
     * 停止下拉刷新动画
     */
    public void stopRefreshAnimation() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 开启下拉刷新动画
     */
    public void startRefreshAnimation() {
        if (!mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
    }

    /**
     * 设置下拉刷新监听
     */
    public void setRefreshListerner() {
        setRefreshEnable(true);
        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    /**
     * 通过监听当前加完成最后一个的位置和整个item数目的比较，来确定是否加载更多的内容
     */
    public void setLoderListerner() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断是否向上滑动，滑动状态是否停止，是否滑动到最底部
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //判断是否停止滑动
                    int fistCompleteVisible = linearLayoutManager.findFirstCompletelyVisibleItemPosition();//当前recyclerview最顶部完全显示的序号
                    int lastCompleteVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();//当前recyclerview最底部完全显示的序号
                    int totalItem = linearLayoutManager.getItemCount();//item总数目
                    if (fistCompleteVisible == 0 && lastCompleteVisible + 1 == totalItem) {
                        //说明 数据并未完全充满屏幕 没有上拉加载
                        return;
                    }
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();//当前recyclerview 显示的最底部序号
                    if (lastVisibleItem + 1 == totalItem) {
                        //判断当前最底部显示的item是最后一个item
                        loadMoreData();
                    }
                }
            }
        });
    }

    /**
     * 刷新
     */
    public abstract void refreshData();

    /**
     * 加载更多
     */
    public abstract void loadMoreData();
}
