package com.example.twdinspection.engineering_works.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stage {

    @SerializedName("stage_id")
    @Expose
    private Integer stageId;
    @SerializedName("stage_name")
    @Expose
    private String stageName;
    @SerializedName("stage_progress_rating")
    @Expose
    private Integer stageProgressRating;

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getStageProgressRating() {
        return stageProgressRating;
    }

    public void setStageProgressRating(Integer stageProgressRating) {
        this.stageProgressRating = stageProgressRating;
    }

}