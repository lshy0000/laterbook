package com.lsy.laterbook.ui.ac;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestxty.ai.data.DataInit;
import com.bestxty.ai.domain.bean.BooksEntity;
import com.chuangfeigu.tools.app.BaseActivity;
import com.chuangfeigu.tools.view.RecyclerViewForEmpty;
import com.lsy.laterbook.R;
import com.lsy.laterbook.contract.DaggerActivityComp;
import com.lsy.laterbook.contract.SearchContract;
import com.lsy.laterbook.ui.adapter.MAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by lshy on 2018-2-1.
 */

public class SearchActivity extends BaseActivity implements SearchContract.SearchView {

    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.clearhistory)
    LinearLayout clearhistory;
    @BindView(R.id.historylistv)
    RecyclerView historylistv;
    @BindView(R.id.history)
    LinearLayout history;
    @BindView(R.id.hintlistv)
    RecyclerView hintlistv;
    @BindView(R.id.hint)
    LinearLayout hint;
    @BindView(R.id.resultlistv)
    RecyclerViewForEmpty resultlistv;
    @BindView(R.id.result)
    LinearLayout result;
    List<String> historylist = new ArrayList<>();
    List<String> hintlist = new ArrayList<>();
    List<BooksEntity> resultlist = new ArrayList<>();
    private MHistoryAdapter histadapter;
    private MHintAdapter hintadapter;
    private MAdapter readapter;
    @Inject
    SearchContract.SearchPresenter searchPresenter;
    private TextWatcher mtextwa;
    int page;
    int pageSize = 10;
    String query = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main_idex);
//        ButterKnife.bind(this);
        searchImage = findViewById(R.id.search_image);
        searchText = findViewById(R.id.search_text);
        delete = findViewById(R.id.delete);
        cancel = findViewById(R.id.cancel);
        clearhistory = findViewById(R.id.clearhistory);
        historylistv = findViewById(R.id.historylistv);
        history = findViewById(R.id.history);
        hintlistv = findViewById(R.id.hintlistv);
        hint = findViewById(R.id.hint);
        resultlistv = findViewById(R.id.resultlistv);
        result = findViewById(R.id.result);
        initInject();

        historylistv.setLayoutManager(new LinearLayoutManager(this));
        histadapter = new MHistoryAdapter(this, historylist);
        historylistv.setAdapter(histadapter);
        hintlistv.setLayoutManager(new LinearLayoutManager(this));
        hintadapter = new MHintAdapter(this, hintlist);
        hintlistv.setAdapter(hintadapter);

        readapter = new MAdapter(this, resultlist);
        RecyclerViewForEmpty.defaultInitListView(this, resultlist, resultlistv, readapter,null);
        setPage(0);
        cancel.setVisibility(View.VISIBLE);
        cancel.setOnClickListener((view) -> {
            finish();
        });
        delete.setVisibility(View.INVISIBLE);
        searchText.setFocusable(true);
        searchText.requestFocus();
        searchText.setCursorVisible(true);
        mtextwa = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    setPage(1);
                    delete.setVisibility(View.VISIBLE);
                    searchPresenter.loadSearchLinkword(searchText.getText().toString().trim());
                } else {
                    searchPresenter.loadSearchHistory();
                    delete.setVisibility(View.GONE);
                    setPage(0);
                }
            }
        };
        searchText.addTextChangedListener(mtextwa);
        delete.setOnClickListener((view) -> {
            searchText.setText("");
        });
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && searchText.getText().toString().trim().length() > 0) {
                    tosearch(searchText.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
        searchImage.setImageResource(R.mipmap.sousuohei_index);
        searchPresenter.loadSearchHistory();
        clearhistory.setOnClickListener(v -> {
            searchPresenter.deleteall();
        });
    }

    private void initInject() {
        DaggerActivityComp.builder()
                .applicationComponent(DataInit.getApplicationComponent())
                .build().inject(this);
        addWatcherLifer(searchPresenter);
        searchPresenter.setView(this);
    }

    private void tosearch(String trim) {
        setPage(2);
        searchText.removeTextChangedListener(mtextwa);
        searchText.setText(trim);
        searchText.setSelection(trim.length());//将光标移至文字末尾
        searchText.addTextChangedListener(mtextwa);
        page = 0;
        resultlist.clear();
        query = trim;
        readapter.notifyDataSetChanged();
        searchPresenter.loadSearchResult(trim);
    }


    private void setPage(int i) {
        history.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
        hint.setVisibility(i == 1 ? View.VISIBLE : View.GONE);
        result.setVisibility(i == 2 ? View.VISIBLE : View.GONE);

    }

    @Override
    public void showSearchHistory(List<String> srhis) {
        historylist.clear();
        historylist.addAll(srhis);
        histadapter.notifyDataSetChanged();
    }

    @Override
    public void showSearchLinkword(List<String> hits) {
        hintlist.clear();
        hintlist.addAll(hits);
        hintadapter.notifyDataSetChanged();
    }

    @Override
    public void accept(List<BooksEntity> results) {
        resultlist.addAll(results);
        readapter.notifyDataSetChanged();
    }


    private class MHintAdapter extends RecyclerView.Adapter<ViewHolder> {
        Context context;
        List<String> array;

        public MHintAdapter(Context context, List<String> array) {
            this.context = context;
            this.array = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.hint_str_index, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(array.get(position));
            ssb.setSpan(new ForegroundColorSpan(Color.RED), 0, searchText.getText().toString().trim().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.text.setText(ssb);
            holder.itemView.setOnClickListener(view -> {
                tosearch(holder.text.getText().toString());
            });
        }

        @Override
        public int getItemCount() {
            return array.size();
        }


    }

    private class MHistoryAdapter extends RecyclerView.Adapter<ViewHolder> {
        Context context;
        List<String> array;

        public MHistoryAdapter(Context context, List<String> array) {
            this.context = context;
            this.array = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.histroy_str_index, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(array.get(position));
            holder.itemView.setOnClickListener(view -> {
                tosearch(holder.text.getText().toString());
            });
        }

        @Override
        public int getItemCount() {
            return array.size();
        }


    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;

        ViewHolder(View view) {
            super(view);
//            ButterKnife.bind(this, view);
            text = view.findViewById(R.id.text);
        }
    }
}
