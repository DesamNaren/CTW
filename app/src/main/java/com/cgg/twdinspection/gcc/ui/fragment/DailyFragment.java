package com.cgg.twdinspection.gcc.ui.fragment;

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
import com.cgg.twdinspection.gcc.adapter.DailyReqSubAdapter;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DailyFragment extends Fragment {
    public static ArrayList<CommonCommodity> commonCommodities;
    private StockMainRowBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String esComm = getArguments().getString(AppConstants.dailyReq);
            Type listType = new TypeToken<ArrayList<CommonCommodity>>(){}.getType();
            commonCommodities = new Gson().fromJson(esComm, listType);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(
                inflater, R.layout.stock_main_row, container, false);
        View view = binding.getRoot();
        DailyReqSubAdapter stockSubAdapter = new DailyReqSubAdapter(getActivity(), commonCommodities);
        binding.groupRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.groupRV.setAdapter(stockSubAdapter);

        return view;
    }

    public void setPos(int pos){
        binding.groupRV.smoothScrollToPosition(pos);
    }
}
