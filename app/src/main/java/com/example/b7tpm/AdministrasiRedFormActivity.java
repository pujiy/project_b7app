package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.b7tpm.Adapter.AdministrasiRedFormAdapter;
import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.AdministrasiRedForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdministrasiRedFormActivity extends AppCompatActivity {

    private CardView cardViewWhiteForm, cardViewRedForm;

    private RecyclerView recyclerViewAdministrasiRedForms;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrasi_red_form);

        recyclerViewAdministrasiRedForms = findViewById(R.id.rv_administrasiredform);
        recyclerViewAdministrasiRedForms.setHasFixedSize(true);
        recyclerViewAdministrasiRedForms.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<AdministrasiRedForm> call = service.getAdministrasiRedForm();

        call.enqueue(new Callback<AdministrasiRedForm>() {
            @Override
            public void onResponse(Call<AdministrasiRedForm> call, Response<AdministrasiRedForm> response) {
                adapter = new AdministrasiRedFormAdapter(response.body().getAllredform(), getApplicationContext());
                recyclerViewAdministrasiRedForms.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AdministrasiRedForm> call, Throwable t) {

                Toast.makeText(AdministrasiRedFormActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
