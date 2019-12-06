package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.adapters.MedicalDetailsAdapter;
import com.example.twdinspection.custom.CustomFontTextView;
import com.example.twdinspection.databinding.ActivityMedialDetailsBinding;
import com.example.twdinspection.source.MedicalDetailsBean;

import java.util.ArrayList;
import java.util.List;

public class MedicalDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMedialDetailsBinding binding;
    MedicalDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medial_details);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_medial_details);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Medical & Health Issues");


        List<MedicalDetailsBean> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            MedicalDetailsBean bean = new MedicalDetailsBean();
            list.add(bean);
        }

        adapter = new MedicalDetailsAdapter(MedicalDetailsActivity.this, list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MedicalDetailsActivity.this));
        binding.recyclerView.setAdapter(adapter);

        binding.feverLayout.setOnClickListener(this);
        binding.coldLayout.setOnClickListener(this);
        binding.headacheLayout.setOnClickListener(this);
        binding.diarrheaLayout.setOnClickListener(this);
        binding.malariaLayout.setOnClickListener(this);
        binding.others.setOnClickListener(this);

//        binding.ivCold.setImageDrawable(getResources().getDrawable(R.drawable.mosquito));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fever_layout:

                changeLayoutColor(view, binding.ivFever, R.drawable.fever_selected,binding.etFever);
                break;
            case R.id.cold_layout:

                changeLayoutColor(view, binding.ivCold, R.drawable.cold_selected,binding.etCold);

                break;
            case R.id.headache_layout:
                changeLayoutColor(view, binding.ivHeadache, R.drawable.headache_selected,binding.etHeadache);

                break;
            case R.id.diarrhea_layout:
                changeLayoutColor(view, binding.ivDiarrhea, R.drawable.diarrhea_selected,binding.etDiarrhea);

                break;
            case R.id.malaria_layout:

                changeLayoutColor(view, binding.ivMalaria, R.drawable.mosquito_selected,binding.etMalaria);

                break;
            case R.id.others:

                changeLayoutColor(view, binding.ivOthers, R.drawable.others_selected,binding.etOthers);

                break;
        }
    }

    private void changeLayoutColor(View view, ImageView imageView, int drawable,CustomFontTextView textView) {
        binding.feverLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.coldLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.headacheLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.diarrheaLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.malariaLayout.setBackgroundColor(getResources().getColor(R.color.white));
        binding.others.setBackgroundColor(getResources().getColor(R.color.white));
        view.setBackgroundColor(getResources().getColor(R.color.list_blue));

        binding.ivFever.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivCold.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivHeadache.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivDiarrhea.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivMalaria.setBackground(getResources().getDrawable(R.drawable.medical));
        binding.ivOthers.setBackground(getResources().getDrawable(R.drawable.medical));
        imageView.setBackground(getResources().getDrawable(R.drawable.medical_selected));

        binding.ivFever.setImageDrawable(getResources().getDrawable(R.drawable.fever));
        binding.ivCold.setImageDrawable(getResources().getDrawable(R.drawable.cough));
        binding.ivHeadache.setImageDrawable(getResources().getDrawable(R.drawable.headache));
        binding.ivDiarrhea.setImageDrawable(getResources().getDrawable(R.drawable.diarrhea));
        binding.ivMalaria.setImageDrawable(getResources().getDrawable(R.drawable.mosquito));
        binding.ivOthers.setImageDrawable(getResources().getDrawable(R.drawable.heartbeat));
        imageView.setImageDrawable(getResources().getDrawable(drawable));

        binding.etFever.setTextColor(getResources().getColor(R.color.black));
        binding.etCold.setTextColor(getResources().getColor(R.color.black));
        binding.etHeadache.setTextColor(getResources().getColor(R.color.black));
        binding.etDiarrhea.setTextColor(getResources().getColor(R.color.black));
        binding.etMalaria.setTextColor(getResources().getColor(R.color.black));
        binding.etOthers.setTextColor(getResources().getColor(R.color.black));
        textView.setTextColor(getResources().getColor(R.color.white));

    }


}
