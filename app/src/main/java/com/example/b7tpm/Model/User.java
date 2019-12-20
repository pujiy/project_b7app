package com.example.b7tpm.Model;

import com.google.gson.annotations.SerializedName;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private String nik;
    private int isuser;
    private int isspv;
    private int isadmin;
    private int isverified;

    public User(String username, String email, String password, String nik, int isuser, int isspv, int isadmin, int isverified) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nik = nik;
        this.isuser = isuser;
        this.isspv = isspv;
        this.isadmin = isadmin;
        this.isverified = isverified;
    }

    public User(int id, String username, String email, String nik, int isuser, int isspv, int isadmin, int isverified) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nik = nik;
        this.isuser = isuser;
        this.isspv = isspv;
        this.isadmin = isadmin;
        this.isverified = isverified;
    }

    public User(int id, String username, String email, String password, String nik, int isuser, int isspv, int isadmin, int isverified) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nik = nik;
        this.isuser = isuser;
        this.isspv = isspv;
        this.isadmin = isadmin;
        this.isverified = isverified;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNik() {
        return nik;
    }

    public int getIsuser() {
        return isuser;
    }

    public int getIsspv() {
        return isspv;
    }

    public int getIsadmin() {
        return isadmin;
    }

    public int getIsverified() {
        return isverified;
    }
}
