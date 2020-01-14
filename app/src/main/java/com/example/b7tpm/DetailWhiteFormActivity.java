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

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Helper.SharedPrefManager;
import com.example.b7tpm.Model.DeleteWhiteFormResponse;
import com.example.b7tpm.Model.UpdateStatusWhiteFormResponse;
import com.example.b7tpm.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private Button buttonEdit, buttonPrint, buttonDelete;
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
        buttonDelete = findViewById(R.id.btn_delete);


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

                        String status = "Open";
                        //building retrofit object
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIUrl.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        //Defining retrofit api service
                        APIService service = retrofit.create(APIService.class);

                        Call<UpdateStatusWhiteFormResponse> call = service.updateStatusWhiteForm(
                                formid,
                                status
                        );

                        //calling api
                        call.enqueue(new Callback<UpdateStatusWhiteFormResponse>() {
                            @Override
                            public void onResponse(Call<UpdateStatusWhiteFormResponse> call, Response<UpdateStatusWhiteFormResponse> response) {

                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(Call<UpdateStatusWhiteFormResponse> call, Throwable t) {

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

                    Call<UpdateStatusWhiteFormResponse> call = service.updateStatusWhiteForm(
                            formid,
                            status
                    );

                    //calling api
                    call.enqueue(new Callback<UpdateStatusWhiteFormResponse>() {
                        @Override
                        public void onResponse(Call<UpdateStatusWhiteFormResponse> call, Response<UpdateStatusWhiteFormResponse> response) {

                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<UpdateStatusWhiteFormResponse> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //building retrofit object
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //Defining retrofit api service
                APIService service = retrofit.create(APIService.class);

                Call<DeleteWhiteFormResponse> call = service.deleteWhiteForm(
                        formid
                );

                call.enqueue(new Callback<DeleteWhiteFormResponse>() {
                    @Override
                    public void onResponse(Call<DeleteWhiteFormResponse> call, Response<DeleteWhiteFormResponse> response) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailWhiteFormActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<DeleteWhiteFormResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
            PdfWriter writer = PdfWriter.getInstance(newPdf, new FileOutputStream(filePath));

            Rectangle one = new Rectangle(280,396);
            newPdf.setPageSize(one);
            newPdf.setMargins(2, 2, 2, 2);
            newPdf.open();



            String detail = textViewDetail.getText().toString().trim();
            String nomorKontrol = "Nomor Kontrol : " + textViewNomorKontrol.getText().toString().trim();
            String bagianMesin = "Bagian Mesin : " + textViewBagianMesin.getText().toString().trim();
            String dipasangOleh = "Dipasang oleh : " + textViewDipasangOleh.getText().toString().trim();
            String tglPasang = "Tanggal Pemasangan : " + textViewTglPasang.getText().toString().trim();
            String deskripsi = "Deskripsi : " + textViewDeskripsi.getText().toString().trim();
            String dueDate = "Due Date : " + textViewDueDate.getText().toString().trim();
            String caraPenanggulangan = textViewCaraPenanggulangan.getText().toString().trim();
            String spasi = " \n";


            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = new Rectangle(0,354,276,396);
            Rectangle rect2 = new Rectangle(0,100, 276, 130);
            rect.setBorder(Rectangle.BOX);
            rect2.setBorder(Rectangle.BOX);
            rect.setBorderWidth(1);
            rect2.setBorderWidth(1);
            rect.setBackgroundColor(BaseColor.WHITE);
            rect2.setBackgroundColor(BaseColor.WHITE);
            rect.setBorderColor(BaseColor.BLACK);
            rect2.setBorderColor(BaseColor.BLACK);
            canvas.rectangle(rect);
            canvas.rectangle(rect2);

            ColumnText ct = new ColumnText(canvas);
            ColumnText ct2 = new ColumnText(canvas);
            ct.setSimpleColumn(rect);
            ct2.setSimpleColumn(rect2);
            ct.setAlignment(Element.ALIGN_CENTER);
            ct2.setAlignment(Element.ALIGN_CENTER);
            Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
            Font isiFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            catFont.setColor(BaseColor.BLACK);
            Paragraph otherParagraph = new Paragraph("WHITE TAG \n Abnormality Tag", catFont);
            otherParagraph.setAlignment(Element.ALIGN_CENTER);
            Paragraph otherParagraph1 = new Paragraph("Penanggulangan", catFont);
            otherParagraph1.setAlignment(Element.ALIGN_CENTER);
            ct.addElement(otherParagraph);
            ct2.addElement(otherParagraph1);
            ct.go();
            ct2.go();

            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(nomorKontrol, isiFont));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(bagianMesin, isiFont));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(dipasangOleh, isiFont));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(tglPasang, isiFont));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(deskripsi, isiFont));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(dueDate, isiFont));
            newPdf.add(new Paragraph(spasi));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(caraPenanggulangan, isiFont));


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
