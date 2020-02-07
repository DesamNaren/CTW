package com.example.twdinspection.gcc.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.StockMainRowBinding;
import com.example.twdinspection.gcc.adapter.StockSubAdapter;
import com.example.twdinspection.gcc.source.stock.CommonCommodity;

import java.util.ArrayList;

public class DailyFragment extends Fragment {
    ArrayList<CommonCommodity> parameter;

    public static DailyFragment newInstance(ArrayList<CommonCommodity> parameter) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("ecs", parameter);
        DailyFragment fragment = new DailyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            parameter = getArguments().getParcelableArrayList("ecs");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StockMainRowBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.stock_main_row, container, false);
        View view = binding.getRoot();
        StockSubAdapter stockSubAdapter = new StockSubAdapter(getActivity(), parameter);
        binding.groupRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.groupRV.setAdapter(stockSubAdapter);

        return view;
    }

}
