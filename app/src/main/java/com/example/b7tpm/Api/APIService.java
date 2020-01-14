package com.example.b7tpm.Api;

import com.example.b7tpm.Model.AdministrasiRedForm;
import com.example.b7tpm.Model.AdministrasiWhiteForm;
import com.example.b7tpm.Model.DataMesin;
import com.example.b7tpm.Model.DeleteRedFormResponse;
import com.example.b7tpm.Model.DeleteWhiteFormResponse;
import com.example.b7tpm.Model.NewRedFormResponse;
import com.example.b7tpm.Model.NewWhiteFormResponse;
import com.example.b7tpm.Model.RedFormClose;
import com.example.b7tpm.Model.ResultDataMesin;
import com.example.b7tpm.Model.StatusRedForm;
import com.example.b7tpm.Model.StatusUser;
import com.example.b7tpm.Model.StatusWhiteForm;
import com.example.b7tpm.Model.UpdateLevelUserResponse;
import com.example.b7tpm.Model.UpdateStatusRedFormResponse;
import com.example.b7tpm.Model.UpdateStatusWhiteFormResponse;
import com.example.b7tpm.Model.WhiteForm;
import com.example.b7tpm.Model.WhiteFormClose;
import com.example.b7tpm.Model.Result;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @POST("newredform")
    Call<NewRedFormResponse> sendNewRedForm (
            @Field("nomor_kontrol") String nomor_kontrol,
            @Field("bagian_mesin") String bagian_mesin,
            @Field("dipasang_oleh") String dipasang_oleh,
            @Field("tgl_pasang") String tgl_pasang,
            @Field("deskripsi") String deskripsi,
            @Field("photo") String photo,
            @Field("nomor_work_request") String nomor_work_request,
            @Field("pic_follow_up") String pic_follow_up,
            @Field("due_date") String due_date,
            @Field("cara_penanggulangan") String cara_penanggulangan

    );

    @FormUrlEncoded
    @POST("updatewhiteform/{formid}")
    Call<NewWhiteFormResponse> updateWhiteForm (
            @Path("formid") int formid,
            @Field("nomor_kontrol") String nomor_kontrol,
            @Field("bagian_mesin") String bagian_mesin,
            @Field("dipasang_oleh") String dipasang_oleh,
            @Field("tgl_pasang") String tgl_pasang,
            @Field("deskripsi") String deskripsi,
            @Field("photo") String photo,
            @Field("due_date") String due_date,
            @Field("cara_penanggulangan") String cara_penanggulangan
    );

    @FormUrlEncoded
    @POST("updateredform/{formid}")
    Call<NewRedFormResponse> updateRedForm (
            @Path("formid") int formid,
            @Field("nomor_kontrol") String nomor_kontrol,
            @Field("bagian_mesin") String bagian_mesin,
            @Field("dipasang_oleh") String dipasang_oleh,
            @Field("tgl_pasang") String tgl_pasang,
            @Field("deskripsi") String deskripsi,
            @Field("photo") String photo,
            @Field("nomor_work_request") String nomorworkrequest,
            @Field("pic_follow_up") String picfollowup,
            @Field("due_date") String due_date,
            @Field("cara_penanggulangan") String cara_penanggulangan
    );

    @FormUrlEncoded
    @POST("updatestatuswhiteform/{formid}")
    Call<UpdateStatusWhiteFormResponse> updateStatusWhiteForm (
            @Path("formid") int formid,
            @Field("status") String nomor_kontrol
    );

    @FormUrlEncoded
    @POST("updatestatusredform/{formid}")
    Call<UpdateStatusRedFormResponse> updateStatusRedForm (
      @Path("formid") int formid,
      @Field("status") String nomor_kontrol
    );

    @FormUrlEncoded
    @POST("updateleveluser/{email}")
    Call<UpdateLevelUserResponse> updateLevelUser (
            @Path("email") String email,
            @Field("isuser") int isuser,
            @Field("isadmin") int isadmin,
            @Field("isspv") int isspv
    );

    @FormUrlEncoded
    @POST("deletewhiteform")
    Call<DeleteWhiteFormResponse> deleteWhiteForm (
            @Field("formid") int formid
    );

    @FormUrlEncoded
    @POST("deleteredform")
    Call<DeleteRedFormResponse> deleteRedForm (
            @Field("formid") int formid
    );

    @GET("whiteform/close")
    Call<WhiteFormClose> getWhiteFormClose();

    @GET("allwhiteform")
    Call<AdministrasiWhiteForm> getAdministrasiWhiteForm();

    @GET("allredform")
    Call<AdministrasiRedForm> getAdministrasiRedForm();

    @GET("redform/close")
    Call<RedFormClose> getRedFormClose();

    @GET("administrasi/whiteform/{nomorkontrol}")
    Call<WhiteForm> getWhiteFormDetail();

    @GET("whiteformstatus")
    Call<StatusWhiteForm> getStatusWhiteForm();

    @GET("redformstatus")
    Call<StatusRedForm> getStatusRedForm();

    @GET("users")
    Call<StatusUser> getStatusUser();



    @GET("datamesin/AB1C1234")
    Call<DataMesin> getDataMesin ();
    //getting messages
   /* @GET("messages/{id}")
    Call<Messages> getMessages(@Path("id") int id); */
}
