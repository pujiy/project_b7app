package com.example.b7tpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.UpdateStatusRedFormResponse;
import com.example.b7tpm.Model.UpdateStatusWhiteFormResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private TextView textViewDetail, textViewNomorKontrol, textViewBagianMesin, textViewDipasangOleh, textViewTglPasang, textViewDeskripsi, textViewNomorWorkRequest, textViewpicfollowup, textViewDueDate, textViewCaraPenanggulangan;
    private ImageView imageViewPhoto;
    private Button buttonEdit, buttonPrint;
    private static final int STORAGE_CODE = 1000;

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
        buttonPrint = findViewById(R.id.btn_print);
        textViewDetail = findViewById(R.id.tv_detail);

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

                        String status = "Open";
                        //building retrofit object
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIUrl.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        //Defining retrofit api service
                        APIService service = retrofit.create(APIService.class);

                        Call<UpdateStatusRedFormResponse> call = service.updateStatusRedForm(
                                formid,
                                status
                        );


                        call.enqueue(new Callback<UpdateStatusRedFormResponse>() {
                            @Override
                            public void onResponse(Call<UpdateStatusRedFormResponse> call, Response<UpdateStatusRedFormResponse> response) {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<UpdateStatusRedFormResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                }

                else {

                    printPdf();

                    String status = "Open";
                    //building retrofit object
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(APIUrl.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    //Defining retrofit api service
                    APIService service = retrofit.create(APIService.class);

                    Call<UpdateStatusRedFormResponse> call = service.updateStatusRedForm(
                            formid,
                            status
                    );

                    call.enqueue(new Callback<UpdateStatusRedFormResponse>() {
                        @Override
                        public void onResponse(Call<UpdateStatusRedFormResponse> call, Response<UpdateStatusRedFormResponse> response) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<UpdateStatusRedFormResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });




                }

            }
        });
    }

    private void printPdf() {

        Document newPdf = new Document();

        String nomorkontrol = textViewNomorKontrol.getText().toString().trim();
        String fileTime = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String fileNamePdf = "REDFORM_"+ nomorkontrol + "_" + fileTime;
        String filePath = Environment.getExternalStorageDirectory() + "/" + fileNamePdf + ".pdf";


        try {
            PdfWriter.getInstance(newPdf, new FileOutputStream(filePath));

            newPdf.open();



            String detail = textViewDetail.getText().toString().trim();
            String nomorKontrol = "Nomor Kontrol \n" + textViewNomorKontrol.getText().toString().trim();
            String bagianMesin = "Bagian Mesin \n" + textViewBagianMesin.getText().toString().trim();
            String dipasangOleh = "Dipasang oleh \n" + textViewDipasangOleh.getText().toString().trim();
            String tglPasang = "Tanggal Pemasangan \n" + textViewTglPasang.getText().toString().trim();
            String deskripsi = "Deskripsi \n" + textViewDeskripsi.getText().toString().trim();
            String nomorworkrequest = "Nomor Work Request \n" + textViewNomorWorkRequest.getText().toString().trim();
            String picfollowup = "PIC Follow Up \n" + textViewpicfollowup.getText().toString().trim();
            String dueDate = "Due Date \n" + textViewDueDate.getText().toString().trim();
            String caraPenanggulangan = "Cara Penanggulangan \n" + textViewCaraPenanggulangan.getText().toString().trim();
            String spasi = " \n";

            newPdf.add(new Paragraph(detail));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(nomorKontrol));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(bagianMesin));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(dipasangOleh));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(tglPasang));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(deskripsi));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(nomorworkrequest));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(picfollowup));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(dueDate));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(caraPenanggulangan));


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
