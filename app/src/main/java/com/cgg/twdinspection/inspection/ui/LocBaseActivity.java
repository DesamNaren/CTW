package com.cgg.twdinspection.inspection.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class LocBaseActivity extends AppCompatActivity {
    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 0;
    boolean flag = false;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 0;

    private static final int REQUEST_CHECK_SETTINGS = 109;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    public LocationCallback mLocationCallback;
    public Location mCurrentLocation;
    SharedPreferences sharedPreferences;
    InstMainViewModel instMainViewModel;
    // boolean flag to toggle the ui
    public Boolean mRequestingLocationUpdates = false;


    String[] permissions = new String[]{
            ACCESS_FINE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences= TWDApplication.get(this).getPreferences();
        instMainViewModel=new InstMainViewModel(getApplication());
        callPermissions();
    }

    public boolean callPermissions() {

        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            flag = true;
                            mRequestingLocationUpdates = true;
                            init();
                            startLocationUpdates();
                        } else if (report.isAnyPermissionPermanentlyDenied()) {

                            flag = false;
                            Utils.openSettings(LocBaseActivity.this);
                            Toast.makeText(LocBaseActivity.this, getString(R.string.all_per), Toast.LENGTH_SHORT).show();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            flag = false;
                            customPerAlert();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
        return flag;
    }


    public void customPerAlert() {
        try {
            Dialog dialog = new Dialog(this);
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
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        callPermissions();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        finish();
                    }
                });
                if (!dialog.isShowing()) {
                    dialog.show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public void startLocationUpdates() {

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        if (checkPermissions()) {

                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        int statusCode = ((ApiException) e).getStatusCode();

                        switch (statusCode) {

                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;

                                    resolvableApiException.startResolutionForResult(LocBaseActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e1) {
                                    e1.printStackTrace();
                                }
                                break;

                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = getString(R.string.loc_sett);
                                Toast.makeText(LocBaseActivity.this, errorMessage, Toast.LENGTH_LONG).show();

                                break;
                        }

                        updateLocationUI();
                    }
                });
    }

    public void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), getString(R.string.loc_stopped), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    startLocationUpdates();
                    break;

                case Activity.RESULT_CANCELED:
                    mRequestingLocationUpdates = false;
                    customPerAlert();
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }


    public void updateLocationUI() {

        if (mCurrentLocation != null) {
//            Toast.makeText(this, " Lat:" + mCurrentLocation.getLatitude() +","
//                    + "Lng:" + mCurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        int permissionStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int permissionPhoneState = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissionPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        return listPermissionsNeeded.isEmpty();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }
        updateLocationUI();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRequestingLocationUpdates) {
            stopLocationUpdates();
        }
    }
}
