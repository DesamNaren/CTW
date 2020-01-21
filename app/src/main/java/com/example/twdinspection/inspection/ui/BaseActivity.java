package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActionBarLayoutBinding;

public class BaseActivity extends AppCompatActivity {
    private ActionBarLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.action_bar_layout);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack();
            }
        });
        binding.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customHomeAlert(BaseActivity.this, getString(R.string.app_name),getString(R.string.go_home));
            }
        });
    }

    protected <T extends ViewDataBinding> T putContentView(@LayoutRes int resId, String title) {
        binding.headerTitle.setText(title);
        return DataBindingUtil.inflate(getLayoutInflater(), resId, binding.appbar, true);
    }

    public void callBack(){
        Utils.customHomeAlert(BaseActivity.this, getString(R.string.app_name),getString(R.string.go_back));
    }
}
