package com.cgg.twdinspection.schemes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.AdapterBenReportBinding;
import com.cgg.twdinspection.schemes.interfaces.BenClickCallback;
import com.cgg.twdinspection.schemes.source.bendetails.BeneficiaryDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BenReportAdapter extends RecyclerView.Adapter<BenReportAdapter.ItemHolder> implements Filterable {

    private final List<BeneficiaryDetail> list;
    private List<BeneficiaryDetail> filterList;
    private BenClickCallback benClickCallback;

    public BenReportAdapter(Context context, List<BeneficiaryDetail> list) {
        this.list = list;
        filterList = new ArrayList<>();
        filterList.addAll(list);
        try {
            benClickCallback = (BenClickCallback) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBenReportBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_ben_report, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final BeneficiaryDetail dataModel = filterList.get(i);
        holder.listItemBinding.setBenReport(dataModel);
        setAnimation(holder.itemView, i);

        holder.listItemBinding.cvBenReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                benClickCallback.onItemClick(dataModel);
            }
        });

        holder.bind(dataModel);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return filterList != null && filterList.size() > 0 ? filterList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                filterList.clear();
                if (charString.isEmpty()) {
                    filterList.addAll(list);
                } else {
                    for (BeneficiaryDetail otData : list) {
                        if (otData.getBenID().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(otData);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<BeneficiaryDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ItemHolder extends RecyclerView.ViewHolder {


        AdapterBenReportBinding listItemBinding;

        ItemHolder(AdapterBenReportBinding listItemBinding) {
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

    public void setData(List<BeneficiaryDetail> beneficiaryDetailArrayList) {
        filterList.clear();
        filterList.addAll(beneficiaryDetailArrayList);
        notifyDataSetChanged();
    }

}

