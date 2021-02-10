package com.cgg.twdinspection.schemes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.SchemeInfoNamesBinding;
import com.cgg.twdinspection.schemes.interfaces.SchemeClickCallback;
import com.cgg.twdinspection.schemes.source.schemes.SchemeEntity;

import java.util.List;

public class SchemeInfoAdapter extends RecyclerView.Adapter<SchemeInfoAdapter.ItemHolder> {

    private Context context;
    private List<SchemeEntity> list;
    private SchemeClickCallback schemeClickCallback;
    private int selectedPos = -1;

    public SchemeInfoAdapter(Context context, List<SchemeEntity> list) {
        this.context = context;
        this.list = list;
        try {
            schemeClickCallback = (SchemeClickCallback) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SchemeInfoNamesBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.scheme_info_names, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {

        final SchemeEntity dataModel = list.get(i);
        holder.listItemBinding.setSchemeInfo(dataModel);

        if (dataModel.isSelection()) {
            holder.listItemBinding.llItem.setBackgroundColor(context.getResources().getColor(R.color.list_blue));
            holder.listItemBinding.tvItem.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.listItemBinding.llItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.listItemBinding.tvItem.setTextColor(context.getResources().getColor(R.color.black));
        }

        holder.listItemBinding.cvBenReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = i;
                notifyDataSetChanged();
                dataModel.setSelection(true);
                schemeClickCallback.onItemClick(String.valueOf(dataModel.getSchemeId()));
            }
        });

        if (selectedPos == i) {
            dataModel.setSelection(true);
        } else {
            dataModel.setSelection(false);
        }

        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        SchemeInfoNamesBinding listItemBinding;

        ItemHolder(SchemeInfoNamesBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
            listItemBinding.executePendingBindings();
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

