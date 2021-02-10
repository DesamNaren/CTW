package com.cgg.twdinspection.inspection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.BR;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.AdapterCocurricularStudBinding;
import com.cgg.twdinspection.inspection.interfaces.StudAchievementsInterface;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;

import java.util.List;

public class StudAchievementsAdapter extends RecyclerView.Adapter<StudAchievementsAdapter.ItemHolder> {
    private Context context;
    private StudAchievementsInterface studAchievementsInterface;
    private List<StudAchievementEntity> studAchievementEntities;
    private String fromClass;

    public StudAchievementsAdapter(Context context, List<StudAchievementEntity> studAchievementEntities, String fromClass) {
        this.context = context;
        this.studAchievementEntities = studAchievementEntities;
        this.fromClass = fromClass;
        try {
            studAchievementsInterface = (StudAchievementsInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public StudAchievementsAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterCocurricularStudBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_cocurricular_stud, parent, false);
        if (fromClass != null && fromClass.equalsIgnoreCase(AppConstants.REPORT_COCAR)) {
            listItemBinding.ivDelete.setVisibility(View.GONE);
        } else {
            listItemBinding.ivDelete.setVisibility(View.VISIBLE);
        }

        return new StudAchievementsAdapter.ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudAchievementsAdapter.ItemHolder holder, final int i) {
        final StudAchievementEntity dataModel = studAchievementEntities.get(i);
        holder.listItemBinding.setCocurricularStud(dataModel);

        holder.listItemBinding.tvSlNo.setText(String.valueOf(i + 1));
        holder.listItemBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studAchievementsInterface.deleteAchievementRecord(dataModel);
            }
        });


        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return studAchievementEntities != null && studAchievementEntities.size() > 0 ? studAchievementEntities.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        AdapterCocurricularStudBinding listItemBinding;

        ItemHolder(AdapterCocurricularStudBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(BR.cocurricularStud, obj);
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
