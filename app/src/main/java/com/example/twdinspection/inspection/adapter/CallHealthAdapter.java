package com.example.twdinspection.inspection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.CallHealthRowBinding;
import com.example.twdinspection.inspection.interfaces.MedicalInterface;
import com.example.twdinspection.inspection.interfaces.StudAttendInterface;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import java.util.List;

public class CallHealthAdapter extends RecyclerView.Adapter<CallHealthAdapter.ItemHolder>{
    private Context context;
    private List<CallHealthInfoEntity> callHealthInfoEntities;
    private MedicalInterface medicalInterface;
    public CallHealthAdapter(Context context, List<CallHealthInfoEntity> callHealthInfoEntities) {
        this.context = context;
        this.callHealthInfoEntities = callHealthInfoEntities;
        try {
            medicalInterface=(MedicalInterface)context;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public CallHealthAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CallHealthRowBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.call_health_row, parent, false);

        return new CallHealthAdapter.ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final CallHealthAdapter.ItemHolder holder, final int i) {
        final CallHealthInfoEntity dataModel = callHealthInfoEntities.get(i);
        holder.listItemBinding.setCallModel(dataModel);

        holder.listItemBinding.tvSlNo.setText(String.valueOf(i+1));
        holder.listItemBinding.tvStuName.setText(String.valueOf(callHealthInfoEntities.get(i).getStu_name()));
        holder.listItemBinding.tvDisease.setText(String.valueOf(callHealthInfoEntities.get(i).getDisease()));
        holder.listItemBinding.tvDate.setText(String.valueOf(callHealthInfoEntities.get(i).getPlanDate()));
        holder.listItemBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicalInterface.deleteCallRecord(dataModel);
            }
        });


        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return callHealthInfoEntities!=null && callHealthInfoEntities.size()>0? callHealthInfoEntities.size():0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        CallHealthRowBinding listItemBinding;

        ItemHolder(CallHealthRowBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(BR.callModel, obj);
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
