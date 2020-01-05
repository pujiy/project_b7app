package com.example.b7tpm.Model;

import com.google.gson.annotations.SerializedName;

public class DataMesin {

    private String nomor_mesin;
    private String nama_mesin;
    private String merk_mesin;
    private String kapasitas_mesin;
    private String jenis_mesin;
    private String fungsi_mesin;

    public DataMesin() {
    }

    public String getNomor_mesin() {
        return nomor_mesin;
    }

    public void setNomor_mesin(String nomor_mesin) {
        this.nomor_mesin = nomor_mesin;
    }

    public String getNama_mesin() {
        return nama_mesin;
    }

    public void setNama_mesin(String nama_mesin) {
        this.nama_mesin = nama_mesin;
    }

    public String getMerk_mesin() {
        return merk_mesin;
    }

    public void setMerk_mesin(String merk_mesin) {
        this.merk_mesin = merk_mesin;
    }

    public String getKapasitas_mesin() {
        return kapasitas_mesin;
    }

    public void setKapasitas_mesin(String kapasitas_mesin) {
        this.kapasitas_mesin = kapasitas_mesin;
    }

    public String getJenis_mesin() {
        return jenis_mesin;
    }

    public void setJenis_mesin(String jenis_mesin) {
        this.jenis_mesin = jenis_mesin;
    }

    public String getFungsi_mesin() {
        return fungsi_mesin;
    }

    public void setFungsi_mesin(String fungsi_mesin) {
        this.fungsi_mesin = fungsi_mesin;
    }
}
