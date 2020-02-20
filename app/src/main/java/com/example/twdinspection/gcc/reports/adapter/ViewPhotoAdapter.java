package com.example.twdinspection.gcc.reports.adapter;

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

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterViewPhotoBinding;
import com.example.twdinspection.databinding.AdapterViewPhotoBinding;
import com.example.twdinspection.gcc.reports.interfaces.ReportClickCallback;
import com.example.twdinspection.gcc.reports.source.ReportPhoto;
import com.example.twdinspection.gcc.reports.source.ReportPhoto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewPhotoAdapter extends RecyclerView.Adapter<ViewPhotoAdapter.ItemHolder> {

    private Context context;
    private List<ReportPhoto> list;
    private ReportClickCallback reportClickCallback;

    public ViewPhotoAdapter(Context context, List<ReportPhoto> list) {
        this.context = context;
        this.list = list;
        try {
            reportClickCallback = (ReportClickCallback) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int lastPosition = -1;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterViewPhotoBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_view_photo, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final ReportPhoto dataModel = list.get(i);
        holder.listItemBinding.setPhoto(dataModel);
        setAnimation(holder.itemView, i);

        holder.listItemBinding.setImageUrl(dataModel.getFilePath());

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


        AdapterViewPhotoBinding listItemBinding;

        ItemHolder(AdapterViewPhotoBinding listItemBinding) {
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

    public  void setData(List<ReportPhoto> beneficiaryDetailArrayList){
        list.clear();
        list.addAll(beneficiaryDetailArrayList);
        notifyDataSetChanged();
    }

}

