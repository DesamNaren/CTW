package com.example.twdinspection.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.StudentsAttendanceBean;
import com.example.twdinspection.databinding.AdapterStudAttndBinding;

import java.util.List;

public class StudentsAttAdapter extends RecyclerView.Adapter<StudentsAttAdapter.ItemHolder> {

    Context context;
    LiveData<List<StudentsAttendanceBean>> list;

    public StudentsAttAdapter(Context context, LiveData<List<StudentsAttendanceBean>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        AdapterStudAttndBinding listItemBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_stud_attnd, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        StudentsAttendanceBean dataModel = list.getValue().get(position);
        holder.listItemBinding.setStudentAttnd(dataModel);
        holder.bind(dataModel);
    }

    @Override
    public int getItemCount() {
        return list.getValue().size();
    }
    class ItemHolder extends RecyclerView.ViewHolder {

        public AdapterStudAttndBinding listItemBinding;
        public ItemHolder(AdapterStudAttndBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }
        public void bind(Object obj) {
            listItemBinding.setVariable(BR.studentAttnd,obj);
            listItemBinding.executePendingBindings();
        }

    }
}

