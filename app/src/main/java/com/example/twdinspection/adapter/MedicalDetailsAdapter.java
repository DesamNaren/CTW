package com.example.twdinspection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterMedicalDetailsBinding;
import com.example.twdinspection.source.MedicalDetailsBean;

import java.util.List;

public class MedicalDetailsAdapter extends RecyclerView.Adapter<MedicalDetailsAdapter.ItemHolder> {

    private Context context;
    private List<MedicalDetailsBean> list;
    private int selectedPos = -1;

    public MedicalDetailsAdapter(Context context, List<MedicalDetailsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        AdapterMedicalDetailsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_medical_details, parent, false);

        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {


    }


    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        public AdapterMedicalDetailsBinding binding;

        ItemHolder(AdapterMedicalDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Object obj) {
            binding.setVariable(BR.studentAttnd, obj);
            binding.executePendingBindings();
        }

    }
}

