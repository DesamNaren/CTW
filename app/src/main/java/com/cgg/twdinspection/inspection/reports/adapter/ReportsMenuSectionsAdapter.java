package com.cgg.twdinspection.inspection.reports.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.InstMainRowBinding;
import com.cgg.twdinspection.inspection.reports.ui.ReportCoCarricularActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportGeneralCommentsActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportMedicalActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportPhotosActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportStaffAttendActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportStudentsAttendActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportsAcademicActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportsDietIssuesActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportsEntitlementsActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportsGeneralInfoActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportsInfraActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportsRegistersActivity;

import java.util.List;

public class ReportsMenuSectionsAdapter extends RecyclerView.Adapter<ReportsMenuSectionsAdapter.ItemHolder> {

    private Context context;
    private List<String> list;

    public ReportsMenuSectionsAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InstMainRowBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.inst_main_row, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {

        holder.listItemBinding.tvClass.setText(list.get(position));
        holder.listItemBinding.llClassHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position + 1) {
                    case 1:
                        callActivity(ReportsGeneralInfoActivity.class);
                        break;
                    case 2:
                        callActivity(ReportStudentsAttendActivity.class);
                        break;
                    case 3:
                        callActivity(ReportStaffAttendActivity.class);
                        break;
                    case 4:
                        callActivity(ReportMedicalActivity.class);
                        break;
                    case 5:
                        callActivity(ReportsDietIssuesActivity.class);
                        break;
                    case 6:
                        callActivity(ReportsInfraActivity.class);
                        break;
                    case 7:
                        callActivity(ReportsAcademicActivity.class);
                        break;
                    case 8:
                        callActivity(ReportCoCarricularActivity.class);
                        break;
                    case 9:
                        callActivity(ReportsEntitlementsActivity.class);
                        break;
                    case 10:
                        callActivity(ReportsRegistersActivity.class);
                        break;
                    case 11:
                        callActivity(ReportGeneralCommentsActivity.class);
                        break;
                    case 12:
                        callActivity(ReportPhotosActivity.class);
                        break;
                }
            }
        });
    }

    private void callActivity(Class<?> calledActivity) {
        Intent myIntent = new Intent(context, calledActivity);
        context.startActivity(myIntent);
    }


    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        public InstMainRowBinding listItemBinding;

        public ItemHolder(InstMainRowBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(com.cgg.twdinspection.BR.section_menu, obj);
            listItemBinding.executePendingBindings();
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

