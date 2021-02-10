package com.cgg.twdinspection.inspection.ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityMedialDetailsBinding;
import com.cgg.twdinspection.inspection.adapter.MedicalDetailsAdapter;
import com.cgg.twdinspection.inspection.interfaces.ClickCallback;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.MedicalDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MedicalDetailsActivity extends AppCompatActivity implements View.OnClickListener, ClickCallback {

    private static final String TAG = MedicalDetailsActivity.class.getSimpleName();
    ActivityMedialDetailsBinding binding;
    MedicalDetailsAdapter adapter;
    List<MedicalDetailsBean> list;
    List<MedicalDetailsBean> totalList;
    private int issueType = 1, selectedType = 0;
    private int f_cnt, c_cnt, h_cnt, d_cnt, m_cnt, o_cnt, tot_cnt;
    private MedicalDetailsViewModel medicalDetailsViewModel;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        binding = DataBindingUtil.setContentView(this, (R.layout.activity_medial_details));
        binding.appBarLayout.backBtn.setVisibility(View.GONE);
        binding.appBarLayout.ivHome.setVisibility(View.GONE);
        binding.appBarLayout.headerTitle.setText(getString(R.string.medical_health));
        medicalDetailsViewModel = new MedicalDetailsViewModel(getApplication());
        binding.setViewModel(medicalDetailsViewModel);
        sharedPreferences = TWDApplication.get(this).getPreferences();

        editor = sharedPreferences.edit();
        instMainViewModel = new InstMainViewModel(getApplication());

        LiveData<List<MedicalDetailsBean>> listLiveData = medicalDetailsViewModel.getMedicalDetails();
        listLiveData.observe(this, new Observer<List<MedicalDetailsBean>>() {
            @Override
            public void onChanged(List<MedicalDetailsBean> medicalDetailsBeans) {
                listLiveData.removeObservers(MedicalDetailsActivity.this);
                if (medicalDetailsBeans != null) {
                    totalList = medicalDetailsBeans;
                } else {
                    totalList = new ArrayList<>();
                }

                binding.feverLayout.setOnClickListener(MedicalDetailsActivity.this);
                binding.coldLayout.setOnClickListener((MedicalDetailsActivity.this));
                binding.headacheLayout.setOnClickListener((MedicalDetailsActivity.this));
                binding.diarrheaLayout.setOnClickListener((MedicalDetailsActivity.this));
                binding.malariaLayout.setOnClickListener((MedicalDetailsActivity.this));
                binding.others.setOnClickListener((MedicalDetailsActivity.this));

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
        });


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
                            return rv.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING;
                        }
                    });
                }
            } else {
                binding.tvTypeCnt.setText("");
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.tvEmpty.setText(getString(R.string.no_records_found));
                binding.recyclerView.setVisibility(View.GONE);
            }
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onItemClick(int position) {

        if (position < list.size()) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            binding.recyclerView.scrollToPosition(position);
            Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.item_animation_fall_down);
            binding.recyclerView.setAnimation(animSlide);
        } else {
            if (totalList.size() > 0) {
                for (int x = totalList.size() - 1; x >= 0; x--) {
                    if (totalList.get(x).getType().equals(String.valueOf(issueType))) {
                        totalList.remove(totalList.get(x));
                    }
                }
            }
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.tvEmpty.setText(R.string.records_submitted);

            totalList.addAll(list);
            medicalDetailsViewModel.insertMedicalDetailsInfo(totalList);
            binding.tvTypeCnt.setText("");
        }
    }

    @Override
    public void onValueChange(int position) {
        binding.tvTypeCnt.setText("Record: " + position);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (tot_cnt != totalList.size()) {

            Utils.customDiscardAlert(this,
                    getResources().getString(R.string.app_name),
                    getString(R.string.are_go_back));
        } else {
            super.onBackPressed();
        }
    }
}
