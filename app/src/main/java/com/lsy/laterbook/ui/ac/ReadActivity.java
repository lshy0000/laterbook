package com.lsy.laterbook.ui.ac;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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
import com.chuangfeigu.tools.app.Const;
import com.chuangfeigu.tools.common.TipsUtils;
import com.chuangfeigu.tools.view.ButtomDialogView;
import com.chuangfeigu.tools.view.RecyclerViewForEmpty;
import com.lsy.laterbook.R;
import com.lsy.laterbook.contract.AllContract;
import com.lsy.laterbook.contract.DaggerActivityComp;
import com.lsy.laterbook.ui.view.BookPageFactory;
import com.lsy.laterbook.ui.view.BookPageFactoryInterface;
import com.lsy.laterbook.ui.view.turntest;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lshy on 2018-4-30.
 */

public class ReadActivity extends BaseActivity implements AllContract.View {
    @BindView(R.id.lay)
    LinearLayout lay;
    @BindView(R.id.top)
    View top;
    @BindView(R.id.isnight)
    CheckBox isnight;
    @BindView(R.id.toindex)
    Button toindex;
    @BindView(R.id.button)
    View button;
    @BindView(R.id.the)
    LinearLayout the;
    @BindView(R.id.ganyin)
    View ganyin;
    private Record record;
    @Inject
    AllContract.Presenter presenter;
    turntest turntest = new turntest();
    private Dialog ass;
    private Dialog bss;

    public static Intent getToIntent(Context context, Record record) {
        TipsUtils.putOb("record", record);
        return new Intent(context, ReadActivity.class);
    }

    BookPageFactoryInterface factor;
    ViewHolder local;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.simleread_layout);
        ButterKnife.bind(this);
        bindData();
        initInject();
        ((ViewGroup) findViewById(R.id.lay)).addView(turntest.onCreate(this, record, factor));
        ganyin.setOnClickListener(v -> {
            change(false);
        });
        toindex.setOnClickListener(v -> {
            if (record.isIsnet()) {
                View view = LayoutInflater.from(this).inflate(R.layout.source, null);
                RecyclerViewForEmpty recycleview = view.findViewById(R.id.recycleview);
                RecyclerView.Adapter adapte = new MMMAdapter(this, record.getChapterList().getChapters());
                recycleview.setLayoutManager(new LinearLayoutManager(ReadActivity.this));
                recycleview.setAdapter(adapte);
                adapte.notifyDataSetChanged();
                bss = new ButtomDialogView(this, view, true, true);
                bss.show();
            } else {
                if (factor.getTotal() < factor.current() || factor.getTotal() < 100) return;
                View view = LayoutInflater.from(this).inflate(R.layout.progress_local, null);
                local = new ViewHolder(view);
                local.seek.setProgress(100 * factor.current() / factor.getTotal());
                local.seekstr.setText(local.seek.getProgress() + "%");
                local.seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        local.seekstr.setText(progress + "%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                local.cancel.setOnClickListener(b -> ass.dismiss());
                local.sure.setOnClickListener(g -> {
                    factor.setIndex(local.seek.getProgress() * (factor.getTotal() / 100));
                    ass.dismiss();
                    change(true);
                });
                ass = new ButtomDialogView(this, view, true, true);
                ass.show();
            }
        });
        top.setOnClickListener(v -> {
            change(true);
        });
        the.setVisibility(View.GONE);
        ganyin.setVisibility(View.VISIBLE);
        button.setOnClickListener(v -> {
            change(true);
        });
        turntest.setNight(getSharedPreferences("ini", Context.MODE_APPEND).getBoolean("ic", false));
        isnight.setChecked(getSharedPreferences("ini", Context.MODE_APPEND).getBoolean("ic", false));
        isnight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                turntest.setNight(isChecked);
                getSharedPreferences("ini", Context.MODE_APPEND).edit().putBoolean("ic", isChecked).commit();
            }
        });
    }

    public void change(boolean i) {
        if (i) {
            ganyin.setVisibility(View.VISIBLE);
            the.setVisibility(View.GONE);
        } else {
            ganyin.setVisibility(View.GONE);
            the.setVisibility(View.VISIBLE);
        }

    }

    private void initInject() {
        DaggerActivityComp.builder()
                .applicationComponent(DataInit.getApplicationComponent())
                .build().inject(this);
        addWatcherLifer(presenter);
        presenter.setView(this);
    }

    private void bindData() {
        record = TipsUtils.getOb("record");
        if (!record.isIsnet()) {
            factor = new BookPageFactory(Const.screenwidth, Const.screenheight, () -> turntest.refresh());
        } else {
            factor = new NetFactory(Const.screenwidth, Const.screenheight, () -> turntest.refresh(), this);
        }
    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && !record.getChapterList().getRead())) {
            new AlertDialog.Builder(this).setTitle("是否加入书架").setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    record.setReadIndex(factor.getReadindex());
                    presenter.save(record);
                    finish();
                }
            }).setNegativeButton("不了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            }).create().show();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        record.setReadIndex(factor.getReadindex());
        if (record.getChapterList().getRead())
            presenter.save(record);
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

    static class ViewHolder {
        @BindView(R.id.seek)
        SeekBar seek;
        @BindView(R.id.seekstr)
        TextView seekstr;
        @BindView(R.id.cancel)
        Button cancel;
        @BindView(R.id.sure)
        Button sure;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class MMMAdapter extends RecyclerView.Adapter<ViewHoldr> {
        Context context;
        List<ChaptersEntity> chapters;

        public MMMAdapter(Context context, List<ChaptersEntity> chapters) {
            this.context = context;
            this.chapters = chapters;
        }

        @Override
        public ViewHoldr onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHoldr(LayoutInflater.from(context).inflate(R.layout.histroy_str_index, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHoldr holder, int position) {
            holder.text.setText(chapters.get(position).getTitle());
            holder.itemView.setOnClickListener(v -> {
                bss.dismiss();
                factor.setIndex(position);
                change(true);
            });
        }

        @Override
        public int getItemCount() {
            return chapters.size();
        }
    }

    static class ViewHoldr extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;

        ViewHoldr(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
