package com.example.twdinspection.schemes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterBenReportBinding;
import com.example.twdinspection.schemes.interfaces.BenClickCallback;
import com.example.twdinspection.schemes.interfaces.SchemeClickCallback;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.ui.BenDetailsActivity;

import java.util.List;
import java.util.Random;

public class BenReportAdapter extends RecyclerView.Adapter<BenReportAdapter.ItemHolder> {

    private Context context;
    private List<BeneficiaryDetail> list;
    private BenClickCallback benClickCallback;

    public BenReportAdapter(Context context, List<BeneficiaryDetail> list) {
        this.context = context;
        this.list = list;
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
        final BeneficiaryDetail dataModel = list.get(i);
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
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


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
}

