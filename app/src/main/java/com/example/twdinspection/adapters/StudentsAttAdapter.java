package com.example.twdinspection.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;

import com.example.twdinspection.source.StudentsAttendanceBean;
import com.example.twdinspection.databinding.AdapterStudAttndBinding;

import java.util.List;

public class StudentsAttAdapter extends RecyclerView.Adapter<StudentsAttAdapter.ItemHolder> {

    Context context;
    LiveData<List<StudentsAttendanceBean>> list;
    private int selectedPos=-1;
    public StudentsAttAdapter(Context context, LiveData<List<StudentsAttendanceBean>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        AdapterStudAttndBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_stud_attnd, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final int position = i;
        final StudentsAttendanceBean dataModel = list.getValue().get(position);
        holder.listItemBinding.setStudentAttnd(dataModel);

        holder.listItemBinding.tvClass.setText("Class " + (position + 1));
        holder.listItemBinding.getRoot().findViewById(R.id.tv_expand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = position;
                notifyDataSetChanged();
//                selectedPos=position;
//                holder.listItemBinding.llEntries.setVisibility(View.VISIBLE);
//                minimiseAll(selectedPos,holder);

            }
        });
        if (selectedPos == position) {

            if (holder.listItemBinding.llEntries.getVisibility() == View.VISIBLE) {

                holder.listItemBinding.llEntries.setVisibility(View.GONE);
                holder.listItemBinding.tvExpand.setBackground(context.getResources().getDrawable(R.drawable.downbutton));

            } else {

                holder.listItemBinding.llEntries.setVisibility(View.VISIBLE);
                holder.listItemBinding.tvExpand.setBackground(context.getResources().getDrawable(R.drawable.up_arrow));
            }
        } else {
            holder.listItemBinding.tvExpand.setBackground(context.getResources().getDrawable(R.drawable.downbutton));
            holder.listItemBinding.llEntries.setVisibility(View.GONE);
        }
        holder.bind(dataModel);
    }

    private void minimiseAll(int selectedPos,ItemHolder holder) {
        if(holder.getAdapterPosition()!=selectedPos){
            holder.listItemBinding.llEntries.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list!=null && list.getValue()!=null? list.getValue().size():0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        public AdapterStudAttndBinding listItemBinding;

        public ItemHolder(AdapterStudAttndBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(BR.studentAttnd, obj);
            listItemBinding.executePendingBindings();
        }

    }
}

