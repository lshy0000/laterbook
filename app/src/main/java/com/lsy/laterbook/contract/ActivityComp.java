package com.lsy.laterbook.contract;

import com.bestxty.ai.data.net.PerActivity;
import com.bestxty.ai.data.net.components.ApplicationComponent;
import com.lsy.laterbook.ui.ac.BookInfoActivity;
import com.lsy.laterbook.ui.ac.FenleiActivity;
import com.lsy.laterbook.ui.ac.FenleiFragment;
import com.lsy.laterbook.ui.ac.NetFactory;
import com.lsy.laterbook.ui.ac.PaihangActivity;
import com.lsy.laterbook.ui.ac.PaihangFragment;
import com.lsy.laterbook.ui.ac.ReadActivity;
import com.lsy.laterbook.ui.ac.RecommendFragment;
import com.lsy.laterbook.ui.ac.SearchActivity;
import com.lsy.laterbook.ui.ac.ShelfActivity;
import com.lsy.laterbook.ui.ac.ShuChengActivity;

import dagger.Component;

/**
 * Created by lshy on 2018-1-30.
 */

@PerActivity
@Component(modules = {PresenterMod.class, ActivityMod.class}, dependencies = ApplicationComponent.class)
public interface ActivityComp {
    void inject(ShelfActivity shelfActivity);

    void inject(ShuChengActivity shelfActivity);

    void inject(SearchActivity searchActivity);

    void inject(RecommendFragment searchActivity);

    void inject(FenleiActivity searchActivity);

    void inject(FenleiFragment searchActivity);

    void inject(PaihangActivity searchActivity);

    void inject(PaihangFragment searchActivity);

    void inject(BookInfoActivity searchActivity);

    void inject(ReadActivity searchActivity);

    void inject(NetFactory searchActivity);
}