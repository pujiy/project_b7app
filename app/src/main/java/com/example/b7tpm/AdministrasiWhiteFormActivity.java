package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.b7tpm.Adapter.AdministrasiWhiteFormAdapter;
import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.AdministrasiWhiteForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdministrasiWhiteFormActivity extends AppCompatActivity {

    private CardView cardViewWhiteForm, cardViewRedForm;

    private RecyclerView recyclerViewAdministrasiWhiteForms;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrasi_white_form);

        recyclerViewAdministrasiWhiteForms = findViewById(R.id.rv_administrasiwhiteform);
        recyclerViewAdministrasiWhiteForms.setHasFixedSize(true);
        recyclerViewAdministrasiWhiteForms.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<AdministrasiWhiteForm> call = service.getAdministrasiWhiteForm();

        call.enqueue(new Callback<AdministrasiWhiteForm>() {
            @Override
            public void onResponse(Call<AdministrasiWhiteForm> call, Response<AdministrasiWhiteForm> response) {
                adapter = new AdministrasiWhiteFormAdapter(response.body().getAllwhiteform(), getApplicationContext());
                recyclerViewAdministrasiWhiteForms.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AdministrasiWhiteForm> call, Throwable t) {

            }
        });

    }
}
