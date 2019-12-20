package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.Result;
import com.example.b7tpm.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignUp;
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextNik;
    private CheckBox checkBoxRegister;
    private int isUser, isSpv, isAdmin, isVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonSignUp = findViewById(R.id.btn_register);
        editTextUsername = findViewById(R.id.edt_username);
        editTextEmail = findViewById(R.id.edt_email);
        editTextNik = findViewById(R.id.edt_nik);
        editTextPassword = findViewById(R.id.edt_password_signup);
        checkBoxRegister = findViewById(R.id.cb_register);


        buttonSignUp.setOnClickListener(this);

    }

    private void userSignUp() {



        //getting the user values
        checkBoxRegister.isChecked();

        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String nik = editTextNik.getText().toString().trim();
        isUser = 1;
        isAdmin = 0;
        isSpv = 0;
        isVerified = 0;

        //validation
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nik)) {
            editTextNik.setError("Please enter nik");
            editTextNik.requestFocus();
            return;
        }

        if(!checkBoxRegister.isChecked()) {
            checkBoxRegister.setError("Please check checkbox");
            checkBoxRegister.requestFocus();
            return;
        }

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //
        User user = new User (username, email, password, nik, isUser, isSpv, isAdmin, isVerified );

        //defining the call
        Call<Result> call = service.createUser(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getNik(),
                user.getIsuser(),
                user.getIsspv(),
                user.getIsadmin()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                //displaying the message from the response
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignUp) {
            userSignUp();
        }

    }
}
