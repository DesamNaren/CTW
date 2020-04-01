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
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.databinding.CustomLayoutForPermissionsBinding;

import org.jetbrains.annotations.NotNull;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 2000;
    private CustomLayoutForPermissionsBinding customBinding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;

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


