package com.lsy.laterbook.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lsy.laterbook.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lshy on 2018-4-29.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ivBookCover)
    public RoundedImageView ivBookCover;
    @BindView(R.id.tvBookTitle)
    public TextView tvBookTitle;
    @BindView(R.id.creator)
    public TextView creator;
    @BindView(R.id.shortcut)
    public TextView shortcut;
    @BindView(R.id.fujia)
    public TextView fujia;

    public ViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}


