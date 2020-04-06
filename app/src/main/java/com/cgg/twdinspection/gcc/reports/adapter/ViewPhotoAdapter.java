package com.cgg.twdinspection.gcc.reports.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.AdapterViewPhotoBinding;
import com.cgg.twdinspection.gcc.reports.interfaces.ReportClickCallback;
import com.cgg.twdinspection.gcc.reports.source.ReportPhoto;

import java.util.List;

import javax.sql.DataSource;

public class ViewPhotoAdapter extends RecyclerView.Adapter<ViewPhotoAdapter.ItemHolder> {

    private Context context;
    private List<ReportPhoto> list;

    public ViewPhotoAdapter(Context context, List<ReportPhoto> list) {
        this.context = context;
        this.list = list;
    }

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
        holder.bind(dataModel);

        Glide.with(context)
                .load(dataModel.getFilePath())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.loader_black1)
                .into(holder.listItemBinding.ivRepairs);
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

