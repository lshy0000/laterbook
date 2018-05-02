package com.lsy.laterbook.ui.ac;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bestxty.ai.data.DataInit;
import com.bestxty.ai.domain.bean.A;
import com.bestxty.ai.domain.bean.B;
import com.bestxty.ai.domain.bean.BookInfo;
import com.bestxty.ai.domain.bean.BooksEntity;
import com.bestxty.ai.domain.bean.ChapterList;
import com.bestxty.ai.domain.bean.ChaptersEntity;
import com.bestxty.ai.domain.bean.D;
import com.bestxty.ai.domain.bean.Details;
import com.bestxty.ai.domain.bean.E;
import com.bestxty.ai.domain.bean.F;
import com.bestxty.ai.domain.bean.RankList;
import com.bestxty.ai.domain.bean.Record;
import com.bestxty.ai.domain.bean.SearchList;
import com.bestxty.ai.domain.bean.Source;
import com.chuangfeigu.tools.app.BaseActivity;
import com.chuangfeigu.tools.app.T;
import com.chuangfeigu.tools.common.TipsUtils;
import com.chuangfeigu.tools.sdk.glide.GlideUtil;
import com.chuangfeigu.tools.view.RecyclerViewForEmpty;
import com.lsy.laterbook.R;
import com.lsy.laterbook.contract.AllContract;
import com.lsy.laterbook.contract.DaggerActivityComp;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

import static com.lsy.laterbook.Const.PREIMAGE;

/**
 * Created by lshy on 2018-4-29.
 */

public class BookInfoActivity extends BaseActivity implements AllContract.View {

    @BindView(R.id.ivBookCover)
    RoundedImageView ivBookCover;
    @BindView(R.id.tvBookTitle)
    TextView tvBookTitle;
    @BindView(R.id.creator)
    TextView creator;
    @BindView(R.id.shortcut)
    TextView shortcut;
    @BindView(R.id.fujia)
    TextView fujia;
    @BindView(R.id.joinshelf)
    Button joinshelf;
    @BindView(R.id.baominglist)
    Button baominglist;
    @BindView(R.id.latelyFollower)
    TextView latelyFollower;
    @BindView(R.id.retentionRatio)
    TextView retentionRatio;
    @BindView(R.id.serializeWordCount)
    TextView serializeWordCount;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.shortde)
    TextView shortde;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    AllContract.Presenter presenter;
    @BindView(R.id.rating)
    TextView rating;
    private List<Source> sources = new ArrayList<>();
    private ChapterList chapter;
    BooksEntity booksEntity;
    BookInfo bookInfo;
    Source source;
    private AlertDialog alertDialog;

    public static Intent getToIntent(Context context, BooksEntity booksEntity) {
        TipsUtils.putOb("book1", booksEntity);
        return new Intent(context, BookInfoActivity.class);
    }

    public static Intent getToIntent(Context context, BooksEntity booksEntity, Source source) {
        TipsUtils.putOb("book1", booksEntity);
        TipsUtils.putOb("source", source);
        return new Intent(context, BookInfoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookinfo_main);
        booksEntity = TipsUtils.getOb("book1");
        source = TipsUtils.getOb("source");
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarTitle.setText(booksEntity.getTitle());
        initInject();
        presenter.getBookInfo(booksEntity.get_id());
        if (source == null) {
            presenter.getSource(booksEntity.get_id());
        } else {
            presenter.getChapters(source.get_id());
        }
        initBook1();
    }


    private void initInject() {
        DaggerActivityComp.builder()
                .applicationComponent(DataInit.getApplicationComponent())
                .build().inject(this);
        addWatcherLifer(presenter);
        presenter.setView(this);
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
        this.bookInfo = bookInfo;
        initBook();
    }

    private void initBook1() {
        GlideUtil.loadView(PREIMAGE + (booksEntity.getCover()), ivBookCover, 2);
        tvBookTitle.setText(booksEntity.getTitle());
        creator.setText(booksEntity.getAuthor());
        shortcut.setText("类别：" + ((booksEntity.getMinorCate() == null || booksEntity.getMinorCate().length() < 1) ? booksEntity.getMajorCate() : booksEntity.getMinorCate()));
    }

    private void initBook() {
        if (bookInfo != null) {
            shortde.setText(bookInfo.getLongIntro());
            rating.setText(bookInfo.getRating().getScore() + "");
            latelyFollower.setText(bookInfo.getLatelyFollower() + "");
            serializeWordCount.setText(bookInfo.getSerializeWordCount() + "");
            retentionRatio.setText(bookInfo.getRetentionRatio() + "%");
            shortcut.setText("类别：" + ((bookInfo.getMinorCate() == null || bookInfo.getMinorCate().length() < 1) ? bookInfo.getMajorCate() : bookInfo.getMinorCate()) + "|" +
                    formatWordCount(bookInfo.getWordCount()));
        }

    }

    private void initBook2() {
        if (source != null) {
            fujia.setText("来自" + source.getName() + source.getHost());
        }
    }

    private void initBook3() {
        if (chapter != null) {
            if (chapter.getRead()) {
                baominglist.setText("继续阅读");
                joinshelf.setText("移出书架");
                joinshelf.setOnClickListener(v -> {
                    presenter.delete(source.get_id());
                });
            } else {
                joinshelf.setOnClickListener(v -> {
                    presenter.save(bookInfo, chapter, 0,true);
                });
            }
            RealmList<ChaptersEntity> cc = chapter.getChapters();
            if (cc.size() > 0) {
                t1.setVisibility(View.VISIBLE);
                t1.setText(cc.get(cc.size() - 1).getTitle());
            } else {
                t1.setVisibility(View.GONE);
            }
            if (cc.size() > 1) {
                t2.setVisibility(View.VISIBLE);
                t2.setText(cc.get(cc.size() - 2).getTitle());
            } else {
                t2.setVisibility(View.GONE);
            }
            if (cc.size() > 2) {
                t3.setVisibility(View.VISIBLE);
                t3.setText(cc.get(cc.size() - 3).getTitle());
            } else {
                t3.setVisibility(View.GONE);
            }
            if (cc.size() > 3) {
                t4.setVisibility(View.VISIBLE);
                t4.setText(cc.get(cc.size() - 4).getTitle());
            } else {
                t4.setVisibility(View.GONE);
            }

            baominglist.setOnClickListener(v -> {
                Record re = new Record();
                re.setReadIndex(0);
                re.setBookInfo(bookInfo);
                re.setChapterList(chapter);
                re.setSourceId(chapter.get_id());
                re.setIsnet(true);
                startActivity(ReadActivity.getToIntent(this, re));
            });

        }

    }

    @Override
    public void getSource(List<Source> sources) {
        if (sources == null) {
            return;
        }
        this.sources.clear();
        this.sources.addAll(sources);
        if (sources.size() > 0) {
            this.source = sources.get(0);
            initBook2();
            presenter.getChapters(source.get_id());
        }

    }

    @Override
    public void getChapters(ChapterList chapterList) {
        if (chapterList == null) {
            return;
        }
        this.chapter = chapterList;
        initBook3();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shucheng) {
            if (sources != null && sources.size() > 0) {
                alertDialog = new AlertDialog.Builder(this).setTitle("选择书源").create();
                View v = LayoutInflater.from(this).inflate(R.layout.source, null);
                RecyclerViewForEmpty recycleview = v.findViewById(R.id.recycleview);
                MMMAdapter adapte = new MMMAdapter();
                RecyclerViewForEmpty.defaultInitListView(this, sources, recycleview, adapte, null);
                adapte.notifyDataSetChanged();
                alertDialog.setView(v);
                alertDialog.show();
//                Window dialogWindow = alertDialog.getWindow();
//                WindowManager m = getWindowManager();
//                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
//                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//                p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6，根据实际情况调整
//                p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
//                dialogWindow.setAttributes(p);

            }
        } else if (item.getItemId() == R.id.about) {
            T.showToast("开发中");
        }
        if (item.getItemId() == R.id.local) {
            T.showToast("开发中");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookinfo, menu);
        return true;
    }

    @Override
    public void save(Boolean b) {
        T.showToast("保存成功");
    }

    @Override
    public void delete(Boolean b) {
        T.showToast("移出成功");
    }

    public static String formatWordCount(int wordCount) {
        if (wordCount / 10000 > 0) {
            return (int) ((wordCount / 10000f) + 0.5) + "万字";
        } else if (wordCount / 1000 > 0) {
            return (int) ((wordCount / 1000f) + 0.5) + "千字";
        } else {
            return wordCount + "字";
        }
    }

    @Override
    public void getRecords(List<Record> records) {

    }

    @Override
    public void getRecommend(List<BooksEntity> records) {


    }

    private class MMMAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(BookInfoActivity.this).inflate(R.layout.source_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.host.setText(sources.get(position).getHost());
            holder.name.setText(sources.get(position).getName());
            holder.lastch.setText("最新：" + sources.get(position).getLastChapter());
            holder.itemView.setOnClickListener(v -> {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                source = sources.get(position);
                initBook2();
                presenter.getChapters(source.get_id());
            });
        }

        @Override
        public int getItemCount() {
            return sources.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.host)
        TextView host;
        @BindView(R.id.lastch)
        TextView lastch;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
