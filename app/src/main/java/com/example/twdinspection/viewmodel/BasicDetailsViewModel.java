package com.example.twdinspection.viewmodel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.twdinspection.Room.repository.DistrictRepository;
import com.example.twdinspection.databinding.ProfileLayoutBinding;
import com.example.twdinspection.source.DistManVillage.DistrictEntity;

import java.util.ArrayList;
import java.util.List;

public class BasicDetailsViewModel extends AndroidViewModel {
    private DistrictRepository mRepository;
    private List<DistrictEntity> districts;
    ProfileLayoutBinding binding;
    ArrayList<String> distNames=new ArrayList<>();

    public BasicDetailsViewModel (Application application, ProfileLayoutBinding binding) {
        super(application);
        mRepository = new DistrictRepository(application);
        this.districts = mRepository.getDistricts();
        this.binding = binding;
        if(districts!=null){
            for(int i=0;i<districts.size();i++){
                distNames.add( districts.get(i).getDist_name());
            }
            ArrayAdapter adapter = new ArrayAdapter(application.getApplicationContext(),
                    android.R.layout.simple_spinner_dropdown_item,distNames
            );
            binding.spDist.setAdapter(adapter);
        }

    }



}
