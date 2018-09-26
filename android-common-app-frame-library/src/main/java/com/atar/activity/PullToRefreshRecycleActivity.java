package com.atar.activity;

import android.interfaces.ScrollListListener;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.atar.common.business.R;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

public class PullToRefreshRecycleActivity extends AtarRefreshActivity<PullToRefreshRecyclerView, RecyclerView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResLayoutID() > 0) {
            addContentView(getResLayoutID());
        }
    }

    public int getResLayoutID() {
        return R.layout.common_pull_to_refresh_recycle;
    }

    @Override
    public void applyScrollListener() {
        if (getRefreshView() != null) {
            getRefreshView().setOnScrollListener(new ScrollListListener(imageLoader));
        }
    }

    @Override
    public void initControl() {
        setRefreshView((PullToRefreshRecyclerView) findViewById(R.id.common_pull_refresh_recyclerview));
    }

    /**
     * RecyclerView 适配器
     *
     * @param adapter
     * @author :Atar
     * @createTime:2015-6-3上午10:44:38
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (getRefreshView() != null) {
            getRefreshView().setAdapter(adapter);
            if (getPullView() != null) {
                getPullView().setScrollingWhileRefreshingEnabled(true);
            }
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (getRefreshView() != null) {
            getRefreshView().setLayoutManager(layoutManager);
        }
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (getRefreshView() != null) {
            getRefreshView().addItemDecoration(itemDecoration);
        }
    }
}
