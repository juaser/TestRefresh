package com.example.zxl.testrefresh;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by zxl on 2016/11/1016.
 */

public class SecondActivity extends BaseListActivity {
    private SecondAdapter adapter;
    private List<String> data;
    private int currentIndex;
    private int time = 1000;
    private int step = 5;

    @Override
    public void initView() {
        data = new ArrayList<>();
        adapter = new SecondAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
        setRefreshListerner();
        setLoderListerner();
    }

    @Override
    public void initData() {
        data.clear();
        currentIndex = step;
        for (int i = 0; i < currentIndex; i++) {
            data.add("i==" + i);
        }
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListerner(new SecondAdapter.OnItemClickListerner() {
            @Override
            public void onItemClick(View view, int position) {
                step = 10;
            }
        });
    }

    @Override
    public void refreshData() {
        Log.e("TAG", "refresh");
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                stopRefreshAnimation();
            }
        }, time);
    }

    @Override
    public void loadMoreData() {
        Log.e("TAG", "loadMore");
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentIndex += step;
                for (int i = data.size(); i < currentIndex; i++) {
                    data.add("i==" + i);
                }
                adapter.notifyDataSetChanged();
            }
        }, time);
    }
}
