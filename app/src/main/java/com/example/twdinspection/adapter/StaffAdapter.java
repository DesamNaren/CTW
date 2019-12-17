package com.example.twdinspection.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Context context;

    public StaffAdapter(Context context, List<EmployeeResponse> employeeResponses) {
        this.employeeResponses = employeeResponses;
        this.context = context;
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

        holder.binding.llPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!employeeResponse.isPresentFlag()) {
                    employeeResponse.setPresentFlag(true);
                    employeeResponse.setAbsentFlag(false);
                    employeeResponse.setOndepFlag(false);

                    Animation animSlide = AnimationUtils.loadAnimation(context,
                            R.anim.item_animation_fall_down);

                    holder.binding.llPresent.setAnimation(animSlide);
                    changeIcon(holder.binding, holder.binding.ivPresent, R.drawable.present, holder.binding.tvPresent);
                }

            }
        });
        holder.binding.llAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!employeeResponse.isAbsentFlag()) {
                    employeeResponse.setPresentFlag(false);
                    employeeResponse.setAbsentFlag(true);
                    employeeResponse.setOndepFlag(false);

                    Animation animSlide = AnimationUtils.loadAnimation(context,
                            R.anim.item_animation_fall_down);

                    holder.binding.llAbsent.setAnimation(animSlide);
                    changeIcon(holder.binding, holder.binding.ivAbsent, R.drawable.absent, holder.binding.tvAbsent);
                }
            }
        });
        holder.binding.llOndep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!employeeResponse.isOndepFlag()) {
                    employeeResponse.setPresentFlag(false);
                    employeeResponse.setAbsentFlag(false);
                    employeeResponse.setOndepFlag(true);

                    Animation animSlide = AnimationUtils.loadAnimation(context,
                            R.anim.item_animation_fall_down);

                    holder.binding.llOndep.setAnimation(animSlide);
                    changeIcon(holder.binding, holder.binding.ivOndep, R.drawable.ondep, holder.binding.tvOndep);
                }
            }
        });

     /*   holder.binding.getRoot().findViewById(R.id.icon).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );*/
    }

    private void changeIcon(ItemStaffAttendanceBinding binding, ImageView imageView, int img, TextView textView) {
        binding.ivPresent.setImageDrawable(context.getResources().getDrawable(R.drawable.present_init));
        binding.ivAbsent.setImageDrawable(context.getResources().getDrawable(R.drawable.absent_init));
        binding.ivOndep.setImageDrawable(context.getResources().getDrawable(R.drawable.ondep_init));

        binding.tvPresent.setTypeface(null, Typeface.NORMAL);
        binding.tvAbsent.setTypeface(null, Typeface.NORMAL);
        binding.tvOndep.setTypeface(null, Typeface.NORMAL);

        imageView.setImageDrawable(context.getResources().getDrawable(img));
        textView.setTypeface(null, Typeface.BOLD);

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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
