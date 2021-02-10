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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.AdapterViewPhotoPdfBinding;
import com.cgg.twdinspection.gcc.reports.source.ReportPhoto;

import java.util.List;

public class ViewPhotoAdapterPdf extends RecyclerView.Adapter<ViewPhotoAdapterPdf.ItemHolder> {

    private Context context;
    private List<ReportPhoto> list;

    public ViewPhotoAdapterPdf(Context context, List<ReportPhoto> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterViewPhotoPdfBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_view_photo_pdf, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final ReportPhoto dataModel = list.get(i);
        holder.listItemBinding.setPhoto(dataModel);
        holder.bind(dataModel);

        holder.listItemBinding.pbar.setVisibility(View.VISIBLE);


        Glide.with(context)
                .load(dataModel.getFilePath())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.camera)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.listItemBinding.pbar.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.listItemBinding.pbar.setVisibility(View.GONE);

                        return false;
                    }
                })
                .into(holder.listItemBinding.ivRepairs);

        holder.listItemBinding.ivRepairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataModel.getFilePath() != null && dataModel.getFileName() != null)
                    Utils.displayPhotoDialogBox(dataModel.getFilePath(), context, dataModel.getFileName(), true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        AdapterViewPhotoPdfBinding listItemBinding;

        ItemHolder(AdapterViewPhotoPdfBinding listItemBinding) {
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

    public void setData(List<ReportPhoto> beneficiaryDetailArrayList) {
        list.clear();
        list.addAll(beneficiaryDetailArrayList);
        notifyDataSetChanged();
    }

}

