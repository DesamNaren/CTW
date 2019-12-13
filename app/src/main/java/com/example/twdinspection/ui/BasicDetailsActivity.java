package com.example.twdinspection.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ProfileLayoutBinding;
import com.example.twdinspection.viewmodel.BasicDetailsViewModel;

import java.util.ArrayList;

public class BasicDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    BasicDetailsViewModel viewModel;
    ProfileLayoutBinding profileLayoutBinding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BasicDetailsActivity.this;

        profileLayoutBinding = DataBindingUtil.setContentView(this, R.layout.profile_layout);
        viewModel = new BasicDetailsViewModel(getApplication());
        profileLayoutBinding.setViewModel(viewModel);
        profileLayoutBinding.executePendingBindings();

        viewModel.getAllDistricts().observe(this, districts -> {
            if (districts != null && districts.size() > 0) {
                ArrayList<String> distNames = new ArrayList<>();
                for (int i = 0; i < districts.size(); i++) {
                    distNames.add(districts.get(i).getDist_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, distNames
                );
                profileLayoutBinding.spDist.setAdapter(adapter);
            }
        });

        profileLayoutBinding.spDist.setOnItemSelectedListener(this);
        profileLayoutBinding.btnProceed.setOnClickListener(
                view -> startActivity(new Intent(BasicDetailsActivity.this, InfoActivity.class)));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.sp_dist){
            viewModel.getDistId(profileLayoutBinding.spDist.getSelectedItem().toString()).observe(BasicDetailsActivity.this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer!=null){
                        viewModel.getAllMandals(integer).observe(BasicDetailsActivity.this, mandals -> {
                            if (mandals != null && mandals.size() > 0) {
                                ArrayList<String> mandalNames = new ArrayList<>();
                                for (int i = 0; i < mandals.size(); i++) {
                                    mandalNames.add(mandals.get(i).getMandal_name());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                        android.R.layout.simple_spinner_dropdown_item, mandalNames
                                );
                                profileLayoutBinding.spMandal.setAdapter(adapter);
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
