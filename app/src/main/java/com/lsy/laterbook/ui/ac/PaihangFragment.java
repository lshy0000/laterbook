package com.lsy.laterbook.ui.ac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.chuangfeigu.tools.app.BaseFragment;
import com.chuangfeigu.tools.common.TipsUtils;
import com.chuangfeigu.tools.sdk.glide.GlideUtil;
import com.chuangfeigu.tools.view.RecyclerViewForEmpty;
import com.lsy.laterbook.Const;
import com.lsy.laterbook.Gender;
import com.lsy.laterbook.R;
import com.lsy.laterbook.contract.AllContract;
import com.lsy.laterbook.contract.DaggerActivityComp;
import com.lsy.laterbook.ui.adapter.ViewHolderre;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lshy on 2018-4-29.
 */

public class PaihangFragment extends BaseFragment implements AllContract.View {

    View root;
    @BindView(R.id.recycleview)
    RecyclerViewForEmpty recycleview;
    Unbinder unbinder;
    RecyclerView.Adapter adapter;

    B b;
    @Inject
    AllContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.recyclerviewnoswipe, container, false);
        unbinder = ButterKnife.bind(this, root);
        adapter = new TMadapter(getContext());
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setAdapter(adapter);
        initInject();
        presenter.getPaihangl();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getFenleil(A a) {

    }

    @Override
    public void getFenleixl(D d) {

    }

    @Override
    public void getPaihangl(B b) {
        this.b = b;
        adapter.notifyDataSetChanged();
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


    private class TMadapter extends RecyclerView.Adapter<ViewHolderre> {
        final Context context;

        public TMadapter(Context context1) {
            this.context = context1;
        }

        @Override
        public ViewHolderre onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolderre(LayoutInflater.from(context).inflate(R.layout.recycle_main_item_index, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolderre holder, int position) {
            holder.grid.setLayoutManager(new LinearLayoutManager(context));
            if (position == 0) {
                holder.text.setText(Gender.MALE.getCn());
                holder.grid.setAdapter(new TMiiadapter(context, b.getMale()));
            }
            if (position == 1) {
                holder.text.setText(Gender.FEMALE.getCn());
                holder.grid.setAdapter(new TMiiadapter(context, b.getFemale()));
            }
            if (position == 2) {
                holder.text.setText(Gender.PICTURE.getCn());
                holder.grid.setAdapter(new TMiiadapter(context, b.getPicture()));
            }
            if (position == 3) {
                holder.text.setText(Gender.PRESS.getCn());
                holder.grid.setAdapter(new TMiiadapter(context, b.getEpub()));
            }
            holder.grid.getAdapter().notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return b==null?0:4;
        }
    }

    private class TMiiadapter extends RecyclerView.Adapter<ViewHolder> {
        final Context context;
        final List<B.MaleEntity> list;

        public TMiiadapter(Context context1, List<B.MaleEntity> list) {
            this.context = context1;
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.paihang_main_item_item_index2, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(list.get(position).getTitle());
            GlideUtil.loadView(Const.PREIMAGE + list.get(position).getCover(), holder.image2, 2);
            holder.itemView.setOnClickListener(v -> {
                TipsUtils.putOb("paihang", list.get(position));
                context.startActivity(new Intent(context, PaihangActivity.class));
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image2)
        ImageView image2;
        @BindView(R.id.text)
        TextView text;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
