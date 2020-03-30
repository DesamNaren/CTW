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
import com.cgg.twdinspection.databinding.AdapterPlantsInfoBinding;
import com.cgg.twdinspection.inspection.interfaces.PlantsInfoInterface;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;

import java.util.List;

public class PlantsInfoAdapter extends RecyclerView.Adapter<PlantsInfoAdapter.ItemHolder>{
    private Context context;
    private PlantsInfoInterface plantsInfoInterface;
    private List<PlantsEntity> plantsEntities;
    private String fromClass;

    public PlantsInfoAdapter(Context context, List<PlantsEntity> plantsEntities, String fromClass) {
        this.context = context;
        this.plantsEntities = plantsEntities;
        this.fromClass = fromClass;
        try {
            plantsInfoInterface=(PlantsInfoInterface) context;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public PlantsInfoAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPlantsInfoBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_plants_info, parent, false);

        if(fromClass!=null && fromClass.equalsIgnoreCase(AppConstants.REPORT_COCAR)){
            listItemBinding.ivDelete.setVisibility(View.GONE);
        }else {
            listItemBinding.ivDelete.setVisibility(View.VISIBLE);
        }

        return new PlantsInfoAdapter.ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlantsInfoAdapter.ItemHolder holder, final int i) {
        final PlantsEntity dataModel = plantsEntities.get(i);
        holder.listItemBinding.setPlantInfo(dataModel);

        holder.listItemBinding.tvSlNo.setText(String.valueOf(i+1));
        holder.listItemBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plantsInfoInterface.deletePlantRecord(dataModel);
            }
        });


        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return plantsEntities!=null && plantsEntities.size()>0? plantsEntities.size():0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        AdapterPlantsInfoBinding listItemBinding;

        ItemHolder(AdapterPlantsInfoBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(BR.plantInfo, obj);
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
