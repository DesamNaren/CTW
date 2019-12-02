package com.example.twdinspection.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.generated.callback.OnClickListener;
import com.example.twdinspection.source.StudentsAttendanceBean;
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
    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        StudentsAttendanceBean dataModel = list.getValue().get(position);
        holder.listItemBinding.setStudentAttnd(dataModel);

        holder.listItemBinding.getRoot().findViewById(R.id.tv_expand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minimiseAll();
                holder.listItemBinding.llEntries.setVisibility(View.VISIBLE);
            }
        });
        holder.bind(dataModel);
    }

    private void minimiseAll() {
        
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

