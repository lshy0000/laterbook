package com.chuangfeigu.tools.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuangfeigu.tools.R;
import com.chuangfeigu.tools.app.Const;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.Collections;
import java.util.List;

public class RecyclerViewForEmpty extends SwipeMenuRecyclerView {
    private View emptyView;
    Adapter adapter;
    //包装类的传递监听
    private AdapterDataObserver observer2 = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            picadapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            picadapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            positionStart += getHeaderItemCount();
            picadapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            picadapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            picadapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            fromPosition += getHeaderItemCount();
            toPosition += getHeaderItemCount();
            picadapter.notifyItemMoved(fromPosition, toPosition);
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    //    private AdapterDataObserver observer = new AdapterDataObserver() {
//
//        @Override
//        public void onChanged() {//设置空view原理都一样，没有数据显示空view，有数据隐藏空view
//
//            if (picadapter.getItemCount() <= ((emptyView == null ? 0 : 1))) {
//                if (emptyView != null)
//                    emptyView.setVisibility(VISIBLE);
//            } else {
//                if (emptyView != null)
//                    emptyView.setVisibility(GONE);
//            }
//        }
//
//        @Override
//        public void onItemRangeChanged(int positionStart, int itemCount) {
//            onChanged();
//        }
//
//        @Override
//        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
//            onChanged();
//        }
//
//        @Override
//        public void onItemRangeInserted(int positionStart, int itemCount) {
//            onChanged();
//        }
//
//        @Override
//        public void onItemRangeRemoved(int positionStart, int itemCount) {
//            onChanged();
//        }
//
//        @Override
//        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
//            onChanged();
//        }
//    };
    private Adapter picadapter = new Adapter() {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0x584) return new ViewHolder(emptyView) {
                @Override
                public String toString() {
                    return super.toString();
                }
            };
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if ((emptyView != null && adapter.getItemCount() == 0)) return;
            adapter.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount() + ((emptyView != null && adapter.getItemCount() == 0) ? 1 : 0);
        }

        @Override
        public int getItemViewType(int position) {
            if ((emptyView != null && adapter.getItemCount() == 0)) return 0x584;
            return adapter.getItemViewType(position);
        }
    };

    public RecyclerViewForEmpty(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RecyclerViewForEmpty(Context context) {
        super(context);
    }

    public RecyclerViewForEmpty(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setEmptyView(View view) {
        this.emptyView = view;
//        View v = emptyView.findViewById(R.id.lay);
//        if (v != null) {
//            v.setPadding(0, Const.screenheight / 4, 0, 0);
//        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter == null) {
            return;
        }
        this.adapter = adapter;
        super.setAdapter(picadapter);
        try {
            adapter.registerAdapterDataObserver(observer2);
        } catch (Exception e) {
        }
//        picadapter.registerAdapterDataObserver(observer);
//        observer.onChanged();
    }

    public static View defaultInitListView(Context context, List list, RecyclerViewForEmpty recycleview, final RecyclerView.Adapter adapter, SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener) {
        return defaultInitListView(context, list, recycleview, adapter, mLoadMoreListener, false);
    }

    public static View defaultInitListView(Context context, List list, RecyclerViewForEmpty recycleview, final RecyclerView.Adapter adapter, SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener, boolean nofengge) {
        OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                int fromPosition = srcHolder.getAdapterPosition();
                int toPosition = targetHolder.getAdapterPosition();

                // Item被拖拽时，交换数据，并更新adapter。
                Collections.swap(list, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int position = srcHolder.getAdapterPosition();
                // Item被侧滑删除时，删除数据，并更新adapter。
                if (position < list.size()) {
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(context) {
            @Override
            public void onLayoutChildren(Recycler recycler, State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (Exception e) {
                    Log.e("recyclerview", "错误的recyclerview的使用，请确保外部数据和内部数据同步更新");
                }

            }
        };
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recycleview.setLayoutManager(layoutManager);
        recycleview.setEmptyView(LayoutInflater.from(context).inflate(R.layout.nocense, recycleview, false));
        recycleview.setAdapter(adapter);
        if (!nofengge)
            recycleview.addItemDecoration(new DefaultItemDecoration(context.getResources().getColor(R.color.new_bg_main), Const.screenwidth, Const.screenheight / 106, 1, 2, 3));
        recycleview.setLongPressDragEnabled(false); // 拖拽排序，默认关闭。
        recycleview.setItemViewSwipeEnabled(false); // 侧滑删除，默认关闭。
//        if (list != null)
//            recycleview.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(context);
//        recycleview.addFooterView(loadMoreView); // 添加为Footer。
        recycleview.setLoadMoreView(loadMoreView);
        recycleview.setHasFixedSize(true);
        if (mLoadMoreListener != null) {
            recycleview.setLoadMoreListener(mLoadMoreListener); // 加载更多的监听。
            recycleview.loadMoreFinish(false, true);
        }
        return loadMoreView;
    }

    /**
     * 这是这个类的主角，如何自定义LoadMoreView。
     */
    static final class DefineLoadMoreView extends LinearLayout implements SwipeMenuRecyclerView.LoadMoreView, View.OnClickListener {

        private View mLoadingView;
        private TextView mTvMessage;

        private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener;

        public DefineLoadMoreView(Context context) {
            super(context);
            setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            setGravity(Gravity.CENTER);
            setVisibility(GONE);

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

            int minHeight = (int) (displayMetrics.density * 60 + 0.5);
            setMinimumHeight(minHeight);

            inflate(context, R.layout.layout_fotter_loadmore_mydefine, this);
            mLoadingView = findViewById(R.id.loading_view);
            mTvMessage = (TextView) findViewById(R.id.tv_message);

//            int color1 = ContextCompat.getColor(getContext(), R.color.colorPrimary);
//            int color2 = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
//            int color3 = ContextCompat.getColor(getContext(), R.color.colorAccent);
//            ((LoadingView) mLoadingView).setCircleColors(color1, color2, color3);

            setOnClickListener(this);
        }

        /**
         * 马上开始回调加载更多了，这里应该显示进度条。
         */
        @Override
        public void onLoading() {
            setVisibility(VISIBLE);
            mLoadingView.setVisibility(VISIBLE);
            mTvMessage.setVisibility(VISIBLE);
            mTvMessage.setText("正在努力加载，请稍后");
        }

        /**
         * 加载更多完成了。
         *
         * @param dataEmpty 是否请求到空数据。
         * @param hasMore   是否还有更多数据等待请求。
         */
        @Override
        public void onLoadFinish(boolean dataEmpty, boolean hasMore) {
            if (!hasMore) {
                setVisibility(VISIBLE);

                if (dataEmpty) {
                    mLoadingView.setVisibility(GONE);
                    mTvMessage.setVisibility(VISIBLE);
                    mTvMessage.setText("暂时没有数据");
                } else {
                    mLoadingView.setVisibility(GONE);
                    mTvMessage.setVisibility(VISIBLE);
                    mTvMessage.setText("没有更多数据啦");
                }
            } else {
                setVisibility(INVISIBLE);
            }
        }

        /**
         * 调用了setAutoLoadMore(false)后，在需要加载更多的时候，这个方法会被调用，并传入加载更多的listener。
         */
        @Override
        public void onWaitToLoadMore(SwipeMenuRecyclerView.LoadMoreListener loadMoreListener) {
            this.mLoadMoreListener = loadMoreListener;

            setVisibility(VISIBLE);
            mLoadingView.setVisibility(GONE);
            mTvMessage.setVisibility(VISIBLE);
            mTvMessage.setText("点我加载更多");
        }

        /**
         * 加载出错啦，下面的错误码和错误信息二选一。
         *
         * @param errorCode    错误码。
         * @param errorMessage 错误信息。
         */
        @Override
        public void onLoadError(int errorCode, String errorMessage) {
            setVisibility(VISIBLE);
            mLoadingView.setVisibility(GONE);
            mTvMessage.setVisibility(VISIBLE);

            // 这里要不直接设置错误信息，要不根据errorCode动态设置错误数据。
            mTvMessage.setText(errorMessage);
        }

        /**
         * 非自动加载更多时mLoadMoreListener才不为空。
         */
        @Override
        public void onClick(View v) {
            if (mLoadMoreListener != null) mLoadMoreListener.onLoadMore();
        }
    }

}
