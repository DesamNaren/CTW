package com.cgg.twdinspection.engineering_works.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.AdapterEngWorksBinding;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;
import com.cgg.twdinspection.engineering_works.ui.InspectionDetailsActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EngWorksAdapter extends RecyclerView.Adapter<EngWorksAdapter.ItemHolder> implements Filterable {

    private final Context context;
    private final List<WorkDetail> list;
    private List<WorkDetail> filterList;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    public EngWorksAdapter(Context context, List<WorkDetail> list) {
        this.context = context;
        this.list = list;
        filterList = new ArrayList<>();
        filterList.addAll(list);
        try {
            sharedPreferences = TWDApplication.get(context).getPreferences();
            editor = sharedPreferences.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterEngWorksBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_eng_works, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final WorkDetail dataModel = filterList.get(i);
        holder.listItemBinding.setEngReport(dataModel);
        setAnimation(holder.itemView, i);

        holder.listItemBinding.cvEngReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String worksMaster = gson.toJson(dataModel);
                editor.putString(AppConstants.ENGWORKSMASTER, worksMaster);
                editor.commit();
                context.startActivity(new Intent(context, InspectionDetailsActivity.class));

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
                    for (WorkDetail otData : list) {
                        if (otData.getWorkId().toString().toLowerCase().contains(charString.toLowerCase()) ||
                                otData.getWorkName().toLowerCase().contains(charString.toLowerCase())) {
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
                filterList = (ArrayList<WorkDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ItemHolder extends RecyclerView.ViewHolder {


        AdapterEngWorksBinding listItemBinding;

        ItemHolder(AdapterEngWorksBinding listItemBinding) {
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

    public void setData(List<WorkDetail> beneficiaryDetailArrayList) {
        filterList.clear();
        filterList.addAll(beneficiaryDetailArrayList);
        notifyDataSetChanged();
    }

}
