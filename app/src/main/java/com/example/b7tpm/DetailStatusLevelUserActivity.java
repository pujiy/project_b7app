package com.example.b7tpm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Helper.SharedPrefManager;
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
    public String[] listStatus ={"User", "Admin", "Spv"};
    public int isuser = 0;
    public int isadmin = 0;
    public int isspv = 0;

    private TextView textViewNik, textViewUsername, textViewEmail, textViewLevel;
    private Button btnEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status_level_user);

        textViewNik = findViewById(R.id.tv_nik);
        textViewUsername = findViewById(R.id.tv_username);
        textViewEmail = findViewById(R.id.tv_email);
        textViewLevel = findViewById(R.id.tv_level);
        btnEdit = findViewById(R.id.btn_edit);

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

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                            }
                        });

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
