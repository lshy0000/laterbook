package com.lsy.laterbook.ui.ac;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestxty.ai.data.DataInit;
import com.bestxty.ai.domain.bean.A;
import com.bestxty.ai.domain.bean.B;
import com.bestxty.ai.domain.bean.BookInfo;
import com.bestxty.ai.domain.bean.BooksEntity;
import com.bestxty.ai.domain.bean.ChapterList;
import com.bestxty.ai.domain.bean.D;
import com.bestxty.ai.domain.bean.Details;
import com.bestxty.ai.domain.bean.E;
import com.bestxty.ai.domain.bean.F;
import com.bestxty.ai.domain.bean.RankList;
import com.bestxty.ai.domain.bean.Record;
import com.bestxty.ai.domain.bean.SearchList;
import com.bestxty.ai.domain.bean.Source;
import com.chuangfeigu.tools.app.App;
import com.chuangfeigu.tools.app.BaseActivity;
import com.chuangfeigu.tools.app.T;
import com.chuangfeigu.tools.common.FileUtils;
import com.chuangfeigu.tools.sdk.glide.GlideUtil;
import com.chuangfeigu.tools.view.RecyclerViewForEmpty;
import com.lsy.laterbook.R;
import com.lsy.laterbook.contract.AllContract;
import com.lsy.laterbook.contract.DaggerActivityComp;
import com.lsy.laterbook.ui.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.lsy.laterbook.Const.PREIMAGE;

/**
 * Created by lshy on 2018-4-29.
 */

public class ShelfActivity extends BaseActivity implements AllContract.View {
    @Inject
    AllContract.Presenter presenter;
    List<Record> list = new ArrayList<>();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleview)
    RecyclerViewForEmpty recycleview;
    @BindView(R.id.swipe_content)
    SwipeRefreshLayout swipeContent;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shujia_main);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        recycleview = findViewById(R.id.recycleview);
        swipeContent = findViewById(R.id.swipe_content);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        initInject();
        adapter = new MIAdapter(this, list);
        RecyclerViewForEmpty.defaultInitListView(this, list, recycleview, adapter, null);
        swipeContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        refresh();
    }


    public void refresh() {
        recycleview.loadMoreFinish(false, true);
        //手动调用,通知系统去测量
        swipeContent.measure(0, 0);
        swipeContent.setRefreshing(true);

        list.clear();
        adapter.notifyDataSetChanged();
        if (presenter != null) {
            presenter.getRecords();
        }
    }


    private void initInject() {
        DaggerActivityComp.builder()
                .applicationComponent(DataInit.getApplicationComponent())
                .build().inject(this);
        presenter.setView(this);
        addWatcherLifer(presenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shujia, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String path = FileUtils.getFilePathByUri(this,uri);
            BookInfo b = new BookInfo();
            ChapterList a = new ChapterList();
            a.set_id(path);
            b.setAuthor("本地书籍");
            b.setTitle(path.substring(path.lastIndexOf("/") + 1, path.length()));
            presenter.save(b, a, 0,false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shucheng) {
            startActivity(new Intent(this, ShuChengActivity.class));
        } else if (item.getItemId() == R.id.local) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("text/plain");//设置类型，我这里是任意类型，任意后缀的可以这样写。
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        }
        if (item.getItemId() == R.id.about) {
            new AlertDialog.Builder(this).setTitle("关于")
                    .setMessage("lshy 个人开源作品\n参考追书神器API：https://github.com/zimplexing/vue-nReader/blob/master/doc/zhuishushenqi.md\n\n 当前版本"+ App.getApp().getVersion())
                    .create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getFenleil(A a) {

    }

    @Override
    public void getFenleixl(D d) {

    }

    @Override
    public void getPaihangl(B b) {

    }

    @Override
    public void getPaihangd(RankList rankList) {

    }

    @Override
    public void getList(E e) {

    }

    @Override
    public void getBookInfo(BookInfo bookInfo) {

    }

    @Override
    public void getSource(List<Source> sources) {

    }

    @Override
    public void getChapters(ChapterList chapterList) {

    }

    @Override
    public void getSearchWord(F f) {

    }

    @Override
    public void getSearchList(SearchList searchList) {

    }

    @Override
    public void getChapterDetails(Details details) {

    }

    @Override
    public void save(Boolean b) {
        T.showToast("添加成功");
        refresh();
    }

    @Override
    public void delete(Boolean b) {
        T.showToast("删除成功");
        refresh();
    }

    @Override
    public void getRecords(List<Record> records) {
        swipeContent.setRefreshing(false);
        list.clear();
        list.addAll(records);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void getRecommend(List<BooksEntity> records) {

    }

    public class MIAdapter extends RecyclerView.Adapter<ViewHolder> {

        private Context context;
        private List<Record> list;

        public MIAdapter(Context context, List<Record> records) {
            this.context = context;
            this.list = records;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_book_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GlideUtil.loadView(PREIMAGE + (list.get(position).getBookInfo().getCover()), holder.ivBookCover, 2);
            holder.tvBookTitle.setText(list.get(position).getBookInfo().getTitle());
            holder.creator.setText(list.get(position).getBookInfo().getAuthor());
            String tr;
            holder.shortcut.setVisibility(View.VISIBLE);
            if (list.get(position).getChapterList().getChapters() == null||list.get(position).getChapterList().getChapters().size()==0) {
                holder.shortcut.setText("当前已读：" + list.get(position).getReadIndex() + "字");
                holder.fujia.setText(list.get(position).getSourceId());
            } else {
                try {
                    holder.shortcut.setText("当前章节："+list.get(position).getChapterList().getChapters().get(list.get(position).getReadIndex()).getTitle());
                } catch (Exception e) {
                    holder.shortcut.setVisibility(View.GONE);
                }
                holder.fujia.setText("最新章节" + list.get(position).getBookInfo().getLastChapter());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(ReadActivity.getToIntent(context, list.get(position)));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(context).setTitle("移出书籍").setMessage("确认移出该书籍吗").setNegativeButton("取消", (a, b) -> a.dismiss()
                    ).setPositiveButton("确定", (c, d) -> {
                        presenter.delete(list.get(position).getSourceId());
                    }).create().show();

                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
