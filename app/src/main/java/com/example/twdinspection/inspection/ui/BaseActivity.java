package com.example.twdinspection.inspection.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActionBarLayoutBinding;

public class BaseActivity extends AppCompatActivity {
    private ActionBarLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.action_bar_layout);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseActivity.this, "Back press", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected <T extends ViewDataBinding> T putContentView(@LayoutRes int resId, String title) {
        binding.toolbarTitle.setText(title);
        return DataBindingUtil.inflate(getLayoutInflater(), resId, binding.appbar, true);
    }
}
