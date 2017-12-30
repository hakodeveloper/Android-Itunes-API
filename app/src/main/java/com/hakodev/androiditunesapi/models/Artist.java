package com.hakodev.androiditunesapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artist {

    @SerializedName("resultCount")
    @Expose
    private Long resultCount;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Long getResultCount() {
        return resultCount;
    }

    public void setResultCount(Long resultCount) {
        this.resultCount = resultCount;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
