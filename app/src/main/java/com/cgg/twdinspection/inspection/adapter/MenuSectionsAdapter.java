package com.cgg.twdinspection.inspection.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.InstMainRowBinding;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.cgg.twdinspection.inspection.ui.AcademicActivity;
import com.cgg.twdinspection.inspection.ui.CoCurricularActivity;
import com.cgg.twdinspection.inspection.ui.DietIssuesActivity;
import com.cgg.twdinspection.inspection.ui.EntitlementsActivity;
import com.cgg.twdinspection.inspection.ui.GeneralCommentsActivity;
import com.cgg.twdinspection.inspection.ui.GeneralInfoActivity;
import com.cgg.twdinspection.inspection.ui.InfraActivity;
import com.cgg.twdinspection.inspection.ui.MedicalActivity;
import com.cgg.twdinspection.inspection.ui.RegistersActivity;
import com.cgg.twdinspection.inspection.ui.StaffAttendActivity;
import com.cgg.twdinspection.inspection.ui.StudentsAttendActivity;
import com.cgg.twdinspection.inspection.ui.UploadedPhotoActivity;

import java.util.List;

public class MenuSectionsAdapter extends RecyclerView.Adapter<MenuSectionsAdapter.ItemHolder> {

    private Context context;
    private List<InstMenuInfoEntity> list;

    public MenuSectionsAdapter(Context context, List<InstMenuInfoEntity> list) {
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
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final InstMenuInfoEntity dataModel = list.get(i);
        holder.listItemBinding.setSectionMenu(dataModel);
        holder.listItemBinding.tvClass.setText(list.get(i).getSection_name());
        if (dataModel.getFlag_completed()==1) {
            holder.listItemBinding.ivSync.setImageDrawable(context.getResources().getDrawable(R.drawable.completed));
        } else {
            holder.listItemBinding.ivSync.setImageDrawable(context.getResources().getDrawable(R.drawable.pending));
        }

        holder.listItemBinding.llClassHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (dataModel.getFlag_completed()==0) {
                    switch (dataModel.getSection_id()) {
                        case 1:
                            callActivity(GeneralInfoActivity.class, dataModel.getFlag_completed());
                            break;
                        case 2:
                            callActivity(StudentsAttendActivity.class, dataModel.getFlag_completed());
                            break;
                        case 3:
                            callActivity(StaffAttendActivity.class, dataModel.getFlag_completed());
                            break;
                        case 4:
                            callActivity(MedicalActivity.class, dataModel.getFlag_completed());
                            break;
                        case 5:
                            callActivity(DietIssuesActivity.class, dataModel.getFlag_completed());
                            break;
                        case 6:
                            callActivity(InfraActivity.class, dataModel.getFlag_completed());
                            break;
                        case 7:
                            callActivity(AcademicActivity.class, dataModel.getFlag_completed());
                            break;
                        case 8:
                            callActivity(CoCurricularActivity.class, dataModel.getFlag_completed());
                            break;
                        case 9:
                            callActivity(EntitlementsActivity.class, dataModel.getFlag_completed());
                            break;
                        case 10:
                            callActivity(RegistersActivity.class, dataModel.getFlag_completed());
                            break;
                        case 11:
                            callActivity(GeneralCommentsActivity.class, dataModel.getFlag_completed());
                            break;
                        case 12:
                            callActivity(UploadedPhotoActivity.class, dataModel.getFlag_completed());

//                            boolean flag = true;
//                            for (int i = 0; i < list.size()-1; i++) {
//                                if (list.get(i).getFlag_completed() == 0) {
//                                    flag = false;
//                                    break;
//                                }
//                            }
//                            if (flag) {
//                                callActivity(UploadedPhotoActivity.class, dataModel.getFlag_completed());
//                            } else {
//                                Utils.customWarningAlert(context, context.getResources().getString(R.string.app_name), "Please complete other sections and submit photos");
//                            }
                            break;
                    }
                }

//            }
        });
        holder.bind(dataModel);
    }

    private void callActivity(Class<?> calledActivity, int flag) {
        Intent myIntent = new Intent(context, calledActivity);
        myIntent.putExtra(AppConstants.LOCAL_FLAG, flag);
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

