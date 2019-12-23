package com.example.b7tpm.Api;

import com.example.b7tpm.Model.NewWhiteFormResponse;
import com.example.b7tpm.Model.RedFormClose;
import com.example.b7tpm.Model.WhiteFormClose;
import com.example.b7tpm.Model.Result;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @FormUrlEncoded
    @POST("newwhiteform")
    Call<NewWhiteFormResponse> sendNewWhiteForm (
            @Field("nomor_kontrol") String nomor_kontrol,
            @Field("bagian_mesin") String bagian_mesin,
            @Field("dipasang_oleh") String dipasang_oleh,
            @Field("tgl_pasang") String tgl_pasang,
            @Field("deskripsi") String deskripsi,
            @Field("photo") String photo,
            @Field("due_date") String due_date,
            @Field("cara_penanggulangan") String cara_penanggulangan
    );

    @GET("whiteform/close")
    Call<WhiteFormClose> getWhiteFormClose();

    @GET("redform/close")
    Call<RedFormClose> getRedFormClose();
}
