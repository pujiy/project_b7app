package com.example.b7tpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class NewWhiteFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button btnsubmitwhiteform;
    private ImageView imageViewPhoto;
    private ProgressBar progressBar;
    private EditText editTextNomorKontrol;

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
        setContentView(R.layout.activity_new_white_form);

        mButtonChooseImage = findViewById(R.id.btnuploadgambar);
        imageViewPhoto = findViewById(R.id.ivimage);
        btnsubmitwhiteform = findViewById(R.id.btn_submit_white_form);
        progressBar = findViewById(R.id.progress_bar);
        editTextNomorKontrol = findViewById(R.id.edtnomorkontrol);
        storageReference = FirebaseStorage.getInstance().getReference("uploadphotoswhiteform");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadphotoswhiteform");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        Button btntglpasang = findViewById(R.id.btntglpasang);
        btntglpasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFlag(TANGGAL_PASANG);
                DialogFragment tglPasangPicker = new DatePickerFragment();
                tglPasangPicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        
        btnsubmitwhiteform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(uploadTask !=null && uploadTask.isInProgress()) {
                    Toast.makeText(NewWhiteFormActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }
        });

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

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
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
                            Toast.makeText(NewWhiteFormActivity.this, "Upload photo successful", Toast.LENGTH_LONG).show();
                            UploadPhotoWhiteForm upload = new UploadPhotoWhiteForm(editTextNomorKontrol.getText().toString().trim(),
                                    storageReference.getDownloadUrl().toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewWhiteFormActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void setFlag(int i) {
        flag = i;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() !=null) {
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(imageViewPhoto);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        if(flag == TANGGAL_PASANG) {
            TextView tvtglpasang = findViewById(R.id.tvtglpasang);
            tvtglpasang.setText(currentDateString);
        }
        else if (flag == DUE_DATE) {
            TextView tvdueDate = findViewById(R.id.tvduedate);
            tvdueDate.setText(currentDateString);
        }


    }
}
