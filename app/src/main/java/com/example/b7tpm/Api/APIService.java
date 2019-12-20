package com.example.b7tpm.Api;

import com.example.b7tpm.Model.Result;

import java.sql.ResultSet;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    //Register call
    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser (
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("nik") String nik,
            @Field("isuser") int isuser,
            @Field("isspv") int isspv,
            @Field("isadmin") int isadmin
    );

    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}
