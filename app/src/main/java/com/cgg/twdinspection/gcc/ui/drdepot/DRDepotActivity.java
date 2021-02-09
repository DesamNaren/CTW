package com.cgg.twdinspection.gcc.ui.drdepot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
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
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.interfaces.GCCSubmitInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
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
import com.cgg.twdinspection.gcc.ui.gcc.GCCDashboardActivity;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoCustomViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.inspection.viewmodel.StockViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class DRDepotActivity extends LocBaseActivity implements GCCSubmitInterface, ErrorHandlerInterface, GCCOfflineInterface {
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
    File mediaStorageDir;
    private GCCSubmitRequest request;
    private GCCOfflineViewModel gccOfflineViewModel;
    private boolean flag;
    private GCCOfflineRepository gccOfflineRepository;

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
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        gccOfflineRepository = new GCCOfflineRepository(getApplication());

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

        LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(
                drDepots.getDivisionId(), drDepots.getSocietyId(), drDepots.getGodownId());

        drGodownLiveData.observe(DRDepotActivity.this, new Observer<GccOfflineEntity>() {
            @Override
            public void onChanged(GccOfflineEntity GCCOfflineEntity) {
                if (GCCOfflineEntity != null) {
                    flag = true;
                    binding.bottomLl.btnNext.setText(getString(R.string.save));
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.offline_mode));
                } else {
                    flag = false;
                    binding.bottomLl.btnNext.setText(getString(R.string.submit));
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.online_mode));
                }
            }
        });

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopAvail.equals(AppConstants.open)) {
                    boolean existFlag = false;
                    stockDetailsResponsemain.setEssential_commodities(EssentialFragment.commonCommodities);
                    stockDetailsResponsemain.setDialy_requirements(DailyFragment.commonCommodities);

                    stockDetailsResponsemain.setEmpties(EmptiesFragment.commonCommodities);
                    if (EssentialFragment.commonCommodities != null && EssentialFragment.commonCommodities.size() > 0) {

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

                        for (int z = 0; z < stockDetailsResponsemain.getEmpties().size(); z++) {
                            if (!TextUtils.isEmpty(stockDetailsResponsemain.getEmpties().get(z).getPhyQuant())) {
                                existFlag = true;
//                                String header = stockDetailsResponsemain.getEmpties().get(0).getComHeader();
//                                setFragPos(header, z);
                                break;
                            }
                        }
                    }

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
                        request = new GCCSubmitRequest();
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

                        if (flag) {
                            callPhotoSubmit();
                        } else {
                            if (Utils.checkInternetConnection(DRDepotActivity.this)) {
                                customProgressDialog.show();
                                gccPhotoViewModel.submitGCCDetails(request);
                            } else {
                                Utils.customWarningAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                            }
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

                                if (ess_flag || dailyreq_flag || emp_flag) {
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
                    if (stockDetailsResponsemain != null && stockDetailsResponsemain.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)
                            && (ess_flag || dailyreq_flag || emp_flag)) {
                        binding.viewPager.setVisibility(View.VISIBLE);
                        binding.tabs.setVisibility(View.VISIBLE);
                        binding.noDataTv.setVisibility(View.GONE);
                        binding.bottomLl.btnNext.setText(getString(R.string.next));
                    } else {
                        binding.tabs.setVisibility(View.GONE);
                        binding.viewPager.setVisibility(View.GONE);
                        binding.noDataTv.setVisibility(View.VISIBLE);
                        binding.bottomLl.btnLayout.setVisibility(View.GONE);
//                        binding.noDataTv.setText(stockDetailsResponsemain.getStatusMessage());
                    }

                } else if (radioGroup.getCheckedRadioButtonId() == R.id.shop_avail_rb_close) {
                    binding.viewPager.setVisibility(View.GONE);
                    binding.tabs.setVisibility(View.GONE);
                    binding.ivShopCam.setVisibility(View.VISIBLE);
                    binding.tvClose.setVisibility(View.VISIBLE);
                    shopAvail = AppConstants.close;
                    if (flag)
                        binding.bottomLl.btnNext.setText(getString(R.string.save));
                    else
                        binding.bottomLl.btnNext.setText(getString(R.string.submit));

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

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        List<MultipartBody.Part> partList = new ArrayList<>();
        List<File> photoList = new ArrayList<>();
        partList.add(body);
        photoList.add(file);

        if (flag) {
            String data = new Gson().toJson(request);
            String photos = new Gson().toJson(photoList);
            GccOfflineEntity gccOfflineEntity = new GccOfflineEntity();
            gccOfflineEntity.setDivisionId(drDepots.getDivisionId());
            gccOfflineEntity.setDivisionName(drDepots.getDivisionName());
            gccOfflineEntity.setSocietyId(drDepots.getSocietyId());
            gccOfflineEntity.setSocietyName(drDepots.getSocietyName());
            gccOfflineEntity.setDrgownId(drDepots.getGodownId());
            gccOfflineEntity.setDrgownName(drDepots.getGodownName());
            gccOfflineEntity.setTime(Utils.getCurrentDateTimeDisplay());

            gccOfflineEntity.setData(data);
            gccOfflineEntity.setPhotos(photos);
            gccOfflineEntity.setType(AppConstants.OFFLINE_DR_DEPOT);
            gccOfflineEntity.setFlag(true);

            gccOfflineRepository.insertGCCRecord(DRDepotActivity.this, gccOfflineEntity);
        } else {
            if (Utils.checkInternetConnection(DRDepotActivity.this)) {
                customProgressDialog.show();
                gccPhotoViewModel.UploadImageServiceCall(partList);
            } else {
                Utils.customWarningAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), "Please check internet");
            }
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
        if (stockDetailsResponsemain != null && stockDetailsResponsemain.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)
                && (ess_flag || dailyreq_flag || emp_flag)) {
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
        if (mediaStorageDir.isDirectory()) {
            String[] children = mediaStorageDir.list();
            for (int i = 0; i < children.length; i++)
                new File(mediaStorageDir, children[i]).delete();
            mediaStorageDir.delete();
        }
        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        callSnackBar(errMsg);
    }

    @Override
    public void gccRecCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, GCCDashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCount(int cnt) {
    }

    @Override
    public void deletedrGoDownCountSubmitted(int cnt, String msg) {

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

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        FilePath = getExternalFilesDir(null)
                + "/" + IMAGE_DIRECTORY_NAME;

        String Image_name = PIC_NAME;
        FilePath = FilePath + "/" + Image_name;

//        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return FilePath;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_TYPE + ".png";
                FilePath = FilePath + "/" + Image_name;

                FilePath = compressImage(FilePath);

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
        mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
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
                    + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }
}
