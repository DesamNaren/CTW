package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.schemes.room.repository.InspectionRemarksRepository;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;

import java.util.List;

public class BenDetailsViewModel extends AndroidViewModel {
    public BeneficiaryDetail beneficiaryDetail;
    private LiveData<List<InspectionRemarksEntity>> inspectionRemarks= new MutableLiveData<>();
    private InspectionRemarksRepository remarksRepository;

    public BenDetailsViewModel(Application application, BeneficiaryDetail beneficiaryDetail) {
        super(application);
        this.beneficiaryDetail = beneficiaryDetail;
        remarksRepository = new InspectionRemarksRepository(application);
    }

    public LiveData<List<InspectionRemarksEntity>> getRemarks() {
        if (inspectionRemarks != null) {
            inspectionRemarks=remarksRepository.getInspRemarks();
        }
        return inspectionRemarks;
    }
}
