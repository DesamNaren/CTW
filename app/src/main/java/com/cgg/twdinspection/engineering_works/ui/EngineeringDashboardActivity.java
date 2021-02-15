package com.cgg.twdinspection.engineering_works.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityEngDashboardBinding;
import com.cgg.twdinspection.engineering_works.adapters.EngWorksAdapter;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;
import com.cgg.twdinspection.engineering_works.viewmodels.EngDashboardCustomViewModel;
import com.cgg.twdinspection.engineering_works.viewmodels.EngDashboardViewModel;
import com.cgg.twdinspection.engineering_works.viewmodels.InspDetailsViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class EngineeringDashboardActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private ActivityEngDashboardBinding binding;
    private SharedPreferences.Editor editor;
    private EngDashboardViewModel viewModel;
    private String selDistId, selDistName, selMandId, selMandalName;
    private Menu mMenu = null;
    private EngWorksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eng_dashboard);

        viewModel = ViewModelProviders.of(this,
                new EngDashboardCustomViewModel(this, getApplication())).get(EngDashboardViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        CustomProgressDialog customProgressDialog = new CustomProgressDialog(this);

        try {
            if (getSupportActionBar() != null) {
                TextView tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getString(R.string.engineering_works));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(tv);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_btn_rounded));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> selectList = new ArrayList<>();
        selectList.add("Select");
        ArrayAdapter<String> selectAdapter = new ArrayAdapter<String>(EngineeringDashboardActivity.this, R.layout.support_simple_spinner_dropdown_item, selectList);
        binding.spMandal.setAdapter(selectAdapter);

        try {
            SharedPreferences sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewModel.getDistricts().observe(EngineeringDashboardActivity.this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (strings != null && strings.size() > 0) {
                    strings.add(0, "Select");
                    for (int i = 0; i < strings.size(); i++) {
                        if (TextUtils.isEmpty(strings.get(i))) {
                            strings.remove(i);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EngineeringDashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, strings);
                    binding.spDist.setAdapter(adapter);
                } else {
                    Utils.customEngSyncAlert(EngineeringDashboardActivity.this, getString(R.string.app_name), "No districts found...\n Do you want to sync Works master data to proceed further?");
                }
            }
        });
        InspDetailsViewModel inspDetailsViewModel = new InspDetailsViewModel(EngineeringDashboardActivity.this, getApplication());
        LiveData<List<GrantScheme>> grantListLiveData = inspDetailsViewModel.getGrantSchemes();
        grantListLiveData.observe(EngineeringDashboardActivity.this, new Observer<List<GrantScheme>>() {
            @Override
            public void onChanged(List<GrantScheme> grantSchemes) {
                grantListLiveData.removeObservers(EngineeringDashboardActivity.this);
                if (!(grantSchemes != null && grantSchemes.size() > 0)) {
                    Utils.customEngSyncAlert(EngineeringDashboardActivity.this, getString(R.string.app_name), "No schemes found...\n Do you want to sync Scheme master data to proceed further?");
                }
            }
        });

        LiveData<List<SectorsEntity>> liveData = inspDetailsViewModel.getSectors();
        liveData.observe(EngineeringDashboardActivity.this, new Observer<List<SectorsEntity>>() {
            @Override
            public void onChanged(List<SectorsEntity> sectorsEntities) {
                liveData.removeObservers(EngineeringDashboardActivity.this);
                if (!(sectorsEntities != null && sectorsEntities.size() > 0)) {
                    Utils.customEngSyncAlert(EngineeringDashboardActivity.this, getString(R.string.app_name), "No sectors found...\n Do you want to sync Sector master data to proceed further?");
                }
            }
        });
        binding.spDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!binding.spDist.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    selDistName = binding.spDist.getSelectedItem().toString();
                    viewModel.getDistId(selDistName).observe(EngineeringDashboardActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String integer) {
                            if (!TextUtils.isEmpty(integer)) {
                                selDistId = integer;
                                viewModel.getMandals(selDistId).observe(EngineeringDashboardActivity.this, new Observer<List<String>>() {
                                    @Override
                                    public void onChanged(List<String> strings) {
                                        if (strings != null && strings.size() > 0) {
                                            strings.add(0, "Select");
                                            for (int i = 0; i < strings.size(); i++) {
                                                if (TextUtils.isEmpty(strings.get(i))) {
                                                    strings.remove(i);
                                                }
                                            }
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EngineeringDashboardActivity.this, R.layout.support_simple_spinner_dropdown_item, strings);
                                            binding.spMandal.setAdapter(adapter);
                                        }
                                    }
                                });
                            } else {
                                callSnackBar("no district ID found");
                                mMenu.findItem(R.id.action_search).setVisible(false);
                                selDistId = "";
                                selMandId = "";
                                selMandalName = "";
                                binding.spMandal.setAdapter(selectAdapter);
                            }
                        }
                    });

                } else {
                    selDistName = "";
                    selDistId = "";
                    binding.spMandal.setAdapter(selectAdapter);
                    mMenu.findItem(R.id.action_search).setVisible(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spMandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!binding.spMandal.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    selMandalName = binding.spMandal.getSelectedItem().toString();
                    viewModel.getMandalId(selMandalName).observe(EngineeringDashboardActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String integer) {
                            selMandId = integer;
                            if (TextUtils.isEmpty(selDistId)) {
                                callSnackBar("No district Id found");
                                binding.recyclerView.setVisibility(View.GONE);
                                mMenu.findItem(R.id.action_search).setVisible(false);
                            } else if (TextUtils.isEmpty(selMandId)) {
                                callSnackBar("No mandal Id found");
                                binding.recyclerView.setVisibility(View.GONE);
                                mMenu.findItem(R.id.action_search).setVisible(false);
                            } else {
                                viewModel.getSelWorkDetails(selDistId, selMandId).observe(EngineeringDashboardActivity.this, new Observer<List<WorkDetail>>() {
                                    @Override
                                    public void onChanged(List<WorkDetail> workDetail) {

                                        if (workDetail != null && workDetail.size() > 0) {
                                            binding.recyclerView.setVisibility(View.VISIBLE);
                                            binding.tvEmpty.setVisibility(View.GONE);
                                            adapter = new EngWorksAdapter(EngineeringDashboardActivity.this, workDetail);
                                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(EngineeringDashboardActivity.this));
                                            binding.recyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                            if (mMenu != null)
                                                mMenu.findItem(R.id.action_search).setVisible(true);

                                        } else {
                                            binding.recyclerView.setVisibility(View.GONE);
                                            binding.tvEmpty.setVisibility(View.VISIBLE);
                                            callSnackBar(getString(R.string.no_sanctioned_works_found));
                                            if (mMenu != null)
                                                mMenu.findItem(R.id.action_search).setVisible(false);
                                        }
                                    }
                                });
                            }
                        }
                    });

                } else {
                    selMandalName = "";
                    selMandId = "";
                    binding.recyclerView.setVisibility(View.GONE);
                    mMenu.findItem(R.id.action_search).setVisible(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(EngineeringDashboardActivity.this, binding.btnProceed);
                if (TextUtils.isEmpty(binding.etEngId.getText().toString())) {
                    callSnackBar("Please enter engineering work ID");
                } else {
                    editor.putString(AppConstants.ENGWORKSMASTER, "");
                    editor.commit();
                    editor.putString(AppConstants.EngSubmitRequest, "");
                    editor.commit();
                }
            }
        });
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void handleError(Throwable e, Context context) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;
        mMenu.findItem(R.id.mi_filter).setVisible(false);
        mMenu.findItem(R.id.mi_sync).setVisible(true);
        MenuItem mSearch = mMenu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_by_workId) + "</font>"));
        mSearchView.setInputType(InputType.TYPE_CLASS_TEXT);
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = mSearchView.findViewById(id);
        textView.setTextColor(Color.WHITE);
        mSearchView.setGravity(Gravity.CENTER);
        mMenu.findItem(R.id.action_search).setVisible(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.mi_sync) {
            startActivity(new Intent(EngineeringDashboardActivity.this, EngSyncActivity.class));
        }
        return true;
    }
}
