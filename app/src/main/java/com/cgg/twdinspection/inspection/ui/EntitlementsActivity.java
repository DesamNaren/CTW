package com.cgg.twdinspection.inspection.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityEntitlementsBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;
import com.cgg.twdinspection.inspection.viewmodel.EntitilementsCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.EntitlementsViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class EntitlementsActivity extends BaseActivity implements SaveListener {
    ActivityEntitlementsBinding binding;
    EntitlementsViewModel entitlementsViewModel;
    EntitlementsEntity entitlementsEntity;
    String bedSheets, carpets, uniforms, sportsDress, slippers, nightDress,
            sanitary_napkins_supplied, sanitaryNapkins, sanitary_napkins_reason, schoolBags,
            notesSupplied, notes, notes_reason, cosmetics, cosmetics_upto_month, cosmetics_reason, hair_cut_complted, entitlementsUniforms;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    String instId, officerID;
    private int localFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_entitlements, getResources().getString(R.string.title_entitlements));

        entitlementsViewModel = ViewModelProviders.of(EntitlementsActivity.this,
                new EntitilementsCustomViewModel(binding, this, getApplication())).get(EntitlementsViewModel.class);
        binding.setViewModel(entitlementsViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);

        binding.etEntitlementsHaircutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entitlementsHaircutDateSelection();
            }
        });

        binding.rgBedsheets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBedsheets.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_bedsheets)
                    bedSheets = AppConstants.Yes;
                else
                    bedSheets = AppConstants.No;
            }
        });

        binding.rgCarpets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCarpets.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_carpets)
                    carpets = AppConstants.Yes;
                else
                    carpets = AppConstants.No;
            }
        });

        binding.rgUniforms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgUniforms.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_uniforms)
                    uniforms = AppConstants.Yes;
                else
                    uniforms = AppConstants.No;
            }
        });
        binding.rgSportsDress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSportsDress.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_sports_dress)
                    sportsDress = AppConstants.Yes;
                else
                    sportsDress = AppConstants.No;
            }
        });
        binding.rgSlippers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSlippers.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_slippers)
                    slippers = AppConstants.Yes;
                else
                    slippers = AppConstants.No;
            }
        });

        binding.rgNightDress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgNightDress.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_night_dress)
                    nightDress = AppConstants.Yes;
                else
                    nightDress = AppConstants.No;
            }
        });

        binding.rgSchoolBags.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSchoolBags.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_school_bags)
                    schoolBags = AppConstants.Yes;
                else
                    schoolBags = AppConstants.No;
            }
        });

        binding.rgSanitaryNapkins.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSanitaryNapkins.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_sanitary_napkins) {
                    sanitary_napkins_supplied = AppConstants.Yes;

                    binding.llSanitaryNapkins.setVisibility(View.VISIBLE);
                    binding.llSanitary.setVisibility(View.VISIBLE);
                    binding.llSanitaryReason.setVisibility(View.GONE);
                    binding.etSanitaryReason.setText("");
                } else if (selctedItem == R.id.rb_no_sanitary_napkins) {
                    sanitary_napkins_supplied = AppConstants.No;

                    binding.llSanitaryNapkins.setVisibility(View.VISIBLE);
                    binding.llSanitary.setVisibility(View.GONE);
                    binding.llSanitaryReason.setVisibility(View.VISIBLE);
                    binding.rgSanitary.clearCheck();
                } else {
                    binding.llSanitaryNapkins.setVisibility(View.GONE);
                }
            }
        });

        binding.rgSanitary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSanitary.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_sanitary)
                    sanitaryNapkins = AppConstants.Yes;
                else if (selctedItem == R.id.rb_no_sanitary)
                    sanitaryNapkins = AppConstants.No;
                else
                    sanitaryNapkins = null;
            }
        });

        binding.rgNotesSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgNotesSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_notes_supplied) {
                    notesSupplied = AppConstants.Yes;
                    binding.llNotesSupplied.setVisibility(View.VISIBLE);
                    binding.llNotes.setVisibility(View.VISIBLE);
                    binding.llNotesReason.setVisibility(View.GONE);
                    binding.etNotesReason.setText("");
                } else if (selctedItem == R.id.rb_no_notes_supplied) {
                    notesSupplied = AppConstants.No;
                    binding.llNotesSupplied.setVisibility(View.VISIBLE);
                    binding.llNotes.setVisibility(View.GONE);
                    binding.llNotesReason.setVisibility(View.VISIBLE);
                    binding.rgNotes.clearCheck();
                } else {
                    binding.llNotesSupplied.setVisibility(View.GONE);
                }
            }
        });
        binding.rgNotes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgNotes.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_notes)
                    notes = AppConstants.Yes;
                else if (selctedItem == R.id.rb_no_notes)
                    notes = AppConstants.No;
                else
                    notes = null;
            }
        });
        binding.rgCosmetics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCosmetics.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_cosmetics) {
                    cosmetics = AppConstants.Yes;
                    binding.llCosmeticsUptoMonth.setVisibility(View.VISIBLE);
                    binding.llCosmeticsReason.setVisibility(View.GONE);
                    binding.etCosmeticsReason.setText("");
                } else if (selctedItem == R.id.rb_no_cosmetics) {
                    cosmetics = AppConstants.No;
                    binding.llCosmeticsUptoMonth.setVisibility(View.GONE);
                    binding.llCosmeticsReason.setVisibility(View.VISIBLE);
                    binding.etCosmeticsUptoMonth.setText("");

                } else {
                    cosmetics = null;
                }
            }
        });
        binding.etCosmeticsUptoMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelection();
            }
        });

        binding.rgEntitlementsUniforms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEntitlementsUniforms.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_entitlements_uniforms)
                    entitlementsUniforms = AppConstants.Yes;
                else
                    entitlementsUniforms = AppConstants.No;
            }
        });

        binding.rgHaircut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHaircut.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_haircut)
                    hair_cut_complted = AppConstants.Yes;
                else
                    hair_cut_complted = AppConstants.No;
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sanitary_napkins_reason = binding.etSanitaryReason.getText().toString().trim();
                cosmetics_upto_month = binding.etCosmeticsUptoMonth.getText().toString().trim();
                cosmetics_reason = binding.etCosmeticsReason.getText().toString().trim();

                if (validate()) {

                    Utils.customSaveAlert(EntitlementsActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

                }
            }
        });


        try {
            localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                LiveData<EntitlementsEntity> entitlementInfoData = instMainViewModel.getEntitlementInfoData();
                entitlementInfoData.observe(EntitlementsActivity.this, new Observer<EntitlementsEntity>() {
                    @Override
                    public void onChanged(EntitlementsEntity entitlementsEntity) {
                        entitlementInfoData.removeObservers(EntitlementsActivity.this);
                        if (entitlementsEntity != null) {
                            binding.setInspData(entitlementsEntity);
                            binding.executePendingBindings();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String checkUpDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etCosmeticsUptoMonth.setText(checkUpDate);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }


    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(bedSheets)) {
            returnFlag = false;
            showSnackBar("Check weather bedsheets provided to students");
        } else if (TextUtils.isEmpty(carpets)) {
            returnFlag = false;
            showSnackBar("Check weather carpets provided to students");
        } else if (TextUtils.isEmpty(uniforms)) {
            returnFlag = false;
            showSnackBar("Check weather uniforms provided to students");
        } else if (TextUtils.isEmpty(sportsDress)) {
            returnFlag = false;
            showSnackBar("Check weather sportsDress provided to students");
        } else if (TextUtils.isEmpty(slippers)) {
            returnFlag = false;
            showSnackBar("Check weather slippers provided to students");
        } else if (TextUtils.isEmpty(nightDress)) {
            returnFlag = false;
            showSnackBar("Check weather nightDress provided to students");
        } else if (TextUtils.isEmpty(schoolBags)) {
            returnFlag = false;
            showSnackBar("Check weather schoolBags provided to students");
        } else if (TextUtils.isEmpty(sanitary_napkins_supplied)) {
            returnFlag = false;
            showSnackBar("Check weather sanitary napkins provided to students");
        } else if (sanitary_napkins_supplied.equalsIgnoreCase("Yes") && TextUtils.isEmpty(sanitaryNapkins)) {
            returnFlag = false;
            showSnackBar("Check weather sanitary napkins are good or bad");
        } else if (sanitary_napkins_supplied.equalsIgnoreCase("No") && TextUtils.isEmpty(sanitary_napkins_reason)) {
            returnFlag = false;
            binding.etSanitaryReason.requestFocus();
            showSnackBar("Enter reason");
        } else if (TextUtils.isEmpty(notesSupplied)) {
            returnFlag = false;
            showSnackBar("Check weather notes Supplied to students");
        } else if (notesSupplied.equalsIgnoreCase("Yes") && TextUtils.isEmpty(notes)) {
            returnFlag = false;
            showSnackBar("Check weather notes are good or bad");
        } else if (notesSupplied.equalsIgnoreCase("No") && TextUtils.isEmpty(notes_reason)) {
            returnFlag = false;
            binding.etNotesReason.requestFocus();
            showSnackBar("Enter reason");
        } else if (TextUtils.isEmpty(cosmetics)) {
            returnFlag = false;
            showSnackBar("Check weather cosmetics distributed");
        } else if (cosmetics.equalsIgnoreCase("Yes") && TextUtils.isEmpty(cosmetics_upto_month)) {
            returnFlag = false;
            showSnackBar("Check weather cosmetics distributed upto which month");
        } else if (cosmetics.equalsIgnoreCase("No") && TextUtils.isEmpty(cosmetics_reason)) {
            returnFlag = false;
            binding.etCosmeticsReason.requestFocus();
            showSnackBar("Enter Reason");
        } else if (TextUtils.isEmpty(binding.etPairOfDressDistributed.getText().toString())) {
            returnFlag = false;
            showSnackBar("Enter no of pair of dress distributed to each student");
        } else if (TextUtils.isEmpty(entitlementsUniforms)) {
            returnFlag = false;
            showSnackBar("Check weather uniforms provided are in good quality");
        } else if (TextUtils.isEmpty(hair_cut_complted)) {
            returnFlag = false;
            showSnackBar("Check weather haircut is completed");
        } else if ((binding.etEntitlementsHaircutDate.getText().toString().equals(getResources().getString(R.string.select_date)))) {
            returnFlag = false;
            showSnackBar("Select date of last haircut");
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    private void entitlementsHaircutDateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        binding.etEntitlementsHaircutDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    public void submitData() {
        String pair_of_dress_distributed = binding.etPairOfDressDistributed.getText().toString().trim();
        String haircutDate = binding.etEntitlementsHaircutDate.getText().toString().trim();

        entitlementsEntity = new EntitlementsEntity();
        entitlementsEntity.setOfficer_id(officerID);
        entitlementsEntity.setInstitute_id(instId);
        entitlementsEntity.setInspection_time(Utils.getCurrentDateTime());
        entitlementsEntity.setBedSheets(bedSheets);
        entitlementsEntity.setCarpets(carpets);
        entitlementsEntity.setUniforms(uniforms);
        entitlementsEntity.setSportsDress(sportsDress);
        entitlementsEntity.setSlippers(slippers);
        entitlementsEntity.setNightDress(nightDress);
        entitlementsEntity.setSchoolBags(schoolBags);
        entitlementsEntity.setSanitary_napkins_supplied(sanitary_napkins_supplied);
        entitlementsEntity.setSanitaryNapkins(sanitaryNapkins);
        entitlementsEntity.setSanitary_napkins_reason(sanitary_napkins_reason);
        entitlementsEntity.setNotesSupplied(notesSupplied);
        entitlementsEntity.setNotes(notes);
        entitlementsEntity.setNotes_reason(notes_reason);
        entitlementsEntity.setPairOfDressDistributedCount(pair_of_dress_distributed);
        entitlementsEntity.setUniforms_provided_quality(entitlementsUniforms);
        entitlementsEntity.setHair_cut_complted(hair_cut_complted);
        entitlementsEntity.setLast_haircut_date(haircutDate);
        entitlementsEntity.setCosmetic_distributed(cosmetics);
        entitlementsEntity.setCosmetic_distributed_upto_month(cosmetics_upto_month);
        entitlementsEntity.setCosmetic_reason(cosmetics_reason);

        long x = entitlementsViewModel.insertEntitlementsInfo(entitlementsEntity);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("Entitlements");
                liveData.observe(EntitlementsActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        liveData.removeObservers(EntitlementsActivity.this);
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instId);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {
                Utils.customSectionSaveAlert(EntitlementsActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    @Override
    public void onBackPressed() {
        super.callBack();
    }
}