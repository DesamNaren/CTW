package com.cgg.twdinspection.gcc.reports.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.StockMainRowBinding;
import com.cgg.twdinspection.gcc.reports.adapter.CommCommodityAdapter;
import com.cgg.twdinspection.gcc.reports.source.ReportSubmitReqCommodities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EmptiesReportFragment extends Fragment {
    public static ArrayList<ReportSubmitReqCommodities> commonCommodities;
    private StockMainRowBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String esComm = getArguments().getString(AppConstants.emptiesRep);
            Type listType = new TypeToken<ArrayList<ReportSubmitReqCommodities>>() {
            }.getType();
            commonCommodities = new Gson().fromJson(esComm, listType);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.stock_main_row, container, false);
        View view = binding.getRoot();
        CommCommodityAdapter stockSubAdapter = new CommCommodityAdapter(getActivity(), commonCommodities);
        binding.groupRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.groupRV.setAdapter(stockSubAdapter);
        return view;
    }

    public void setPos(int pos) {
        binding.groupRV.smoothScrollToPosition(pos);
    }
}
