package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.b7tpm.Adapter.AdministrasiWhiteFormAdapter;
import com.example.b7tpm.Adapter.StatusWhiteFormAdapter;
import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.StatusWhiteForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusWhiteFormActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStatusWhiteForm;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_white_form);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        recyclerViewStatusWhiteForm = findViewById(R.id.rv_statuswhiteform);
        recyclerViewStatusWhiteForm.setHasFixedSize(true);
        recyclerViewStatusWhiteForm.setLayoutManager(new LinearLayoutManager(this));




        //Building Retrofit Object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<StatusWhiteForm> call = service.getStatusWhiteForm();

        call.enqueue(new Callback<StatusWhiteForm>() {
            @Override
            public void onResponse(Call<StatusWhiteForm> call, Response<StatusWhiteForm> response) {
                progressDialog.dismiss();
                adapter = new StatusWhiteFormAdapter(response.body().getWhiteformstatus(), getApplicationContext());
                recyclerViewStatusWhiteForm.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<StatusWhiteForm> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
}
