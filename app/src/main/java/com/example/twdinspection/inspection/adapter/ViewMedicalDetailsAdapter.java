package com.example.twdinspection.inspection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ViewMedicalRowBinding;
import com.example.twdinspection.inspection.source.MedicalDetailsBean;

import java.util.List;

public class ViewMedicalDetailsAdapter extends RecyclerView.Adapter<ViewMedicalDetailsAdapter.ItemHolder> {
    private Context context;
    private List<MedicalDetailsBean> medicalDetailsBeans;

    ViewMedicalDetailsAdapter(Context context, List<MedicalDetailsBean> medicalDetailsBeans) {
        this.context = context;
        this.medicalDetailsBeans = medicalDetailsBeans;
    }

    @NonNull
    @Override
    public ViewMedicalDetailsAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewMedicalRowBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.view_medical_row, parent, false);

        return new ViewMedicalDetailsAdapter.ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewMedicalDetailsAdapter.ItemHolder holder, final int i) {
        final MedicalDetailsBean dataModel = medicalDetailsBeans.get(i);
        holder.listItemBinding.tvStuName.setText(dataModel.getStudent_name());
        holder.listItemBinding.tvClass.setText(dataModel.getStudent_class());
        holder.listItemBinding.tvReason.setText(dataModel.getReason());
        holder.listItemBinding.tvHname.setText(dataModel.getHospitalName());
        holder.listItemBinding.tvAName.setText(dataModel.getAcc_name());
        holder.listItemBinding.tvADes.setText(dataModel.getAcc_designation());
        holder.listItemBinding.tvDate.setText(dataModel.getAdmittedDate());
    }


    @Override
    public int getItemCount() {
        return medicalDetailsBeans != null && medicalDetailsBeans.size() > 0 ? medicalDetailsBeans.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ViewMedicalRowBinding listItemBinding;

        ItemHolder(ViewMedicalRowBinding listItemBinding) {
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
