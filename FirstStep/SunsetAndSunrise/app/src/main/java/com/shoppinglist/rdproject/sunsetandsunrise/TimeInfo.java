package com.shoppinglist.rdproject.sunsetandsunrise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// generated with http://www.jsonschema2pojo.org/
// thanks
class TimeInfo {

    @SerializedName("results")
    @Expose
    private Results results;
    @SerializedName("status")
    @Expose
    private String status;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}