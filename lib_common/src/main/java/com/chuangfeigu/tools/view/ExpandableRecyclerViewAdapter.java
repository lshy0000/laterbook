package com.chuangfeigu.tools.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lshy on 2018-2-28.
 */

public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Expandable expandable;
    Context context;
    List<String> isexpandable;

    public ExpandableRecyclerViewAdapter(Expandable expandable, Context context) {
        this.expandable = expandable;
        this.context = context;
        isexpandable = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return expandable.onCreateGroupViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int[] re = getposition(position);
        if (re[1] >= 0) {
            expandable.onBindChildView(holder, re[0], re[1]);
        } else {
            expandable.onBindGroupView(holder, re[0]);
            holder.itemView.setOnClickListener(view -> expandorcoll(re[0]));
        }
    }

    private void expandorcoll(int gposition) {
        if (isexpandable.contains(String.valueOf(gposition))) {
            collapseGroup(gposition);
        } else {
            expandable.prepareExpand(gposition);
        }
    }

    public void expandGroup(int groupposition) {
        isexpandable.add(String.valueOf(groupposition));
        notifyDataSetChanged();
    }

    public void collapseGroup(int groupposition) {
        isexpandable.remove(String.valueOf(groupposition));
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        int p = 0;
        for (int i = 0; i < expandable.getGroupCount(); i++) {
            p++;
            p += isExpand(i) ? expandable.getChildCount(i) : 0;
        }
        return p;
    }

    public boolean isExpand(int group) {
        return isexpandable.contains(String.valueOf(group));
    }


    public int[] getposition(int position) {
        int[] re = new int[2];
        for (int i = 0; i < expandable.getGroupCount(); i++) {
            if (getusepo(i + 1) > position) {
                re[0] = i;
                re[1] = position - getusepo(i) - 1;
                break;
            }
        }
        return re;
    }

    private int getusepo(int po) {
        int p = 0;
        for (int i = 0; i < po; i++) {
            p++;
            p += isExpand(i) ? expandable.getChildCount(i) : 0;
        }
        return p;
    }

    @Override
    public int getItemViewType(int position) {
        int[] re = getposition(position);
        if (re[1] >= 0) {
            return expandable.getChildType(re[0], re[1]);
        } else return expandable.getGroupType(re[0]);
    }

    public interface Expandable {
        int getGroupCount();

        int getChildCount(int group);

        void onBindGroupView(RecyclerView.ViewHolder holder, int positiong);

        int getGroupType(int position);

        void onBindChildView(RecyclerView.ViewHolder holder, int grouppositiong, int childpositiong);

        int getChildType(int grouppositiong, int childposition);

        RecyclerView.ViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType);

        void prepareExpand(int groupposition);
    }

}
