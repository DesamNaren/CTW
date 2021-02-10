package com.cgg.twdinspection.inspection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.BR;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.ItemSubjectsBinding;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicSubjectsEntity;

import java.util.List;
import java.util.Random;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.UserHolder> {

    private List<AcademicSubjectsEntity> academicSubjectsEntities;
    private Context context;

    public SubjectsAdapter(Context context, List<AcademicSubjectsEntity> employeeResponses) {
        this.academicSubjectsEntities = employeeResponses;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubjectsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_subjects, parent, false);
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
        AcademicSubjectsEntity academicSubjectsEntity = academicSubjectsEntities.get(position);
        holder.binding.setSubjects(academicSubjectsEntity);
        holder.bind(academicSubjectsEntity);

        if (academicSubjectsEntity.isStatus()) {
            holder.binding.ivCheck.setImageDrawable(context.getResources().getDrawable(R.drawable.absent));
            holder.binding.tvName.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            holder.binding.ivCheck.setImageDrawable(context.getResources().getDrawable(R.drawable.absent_init));
            holder.binding.tvName.setTextColor(context.getResources().getColor(R.color.black));
        }

        holder.binding.llSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!academicSubjectsEntity.isStatus()) {
                    academicSubjectsEntity.setStatus(true);
                    holder.binding.ivCheck.setImageDrawable(context.getResources().getDrawable(R.drawable.absent));
                    holder.binding.tvName.setTextColor(context.getResources().getColor(R.color.red));
                } else {
                    academicSubjectsEntity.setStatus(false);
                    holder.binding.ivCheck.setImageDrawable(context.getResources().getDrawable(R.drawable.absent_init));
                    holder.binding.tvName.setTextColor(context.getResources().getColor(R.color.black));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return academicSubjectsEntities != null ? academicSubjectsEntities.size() : 0;
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private ItemSubjectsBinding binding;

        UserHolder(ItemSubjectsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AcademicSubjectsEntity employeeResponse) {
            this.binding.setVariable(BR.subjects, employeeResponse);
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
