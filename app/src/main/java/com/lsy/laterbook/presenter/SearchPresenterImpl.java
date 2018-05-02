package com.lsy.laterbook.presenter;


import com.bestxty.ai.data.RealmDao;
import com.bestxty.ai.data.net.PerActivity;
import com.bestxty.ai.domain.Obse;
import com.bestxty.ai.domain.bean.F;
import com.bestxty.ai.domain.bean.SearchHistoryBean;
import com.bestxty.ai.domain.bean.SearchList;
import com.bestxty.ai.domain.interactor.AllCase;
import com.lsy.laterbook.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by lshy on 2018-2-2.
 */

@PerActivity
public class SearchPresenterImpl implements SearchContract.SearchPresenter {
    SearchContract.SearchView searchView;
    final AllCase indexCase;

    @Inject
    public SearchPresenterImpl(AllCase indexCase) {
        this.indexCase = indexCase;
    }

    @Override
    public void setView(SearchContract.SearchView view) {
        this.searchView = view;
    }

    @Override
    public void deleteall() {
        RealmDao.getInstance().deleteAll(SearchHistoryBean.class);
        searchView.showSearchHistory(new ArrayList<>());
    }

    @Override
    public void loadSearchHistory() {
        Observable.just("").map(s -> {
            List<SearchHistoryBean> re = new ArrayList<>();
            re.addAll(RealmDao.getInstance().findAll(SearchHistoryBean.class));
            return re;
        }).subscribe(new Obse<List<SearchHistoryBean>>() {
            @Override
            public void onNext(List<SearchHistoryBean> s) {
                List<String> re = new ArrayList<>();
                for (SearchHistoryBean searchHistoryBean : s) {
                    re.add(searchHistoryBean.getWord());
                }
                searchView.showSearchHistory(re);
            }
        });

    }

    @Override
    public void loadSearchLinkword(String hits) {
//        Observable.just("").map(s -> {
//            List<SearchHistoryBean> re = new ArrayList<>();
//            re.addAll(RealmDao.getInstance().findAll(SearchHistoryBean.class));
//            return re;
//        }).subscribe(new Obse<List<SearchHistoryBean>>() {
//            @Override
//            public void onNext(List<SearchHistoryBean> s) {
//                List<String> re = new ArrayList<>();
//                for (SearchHistoryBean searchHistoryBean : s) {
//
//                    if (searchHistoryBean.getWord().startsWith(hits))
//                        re.add(searchHistoryBean.getWord());
//                }
//                searchView.showSearchLinkword(re);
//            }
//        });
        indexCase.execute(new Obse<F>() {
            @Override
            public void onNext(F searchList) {
                searchView.showSearchLinkword(searchList.getKeywords());
            }
        }, indexCase.getSearchWord(hits));

    }


    @Override
    public void loadSearchResult(String query) {
        RealmDao.getInstance().saveOrUpdate(new SearchHistoryBean(query));
        indexCase.execute(new MObse(), indexCase.getSearchList(query));
    }

    private class MObse extends Obse<SearchList> {
        @Override
        public void onNext(SearchList o) {
            searchView.accept(o.getBooks());
        }

        @Override
        public void onError(Throwable e) {
            searchView.error(-2, e.toString());
        }

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

    }

    @Override
    public void destory() {
        indexCase.dispose();
    }
}
