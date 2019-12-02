package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityEntitlementsBinding;

import java.util.Calendar;

public class EntitlementsActivity extends AppCompatActivity {
    ActivityEntitlementsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_entitlements);
        binding.etEntitlementsHaircutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entitlementsHaircutDateSelection();
            }
        });
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
        datePickerDialog.show();
    }

}
