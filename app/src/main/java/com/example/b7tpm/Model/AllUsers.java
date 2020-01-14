package com.example.b7tpm.Model;

public class AllUsers {
    private int id;
    private String username;
    private String email;
    private String password;
    private String nik;
    private int isuser;
    private int isspv;
    private int isadmin;

    public AllUsers(int id, String username, String email, String password, String nik, int isuser, int isspv, int isadmin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nik = nik;
        this.isuser = isuser;
        this.isspv = isspv;
        this.isadmin = isadmin;
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
}
