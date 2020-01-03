package com.example.b7tpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Helper.SharedPrefManager;
import com.example.b7tpm.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailWhiteFormActivity extends AppCompatActivity {

    public static final String EXTRA_FORMID = "formid";
    public static final String EXTRA_NOMORKONTROL = "nomor_kontrol";
    public static final String EXTRA_BAGIANMESIN = "bagianmesin";
    public static final String EXTRA_DIPASANGOLEH = "dipasangoleh";
    public static final String EXTRA_TGLPASANG = "tglpasang";
    public static final String EXTRA_DESKRIPSI = "deskripsi";
    public static final String EXTRA_DUEDATE = "duedate";
    public static final String EXTRA_CARAPENANGGULANGAN = "cara penanggulangan";
    public static final String EXTRA_PHOTO = "photo";
    private TextView textViewDetail;
    private TextView textViewNomorKontrol, textViewBagianMesin, textViewDipasangOleh, textViewTglPasang, textViewDeskripsi, textViewDueDate, textViewCaraPenanggulangan;
    private ImageView imageViewPhoto;
    private Button buttonEdit, buttonPrint;
    private static final int STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_white_form);


        textViewDetail = findViewById(R.id.tv_detail);

        textViewNomorKontrol = findViewById(R.id.tv_nomorkontrol);
        textViewBagianMesin = findViewById(R.id.tv_bagianmesin);
        textViewDipasangOleh = findViewById(R.id.tv_dipasangoleh);
        textViewTglPasang = findViewById(R.id.tv_tglpasang);
        textViewDeskripsi = findViewById(R.id.tv_deskripsi);
        textViewDueDate = findViewById(R.id.tv_duedate);
        textViewCaraPenanggulangan = findViewById(R.id.tv_carapenanggulangan);
        imageViewPhoto = findViewById(R.id.iv_photo);
        buttonEdit = findViewById(R.id.btn_edit);
        buttonPrint = findViewById(R.id.btn_print);


        final int formid = getIntent().getIntExtra(EXTRA_FORMID, 0);
        final String nomorkontrol = getIntent().getStringExtra(EXTRA_NOMORKONTROL);
        final String bagianmesin = getIntent().getStringExtra(EXTRA_BAGIANMESIN);
        final String dipasangoleh = getIntent().getStringExtra(EXTRA_DIPASANGOLEH);
        final String tglpasang = getIntent().getStringExtra(EXTRA_TGLPASANG);
        final String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        final String duedate = getIntent().getStringExtra(EXTRA_DUEDATE);
        final String carapenanggulangan = getIntent().getStringExtra(EXTRA_CARAPENANGGULANGAN);
        final String photo = getIntent().getStringExtra(EXTRA_PHOTO);

        textViewNomorKontrol.setText(nomorkontrol);
        textViewBagianMesin.setText(bagianmesin);
        textViewDipasangOleh.setText(dipasangoleh);
        textViewTglPasang.setText(tglpasang);
        textViewDeskripsi.setText(deskripsi);
        textViewDueDate.setText(duedate);
        textViewCaraPenanggulangan.setText(carapenanggulangan);
        Picasso.get().load(photo).into(imageViewPhoto);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        if (user.getIsuser() == 0) {
            buttonEdit.setEnabled(true);
        }


        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveWithDataIntent = new Intent(DetailWhiteFormActivity.this, EditAdministrasiWhiteFormActivity.class);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_DIPASANGOLEH, dipasangoleh);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_TGLPASANG, tglpasang);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_DESKRIPSI, deskripsi);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_DUEDATE, duedate);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_CARAPENANGGULANGAN, carapenanggulangan);
                moveWithDataIntent.putExtra(EditAdministrasiWhiteFormActivity.EXTRA_PHOTO, photo);
                startActivity(moveWithDataIntent);
            }
        });

        buttonPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle runtime permission for devices with marshmallow and above
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else {

                        printPdf();

                    }
                }

                else {

                    printPdf();

                }
            }
        });



    }

    private void printPdf() {

        Document newPdf = new Document();

        String nomorkontrol = textViewNomorKontrol.getText().toString().trim();
        String fileTime = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String fileNamePdf = "WHITEFORM_"+ nomorkontrol + "_" + fileTime;
        String filePath = Environment.getExternalStorageDirectory() + "/" + fileNamePdf + ".pdf";


        try {
            PdfWriter.getInstance(newPdf, new FileOutputStream(filePath));

            newPdf.open();



            String detail = textViewDetail.getText().toString().trim();
            String nomorKontrol = "Nomor Kontrol \n" + textViewNomorKontrol.getText().toString().trim();


            newPdf.add(new Paragraph(detail));
            newPdf.add(new Paragraph(nomorKontrol));


            newPdf.close();

            Toast.makeText(this, fileNamePdf + ".pdf\nis saved to\n" + filePath, Toast.LENGTH_LONG).show();


        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (DocumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case STORAGE_CODE : {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                   printPdf();
                }
                else {
                    Toast.makeText(this, "Akses Ditolak...!", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
}
