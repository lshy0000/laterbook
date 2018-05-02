package com.lsy.laterbook.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lsy.laterbook.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lshy on 2018-4-29.
 */

public class ViewHolderre extends RecyclerView.ViewHolder {
    @BindView(R.id.text)
   public TextView text;
    @BindView(R.id.grid)
  public   RecyclerView grid;

   public ViewHolderre(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}


