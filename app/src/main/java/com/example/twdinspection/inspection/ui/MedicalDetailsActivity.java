package com.example.twdinspection.inspection.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.common.custom.CustomFontTextView;
import com.example.twdinspection.databinding.ActivityMedialDetailsBinding;
import com.example.twdinspection.inspection.adapter.MedicalDetailsAdapter;
import com.example.twdinspection.inspection.interfaces.ClickCallback;
import com.example.twdinspection.inspection.source.MedicalDetailsBean;

import java.util.ArrayList;
import java.util.List;

public class MedicalDetailsActivity extends BaseActivity implements View.OnClickListener, ClickCallback {

    private static final String TAG = MedicalDetailsActivity.class.getSimpleName();
    ActivityMedialDetailsBinding binding;
    MedicalDetailsAdapter adapter;
    List<MedicalDetailsBean> list;
    List<MedicalDetailsBean> totalList;
    private int issueType = 1, selectedType = 0;
    private int f_cnt, c_cnt, h_cnt, d_cnt, m_cnt, o_cnt, tot_cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalList = new ArrayList<>();
        list = new ArrayList<>();
        binding = putContentView(R.layout.activity_medial_details, getResources().getString(R.string.medical_health));

        binding.feverLayout.setOnClickListener(this);
        binding.coldLayout.setOnClickListener(this);
        binding.headacheLayout.setOnClickListener(this);
        binding.diarrheaLayout.setOnClickListener(this);
        binding.malariaLayout.setOnClickListener(this);
        binding.others.setOnClickListener(this);

        try {
            f_cnt = getIntent().getIntExtra("f_cnt", 0);
            c_cnt = getIntent().getIntExtra("c_cnt", 0);
            h_cnt = getIntent().getIntExtra("h_cnt", 0);
            d_cnt = getIntent().getIntExtra("d_cnt", 0);
            m_cnt = getIntent().getIntExtra("m_cnt", 0);
            o_cnt = getIntent().getIntExtra("o_cnt", 0);
            tot_cnt = getIntent().getIntExtra("tot_cnt", 0);

            binding.etFever.setText(getString(R.string.fever) + " -" + f_cnt);
            binding.etCold.setText(getString(R.string.cold_amp_cough) + " -" + c_cnt);
            binding.etHeadache.setText(getString(R.string.headache) + " -" + h_cnt);
            binding.etDiarrhea.setText(getString(R.string.diarrhea) + " -" + d_cnt);
            binding.etMalaria.setText(getString(R.string.malaria) + " -" + m_cnt);
            binding.etOthers.setText(getString(R.string.others) + " -" + o_cnt);


            if (f_cnt > 0) {
                issueType = 1;
                changeLayoutColor(binding.feverLayout, binding.ivFever, R.drawable.fever_selected, binding.etFever, R.string.fever, f_cnt);
            } else if (c_cnt > 0) {
                issueType = 2;
                changeLayoutColor(binding.coldLayout, binding.ivCold, R.drawable.cold_selected, binding.etCold, R.string.cold_amp_cough, c_cnt);
            } else if (h_cnt > 0) {
                issueType = 3;
                changeLayoutColor(binding.headacheLayout, binding.ivHeadache, R.drawable.headache_selected, binding.etHeadache, R.string.headache, h_cnt);
            } else if (d_cnt > 0) {
                issueType = 4;
                changeLayoutColor(binding.diarrheaLayout, binding.ivDiarrhea, R.drawable.diarrhea_selected, binding.etDiarrhea, R.string.diarrhea, d_cnt);
            } else if (m_cnt > 0) {
                issueType = 5;
                changeLayoutColor(binding.malariaLayout, binding.ivMalaria, R.drawable.mosquito_selected, binding.etMalaria, R.string.malaria, m_cnt);
            } else if (o_cnt > 0) {
                issueType = 6;
                changeLayoutColor(binding.others, binding.ivOthers, R.drawable.others_selected, binding.etOthers, R.string.others, o_cnt);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fever_layout:

                issueType = 1;
                changeLayoutColor(view, binding.ivFever, R.drawable.fever_selected, binding.etFever, R.string.fever, f_cnt);
                break;
            case R.id.cold_layout:
                issueType = 2;
                changeLayoutColor(view, binding.ivCold, R.drawable.cold_selected, binding.etCold, R.string.cold_amp_cough, c_cnt);
                break;
            case R.id.headache_layout:
                issueType = 3;
                changeLayoutColor(view, binding.ivHeadache, R.drawable.headache_selected, binding.etHeadache, R.string.headache, h_cnt);
                break;
            case R.id.diarrhea_layout:
                issueType = 4;
                changeLayoutColor(view, binding.ivDiarrhea, R.drawable.diarrhea_selected, binding.etDiarrhea, R.string.diarrhea, d_cnt);
                break;
            case R.id.malaria_layout:
                issueType = 5;
                changeLayoutColor(view, binding.ivMalaria, R.drawable.mosquito_selected, binding.etMalaria, R.string.malaria, m_cnt);
                break;
            case R.id.others:
                issueType = 6;
                changeLayoutColor(view, binding.ivOthers, R.drawable.others_selected, binding.etOthers, R.string.others, o_cnt);
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
            if (typeCount > 0) {
                boolean flag = false;
                if (totalList.size() > 0) {
                    list = new ArrayList<>();
                    for (int x = 0; x < totalList.size(); x++) {
                        if (totalList.get(x).getType().equals(String.valueOf(issueType))) {
                            list.add(totalList.get(x));
                            flag = true;
                        }
                    }
                }
                binding.tvEmpty.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

                if (flag) {
                    adapter = new MedicalDetailsAdapter(MedicalDetailsActivity.this, list, this);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MedicalDetailsActivity.this, RecyclerView.HORIZONTAL, false));
                    binding.recyclerView.setAdapter(adapter);
                } else {
                    list = new ArrayList<>();
                    for (int i = 0; i < typeCount; i++) {
                        MedicalDetailsBean bean = new MedicalDetailsBean();
                        bean.setType(String.valueOf(issueType));
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
                }
            } else {
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.tvEmpty.setText(getString(R.string.no_records_found));
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


            if (totalList.size() > 0) {
                for (int x = totalList.size()-1; x>=0; x--) {
                    if (totalList.get(x).getType().equals(String.valueOf(issueType))) {
                        totalList.remove(totalList.get(x));
                    }
                }
            }

            binding.recyclerView.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.tvEmpty.setText("Records submitted");

            totalList.addAll(list);
            Log.i("SSSS", "onItemClick: "+totalList.size()+totalList.get(0).getStudent_name());
        }
    }

    @Override
    public void onBackPressed() {
        if (tot_cnt != totalList.size()) {
            Toast.makeText(this, "Submit all", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
