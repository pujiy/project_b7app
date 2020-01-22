package com.example.b7tpm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Helper.SharedPrefManager;
import com.example.b7tpm.Model.DeleteUserResponse;
import com.example.b7tpm.Model.UpdateLevelUserResponse;
import com.example.b7tpm.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailStatusLevelUserActivity extends AppCompatActivity {

    public static final String EXTRA_NIK = "extranik";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_ISLEVEL = "level";
    public String[] listStatus = {"User", "Admin", "Spv"};
    public int isuser = 0;
    public int isadmin = 0;
    public int isspv = 0;

    private TextView textViewNik, textViewUsername, textViewEmail, textViewLevel;
    private Button btnEdit, btnDelete;
    private Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status_level_user);

        textViewNik = findViewById(R.id.tv_nik);
        textViewUsername = findViewById(R.id.tv_username);
        textViewEmail = findViewById(R.id.tv_email);
        textViewLevel = findViewById(R.id.tv_level);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);

        final String nik = getIntent().getStringExtra(EXTRA_NIK);
        final String username = getIntent().getStringExtra(EXTRA_USERNAME);
        final String email = getIntent().getStringExtra(EXTRA_EMAIL);
        final String islevel = getIntent().getStringExtra(EXTRA_ISLEVEL);

        textViewNik.setText(nik);
        textViewUsername.setText(username);
        textViewEmail.setText(email);
        textViewLevel.setText(islevel);



        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        if (user.getIsuser() == 0) {
            btnEdit.setEnabled(true);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailStatusLevelUserActivity.this);
                builder.setTitle("Update Level");
                builder.setItems(listStatus, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                isuser = 1;
                                isadmin = 0;
                                isspv = 0;
                                break;

                            case 1:
                                isuser = 0;
                                isadmin = 1;
                                isspv = 0;
                                break;

                            case 2:
                                isuser = 0;
                                isadmin = 0;
                                isspv = 1;

                        }

                        //building retrofit object
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIUrl.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        //Defining retrofit api service
                        APIService service = retrofit.create(APIService.class);

                        Call<UpdateLevelUserResponse> call = service.updateLevelUser(
                                email,
                                isuser,
                                isadmin,
                                isspv
                        );

                        call.enqueue(new Callback<UpdateLevelUserResponse>() {
                            @Override
                            public void onResponse(Call<UpdateLevelUserResponse> call, Response<UpdateLevelUserResponse> response) {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<UpdateLevelUserResponse> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Method to button delete

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = SharedPrefManager.getInstance(DetailStatusLevelUserActivity.this).getUser();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        DetailStatusLevelUserActivity.this);

                if (user.getIsuser() == 0) {

                    // set title dialog
                    alertDialogBuilder.setTitle("Delete User");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Apakah kamu ingin hapus user?")
                            .setIcon(R.mipmap.ic_b7tpm)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //building retrofit object
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(APIUrl.BASE_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    //Defining retrofit api service
                                    APIService service = retrofit.create(APIService.class);

                                    Call<DeleteUserResponse> call = service.deleteUser(
                                            email
                                    );

                                    call.enqueue(new Callback<DeleteUserResponse>() {
                                        @Override
                                        public void onResponse(Call<DeleteUserResponse> call, Response<DeleteUserResponse> response) {
                                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(DetailStatusLevelUserActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(Call<DeleteUserResponse> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                            /*.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // do something
                                    dialogInterface.cancel();
                                }
                            }); */


                }

                else if(user.getIsuser() == 1) {


                    // set title dialog
                    alertDialogBuilder.setTitle("Delete User");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Dibutuhkan akses admin")
                            .setIcon(R.mipmap.ic_b7tpm)
                            .setCancelable(false)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // do something
                                    dialogInterface.cancel();
                                }
                            });

                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();
                }
            }
        });
    }

}
