package com.lsy.laterbook.contract;

import com.bestxty.ai.domain.bean.BooksEntity;
import com.chuangfeigu.tools.app.LoadDataView;
import com.chuangfeigu.tools.app.WatcherLifer;

import java.util.List;

/**
 * Created by lshy on 2018-2-2.
 */

public interface SearchContract {

    interface SearchView extends LoadDataView {
        void showSearchHistory(List<String> srhis);

        void showSearchLinkword(List<String> hits);

        void accept(List<BooksEntity> results);

    }

    interface SearchPresenter extends WatcherLifer {
        void setView(SearchView view);

        void deleteall();

        void loadSearchHistory();

        void loadSearchLinkword(String word);

        void loadSearchResult(String query);
    }
}
