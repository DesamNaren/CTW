package com.cgg.twdinspection.inspection.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivitySplashBinding;
import com.cgg.twdinspection.databinding.CustomLayoutForPermissionsBinding;
import com.cgg.twdinspection.gcc.ui.gcc.GCCSyncActivity;
import com.cgg.twdinspection.inspection.source.version_check.VersionResponse;
import com.cgg.twdinspection.inspection.viewmodel.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 2000;
    private CustomLayoutForPermissionsBinding customBinding;
    private Context context;
    private SplashViewModel splashViewModel;
    private String appVersion;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        context = SplashActivity.this;
        appVersion = Utils.getVersionName(this);
        splashViewModel = new SplashViewModel(this, getApplication());

        if(Utils.checkInternetConnection(this)){
            splashViewModel.getCurrentVersion().observe(this, new Observer<VersionResponse>() {
                @Override
                public void onChanged(VersionResponse versionResponse) {
                    if(versionResponse!=null) {
                        if (versionResponse.getStatusCode() != null && versionResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (appVersion != null) {
                                if (versionResponse.getCurrentVersion() != null && versionResponse.getCurrentVersion().equalsIgnoreCase(appVersion)) {
                                    //place handler logic here with 0 ms
                                }else if (versionResponse.getStatusMessage() != null) {
                                    Utils.ShowPlayAlert(SplashActivity.this, getResources().getString(R.string.app_name), versionResponse.getStatusMessage());
                                }else{
                                    Utils.ShowPlayAlert(SplashActivity.this, getResources().getString(R.string.app_name), getString(R.string.update_msg));
                                }
                            } else {
                                Toast.makeText(context, getString(R.string.app_ver), Toast.LENGTH_SHORT).show();
                            }
                        } else if (versionResponse.getStatusCode() != null && versionResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            if(!TextUtils.isEmpty(versionResponse.getStatusMessage())) {
                                Utils.customErrorAlert(SplashActivity.this, versionResponse.getStatusMessage(), getString(R.string.plz_check_int));
                            }else{
                                Snackbar.make(binding.cl, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(binding.cl, getString(R.string.server_not), Snackbar.LENGTH_SHORT).show();
                        }
                    }else{
                        Snackbar.make(binding.cl, getString(R.string.server_not), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Utils.customErrorAlert(SplashActivity.this,getResources().getString(R.string.app_name),getString(R.string.plz_check_int));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    int permissionCheck1 = ContextCompat.checkSelfPermission(
                            SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                    int permissionCheck2 = ContextCompat.checkSelfPermission(
                            SplashActivity.this, Manifest.permission.CAMERA);
                    int permissionCheck3 = ContextCompat.checkSelfPermission(
                            SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int permissionCheck4 = ContextCompat.checkSelfPermission(
                            SplashActivity.this, Manifest.permission.READ_PHONE_STATE);


                    if ((permissionCheck1 != PackageManager.PERMISSION_GRANTED)
                            && (permissionCheck2 != PackageManager.PERMISSION_GRANTED)
                            && (permissionCheck3 != PackageManager.PERMISSION_GRANTED)
                            && (permissionCheck4 != PackageManager.PERMISSION_GRANTED)) {

                        customBinding = DataBindingUtil.setContentView(SplashActivity.this,
                                R.layout.custom_layout_for_permissions);
                        customBinding.accept.setOnClickListener(onBtnClick);
                        customBinding.declined.setOnClickListener(onBtnClick);
                    }  else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }

    View.OnClickListener onBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                switch (view.getId()) {
                    case R.id.accept:
                        ActivityCompat.requestPermissions(SplashActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                        , Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_PHONE_STATE},
                                REQUEST_PERMISSION_CODE);
                        break;
                    case R.id.declined:

                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();


                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String permissions[], @NotNull int[] grantResults) {
        try {
            switch (requestCode) {
                case REQUEST_PERMISSION_CODE:

                    if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                            && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                            && (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                            && (grantResults[3] == PackageManager.PERMISSION_GRANTED)) {
                        //TODO
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    finish();

                            }
                        }, 2000);
                    } else {
                        customAlert();
                    }
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void customAlert() {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                CustomFontTextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(getString(R.string.plz_grant));
                Button yes = dialog.findViewById(R.id.btDialogYes);
                Button no = dialog.findViewById(R.id.btDialogNo);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(SplashActivity.this,
                                new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                        , Manifest.permission.CAMERA
                                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        , Manifest.permission.READ_PHONE_STATE},
                                REQUEST_PERMISSION_CODE);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


