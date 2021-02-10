package com.cgg.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.databinding.ActionBarLayoutBinding;

public class BaseActivity extends AppCompatActivity {
    private ActionBarLayoutBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.action_bar_layout);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        instMainViewModel = new InstMainViewModel(getApplication());
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack();
            }
        });
        binding.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customHomeAlert(BaseActivity.this, getString(R.string.app_name), getString(R.string.go_home));
            }
        });
    }

    public static void setIds(TextView[] ids,int SL_NO) {
        for (TextView id : ids) {
            SL_NO = SL_NO + 1;
            id.setText(SL_NO + ".");
        }
    }

    protected <T extends ViewDataBinding> T putContentView(@LayoutRes int resId, String title) {
        binding.headerTitle.setText(title);
        return DataBindingUtil.inflate(getLayoutInflater(), resId, binding.appbar, true);
    }

    public void callBack() {
        Utils.customHomeAlert(BaseActivity.this, getString(R.string.app_name), getString(R.string.go_back));
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

}
