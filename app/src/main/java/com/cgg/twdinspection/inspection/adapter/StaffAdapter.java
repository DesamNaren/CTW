package com.cgg.twdinspection.inspection.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
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

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ItemStaffAttendanceBinding;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

        RxTextView
                .textChangeEvents(holder.binding.etLeavesAvailed)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {

                        String str = textViewTextChangeEvent.text().toString();
                        if (!TextUtils.isEmpty(str) && holder.binding.etLeavesTaken.getText()!=null && !TextUtils.isEmpty((holder.binding.etLeavesTaken.getText().toString()))) {
                            int tot = Integer.valueOf(str);
                            int taken = Integer.valueOf(holder.binding.etLeavesTaken.getText().toString());
                            holder.binding.etLeaveBal.setText(String.valueOf(tot-taken));
                        } else {
                            holder.binding.etLeaveBal.setText("");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        RxTextView
                .textChangeEvents(holder.binding.etLeavesTaken)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {

                        String str = textViewTextChangeEvent.text().toString();
                        if (!TextUtils.isEmpty(str) && holder.binding.etLeavesAvailed.getText()!=null && !TextUtils.isEmpty((holder.binding.etLeavesAvailed.getText().toString()))) {
                            int taken = Integer.valueOf(str);
                            int tot = Integer.valueOf(holder.binding.etLeavesAvailed.getText().toString());
                            holder.binding.etLeaveBal.setText(String.valueOf(tot-taken));
                        } else {
                            holder.binding.etLeaveBal.setText("");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        holder.binding.llPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!employeeResponse.isPresentFlag()) {
                    employeeResponse.setPresentFlag(true);
                    employeeResponse.setAbsentFlag(false);
                    employeeResponse.setOndepFlag(false);
                    employeeResponse.setLeavesFlag(false);

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
                    employeeResponse.setLeavesFlag(false);

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
                    employeeResponse.setLeavesFlag(false);

                    employeeResponse.setEmp_presence(AppConstants.ONDEPUTATION);

                    Animation animSlide = AnimationUtils.loadAnimation(context,
                            R.anim.item_animation_fall_down);

                    holder.binding.llOndep.setAnimation(animSlide);
                    changeIcon(holder.binding, holder.binding.ivOndep, R.drawable.ondep, holder.binding.tvOndep);
                }
            }
        });
        holder.binding.llLeaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!employeeResponse.isLeavesFlag()) {
                    employeeResponse.setPresentFlag(false);
                    employeeResponse.setAbsentFlag(false);
                    employeeResponse.setOndepFlag(false);
                    employeeResponse.setLeavesFlag(true);

                    employeeResponse.setEmp_presence(AppConstants.LEAVES);

                    Animation animSlide = AnimationUtils.loadAnimation(context,
                            R.anim.item_animation_fall_down);

                    holder.binding.llOd.setAnimation(animSlide);
                    changeIcon(holder.binding, holder.binding.ivLeaves, R.drawable.leaves, holder.binding.tvLeaves);
                }
            }
        });

        holder.binding.tvYesDutyAlloted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.tvYesDutyAlloted.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                holder.binding.tvNoDutyAlloted.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
                employeeResponse.setYday_duty_allotted(AppConstants.Yes);
            }
        });

        holder.binding.tvNoDutyAlloted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.tvYesDutyAlloted.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
                holder.binding.tvNoDutyAlloted.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
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
        binding.ivLeaves.setImageDrawable(context.getResources().getDrawable(R.drawable.leaves_init));

        binding.tvPresent.setTypeface(null, Typeface.NORMAL);
        binding.tvAbsent.setTypeface(null, Typeface.NORMAL);
        binding.tvOndep.setTypeface(null, Typeface.NORMAL);
        binding.tvLeaves.setTypeface(null, Typeface.NORMAL);

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
            this.binding.setVariable(com.cgg.twdinspection.BR.staff, employeeResponse);
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
