package com.example.twdinspection.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.Room.repository.DistrictRepository;
import com.example.twdinspection.source.DistManVillage.Districts;
import com.example.twdinspection.source.DistManVillage.Mandals;
import com.example.twdinspection.source.DistManVillage.Villages;

import java.util.List;

public class BasicDetailsViewModel extends AndroidViewModel {
    private LiveData<List<Districts>> districts;
    private LiveData<List<Mandals>> mandals;
    private LiveData<List<Villages>> villages;
    private LiveData<List<String>> inst_names;
    private DistrictRepository mRepository;

    public BasicDetailsViewModel(Application application) {
        super(application);
        districts = new MutableLiveData<>();
        mandals = new MutableLiveData<>();
        villages = new MutableLiveData<>();
        inst_names = new MutableLiveData<>();
        mRepository = new DistrictRepository(application);
    }

    public LiveData<List<Districts>> getAllDistricts() {
        if (districts != null) {
            districts = mRepository.getDistricts();
        }
        return districts;
    }

    public LiveData<String> getStudCount(int class_id) {
        LiveData<String> count= mRepository.getStudCount(class_id);
        return count;
    }

    public LiveData<List<Mandals>> getAllMandals(int distId) {
        if (mandals != null) {
            mandals=mRepository.getMandals(distId);
        }
        return mandals;
    }

    public LiveData<List<String>> getInstitutes() {
        if (inst_names != null) {
            inst_names=mRepository.getInstitutes();
        }
        return inst_names;
    }

    public LiveData<List<Villages>> getAllVillages(int mandalId,int distId) {
            villages=mRepository.getVillages(mandalId,distId);
        return villages;
    }

    @SuppressLint("CheckResult")
//    public void getDistId(final DMVInterface dmvInterface, String distName) {
//
//        PublishSubject.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                String bankNmae = mRepository.getM(distName);
//                if (!emitter.isDisposed())
//                    if (TextUtils.isEmpty(bankNmae)) {
//                        bankNmae = "";
//                    }
//                emitter.onNext(bankNmae);
//
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String bName) throws Exception {
//                        bbInterface.getBankName(bName);
//                    }
//                });
//    }
    public LiveData<Integer> getDistId(String distName){
      LiveData<Integer> distId= mRepository.getDistId(distName);
        return distId;
    }
    public LiveData<Integer> getMandalId(String mandalName,int distId){
        LiveData<Integer> mandalId= mRepository.getMandalId(mandalName,distId);
        return mandalId;
    }
}
