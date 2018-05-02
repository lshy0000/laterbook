package com.lsy.laterbook.contract;

import com.bestxty.ai.data.net.PerActivity;
import com.lsy.laterbook.presenter.AllContractPresenterImpl;
import com.lsy.laterbook.presenter.SearchPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lshy on 2018-4-4.
 */
@Module
public class PresenterMod {

    @PerActivity
    @Provides
    AllContract.Presenter AllContract(AllContractPresenterImpl presenter) {
        return presenter;
    }

    @PerActivity
    @Provides
    SearchContract.SearchPresenter searchPresenter(SearchPresenterImpl presenter) {
        return presenter;
    }

}
