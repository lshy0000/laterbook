package com.lsy.laterbook.ui.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chuangfeigu.tools.app.BaseActivity;
import com.chuangfeigu.tools.app.BaseFragment;
import com.chuangfeigu.tools.view.SavaStateFragmentAdapter;
import com.lsy.laterbook.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lshy on 2018-4-29.
 */

public class ShuChengActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_person)
    TabLayout tabPerson;
    @BindView(R.id.lay_person)
    ViewPager layPerson;
    public static String[] itemstr = new String[]{"推荐", "分类", "排行"};
    BaseFragment r1=new RecommendFragment();
    BaseFragment r2=new FenleiFragment();
    BaseFragment r3=new PaihangFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shucheng_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(v -> finish());
        tabPerson.setupWithViewPager(layPerson);
        layPerson.setAdapter(new SavaStateFragmentAdapter(getSupportFragmentManager(), Arrays.asList(r1,r2,r3)) {

            @Override
            public int getCount() {
                return itemstr.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position < itemstr.length)
                    return itemstr[position];
                return null;
            }
        });


    }
}
