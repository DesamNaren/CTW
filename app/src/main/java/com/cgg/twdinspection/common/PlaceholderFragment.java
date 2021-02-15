package com.cgg.twdinspection.common;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.databinding.FragmentPrivacyPolicyBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private CustomProgressDialog customProgressDialog;
    private FragmentPrivacyPolicyBinding binding;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        customProgressDialog = new CustomProgressDialog(getActivity());
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_policy, container, false);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String url) {
                try {
                    customProgressDialog.show();
                    customProgressDialog.setCancelable(true);
                    customProgressDialog.setCanceledOnTouchOutside(true);

                    binding.webView.getSettings().setDomStorageEnabled(true); // Add this
                    binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                    binding.webView.setWebViewClient(new WebViewClient());
                    binding.webView.loadUrl(url);
                    WebSettings webSettings = binding.webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                } catch (Exception e) {
                    customProgressDialog.dismiss();
                    e.printStackTrace();
                }

            }
        });

        return binding.getRoot();
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            customProgressDialog.hide();
            super.onPageFinished(view, url);

        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        }
    }
}