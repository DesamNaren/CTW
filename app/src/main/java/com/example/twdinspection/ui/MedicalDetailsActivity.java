package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.adapter.MedicalDetailsAdapter;
import com.example.twdinspection.custom.CustomFontTextView;
import com.example.twdinspection.databinding.ActivityMedialDetailsBinding;
import com.example.twdinspection.interfaces.ClickCallback;
import com.example.twdinspection.source.MedicalDetailsBean;

import java.util.ArrayList;
import java.util.List;

public class MedicalDetailsActivity extends AppCompatActivity implements View.OnClickListener, ClickCallback {

    private static final String TAG = MedicalDetailsActivity.class.getSimpleName();
    ActivityMedialDetailsBinding binding;
    MedicalDetailsAdapter adapter;
    List<MedicalDetailsBean> list;
    List<MedicalDetailsBean> totalList;
    private int issueType=1, selectedType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medial_details);
        totalList = new ArrayList<>();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medial_details);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Medical & Health Issues");

        binding.feverLayout.setOnClickListener(this);
        binding.coldLayout.setOnClickListener(this);
        binding.headacheLayout.setOnClickListener(this);
        binding.diarrheaLayout.setOnClickListener(this);
        binding.malariaLayout.setOnClickListener(this);
        binding.others.setOnClickListener(this);

        changeLayoutColor(binding.feverLayout, binding.ivFever, R.drawable.fever_selected, binding.etFever, R.string.fever, 10);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fever_layout:
                issueType = 1;
                changeLayoutColor(view, binding.ivFever, R.drawable.fever_selected, binding.etFever, R.string.fever, 10);
                break;
            case R.id.cold_layout:
                issueType = 2;
                changeLayoutColor(view, binding.ivCold, R.drawable.cold_selected, binding.etCold, R.string.cold_amp_cough, 5);
                break;
            case R.id.headache_layout:
                issueType = 3;
                changeLayoutColor(view, binding.ivHeadache, R.drawable.headache_selected, binding.etHeadache, R.string.headache, 13);
                break;
            case R.id.diarrhea_layout:
                issueType = 4;
                changeLayoutColor(view, binding.ivDiarrhea, R.drawable.diarrhea_selected, binding.etDiarrhea, R.string.diarrhea, 1);
                break;
            case R.id.malaria_layout:
                issueType = 5;
                changeLayoutColor(view, binding.ivMalaria, R.drawable.mosquito_selected, binding.etMalaria, R.string.malaria, 2);
                break;
            case R.id.others:
                issueType = 6;
                changeLayoutColor(view, binding.ivOthers, R.drawable.others_selected, binding.etOthers, R.string.others, 0);
                break;
        }
    }

    private void changeLayoutColor(View view, ImageView imageView, int drawable, CustomFontTextView textView, int type, int typeCount) {


        binding.feverLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.coldLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.headacheLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.diarrheaLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.malariaLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.others.setBackgroundColor(getResources().getColor(R.color.white));


        binding.ivFever.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivCold.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivHeadache.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivDiarrhea.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivMalaria.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivOthers.setBackground(getResources().getDrawable(R.drawable.medical));


        binding.ivFever.setImageDrawable(getResources().getDrawable(R.drawable.fever));
        binding.ivCold.setImageDrawable(getResources().getDrawable(R.drawable.cough));
        binding.ivHeadache.setImageDrawable(getResources().getDrawable(R.drawable.headache));
        binding.ivDiarrhea.setImageDrawable(getResources().getDrawable(R.drawable.diarrhea));
        binding.ivMalaria.setImageDrawable(getResources().getDrawable(R.drawable.mosquito));
        binding.ivOthers.setImageDrawable(getResources().getDrawable(R.drawable.heartbeat));


        binding.etFever.setTextColor(getResources().getColor(R.color.black));
        binding.etCold.setTextColor(getResources().getColor(R.color.black));
        binding.etHeadache.setTextColor(getResources().getColor(R.color.black));
        binding.etDiarrhea.setTextColor(getResources().getColor(R.color.black));
        binding.etMalaria.setTextColor(getResources().getColor(R.color.black));
        binding.etOthers.setTextColor(getResources().getColor(R.color.black));


        view.setBackgroundColor(getResources().getColor(R.color.list_blue));
        imageView.setBackground(getResources().getDrawable(R.drawable.medical_selected));
        imageView.setImageDrawable(getResources().getDrawable(drawable));
        textView.setTextColor(getResources().getColor(R.color.white));
        binding.tvType.setText(getResources().getString(type));

        if (issueType != selectedType) {

            selectedType = issueType;

            list = new ArrayList<>();
            if (typeCount > 0) {
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.tvEmpty.setVisibility(View.GONE);

                for (int i = 0; i < typeCount; i++) {
                    MedicalDetailsBean bean = new MedicalDetailsBean();
                    list.add(bean);
                }
                adapter = new MedicalDetailsAdapter(MedicalDetailsActivity.this, list, this);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(MedicalDetailsActivity.this, RecyclerView.HORIZONTAL, false));
                binding.recyclerView.setAdapter(adapter);

                binding.recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        // Stop only scrolling.
                        return rv.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING;
                    }
                });
            } else {
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onItemClick(int position) {

        if (position < list.size()) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);

            adapter.notifyDataSetChanged();
            binding.recyclerView.scrollToPosition(position);

            // Load the animation like this
            Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.item_animation_fall_down);

//                 Start the animation like this
            binding.recyclerView.setAnimation(animSlide);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.tvEmpty.setText("Completed");
            totalList.addAll(list);
            Log.i(TAG, "onItemClick: list " + list.size());
            Log.i(TAG, "onItemClick: total " + totalList.size());
            Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show();
        }
    }
}
