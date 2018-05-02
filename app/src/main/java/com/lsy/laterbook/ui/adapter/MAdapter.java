package com.lsy.laterbook.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestxty.ai.domain.bean.BooksEntity;
import com.chuangfeigu.tools.sdk.glide.GlideUtil;
import com.lsy.laterbook.R;
import com.lsy.laterbook.ui.ac.BookInfoActivity;

import java.util.List;

import static com.lsy.laterbook.Const.PREIMAGE;

public class MAdapter extends RecyclerView.Adapter<ViewHolder> {


    private Context context;
    private List<BooksEntity> list;
    View.OnLongClickListener onLongClickListener;

    public View.OnLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public MAdapter(Context context, List<BooksEntity> records) {
        this.context = context;
        this.list = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_book_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GlideUtil.loadView(PREIMAGE + (list.get(position).getCover()), holder.ivBookCover, 2);
        holder.tvBookTitle.setText(list.get(position).getTitle());
        holder.creator.setText(list.get(position).getAuthor());
        holder.shortcut.setText(list.get(position).getShortIntro());
        holder.fujia.setText(list.get(position).getLatelyFollower() + "人在追|" + list.get(position).getRetentionRatio() + "%读者留存");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(BookInfoActivity.getToIntent(context, list.get(position)));
            }
        });
        holder.itemView.setOnLongClickListener(onLongClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}