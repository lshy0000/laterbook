package com.lsy.laterbook.ui.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;
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
import com.chuangfeigu.tools.app.BaseActivity;
import com.chuangfeigu.tools.common.TipsUtils;
import com.chuangfeigu.tools.view.RecyclerViewForEmpty;
import com.lsy.laterbook.BookType;
import com.lsy.laterbook.Gender;
import com.lsy.laterbook.R;
import com.lsy.laterbook.contract.AllContract;
import com.lsy.laterbook.contract.DaggerActivityComp;
import com.lsy.laterbook.ui.adapter.MAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lshy on 2018-4-29.
 */

public class FenleiActivity extends BaseActivity implements AllContract.View {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radiog)
    RadioGroup radiog;
    @BindView(R.id.recycleview)
    RecyclerViewForEmpty recycleview;
    private MAdapter adapter;
    private List<BooksEntity> list = new ArrayList<>();
    @Inject
    AllContract.Presenter presenter;
    int page = 0;
    int pageSize = 10;
    A.MaleEntity a;
    Gender gender;
    BookType bookType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fenlei_main);
        a = ((A.MaleEntity) TipsUtils.getOb("fenlei"));
        gender = ((Gender) TipsUtils.getOb("gender"));
        bookType = BookType.HOT;
        ButterKnife.bind(this);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarTitle.setText(a.getName());
        adapter = new MAdapter(this, list);
        RecyclerViewForEmpty.defaultInitListView(this, list, recycleview, adapter, new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getList(gender.getEn(), bookType.getEn(), a.getName(), page, pageSize);
            }
        });
        initInject();
        radiog.check(R.id.hot);
        radiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int position) {
                if (position == R.id.hot) {
                    bookType = BookType.HOT;
                }
                if (position == 1) {
                    bookType = BookType.NEW;
                }
                if (position == 2) {
                    bookType = BookType.REPULATION;
                }
                if (position == 3) {
                    bookType = BookType.OVER;
                }
                refresh();
            }
        });
        refresh();
    }

    private void refresh() {
        page = 0;
        list.clear();
        recycleview.loadMoreFinish(false, true);
        presenter.getList(gender.getEn(), bookType.getEn(), a.getName(), page, pageSize);
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
        if (e != null) {
            if (e.getBooks() != null) {
                list.addAll(e.getBooks());
            }
        }
        adapter.notifyDataSetChanged();
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

    }

    @Override
    public void delete(Boolean b) {

    }

    @Override
    public void getRecords(List<Record> records) {

    }

    @Override
    public void getRecommend(List<BooksEntity> records) {

    }
}
