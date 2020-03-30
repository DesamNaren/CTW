package com.cgg.twdinspection.schemes.viewmodel;

import android.app.Activity;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.room.repository.SchemesInfoRepository;
import com.cgg.twdinspection.schemes.source.bendetails.BeneficiaryReport;
import com.cgg.twdinspection.schemes.source.bendetails.BeneficiaryRequest;
import com.cgg.twdinspection.schemes.source.schemes.SchemeEntity;
import com.example.twdinspection.databinding.ActivitySchemeReportBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BenReportViewModel extends ViewModel {
    private MutableLiveData<BeneficiaryReport> beneficiaryLiveData;
    private LiveData<List<SchemeEntity>> schemesMutableLiveData;
    private SchemesInfoRepository schemesInfoRepository;
    private ErrorHandlerInterface errorHandlerInterface;
    private Activity context;
    private ActivityBeneficiaryReportBinding binding;
    private ActivitySchemeReportBinding schemeReportBinding;


    BenReportViewModel(ActivityBeneficiaryReportBinding binding, Activity context) {
        this.binding = binding;
        this.context= context;
        beneficiaryLiveData = new MutableLiveData<>();
        schemesMutableLiveData = new MutableLiveData<>();
        schemesInfoRepository = new SchemesInfoRepository(context);
        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BenReportViewModel(ActivitySchemeReportBinding schemeReportBinding, Activity context) {
        this.schemeReportBinding = schemeReportBinding;
        this.context= context;
        beneficiaryLiveData = new MutableLiveData<>();
        schemesMutableLiveData = new MutableLiveData<>();
        schemesInfoRepository = new SchemesInfoRepository(context);
        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public LiveData<BeneficiaryReport> getBeneficiaryInfo(BeneficiaryRequest beneficiaryRequest) {
        if (beneficiaryLiveData != null) {
            if (Utils.checkInternetConnection(context)) {
                getBeneficiaryDetails(beneficiaryRequest);
            }else{
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                Utils.customWarningAlert(context,context.getResources().getString(R.string.app_name),"Please check internet",true);
            }
        }
        return beneficiaryLiveData;
    }

    public LiveData<List<SchemeEntity>> getSchemeInfo() {
        if (schemesMutableLiveData != null) {
            schemesMutableLiveData = schemesInfoRepository.getSchemesInfo();
        }
        return schemesMutableLiveData;
    }

    private void getBeneficiaryDetails(BeneficiaryRequest beneficiaryRequest) {
        TWDService twdService = TWDService.Factory.create("schemes");
        twdService.getBeneficiaryDetails(beneficiaryRequest.getDistId(), beneficiaryRequest.getMandalId(), beneficiaryRequest.getVillageId(), beneficiaryRequest.getFinYearId()).enqueue(new Callback<BeneficiaryReport>() {
            @Override
            public void onResponse(@NotNull Call<BeneficiaryReport> call, @NotNull Response<BeneficiaryReport> response) {
                beneficiaryLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<BeneficiaryReport> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
