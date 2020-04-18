package com.cgg.twdinspection.inspection.viewmodel;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityLoginCreBinding;
import com.cgg.twdinspection.inspection.source.login.LoginResponse;
import com.cgg.twdinspection.inspection.source.login.LoginUser;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.inspection.ui.SplashActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResponse> responseMutableLiveData;
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private Context context;
    private ActivityLoginCreBinding binding;
    private ErrorHandlerInterface errorHandlerInterface;
    CustomProgressDialog customProgressDialog;
    LoginViewModel(ActivityLoginCreBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        customProgressDialog = new CustomProgressDialog(context);
//        username.setValue("maadhavisriram");
//        password.setValue("guest");

        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<LoginResponse> geListLiveData() {
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

        if (Utils.checkInternetConnection(context)) {
            callLoginAPI(loginUser);
        }else{
            Utils.customErrorAlert(context,context.getResources().getString(R.string.app_name),context.getString(R.string.plz_check_int));
        }
    }

    public void onViewPwd() {
        if (binding.etPwd.getInputType() == InputType.TYPE_CLASS_TEXT) {
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
            customProgressDialog.show();
            TWDService twdService = TWDService.Factory.create("school");
            twdService.getLoginResponse(loginUser.getEmail(), loginUser.getPassword(), Utils.getDeviceID(context), Utils.getVersionName(context))
                    .enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                    customProgressDialog.dismiss();
                    binding.btnLogin.setVisibility(View.VISIBLE);
                    responseMutableLiveData.setValue(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                    customProgressDialog.dismiss();
                    binding.btnLogin.setVisibility(View.VISIBLE);
                    errorHandlerInterface.handleError(t, context);
                }
            });
    }
}
