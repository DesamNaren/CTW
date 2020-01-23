package com.example.twdinspection.inspection.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterMedicalDetailsBinding;
import com.example.twdinspection.inspection.interfaces.ClickCallback;
import com.example.twdinspection.inspection.source.MedicalDetailsBean;

import java.util.Calendar;
import java.util.List;

public class MedicalDetailsAdapter extends RecyclerView.Adapter<MedicalDetailsAdapter.ItemHolder> {

    private Context context;
    private List<MedicalDetailsBean> list;
    private int selectedPos = 0;
    private ClickCallback callback;
    private String dateValue;
    private String sName, cName, reason, adDate, hName, acName, acDes;

    public MedicalDetailsAdapter(Context context, List<MedicalDetailsBean> list, ClickCallback callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterMedicalDetailsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_medical_details, parent, false);

        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {

        callback.onValueChange(position + 1);

        holder.binding.etStudentName.setText(list.get(position).getStudent_name());
        holder.binding.etClass.setText(list.get(position).getStudent_class());
        holder.binding.etReason.setText(list.get(position).getReason());
        holder.binding.etHospital.setText(list.get(position).getHospitalName());
        holder.binding.etAccName.setText(list.get(position).getAcc_name());
        holder.binding.etAccDes.setText(list.get(position).getAcc_designation());
        holder.binding.etAdmittedDate.setText(list.get(position).getAdmittedDate());

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

                if (holder.binding.etStudentName.getText().toString().trim().isEmpty()) {
                    holder.binding.etStudentName.setError(context.getString(R.string.enter_stu_name));
                    holder.binding.etStudentName.requestFocus();

                } else if (holder.binding.etClass.getText().toString().trim().isEmpty()) {
                    holder.binding.etClass.setError(context.getString(R.string.enter_cls_name));
                    holder.binding.etClass.requestFocus();
                } else if (holder.binding.etReason.getText().toString().trim().isEmpty()) {
                    holder.binding.etReason.setError(context.getString(R.string.enter_reason));
                    holder.binding.etReason.requestFocus();
                } else if (holder.binding.etHospital.getText().toString().trim().isEmpty()) {
                    holder.binding.etHospital.setError(context.getString(R.string.enter_hos_name));
                    holder.binding.etHospital.requestFocus();
                } else if (holder.binding.etAccName.getText().toString().trim().isEmpty()) {
                    holder.binding.etAccName.setError(context.getString(R.string.enter_acc_name));
                    holder.binding.etAccName.requestFocus();
                } else if (holder.binding.etAccDes.getText().toString().trim().isEmpty()) {
                    holder.binding.etAccDes.setError(context.getString(R.string.enter_acc_des));
                    holder.binding.etAccDes.requestFocus();
                } else if (!holder.binding.etAdmittedDate.getText().toString().contains("/")) {
                    holder.binding.etAdmittedDate.setError(context.getString(R.string.enter_adm_date));
                    holder.binding.etAdmittedDate.requestFocus();
                } else {

                    sName = holder.binding.etStudentName.getText().toString();
                    cName = holder.binding.etClass.getText().toString();
                    reason = holder.binding.etReason.getText().toString();
                    hName = holder.binding.etHospital.getText().toString();
                    acName = holder.binding.etAccName.getText().toString();
                    acDes = holder.binding.etAccDes.getText().toString();
                    adDate = holder.binding.etAdmittedDate.getText().toString();

                    MedicalDetailsBean medicalDetailsBean = new MedicalDetailsBean(
                            sName, cName, reason, adDate, hName, acName, acDes, list.get(position).getType());
                    list.set(position, medicalDetailsBean);


                    if (position < list.size()) {
                        selectedPos = position + 1;
                        callback.onItemClick(position + 1);
                    }
                }
            }
        });

        holder.binding.etAdmittedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admDatePicker(holder.binding);
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
            binding.setVariable(com.example.twdinspection.BR.medicalDetails, obj);
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


    private void admDatePicker(AdapterMedicalDetailsBinding binding) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateValue = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etAdmittedDate.setText(dateValue);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}



