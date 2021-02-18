package com.cgg.twdinspection.inspection.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivitySplashBinding;
import com.cgg.twdinspection.databinding.CustomLayoutForPermissionsBinding;
import com.cgg.twdinspection.inspection.source.version_check.VersionResponse;
import com.cgg.twdinspection.inspection.viewmodel.SplashViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;


public class SplashActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private static final int REQUEST_PERMISSION_CODE = 2000;
    private CustomLayoutForPermissionsBinding customBinding;
    private Context context;
    private String appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SplashActivity.this;
        appVersion = Utils.getVersionName(this);
        SplashViewModel splashViewModel = new SplashViewModel(this, getApplication());


        if (Utils.checkInternetConnection(this)) {
            splashViewModel.getCurrentVersion().observe(this, new Observer<VersionResponse>() {
                @Override
                public void onChanged(VersionResponse versionResponse) {
                    if (versionResponse != null) {
                        if (versionResponse.getStatusCode() != null && versionResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (appVersion != null) {
                                if (versionResponse.getCurrentVersion() != null && versionResponse.getCurrentVersion().equalsIgnoreCase(appVersion)) {
                                    AppConstants.VERSION_DATE = versionResponse.getVersionDate();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                if (versionResponse.getRadius() != null) {
                                                    AppConstants.DISTANCE = Float.parseFloat(versionResponse.getRadius());
                                                } else {
                                                    handleError(SplashActivity.this);
                                                    return;
                                                }
                                                int permissionCheck1 = ContextCompat.checkSelfPermission(
                                                        SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                                                int permissionCheck2 = ContextCompat.checkSelfPermission(
                                                        SplashActivity.this, Manifest.permission.CAMERA);
                                                int permissionCheck3 = ContextCompat.checkSelfPermission(
                                                        SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


                                                if ((permissionCheck1 != PackageManager.PERMISSION_GRANTED)
                                                        && (permissionCheck2 != PackageManager.PERMISSION_GRANTED)
                                                        && (permissionCheck3 != PackageManager.PERMISSION_GRANTED)) {

                                                    customBinding = DataBindingUtil.setContentView(SplashActivity.this,
                                                            R.layout.custom_layout_for_permissions);
                                                    customBinding.accept.setOnClickListener(onBtnClick);
                                                } else {
                                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                                    finish();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 1000);

                                } else if (versionResponse.getStatusMessage() != null) {
                                    Utils.ShowPlayAlert(SplashActivity.this, getResources().getString(R.string.app_name), versionResponse.getStatusMessage());
                                } else {
                                    Utils.ShowPlayAlert(SplashActivity.this, getResources().getString(R.string.app_name), getString(R.string.update_msg));
                                }
                            } else {
                                Toast.makeText(context, getString(R.string.app_ver), Toast.LENGTH_SHORT).show();
                            }
                        } else if (versionResponse.getStatusCode() != null && versionResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            if (!TextUtils.isEmpty(versionResponse.getStatusMessage())) {
                                Utils.customErrorAlert(SplashActivity.this, getResources().getString(R.string.app_name), versionResponse.getStatusMessage());
                            } else {
                                Utils.customSplashErrorAlert(SplashActivity.this, getResources().getString(R.string.app_name), getString(R.string.something));
                            }
                        } else {
                            Utils.customSplashErrorAlert(SplashActivity.this, getResources().getString(R.string.app_name), getString(R.string.server_not));
                        }
                    } else {
                        Utils.customSplashErrorAlert(SplashActivity.this, getResources().getString(R.string.app_name), getString(R.string.server_not));
                    }
                }
            });
        } else {
            Utils.customSplashErrorAlert(SplashActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }

    }

    private void handleError(Activity context) {
        Utils.customSplashErrorAlert(context, getResources().getString(R.string.app_name), getString(R.string.radius_not_found));
    }

    View.OnClickListener onBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                if (view.getId() == R.id.accept) {
                    ActivityCompat.requestPermissions(SplashActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                    , Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION_CODE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull  String permissions[], @NotNull int @NotNull [] grantResults) {
        try {
            if (requestCode == REQUEST_PERMISSION_CODE) {
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[2] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();

                        }
                    }, 1000);
                } else {
                    customAlert();
                }
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
                                        , Manifest.permission.WRITE_EXTERNAL_STORAGE},
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


    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customSplashErrorAlert(SplashActivity.this, getResources().getString(R.string.app_name), errMsg);
    }

}


