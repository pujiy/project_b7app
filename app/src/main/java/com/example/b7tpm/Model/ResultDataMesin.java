package com.example.b7tpm.Model;

import com.google.gson.annotations.SerializedName;

public class ResultDataMesin {

    @SerializedName("data_mesin")
    private DataMesin dataMesin;

    public ResultDataMesin(DataMesin dataMesin) {
        this.dataMesin = dataMesin;
    }

    public DataMesin getDataMesin() {
        return dataMesin;
    }
}
