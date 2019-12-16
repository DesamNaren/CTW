package com.example.twdinspection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterMedicalDetailsBinding;
import com.example.twdinspection.interfaces.ClickCallback;
import com.example.twdinspection.source.MedicalDetailsBean;

import java.util.List;
import java.util.Random;

public class MedicalDetailsAdapter extends RecyclerView.Adapter<MedicalDetailsAdapter.ItemHolder> {

    private Context context;
    private List<MedicalDetailsBean> list;
    private int selectedPos = 0;
    MedicalDetailsAdapter adapter;
    ClickCallback callback;
    private int lastPosition = -1;

    public MedicalDetailsAdapter(Context context, List<MedicalDetailsBean> list, ClickCallback callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
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
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {

        holder.binding.etClass.setText("Class " + (position + 1));

        if (position == 0)
            holder.binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        else
            holder.binding.btnLayout.btnPrevious.setVisibility(View.VISIBLE);

        if (position == list.size() - 1)
            holder.binding.btnLayout.btnNext.setText(context.getResources().getString(R.string.completed));
        else
            holder.binding.btnLayout.btnNext.setText(context.getResources().getString(R.string.next));
        holder.binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if (holder.binding.etStudentName.getText().toString().trim().isEmpty()) {
                    holder.binding.etStudentName.setError("Please enter student name");
                    holder.binding.etStudentName.requestFocus();
                } else if (holder.binding.etClass.getText().toString().trim().isEmpty()) {
                    holder.binding.etClass.setError("Please enter class name");
                    holder.binding.etClass.requestFocus();
                } else if (holder.binding.etReason.getText().toString().trim().isEmpty()) {
                    holder.binding.etReason.setError("Please enter reason");
                    holder.binding.etReason.requestFocus();
                } else if (holder.binding.etHospital.getText().toString().trim().isEmpty()) {
                    holder.binding.etHospital.setError("Please enter hospital name");
                    holder.binding.etHospital.requestFocus();
                } else if (holder.binding.etAdmittedDate.getText().toString().trim().isEmpty()) {
                    holder.binding.etAdmittedDate.setError("Please enter class name");
                    holder.binding.etAdmittedDate.requestFocus();
                } else if (holder.binding.etAccName.getText().toString().trim().isEmpty()) {
                    holder.binding.etAccName.setError("Please enter Accompanied Name");
                    holder.binding.etAccName.requestFocus();
                } else if (holder.binding.etAccDes.getText().toString().trim().isEmpty()) {
                    holder.binding.etAccDes.setError("Please enter Accompanied Designation");
                    holder.binding.etAccDes.requestFocus();
                } else {*/
                    if (position < list.size()) {
                        selectedPos = position + 1;
                        callback.onItemClick(position + 1);
                    }
//                }
            }
        });

        holder.binding.btnLayout.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = position - 1;
                callback.onItemClick(position - 1);
            }
        });

        if (selectedPos == position) {
            holder.binding.btnLayout.btnLayout.setVisibility(View.VISIBLE);
        } else {
            holder.binding.btnLayout.btnLayout.setVisibility(View.GONE);
        }
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
            binding.setVariable(BR.medicalDetails, obj);
            binding.executePendingBindings();
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

