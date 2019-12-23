package com.example.twdinspection.inspection.adapter;

import android.content.Context;
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
import com.example.twdinspection.databinding.InstMainRowBinding;

import java.util.ArrayList;
import java.util.Random;

public class InstMenuAdapter extends RecyclerView.Adapter<InstMenuAdapter.UserHolder> {

    private ArrayList<String> sections;
    private Context context;

    public InstMenuAdapter(Context context, ArrayList<String> sections) {
        this.sections = sections;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InstMainRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.inst_main_row, parent, false);
        return new UserHolder(binding);
    }

    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        String string = sections.get(position);
        holder.bind(string);
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return sections != null ? sections.size() : 0;
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private InstMainRowBinding binding;

        UserHolder(InstMainRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(String string) {
            this.binding.setVariable(BR.staff, string);
            this.binding.executePendingBindings();
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
