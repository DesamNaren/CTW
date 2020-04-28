package com.cgg.twdinspection.gcc.ui.drdepot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityDrDepotBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCSubmitInterface;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.ui.fragment.DailyFragment;
import com.cgg.twdinspection.gcc.ui.fragment.EmptiesFragment;
import com.cgg.twdinspection.gcc.ui.fragment.EssentialFragment;
import com.cgg.twdinspection.gcc.ui.fragment.MFPFragment;
import com.cgg.twdinspection.gcc.ui.fragment.PUnitFragment;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoCustomViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoViewModel;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.inspection.viewmodel.StockViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class DRDepotActivity extends LocBaseActivity implements GCCSubmitInterface, ErrorHandlerInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StockViewModel viewModel;
    ActivityDrDepotBinding binding;
    CustomProgressDialog customProgressDialog;
    private StockDetailsResponse stockDetailsResponsemain;
    private DRDepots drDepots;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private String shopAvail, divId, suppId;
    String PIC_NAME, PIC_TYPE, officerID;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    String FilePath, checkUpDate;
    Bitmap bm;
    File file;
    StockDetailsResponse stockDetailsResponse;
    private int shopFlag = 0;
    GCCPhotoViewModel gccPhotoViewModel;
    private String randomNum;
    private boolean punit_flag, dailyreq_flag, emp_flag, ess_flag, mfp_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_depot);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_dr_depot));

        randomNum = Utils.getRandomNumberString();
        stockDetailsResponsemain = null;
        EssentialFragment.commonCommodities = null;
        DailyFragment.commonCommodities = null;
        EmptiesFragment.commonCommodities = null;
        MFPFragment.commonCommodities = null;
        PUnitFragment.commonCommodities = null;

        gccPhotoViewModel = ViewModelProviders.of(this,
                new GCCPhotoCustomViewModel(this)).get(GCCPhotoViewModel.class);

        customProgressDialog = new CustomProgressDialog(this);
        viewModel = new StockViewModel(getApplication(), this);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        binding.header.ivHome.setVisibility(View.GONE);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.includeBasicLayout.divLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.socLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.dr_depo_name));
        binding.includeBasicLayout.drGodownLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.salesLL.setVisibility(View.VISIBLE);


        try {

            sharedPreferences = TWDApplication.get(this).getPreferences();
            Gson gson = new Gson();
            String stockData = sharedPreferences.getString(AppConstants.stockData, "");
            stockDetailsResponse = gson.fromJson(stockData, StockDetailsResponse.class);
            String depotData = sharedPreferences.getString(AppConstants.DR_DEPOT_DATA, "");
            DRDepots drDepot = gson.fromJson(depotData, DRDepots.class);
            divId = drDepot.getDivisionId();
            suppId = drDepot.getGodownId();
            String str = sharedPreferences.getString(AppConstants.DR_DEPOT_DATA, "");
            drDepots = gson.fromJson(str, DRDepots.class);
            if (drDepots != null) {
                binding.includeBasicLayout.divName.setText(drDepots.getDivisionName());
                binding.includeBasicLayout.socName.setText(drDepots.getSocietyName());
                binding.includeBasicLayout.drGodownName.setText(drDepots.getGodownName());
                binding.includeBasicLayout.salesManTV.setText(drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge() + drDepots.getIncharge());
                binding.includeBasicLayout.dateTv.setText(Utils.getCurrentDateTimeDisplay());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopAvail.equals(AppConstants.open)) {
                    boolean existFlag = false;

                    if (EssentialFragment.commonCommodities != null && EssentialFragment.commonCommodities.size() > 0) {
                        stockDetailsResponsemain.setEssential_commodities(EssentialFragment.commonCommodities);
                        for (int z = 0; z < stockDetailsResponsemain.getEssential_commodities().size(); z++) {
                            if (!TextUtils.isEmpty(stockDetailsResponsemain.getEssential_commodities().get(z).getPhyQuant())) {
                                existFlag = true;
//                                String header = stockDetailsResponsemain.getEssential_commodities().get(0).getComHeader();
//                                setFragPos(header, z);
                                break;
                            }
                        }
                    }
                    if (!existFlag && DailyFragment.commonCommodities != null && DailyFragment.commonCommodities.size() > 0) {
                        stockDetailsResponsemain.setDialy_requirements(DailyFragment.commonCommodities);
                        for (int z = 0; z < stockDetailsResponsemain.getDialy_requirements().size(); z++) {
                            if (!TextUtils.isEmpty(stockDetailsResponsemain.getDialy_requirements().get(z).getPhyQuant())) {
                                existFlag = true;
//                                String header = stockDetailsResponsemain.getDialy_requirements().get(0).getComHeader();
//                                setFragPos(header, z);
                                break;
                            }
                        }
                    }

                    if (!existFlag && EmptiesFragment.commonCommodities != null && EmptiesFragment.commonCommodities.size() > 0) {
                        stockDetailsResponsemain.setEmpties(EmptiesFragment.commonCommodities);
                        for (int z = 0; z < stockDetailsResponsemain.getEmpties().size(); z++) {
                            if (!TextUtils.isEmpty(stockDetailsResponsemain.getEmpties().get(z).getPhyQuant())) {
                                existFlag = true;
//                                String header = stockDetailsResponsemain.getEmpties().get(0).getComHeader();
//                                setFragPos(header, z);
                                break;
                            }
                        }
                    }


//                    if (MFPFragment.commonCommodities != null && MFPFragment.commonCommodities.size() > 0) {
//                        stockDetailsResponsemain.setMfp_commodities(MFPFragment.commonCommodities);
//                        for (int z = 0; z < stockDetailsResponsemain.getMfp_commodities().size(); z++) {
//                            if (TextUtils.isEmpty(stockDetailsResponsemain.getMfp_commodities().get(z).getPhyQuant())) {
//                                String header = stockDetailsResponsemain.getMfp_commodities().get(0).getComHeader();
//                                setFragPos(header, z);
//                                return;
//                            }
//                        }
//                    }
//
//                    if (PUnitFragment.commonCommodities != null && PUnitFragment.commonCommodities.size() > 0) {
//                        stockDetailsResponsemain.setProcessing_units(PUnitFragment.commonCommodities);
//                        for (int z = 0; z < stockDetailsResponsemain.getProcessing_units().size(); z++) {
//                            if (TextUtils.isEmpty(stockDetailsResponsemain.getProcessing_units().get(z).getPhyQuant())) {
//                                String header = stockDetailsResponsemain.getProcessing_units().get(0).getComHeader();
//                                setFragPos(header, z);
//                                return;
//                            }
//                        }
//                    }

                    if (existFlag) {
                        Gson gson = new Gson();
                        String stockData = gson.toJson(stockDetailsResponsemain);
                        try {
                            editor = TWDApplication.get(DRDepotActivity.this).getPreferences().edit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        editor.putString(AppConstants.stockData, stockData);
                        editor.commit();
                        Intent intent = new Intent(DRDepotActivity.this, DRDepotFindingsActivity.class);
                        startActivity(intent);
                    } else {
                        Utils.customErrorAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), getString(R.string.one_record));
                    }
                } else {
                    if (shopFlag == 0) {
                        showSnackBar("Please capture shop image");
                    } else {
                        if (Utils.checkInternetConnection(DRDepotActivity.this)) {
                            GCCSubmitRequest request = new GCCSubmitRequest();
                            request.setOfficerId(officerID);
                            request.setDivisionId(divId);
                            request.setDivisionName(drDepots.getDivisionName());
                            request.setSocietyId(drDepots.getSocietyId());
                            request.setSocietyName(drDepots.getSocietyName());
                            request.setInchargeName(drDepots.getIncharge());
                            request.setSupplierType(getString(R.string.dr_depot_req));
                            request.setInspectionTime(Utils.getCurrentDateTime());
                            request.setGodown_name(drDepots.getGodownName());
                            request.setGodownId(drDepots.getGodownId());
                            request.setShop_avail(shopAvail);
                            request.setDeviceId(Utils.getDeviceID(DRDepotActivity.this));
                            request.setVersionNo(Utils.getVersionName(DRDepotActivity.this));
                            request.setPhoto_key_id(randomNum);
                            gccPhotoViewModel.submitGCCDetails(request);
                        } else {
                            Utils.customWarningAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                        }
                    }
                }
            }

        });

        if (Utils.checkInternetConnection(DRDepotActivity.this)) {
            if (drDepots != null && drDepots.getGodownId() != null) {
                customProgressDialog.show();
                LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData(drDepots.getGodownId());
                officesResponseLiveData.observe(DRDepotActivity.this, new Observer<StockDetailsResponse>() {
                    @Override
                    public void onChanged(StockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(DRDepotActivity.this);
                        stockDetailsResponsemain = stockDetailsResponse;

                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                                if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                                    ess_flag = true;
                                    stockDetailsResponse.getEssential_commodities().get(0).setComHeader("Essential Commodities");
                                    EssentialFragment essentialFragment = new EssentialFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getEssential_commodities());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.essComm, essentialComm);
                                    essentialFragment.setArguments(bundle);
                                    adapter.addFrag(essentialFragment, "Essential Commodities");
                                }

                                if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
                                    dailyreq_flag = true;
                                    stockDetailsResponse.getDialy_requirements().get(0).setComHeader("Daily Requirements");
                                    DailyFragment dailyFragment = new DailyFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getDialy_requirements());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.dailyReq, essentialComm);
                                    dailyFragment.setArguments(bundle);
                                    adapter.addFrag(dailyFragment, "Daily Requirements");
                                }

                                if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                                    emp_flag = true;
                                    stockDetailsResponse.getEmpties().get(0).setComHeader("Empties");
                                    EmptiesFragment emptiesFragment = new EmptiesFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getEmpties());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.empties, essentialComm);
                                    emptiesFragment.setArguments(bundle);
                                    adapter.addFrag(emptiesFragment, "Empties");
                                }

                                    if(ess_flag || dailyreq_flag || emp_flag) {
                                        binding.tabs.setupWithViewPager(binding.viewPager);
                                        binding.viewPager.setAdapter(adapter);
                                    }


                            } else if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {

                                callSnackBar(stockDetailsResponse.getStatusMessage());
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        } else {
                            callSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                Utils.customWarningAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), getString(R.string.something));
            }
        } else {
            Utils.customWarningAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }

        binding.rgShopAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                binding.bottomLl.btnLayout.setVisibility(View.VISIBLE);

                if (radioGroup.getCheckedRadioButtonId() == R.id.shop_avail_rb_open) {
                    binding.ivShopCam.setVisibility(View.GONE);
                    binding.tvClose.setVisibility(View.GONE);
                    shopAvail = AppConstants.open;
                    binding.noDataTv.setVisibility(View.GONE);
                    if (stockDetailsResponsemain!=null && stockDetailsResponsemain.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)
                     && (ess_flag||dailyreq_flag||emp_flag)) {
                        binding.viewPager.setVisibility(View.VISIBLE);
                        binding.tabs.setVisibility(View.VISIBLE);
                        binding.noDataTv.setVisibility(View.GONE);
                        binding.bottomLl.btnNext.setText("Next");
                    } else {
                        binding.tabs.setVisibility(View.GONE);
                        binding.viewPager.setVisibility(View.GONE);
                        binding.noDataTv.setVisibility(View.VISIBLE);
                        binding.bottomLl.btnLayout.setVisibility(View.GONE);
                        binding.noDataTv.setText(stockDetailsResponsemain.getStatusMessage());
                    }

                } else if (radioGroup.getCheckedRadioButtonId() == R.id.shop_avail_rb_close) {
                    binding.viewPager.setVisibility(View.GONE);
                    binding.tabs.setVisibility(View.GONE);
                    binding.ivShopCam.setVisibility(View.VISIBLE);
                    binding.tvClose.setVisibility(View.VISIBLE);
                    shopAvail = AppConstants.close;
                    binding.bottomLl.btnNext.setText("Submit");
                }
            }
        });
        binding.ivShopCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.SHOP_CLOSED;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });


    }

    private void callPhotoSubmit() {
        if (Utils.checkInternetConnection(DRDepotActivity.this)) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            customProgressDialog.show();


            List<MultipartBody.Part> partList = new ArrayList<>();
            partList.add(body);
            gccPhotoViewModel.UploadImageServiceCall(partList);
        } else {
            Utils.customWarningAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    void setFragPos(String header, int pos) {
        for (int x = 0; x < mFragmentTitleList.size(); x++) {
            if (header.equalsIgnoreCase(mFragmentTitleList.get(x))) {
                callSnackBar("Submit all records in " + header);
                binding.viewPager.setCurrentItem(x);
                if (header.contains("Essential Commodities")) {
                    ((EssentialFragment) mFragmentList.get(x)).setPos(pos);
                }
                if (header.equalsIgnoreCase("Daily Requirements")) {
                    ((DailyFragment) mFragmentList.get(x)).setPos(pos);
                }
                if (header.equalsIgnoreCase("Empties")) {
                    ((EmptiesFragment) mFragmentList.get(x)).setPos(pos);
                }
                if (header.equalsIgnoreCase("MFP Commodities")) {
                    ((MFPFragment) mFragmentList.get(x)).setPos(pos);
                }
                if (header.equalsIgnoreCase("Processing Units")) {
                    ((PUnitFragment) mFragmentList.get(x)).setPos(pos);
                }
                break;
            }
        }
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        if (stockDetailsResponsemain!=null && stockDetailsResponsemain.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)
        && (ess_flag|| dailyreq_flag|| emp_flag)) {
            Utils.customDiscardAlert(this,
                    getResources().getString(R.string.app_name),
                    getString(R.string.are_go_back));
        } else {
            finish();
        }
    }

    @Override
    public void getData(GCCSubmitResponse gccSubmitResponse) {
        customProgressDialog.hide();
        if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            callPhotoSubmit();
        } else if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            showSnackBar(gccSubmitResponse.getStatusMessage());
        } else {
            showSnackBar(getString(R.string.something));
        }
    }

    @Override
    public void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse) {
        customProgressDialog.hide();
        if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
            CallSuccessAlert(gccPhotoSubmitResponse.getStatusMessage());
        } else if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
            showSnackBar(gccPhotoSubmitResponse.getStatusMessage());
        } else {
            showSnackBar(getString(R.string.something));
        }
    }

    private void CallSuccessAlert(String msg) {
        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        callSnackBar(errMsg);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {


        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_NAME;
                FilePath = FilePath + "/" + Image_name;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                file = new File(FilePath);
                Glide.with(DRDepotActivity.this).load(file).into(binding.ivShopCam);
                shopFlag = 1;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    DRDepotActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            PIC_NAME = PIC_TYPE + "~" + officerID + "~" + divId + "~" + suppId + "~" + Utils.getCurrentDateTimeFormat() + "~" +
                    Utils.getDeviceID(DRDepotActivity.this) + "~" + Utils.getVersionName(DRDepotActivity.this) + "~" + randomNum + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME);
        } else {
            return null;
        }

        return mediaFile;
    }
}
