package com.example.twdinspection.inspection.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ItemStaffAttendanceBinding;
import com.example.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;

import java.util.List;
import java.util.Random;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.UserHolder> {

    private List<StaffAttendanceEntity> staffAttendanceEntities;
    private Context context;

    public StaffAdapter(Context context, List<StaffAttendanceEntity> employeeResponses) {
        this.staffAttendanceEntities = employeeResponses;
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
        StaffAttendanceEntity employeeResponse = staffAttendanceEntities.get(position);
        holder.binding.setStaff(employeeResponse);

        holder.bind(employeeResponse);
        setAnimation(holder.itemView, position);


        holder.binding.llPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!employeeResponse.isPresentFlag()) {
                    employeeResponse.setPresentFlag(true);
                    employeeResponse.setAbsentFlag(false);
                    employeeResponse.setOndepFlag(false);
                    employeeResponse.setOdFlag(false);
                    employeeResponse.setUnauthLeaveFlag(false);
                    employeeResponse.setEmp_presence(AppConstants.PRESENT);
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
                    employeeResponse.setOdFlag(false);
                    employeeResponse.setUnauthLeaveFlag(false);
                    employeeResponse.setEmp_presence(AppConstants.ABSENT);

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
                    employeeResponse.setOdFlag(false);
                    employeeResponse.setUnauthLeaveFlag(false);
                    employeeResponse.setEmp_presence(AppConstants.ONDEPUTATION);

                    Animation animSlide = AnimationUtils.loadAnimation(context,
                            R.anim.item_animation_fall_down);

                    holder.binding.llOndep.setAnimation(animSlide);
                    changeIcon(holder.binding, holder.binding.ivOndep, R.drawable.ondep, holder.binding.tvOndep);
                }
            }
        });
        holder.binding.llOd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!employeeResponse.isOdFlag()) {
                    employeeResponse.setPresentFlag(false);
                    employeeResponse.setAbsentFlag(false);
                    employeeResponse.setOndepFlag(false);
                    employeeResponse.setOdFlag(true);
                    employeeResponse.setUnauthLeaveFlag(false);
                    employeeResponse.setEmp_presence(AppConstants.OD);

                    Animation animSlide = AnimationUtils.loadAnimation(context,
                            R.anim.item_animation_fall_down);

                    holder.binding.llOd.setAnimation(animSlide);
                    changeIcon(holder.binding, holder.binding.ivOd, R.drawable.ondep, holder.binding.tvOd);
                }
            }
        });
        holder.binding.llUnauthAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!employeeResponse.isUnauthLeaveFlag()) {
                    employeeResponse.setPresentFlag(false);
                    employeeResponse.setAbsentFlag(false);
                    employeeResponse.setOndepFlag(false);
                    employeeResponse.setOdFlag(false);
                    employeeResponse.setUnauthLeaveFlag(true);
                    employeeResponse.setEmp_presence(AppConstants.UNAUTHABSENT);

                    Animation animSlide = AnimationUtils.loadAnimation(context,
                            R.anim.item_animation_fall_down);

                    holder.binding.llUnauthAbsent.setAnimation(animSlide);
                    changeIcon(holder.binding, holder.binding.ivUnauthAbsent, R.drawable.ondep, holder.binding.tvUnauthAbsent);
                }
            }
        });

        holder.binding.tvYesDutyAlloted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.tvYesDutyAlloted.setBackgroundColor(context.getResources().getColor(R.color.list_blue));
                holder.binding.tvNoDutyAlloted.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
                employeeResponse.setYday_duty_allotted(AppConstants.Yes);
            }
        });

        holder.binding.tvNoDutyAlloted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.tvYesDutyAlloted.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
                holder.binding.tvNoDutyAlloted.setBackgroundColor(context.getResources().getColor(R.color.list_blue));
                employeeResponse.setYday_duty_allotted(AppConstants.No);
            }
        });


        holder.binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                employeeResponse.setCateg_pos(i);
                employeeResponse.setCategory(holder.binding.spCategory.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.binding.lastWeek.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                employeeResponse.setLast_week_turn_duties_attended(s.toString());
            }
        });
        holder.binding.etLeavesAvailed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                employeeResponse.setLeaves_availed(s.toString());
            }
        });
        holder.binding.etLeaveBal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                employeeResponse.setLeaves_bal(s.toString());
            }
        });
        holder.binding.etLeavesTaken.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                employeeResponse.setLeaves_taken(s.toString());
            }
        });
        holder.binding.etAcadGradePanel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                employeeResponse.setAcad_panel_grade(s.toString());
            }
        });
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
        return staffAttendanceEntities != null ? staffAttendanceEntities.size() : 0;
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private ItemStaffAttendanceBinding binding;

        UserHolder(ItemStaffAttendanceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(StaffAttendanceEntity employeeResponse) {
            this.binding.setVariable(com.example.twdinspection.BR.staff, employeeResponse);
            this.binding.executePendingBindings();
        }

    }

    public void setEmployeeList(List<StaffAttendanceEntity> employees) {
        this.staffAttendanceEntities = employees;
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
