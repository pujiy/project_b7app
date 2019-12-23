package com.example.b7tpm.Model;

public class RedForm {

    private int form_id;
    private String nomor_kontrol;
    private String bagian_mesin;
    private String dipasang_oleh;
    private String tgl_pasang;
    private String deskripsi;
    private String photo;
    private String nomor_work_request;
    private String pic_follow_up;
    private String due_date;
    private String cara_penanggulangan;
    private String status;

    public RedForm(int form_id, String nomor_kontrol, String bagian_mesin, String dipasang_oleh, String tgl_pasang, String deskripsi, String photo, String nomor_work_request, String pic_follow_up, String due_date, String cara_penanggulangan, String status) {
        this.form_id = form_id;
        this.nomor_kontrol = nomor_kontrol;
        this.bagian_mesin = bagian_mesin;
        this.dipasang_oleh = dipasang_oleh;
        this.tgl_pasang = tgl_pasang;
        this.deskripsi = deskripsi;
        this.photo = photo;
        this.nomor_work_request = nomor_work_request;
        this.pic_follow_up = pic_follow_up;
        this.due_date = due_date;
        this.cara_penanggulangan = cara_penanggulangan;
        this.status = status;
    }

    public int getForm_id() {
        return form_id;
    }

    public String getNomor_kontrol() {
        return nomor_kontrol;
    }

    public String getBagian_mesin() {
        return bagian_mesin;
    }

    public String getDipasang_oleh() {
        return dipasang_oleh;
    }

    public String getTgl_pasang() {
        return tgl_pasang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNomor_work_request() {
        return nomor_work_request;
    }

    public String getPic_follow_up() {
        return pic_follow_up;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getCara_penanggulangan() {
        return cara_penanggulangan;
    }

    public String getStatus() {
        return status;
    }
}
