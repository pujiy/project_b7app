package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.b7tpm.Adapter.StatusRedFormAdapter;
import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.StatusRedForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusRedFormActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStatusRedForm;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_red_form);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        recyclerViewStatusRedForm = findViewById(R.id.rv_statusredform);
        recyclerViewStatusRedForm.setHasFixedSize(true);
        recyclerViewStatusRedForm.setLayoutManager(new LinearLayoutManager(this));

        //Building Retrofit Object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<StatusRedForm> call = service.getStatusRedForm();

        call.enqueue(new Callback<StatusRedForm>() {
            @Override
            public void onResponse(Call<StatusRedForm> call, Response<StatusRedForm> response) {
                progressDialog.dismiss();
                adapter = new StatusRedFormAdapter(response.body().getRedformstatus(), getApplicationContext());
                recyclerViewStatusRedForm.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<StatusRedForm> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
}

