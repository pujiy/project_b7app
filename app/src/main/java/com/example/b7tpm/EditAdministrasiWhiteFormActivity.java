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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
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

public class EditAdministrasiWhiteFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_FORMID = "formid";
    public static final String EXTRA_NOMORKONTROL = "nomorkontrol";
    public static final String EXTRA_BAGIANMESIN = "bagianmesin";
    public static final String EXTRA_NAMAMESIN = "namamesin";
    public static final String EXTRA_NOMORMESIN = "nomormesin";
    public static final String EXTRA_DIPASANGOLEH = "dipasangoleh";
    public static final String EXTRA_TGLPASANG = "tglpasang";
    public static final String EXTRA_DESKRIPSI = "deskripsi";
    public static final String EXTRA_DUEDATE = "duedate";
    public static final String EXTRA_CARAPENANGGULANGAN = "cara penanggulangan";
    public static final String EXTRA_PHOTO = "photo";

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button btnsubmitwhiteform;
    private ImageView imageViewPhoto;
    private ProgressBar progressBar;
    private String photoUrl;
    private TextView textViewTanggalPasang, textViewDueDate, textViewImage;
    private EditText editTextNomorKontrol, editTextBagianMesin, editTextNomorMesin, editTextdipasangoleh, editTextdeskripsi, editTextCaraPenanggulangan;
    private Spinner spnamamesin;
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
        setContentView(R.layout.activity_edit_administrasi_white_form);

        mButtonChooseImage = findViewById(R.id.btnuploadgambar);
        imageViewPhoto = findViewById(R.id.ivimage);
        btnsubmitwhiteform = findViewById(R.id.btn_submit_white_form);
        progressBar = findViewById(R.id.progress_bar);
        textViewTanggalPasang = findViewById(R.id.tvtglpasang);
        textViewDueDate = findViewById(R.id.tvduedate);
        textViewImage = findViewById(R.id.tv_image);
        editTextNomorKontrol = findViewById(R.id.edtnomorkontrol);
        editTextBagianMesin = findViewById(R.id.edtbagianmesin);
        editTextNomorMesin = findViewById(R.id.edtnomormesin);
        spnamamesin = findViewById(R.id.spnamamesin);
        editTextdipasangoleh = findViewById(R.id.edtdipasangoleh);
        editTextdeskripsi = findViewById(R.id.edtdeskripsi);
        editTextCaraPenanggulangan = findViewById(R.id.edtcarapenanggulangan);
        String photo = photoUrl;
        storageReference = FirebaseStorage.getInstance().getReference("uploadphotoswhiteform");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadphotoswhiteform");

        final int formid = getIntent().getIntExtra(EXTRA_FORMID, 0);
        final String nomorkontrol = getIntent().getStringExtra(EXTRA_NOMORKONTROL);
        final String bagianmesin = getIntent().getStringExtra(EXTRA_BAGIANMESIN);
        final String namamesin = getIntent().getStringExtra(EXTRA_NAMAMESIN);
        final String nomormesin = getIntent().getStringExtra(EXTRA_NOMORMESIN);
        final String dipasangoleh = getIntent().getStringExtra(EXTRA_DIPASANGOLEH);
        final String tglpasang = getIntent().getStringExtra(EXTRA_TGLPASANG);
        final String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        final String duedate = getIntent().getStringExtra(EXTRA_DUEDATE);
        final String carapenanggulangan = getIntent().getStringExtra(EXTRA_CARAPENANGGULANGAN);

        editTextNomorKontrol.setText(nomorkontrol);
        editTextBagianMesin.setText(bagianmesin);
        editTextNomorMesin.setText(nomormesin);
        editTextdipasangoleh.setText(dipasangoleh);
        textViewTanggalPasang.setText(tglpasang);
        editTextdeskripsi.setText(deskripsi);
        textViewDueDate.setText(duedate);
        editTextCaraPenanggulangan.setText(carapenanggulangan);

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


        //Button kirim data baru
        btnsubmitwhiteform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
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

        //spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.namamesin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnamamesin.setAdapter(adapter);

        // spinner on click

        spnamamesin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Spinner Selected Item",""+spnamamesin.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void uploadFile() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Upload Photo...");
        progressDialog.show();

        // getting values

        String nomor_kontrol = editTextNomorKontrol.getText().toString().trim();
        String bagian_mesin = editTextBagianMesin.getText().toString().trim();
        String nomor_mesin = editTextNomorMesin.getText().toString().trim();
        String dipasang_oleh = editTextdipasangoleh.getText().toString().trim();
        String tgl_pasang = textViewTanggalPasang.getText().toString().trim();
        String deskripsi = editTextdeskripsi.getText().toString().trim();
        String due_date = textViewDueDate.getText().toString().trim();
        String cara_penanggulangan =  editTextCaraPenanggulangan.getText().toString().trim();

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

        if (TextUtils.isEmpty(nomor_mesin)) {
            editTextNomorMesin.setError("Masukkan Nomor Mesin");
            editTextNomorMesin.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(dipasang_oleh)) {
            editTextdipasangoleh.setError("Masukkan Nama");
            editTextdipasangoleh.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(deskripsi)) {
            editTextdeskripsi.setError("Masukkan Deskripsi");
            editTextdeskripsi.requestFocus();
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
            Toast.makeText(EditAdministrasiWhiteFormActivity.this, "Masukkan Tanggal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textViewDueDate.getText().toString().matches("")) {
            textViewDueDate.setError("Masukkan Tanggal");
            textViewDueDate.requestFocus();
            progressDialog.dismiss();
            Toast.makeText(EditAdministrasiWhiteFormActivity.this, "Masukkan Tanggal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textViewImage.getText().toString().matches("")) {
            textViewImage.setError("Masukkan Foto");
            textViewImage.requestFocus();
            progressDialog.dismiss();
            Toast.makeText(EditAdministrasiWhiteFormActivity.this, "Masukkan Foto", Toast.LENGTH_SHORT).show();
            return;
        }

        //Method to upload file
        if(imageUri !=null) {



            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
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
                            UploadPhotoWhiteForm upload = new UploadPhotoWhiteForm(editTextNomorKontrol.getText().toString().trim(),
                                    storageReference.getDownloadUrl().toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    photoUrl = downloadUrl.toString();
                                    progressDialog.dismiss();
                                    Toast.makeText(EditAdministrasiWhiteFormActivity.this, "Upload photo successful", Toast.LENGTH_LONG).show();
                                    EditWhiteForm();

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditAdministrasiWhiteFormActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void EditWhiteForm() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending New Form...");
        progressDialog.show();

        //getting new white form values

        int formid = getIntent().getIntExtra(EXTRA_FORMID, 0);
        String nomor_kontrol = editTextNomorKontrol.getText().toString().trim();
        String bagian_mesin = editTextBagianMesin.getText().toString().trim();
        String nama_mesin = spnamamesin.getSelectedItem().toString();
        String nomor_mesin = editTextNomorMesin.getText().toString().trim();
        String dipasang_oleh = editTextdipasangoleh.getText().toString().trim();
        String tgl_pasang = textViewTanggalPasang.getText().toString().trim();
        String deskripsi = editTextdeskripsi.getText().toString().trim();
        String photo = photoUrl;
        String due_date = textViewDueDate.getText().toString().trim();
        String cara_penanggulangan =  editTextCaraPenanggulangan.getText().toString().trim();

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

        if (TextUtils.isEmpty(nomor_mesin)) {
            editTextNomorMesin.setError("Masukkan Nomor Mesin");
            editTextNomorMesin.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(dipasang_oleh)) {
            editTextdipasangoleh.setError("Masukkan Nama");
            editTextdipasangoleh.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(deskripsi)) {
            editTextdeskripsi.setError("Masukkan Deskripsi");
            editTextdeskripsi.requestFocus();
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
            Toast.makeText(EditAdministrasiWhiteFormActivity.this, "Masukkan Tanggal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textViewDueDate.getText().toString().matches("")) {
            textViewDueDate.setError("Masukkan Tanggal");
            textViewDueDate.requestFocus();
            progressDialog.dismiss();
            Toast.makeText(EditAdministrasiWhiteFormActivity.this, "Masukkan Tanggal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textViewImage.getText().toString().matches("")) {
            textViewImage.setError("Masukkan Foto");
            textViewImage.requestFocus();
            progressDialog.dismiss();
            Toast.makeText(EditAdministrasiWhiteFormActivity.this, "Masukkan Foto", Toast.LENGTH_SHORT).show();
            return;
        }

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Call<NewWhiteFormResponse> call = service.updateWhiteForm(
                formid,
                nomor_kontrol,
                bagian_mesin,
                nama_mesin,
                nomor_mesin,
                dipasang_oleh,
                tgl_pasang,
                deskripsi,
                photo,
                due_date,
                cara_penanggulangan
        );

        //calling the api
        call.enqueue(new Callback<NewWhiteFormResponse>() {
            @Override
            public void onResponse(Call<NewWhiteFormResponse> call, Response<NewWhiteFormResponse> response) {

                progressDialog.dismiss();
                //displaying the message from the response
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<NewWhiteFormResponse> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    // Get File Extension
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

    public void setFlag(int i) {
        flag = i;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        int month1 = month + 1;

        if(flag == TANGGAL_PASANG) {
            TextView tvtglpasang = findViewById(R.id.tvtglpasang);
            tvtglpasang.setText(year+"-"+month1+"-"+dayOfMonth);

        }
        else if (flag == DUE_DATE) {
            TextView tvdueDate = findViewById(R.id.tvduedate);
            tvdueDate.setText(year+"-"+month1+"-"+dayOfMonth);
        }
    }


}
