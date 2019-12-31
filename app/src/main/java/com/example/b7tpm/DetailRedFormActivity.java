package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailRedFormActivity extends AppCompatActivity {

    public static final String EXTRA_FORMID = "formid";
    public static final String EXTRA_NOMORKONTROL = "nomor_kontrol";
    public static final String EXTRA_BAGIANMESIN = "bagianmesin";
    public static final String EXTRA_DIPASANGOLEH = "dipasangoleh";
    public static final String EXTRA_TGLPASANG = "tglpasang";
    public static final String EXTRA_DESKRIPSI = "deskripsi";
    public static final String EXTRA_NOMORWORKREQUEST = "nomorworkrequest";
    public static final String EXTRA_PICFOLLOWUP = "picfollowup";
    public static final String EXTRA_DUEDATE = "duedate";
    public static final String EXTRA_CARAPENANGGULANGAN = "cara penanggulangan";
    public static final String EXTRA_PHOTO = "photo";
    private TextView textViewNomorKontrol, textViewBagianMesin, textViewDipasangOleh, textViewTglPasang, textViewDeskripsi, textViewNomorWorkRequest, textViewpicfollowup, textViewDueDate, textViewCaraPenanggulangan;
    private ImageView imageViewPhoto;
    private Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_red_form);

        textViewNomorKontrol = findViewById(R.id.tv_nomorkontrol);
        textViewBagianMesin = findViewById(R.id.tv_bagianmesin);
        textViewDipasangOleh = findViewById(R.id.tv_dipasangoleh);
        textViewTglPasang = findViewById(R.id.tv_tglpasang);
        textViewDeskripsi = findViewById(R.id.tv_deskripsi);
        textViewNomorWorkRequest = findViewById(R.id.tv_nomorworkrequest);
        textViewpicfollowup = findViewById(R.id.tv_picfollowup);
        textViewDueDate = findViewById(R.id.tv_duedate);
        textViewCaraPenanggulangan = findViewById(R.id.tv_carapenanggulangan);
        imageViewPhoto = findViewById(R.id.iv_photo);
        buttonEdit = findViewById(R.id.btn_edit);

        final int formid = getIntent().getIntExtra(EXTRA_FORMID, 0);
        final String nomorkontrol = getIntent().getStringExtra(EXTRA_NOMORKONTROL);
        final String bagianmesin = getIntent().getStringExtra(EXTRA_BAGIANMESIN);
        final String dipasangoleh = getIntent().getStringExtra(EXTRA_DIPASANGOLEH);
        final String tglpasang = getIntent().getStringExtra(EXTRA_TGLPASANG);
        final String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        final String nomorworkrequest = getIntent().getStringExtra(EXTRA_NOMORWORKREQUEST);
        final String picfollowup = getIntent().getStringExtra(EXTRA_PICFOLLOWUP);
        final String duedate = getIntent().getStringExtra(EXTRA_DUEDATE);
        final String carapenanggulangan = getIntent().getStringExtra(EXTRA_CARAPENANGGULANGAN);
        final String photo = getIntent().getStringExtra(EXTRA_PHOTO);

        textViewNomorKontrol.setText(nomorkontrol);
        textViewBagianMesin.setText(bagianmesin);
        textViewDipasangOleh.setText(dipasangoleh);
        textViewTglPasang.setText(tglpasang);
        textViewDeskripsi.setText(deskripsi);
        textViewNomorWorkRequest.setText(nomorworkrequest);
        textViewpicfollowup.setText(picfollowup);
        textViewDueDate.setText(duedate);
        textViewCaraPenanggulangan.setText(carapenanggulangan);
        Picasso.get().load(photo).into(imageViewPhoto);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveWithDataIntent = new Intent(DetailRedFormActivity.this, EditAdministrasiRedFormActivity.class);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_DIPASANGOLEH, dipasangoleh);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_TGLPASANG, tglpasang);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_DESKRIPSI, deskripsi);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_NOMORWORKREQUEST, nomorworkrequest);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_PICFOLLOWUP, picfollowup);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_DUEDATE, duedate);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_CARAPENANGGULANGAN, carapenanggulangan);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_PHOTO, photo);
                startActivity(moveWithDataIntent);
            }
        });
    }
}
