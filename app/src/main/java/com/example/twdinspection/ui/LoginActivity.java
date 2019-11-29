package com.example.twdinspection.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityLoginCreBinding;
import com.example.twdinspection.source.LoginUser;
import com.example.twdinspection.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginCreBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login_cre);
        LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(@Nullable LoginUser user) {
                if (user != null && (user.getEmail().length() > 0 || user.getPassword().length() > 0))
                    Toast.makeText(getApplicationContext(), "email : " + user.getEmail() + " password " + user.getPassword(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
