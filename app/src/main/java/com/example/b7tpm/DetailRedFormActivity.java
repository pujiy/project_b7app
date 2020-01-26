package com.example.b7tpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Helper.SharedPrefManager;
import com.example.b7tpm.Model.DeleteRedFormResponse;
import com.example.b7tpm.Model.UpdateStatusRedFormResponse;
import com.example.b7tpm.Model.UpdateStatusWhiteFormResponse;
import com.example.b7tpm.Model.User;
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
import java.net.MalformedURLException;
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
    public static final String EXTRA_NAMAMESIN = "namamesin";
    public static final String EXTRA_NOMORMESIN = "nomormesin";
    public static final String EXTRA_DIPASANGOLEH = "dipasangoleh";
    public static final String EXTRA_TGLPASANG = "tglpasang";
    public static final String EXTRA_DESKRIPSI = "deskripsi";
    public static final String EXTRA_NOMORWORKREQUEST = "nomorworkrequest";
    public static final String EXTRA_PICFOLLOWUP = "picfollowup";
    public static final String EXTRA_DUEDATE = "duedate";
    public static final String EXTRA_CARAPENANGGULANGAN = "cara penanggulangan";
    public static final String EXTRA_PHOTO = "photo";
    private TextView textViewDetail, textViewNomorKontrol, textViewBagianMesin, textViewNamaMesin, textViewNomorMesin, textViewDipasangOleh, textViewTglPasang, textViewDeskripsi, textViewNomorWorkRequest, textViewpicfollowup, textViewDueDate, textViewCaraPenanggulangan;
    private ImageView imageViewPhoto;
    private String imgPhoto;
    private Button buttonEdit, buttonPrint, buttonDelete;
    private static final int STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_red_form);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        textViewNomorKontrol = findViewById(R.id.tv_nomorkontrol);
        textViewBagianMesin = findViewById(R.id.tv_bagianmesin);
        textViewNamaMesin = findViewById(R.id.tv_namamesin);
        textViewNomorMesin = findViewById(R.id.tv_nomormesin);
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
        buttonDelete = findViewById(R.id.btn_delete);
        textViewDetail = findViewById(R.id.tv_detail);

        final int formid = getIntent().getIntExtra(EXTRA_FORMID, 0);
        final String nomorkontrol = getIntent().getStringExtra(EXTRA_NOMORKONTROL);
        final String bagianmesin = getIntent().getStringExtra(EXTRA_BAGIANMESIN);
        final String namamesin = getIntent().getStringExtra(EXTRA_NAMAMESIN);
        final String nomormesin = getIntent().getStringExtra(EXTRA_NOMORMESIN);
        final String dipasangoleh = getIntent().getStringExtra(EXTRA_DIPASANGOLEH);
        final String tglpasang = getIntent().getStringExtra(EXTRA_TGLPASANG);
        final String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        final String nomorworkrequest = getIntent().getStringExtra(EXTRA_NOMORWORKREQUEST);
        final String picfollowup = getIntent().getStringExtra(EXTRA_PICFOLLOWUP);
        final String duedate = getIntent().getStringExtra(EXTRA_DUEDATE);
        final String carapenanggulangan = getIntent().getStringExtra(EXTRA_CARAPENANGGULANGAN);
        final String photo = getIntent().getStringExtra(EXTRA_PHOTO);
        imgPhoto = photo;

        textViewNomorKontrol.setText(nomorkontrol);
        textViewBagianMesin.setText(bagianmesin);
        textViewNamaMesin.setText(namamesin);
        textViewNomorMesin.setText(nomormesin);
        textViewDipasangOleh.setText(dipasangoleh);
        textViewTglPasang.setText(tglpasang);
        textViewDeskripsi.setText(deskripsi);
        textViewNomorWorkRequest.setText(nomorworkrequest);
        textViewpicfollowup.setText(picfollowup);
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

                Intent moveWithDataIntent = new Intent(DetailRedFormActivity.this, EditAdministrasiRedFormActivity.class);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_NAMAMESIN, namamesin);
                moveWithDataIntent.putExtra(EditAdministrasiRedFormActivity.EXTRA_NOMORMESIN, nomormesin);
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

                        try {
                            printPdf();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

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

                    try {
                        printPdf();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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

                Call<DeleteRedFormResponse> call = service.deleteRedForm(
                        formid
                );

                call.enqueue(new Callback<DeleteRedFormResponse>() {
                    @Override
                    public void onResponse(Call<DeleteRedFormResponse> call, Response<DeleteRedFormResponse> response) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailRedFormActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<DeleteRedFormResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    /*
    PdfContentByte canvas = writer.getDirectContent();
BaseColor bColor = new BaseColor(0xFF, 0xD0, 0x00);
canvas.setColorFill(bColor);
canvas.rectangle(rect.getLeft(), rect.getBottom() - 1.5f, rect.getWidth(), rect.getHeight());
canvas.fillStroke();

     */
    private void printPdf() throws DocumentException, MalformedURLException, IOException {

        Toast.makeText(this, "Download File...", Toast.LENGTH_LONG).show();

        Document newPdf = new Document();

        String nomorkontrol = textViewNomorKontrol.getText().toString().trim();
        String fileTime = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String fileNamePdf = "REDFORM_"+ nomorkontrol + "_" + fileTime;
        String filePath = Environment.getExternalStorageDirectory() + "/" + fileNamePdf + ".pdf";



        try {

            PdfWriter writer = PdfWriter.getInstance(newPdf, new FileOutputStream(filePath));

            Rectangle one = new Rectangle(280,515);
            newPdf.setPageSize(one);
            newPdf.setMargins(2, 2, 2, 2);
            newPdf.open();



            String detail = textViewDetail.getText().toString().trim();
            String nomorKontrol = "Nomor Kontrol : " + textViewNomorKontrol.getText().toString().trim();
            String bagianMesin = "Bagian Mesin : " + textViewBagianMesin.getText().toString().trim();
            String namaMesin = "Nama Mesin  : " + textViewNamaMesin.getText().toString().trim();
            String nomorMesin = "Nomor Mesin  " + textViewNomorMesin.getText().toString().trim();
            String dipasangOleh = "Dipasang oleh : " + textViewDipasangOleh.getText().toString().trim();
            String tglPasang = "Tanggal Pemasangan : " + textViewTglPasang.getText().toString().trim();
            String deskripsi = "Deskripsi : " + textViewDeskripsi.getText().toString().trim();
            String nomorworkrequest = "Nomor Work Request : " + textViewNomorWorkRequest.getText().toString().trim();
            String picfollowup = "PIC Follow Up : " + textViewpicfollowup.getText().toString().trim();
            String dueDate = "Due Date : " + textViewDueDate.getText().toString().trim();
            String caraPenanggulangan = textViewCaraPenanggulangan.getText().toString().trim();
            String spasi = " \n";
            String url = imgPhoto;
            Image image = Image.getInstance(url);


            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = new Rectangle(0,454,276,515);
            Rectangle rect2 = new Rectangle(0,45, 276, 75);
            rect.setBorder(Rectangle.BOX);
            rect2.setBorder(Rectangle.BOX);
            rect.setBorderWidth(1);
            rect.setBackgroundColor(BaseColor.RED);
            rect2.setBackgroundColor(BaseColor.RED);
            rect.setBorderColor(BaseColor.RED);
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
            catFont.setColor(BaseColor.WHITE);
            Paragraph otherParagraph = new Paragraph("RED TAG \n Abnormality Tag", catFont);
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
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + nomorKontrol, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + bagianMesin, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + namaMesin, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + nomorMesin,isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + dipasangOleh, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + tglPasang, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + deskripsi, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            image.scaleToFit((float)100.0, (float)75.0);
            newPdf.add(image);
            newPdf.add(new Paragraph("  " + nomorworkrequest, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + picfollowup, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + dueDate, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph(spasi, isiFont));
            newPdf.add(new Paragraph("  " + caraPenanggulangan, isiFont));


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

                    try {
                        printPdf();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(this, "Akses Ditolak...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
