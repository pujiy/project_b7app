package com.example.b7tpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.NewRedFormResponse;
import com.example.b7tpm.Model.NewWhiteFormResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewRedFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button btnsubmitwhiteform;
    private ImageView imageViewPhoto;
    private ProgressBar progressBar;
    private String photo;
    private int isAdmin = 0;
    private int isSpv = 0;
    private TextView textViewTanggalPasang, textViewDueDate, textViewImage;
    private EditText editTextNomorKontrol, editTextBagianMesin, editTextDipasangoleh, editTextPicFollowUp, editTextNomorWorkRequest, editTextDeskripsi, editTextCaraPenanggulangan;

    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private StorageTask uploadTask;

    public static final int TANGGAL_PASANG = 0;
    public static final int DUE_DATE = 1;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_red_form);


        mButtonChooseImage = findViewById(R.id.btnuploadgambar);
        imageViewPhoto = findViewById(R.id.ivimage);
        btnsubmitwhiteform = findViewById(R.id.btn_submit_white_form);
        progressBar = findViewById(R.id.progress_bar);
        textViewTanggalPasang = findViewById(R.id.tvtglpasang);
        textViewDueDate = findViewById(R.id.tvduedate);
        textViewImage = findViewById(R.id.tv_image);
        editTextNomorKontrol = findViewById(R.id.edtnomorkontrol);
        editTextBagianMesin = findViewById(R.id.edtbagianmesin);
        editTextDipasangoleh = findViewById(R.id.edtdipasangoleh);
        editTextDeskripsi = findViewById(R.id.edtdeskripsi);
        editTextNomorWorkRequest = findViewById(R.id.edtnomorworkrequest);
        editTextPicFollowUp = findViewById(R.id.edtpicfollowup);
        editTextCaraPenanggulangan = findViewById(R.id.edtcarapenanggulangan);
        String photo = "photo";
        storageReference = FirebaseStorage.getInstance().getReference("uploadphotosredform");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadphotosredform");



        //Button kirim data baru
        btnsubmitwhiteform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRedForm();
            }
        });


        // button select image
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        //Button Tanggal Pemasangan On Click
        Button btntglpasang = findViewById(R.id.btntglpasang);
        btntglpasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFlag(TANGGAL_PASANG);
                DialogFragment tglPasangPicker = new DatePickerFragment();
                tglPasangPicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //Button tanggal due date
        Button btnDueDate = findViewById(R.id.btnduedate);
        btnDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFlag(DUE_DATE);
                DialogFragment dueDatePicker = new DatePickerFragment();
                dueDatePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //Function data red form baru
    private void newRedForm() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending New Form...");
        progressDialog.show();

        //getting new white form values

        String nomor_kontrol = editTextNomorKontrol.getText().toString().trim();
        String bagian_mesin = editTextBagianMesin.getText().toString().trim();
        String dipasang_oleh = editTextDipasangoleh.getText().toString().trim();
        String tgl_pasang = textViewTanggalPasang.getText().toString().trim();
        String deskripsi = editTextDeskripsi.getText().toString().trim();
        String photo = "photo";
        String due_date = textViewDueDate.getText().toString().trim();
        String nomor_work_request = editTextNomorWorkRequest.toString().trim();
        String pic_follow_up = editTextPicFollowUp.toString().trim();
        String cara_penanggulangan =  editTextCaraPenanggulangan.toString().trim();

        //validation

        if (TextUtils.isEmpty(nomor_kontrol)) {
            editTextNomorKontrol.setError("Masukkan Nomor Kontrol");
            editTextNomorKontrol.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(bagian_mesin)) {
            editTextBagianMesin.setError("Masukkan Bagian Mesin");
            editTextBagianMesin.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(dipasang_oleh)) {
            editTextDipasangoleh.setError("Masukkan Nama");
            editTextDipasangoleh.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(deskripsi)) {
            editTextDeskripsi.setError("Masukkan Deskripsi");
            editTextDeskripsi.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(cara_penanggulangan)) {
            editTextCaraPenanggulangan.setError("Masukkan Data");
            editTextCaraPenanggulangan.requestFocus();
            progressDialog.dismiss();
            return;
        }



        if (textViewTanggalPasang.getText().toString().matches("")) {
            textViewTanggalPasang.setError("Masukkan Tanggal");
            textViewTanggalPasang.requestFocus();
            progressDialog.dismiss();
            Toast.makeText(NewRedFormActivity.this, "Masukkan Tanggal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(nomor_work_request)) {
            editTextNomorWorkRequest.setError("Masukkan Nomor Work Request");
            editTextNomorWorkRequest.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(pic_follow_up)) {
            editTextPicFollowUp.setError("Masukkan PIC Follow Up");
            editTextPicFollowUp.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (textViewDueDate.getText().toString().matches("")) {
            textViewDueDate.setError("Masukkan Tanggal");
            textViewDueDate.requestFocus();
            progressDialog.dismiss();
            Toast.makeText(NewRedFormActivity.this, "Masukkan Tanggal", Toast.LENGTH_SHORT).show();
            return;
        }



        if (textViewImage.getText().toString().matches("")) {
            textViewImage.setError("Masukkan Foto");
            textViewImage.requestFocus();
            progressDialog.dismiss();
            Toast.makeText(NewRedFormActivity.this, "Masukkan Foto", Toast.LENGTH_SHORT).show();
            return;
        }


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Call<NewRedFormResponse> call = service.sendNewRedForm(
                nomor_kontrol,
                bagian_mesin,
                dipasang_oleh,
                tgl_pasang,
                deskripsi,
                photo,
                due_date,
                nomor_work_request,
                pic_follow_up,
                cara_penanggulangan
        );

        //Calling the api
        call.enqueue(new Callback<NewRedFormResponse>() {
            @Override
            public void onResponse(Call<NewRedFormResponse> call, Response<NewRedFormResponse> response) {
                progressDialog.dismiss();
                //displaying the message from the response
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();


                //Progress upload image
                if(uploadTask !=null && uploadTask.isInProgress()) {
                    Toast.makeText(NewRedFormActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }

            @Override
            public void onFailure(Call<NewRedFormResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }



    private void uploadFile() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Upload Photo...");
        progressDialog.show();

        if(imageUri !=null) {



            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+ getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);
                            progressDialog.dismiss();
                            Toast.makeText(NewRedFormActivity.this, "Upload photo successful", Toast.LENGTH_LONG).show();
                            UploadPhotoWhiteForm upload = new UploadPhotoWhiteForm(editTextNomorKontrol.getText().toString().trim(),
                                    storageReference.getDownloadUrl().toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(NewRedFormActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {

            progressDialog.dismiss();
            Toast.makeText(this, "Tidak ada foto yang dipilih", Toast.LENGTH_SHORT).show();
        }
    }


    // Get File Extension
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    // Set Flag
    public void setFlag(int i) {
        flag = i;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() !=null) {
            imageUri = data.getData();
            textViewImage.setText("1 Foto Dipilih");
            Picasso.get().load(imageUri).into(imageViewPhoto);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());

        if(flag == TANGGAL_PASANG) {
            TextView tvtglpasang = findViewById(R.id.tvtglpasang);
            tvtglpasang.setText(year+"-"+month+"-"+dayOfMonth);

        }
        else if (flag == DUE_DATE) {
            TextView tvdueDate = findViewById(R.id.tvduedate);
            tvdueDate.setText(currentDateString);
        }

    }
}
