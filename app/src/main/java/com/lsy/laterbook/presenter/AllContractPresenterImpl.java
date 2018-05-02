package com.lsy.laterbook.presenter;

import com.bestxty.ai.data.net.PerActivity;
import com.bestxty.ai.domain.Obse;
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
import com.bestxty.ai.domain.interactor.AllCase;
import com.lsy.laterbook.contract.AllContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by lshy on 2018-2-7.
 */
@PerActivity
public class AllContractPresenterImpl implements AllContract.Presenter {

    final AllCase allCase;


    AllContract.View view;

    @Inject
    public AllContractPresenterImpl(AllCase allCase) {
        this.allCase = allCase;
    }


    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        allCase.dispose();
    }

    @Override
    public void destory() {

    }


    @Override
    public void getFenleil() {
        allCase.execute(new Obse<A>() {
            @Override
            public void onNext(A a) {
                view.getFenleil(a);
            }
        }, allCase.getFenleil());
    }

    @Override
    public void getFenleixl() {
        allCase.execute(new Obse<D>() {
            @Override
            public void onNext(D a) {
                view.getFenleixl(a);
            }
        }, allCase.getFenleixl());
    }

    @Override
    public void getPaihangl() {
        allCase.execute(new Obse<B>() {
            @Override
            public void onNext(B a) {
                view.getPaihangl(a);
            }
        }, allCase.getPaihangl());
    }

    @Override
    public void getPaihangd(String rankId) {
        allCase.execute(new Obse<RankList>() {
            @Override
            public void onNext(RankList a) {
                view.getPaihangd(a);
            }
        }, allCase.getPaihangd(rankId));
    }

    @Override
    public void getList(String gender, String type, String major, int page, int pageSize) {
        allCase.execute(new Obse<E>() {
            @Override
            public void onNext(E a) {
                view.getList(a);
            }
        }, allCase.getList(gender, type, major, page, pageSize));
    }

    @Override
    public void getBookInfo(String booId) {
        allCase.execute(new Obse<BookInfo>() {
            @Override
            public void onNext(BookInfo a) {
                view.getBookInfo(a);
            }
        }, allCase.getBookInfo(booId));
    }

    @Override
    public void getSource(String bookId) {
        allCase.execute(new Obse<List<Source>>() {
            @Override
            public void onNext(List<Source> a) {
                view.getSource(a);
            }
        }, allCase.getSource(bookId));
    }

    @Override
    public void getChapters(String sourceId) {
        allCase.execute(new Obse<ChapterList>() {
            @Override
            public void onNext(ChapterList a) {
                view.getChapters(a);
            }
        }, allCase.getChapters(sourceId));
    }

    @Override
    public void getSearchWord(String query) {
        allCase.execute(new Obse<F>() {
            @Override
            public void onNext(F a) {
                view.getSearchWord(a);
            }
        }, allCase.getSearchWord(query));
    }

    @Override
    public void getSearchList(String query) {
        allCase.execute(new Obse<SearchList>() {
            @Override
            public void onNext(SearchList a) {
                view.getSearchList(a);
            }
        }, allCase.getSearchList(query));
    }

    @Override
    public void getChapterDetails(String link) {
        allCase.execute(new Obse<Details>() {
            @Override
            public void onNext(Details a) {
                view.getChapterDetails(a);
            }
        }, allCase.getChapterDetails(link));
    }

    @Override
    public void save(BookInfo bookInfo, ChapterList chapterList, int index,boolean isnet) {
        allCase.execute(new Obse<Boolean>() {
            @Override
            public void onNext(Boolean a) {
                view.save(a);
            }
        }, allCase.save(bookInfo, chapterList, index,isnet));
    }

    @Override
    public void save(Record record) {
        allCase.execute(new Obse<Boolean>() {
            @Override
            public void onNext(Boolean a) {
                view.save(a);
            }
        }, allCase.save(record));
    }

    @Override
    public void delete(String sourceId) {
        allCase.execute(new Obse<Boolean>() {
            @Override
            public void onNext(Boolean a) {
                view.delete(a);
            }
        }, allCase.delete(sourceId));
    }

    @Override
    public void getRecords() {
        allCase.execute(new Obse<List<Record>>() {
            @Override
            public void onNext(List<Record> a) {
                view.getRecords(a);
            }
        }, allCase.getRecords());
    }

    @Override
    public void setView(AllContract.View view) {
        this.view = view;
    }

    @Override
    public void getRecommend() {
        allCase.execute(new Obse<List<BooksEntity>>() {
            @Override
            public void onNext(List<BooksEntity> a) {
                view.getRecommend(a);
            }
        }, allCase.getRecommmoned());
    }


}
