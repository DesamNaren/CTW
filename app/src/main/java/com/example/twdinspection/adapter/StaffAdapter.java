package com.example.twdinspection.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ItemStaffAttendanceBinding;
import com.example.twdinspection.source.EmployeeResponse;

import java.util.List;
import java.util.Random;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.UserHolder> {

    private List<EmployeeResponse> employeeResponses;

    public StaffAdapter(List<EmployeeResponse> employeeResponses) {
        this.employeeResponses = employeeResponses;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStaffAttendanceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_staff_attendance, parent, false);
        return new UserHolder(binding);
    }

    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        EmployeeResponse employeeResponse = employeeResponses.get(position);
        holder.bind(employeeResponse);
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return employeeResponses != null ? employeeResponses.size() : 0;
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private ItemStaffAttendanceBinding binding;

        UserHolder(ItemStaffAttendanceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(EmployeeResponse employeeResponse) {
            this.binding.setVariable(BR.staff, employeeResponse);
            this.binding.executePendingBindings();
        }

    }

    public void setEmployeeList(List<EmployeeResponse> employees) {
        this.employeeResponses = employees;
        notifyDataSetChanged();
    }
}
