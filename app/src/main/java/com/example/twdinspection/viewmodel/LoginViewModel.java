package com.example.twdinspection.viewmodel;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityLoginCreBinding;
import com.example.twdinspection.network.TWDService;
import com.example.twdinspection.source.EmployeeResponse;
import com.example.twdinspection.source.LoginUser;
import com.example.twdinspection.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<EmployeeResponse> responseMutableLiveData;
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<Integer> busy;
    private Context context;
    private ActivityLoginCreBinding binding;

    LoginViewModel(ActivityLoginCreBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    public MutableLiveData<Integer> getBusy() {
        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(8);
        }
        return busy;
    }

    public LiveData<EmployeeResponse> geListLiveData() {
        responseMutableLiveData = new MutableLiveData<>();
        return responseMutableLiveData;
    }

    public void onBtnClick() {
        LoginUser loginUser = new LoginUser(username.getValue(), password.getValue());
       if (TextUtils.isEmpty(loginUser.getEmail())) {
            binding.tName.setError("Please enter username");
            return;

        } else {
            binding.tName.setError(null);
        }
        if (TextUtils.isEmpty(loginUser.getPassword())) {
            binding.tPwd.setError("Please enter password");
            return;
        } else {
            binding.tPwd.setError(null);
        }


        callLoginAPI(loginUser);
    }

    public void onViewPwd() { if (binding.etPwd.getInputType() == InputType.TYPE_CLASS_TEXT) {
            binding.pwdImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pwd_hide));
            binding.etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            binding.pwdImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pwd_view));
            binding.etPwd.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        if (password.getValue() != null)
            binding.etPwd.setSelection(password.getValue().length());
    }

    private void callLoginAPI(LoginUser loginUser) {
        Utils.hideKeyboard(context, binding.btnLogin);
        binding.btnLogin.setVisibility(View.GONE);
        binding.progress.setVisibility(View.VISIBLE);
        TWDService twdService = TWDService.Factory.create();
        twdService.getLoginResponse(loginUser.getEmail(), loginUser.getPassword(), "").enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binding.btnLogin.setVisibility(View.VISIBLE);
                    binding.progress.setVisibility(View.GONE);
                    responseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                binding.btnLogin.setVisibility(View.VISIBLE);
                Log.i("UU", "onFailure: " + t.getMessage());
            }
        });
    }
}
