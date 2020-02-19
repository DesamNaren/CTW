package com.example.twdinspection.inspection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.GroupViewMedicalRowBinding;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;

import java.util.HashMap;
import java.util.List;

public class GViewMedicalDetailsAdapter extends RecyclerView.Adapter<GViewMedicalDetailsAdapter.ItemHolder> {
    private Context context;
    private HashMap<String, List<MedicalDetailsBean>> medicalDetailsBeans;
    private GroupViewMedicalRowBinding listItemBinding;
    private String[] mKeys;

    public GViewMedicalDetailsAdapter(Context context, HashMap<String, List<MedicalDetailsBean>> medicalDetailsBeans) {
        this.context = context;
        this.medicalDetailsBeans = medicalDetailsBeans;
        mKeys = medicalDetailsBeans.keySet().toArray(new String[medicalDetailsBeans.size()]);
    }

    @NonNull
    @Override
    public GViewMedicalDetailsAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.group_view_medical_row, parent, false);

        return new GViewMedicalDetailsAdapter.ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final GViewMedicalDetailsAdapter.ItemHolder holder, final int i) {
        String key = mKeys[i];
        String type = "";
        switch (key) {
            case "1":
                type = context.getString(R.string.fever);
                break;
            case "2":
                type = context.getString(R.string.cold_amp_cough);
                break;
            case "3":
                type = context.getString(R.string.headache);
                break;
            case "4":
                type = context.getString(R.string.diarrhea);
                break;
            case "5":
                type = context.getString(R.string.malaria);
                break;
            case "6":
                type = context.getString(R.string.others);
                break;
        }

        listItemBinding.tvType.setText(type);
        final List<MedicalDetailsBean> dataModel = medicalDetailsBeans.get(key);
        setAdapter(dataModel);
    }

    private void setAdapter(List<MedicalDetailsBean> list) {
        ViewMedicalDetailsAdapter viewMedicalDetailsAdapter = new ViewMedicalDetailsAdapter(context, list);
        listItemBinding.groupRV.setLayoutManager(new LinearLayoutManager(context));
        listItemBinding.groupRV.setAdapter(viewMedicalDetailsAdapter);
    }

    @Override
    public int getItemCount() {
        return medicalDetailsBeans != null && medicalDetailsBeans.size() > 0 ? medicalDetailsBeans.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        GroupViewMedicalRowBinding listItemBinding;

        ItemHolder(GroupViewMedicalRowBinding listItemBinding) {
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
