package com.lsy.laterbook.ui.ac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chuangfeigu.tools.app.BaseFragment;
import com.chuangfeigu.tools.app.Const;
import com.chuangfeigu.tools.sdk.glide.GlideUtil;
import com.chuangfeigu.tools.view.RecyclerViewForEmpty;
import com.lsy.laterbook.R;
import com.lsy.laterbook.contract.AllContract;
import com.lsy.laterbook.contract.DaggerActivityComp;
import com.lsy.laterbook.ui.adapter.MAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.lsy.laterbook.Const.PREIMAGE;

/**
 * Created by lshy on 2018-4-29.
 */

public class RecommendFragment extends BaseFragment implements AllContract.View {
    View root;
    Unbinder unbinder;
    @BindView(R.id.id_cb)
    ConvenientBanner idCb;
    @BindView(R.id.superspecial)
    LinearLayout superspecial;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.lay_person)
    RecyclerViewForEmpty layPerson;
    @BindView(R.id.laymain)
    CoordinatorLayout laymain;
    List<List<BooksEntity>> imageList = new ArrayList<>();
    List<BooksEntity> list = new ArrayList<>();
    private MAdapter adapter;

    @Inject
    AllContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.shucheng_index, container, false);
        unbinder = ButterKnife.bind(this, root);
        searchImage.setOnClickListener(tosearch);
        searchText.setOnClickListener(tosearch);

        idCb.getLayoutParams().height = Const.screenheight * 2 / 7;
        idCb.setLayoutParams(idCb.getLayoutParams());
        idCb.setPages(new CBViewHolderCreator<ImageViewHolder>() {
            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        }, imageList);
        //  idCb.setPageIndicator(new int[]{R.drawable.circlekuang, R.drawable.circlekuang});
        // idCb.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //  idCb.stopTurning();              //停止轮播  建议在onPause方法中设置
        idCb.setManualPageable(true);//设置不能手动影响  默认是手指触摸 轮播图不能翻页
        idCb.setCanLoop(true);  //默认true,设置轮播图是否轮播
        idCb.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        adapter = new MAdapter(getContext(), list);
        RecyclerViewForEmpty.defaultInitListView(getContext(), list, layPerson, adapter, null);
        initInject();
        presenter.getRecommend();
        return root;
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

    }

    @Override
    public void getSource(List<Source> sources) {

    }

    @Override
    public void onResume() {
        super.onResume();
        idCb.startTurning(2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        idCb.stopTurning();
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
        list.clear();
        list.addAll(records);
        imageList.clear();
        for (int i = 0; i < 5 && records.size() >= i * 3 + 3; i++) {
            imageList.add(records.subList(i * 3, i * 3 + 3));
        }
        adapter.notifyDataSetChanged();
        idCb.notifyDataSetChanged();
    }

    public class ImageViewHolder implements Holder<List<BooksEntity>> {
        @BindView(R.id.i1)
        ImageView i1;
        @BindView(R.id.i2)
        ImageView i2;
        @BindView(R.id.i3)
        ImageView i3;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.iiii, null);
            i1 = view.findViewById(R.id.i1);
            i2 = view.findViewById(R.id.i2);
            i3 = view.findViewById(R.id.i3);
            return view;
        }


        @Override
        public void UpdateUI(Context context, int position, List<BooksEntity> data) {
            //imageView.setImageResource(R.mipmap.touxiang104);
            try {
                GlideUtil.loadView(PREIMAGE + data.get(0).getCover(), i1, 2);
                i1.setOnClickListener(v -> startActivity(BookInfoActivity.getToIntent(getContext(), data.get(0))));
                GlideUtil.loadView(PREIMAGE + data.get(1).getCover(), i2, 2);
                i2.setOnClickListener(v -> startActivity(BookInfoActivity.getToIntent(getContext(), data.get(1))));
                GlideUtil.loadView(PREIMAGE + data.get(2).getCover(), i3, 2);
                i3.setOnClickListener(v -> startActivity(BookInfoActivity.getToIntent(getContext(), data.get(2))));
            } catch (Exception e) {

            }

        }
    }

    public View.OnClickListener tosearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getContext(), SearchActivity.class));
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
