package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.b7tpm.Adapter.RCARedFormAdapter;
import com.example.b7tpm.Adapter.RCAWhiteFormAdapter;
import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.RedFormClose;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RCARedFormActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewRedForm;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcared_form);

        recyclerViewRedForm = findViewById(R.id.rv_rcaredform);
        recyclerViewRedForm.setHasFixedSize(true);
        recyclerViewRedForm.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<RedFormClose> call = service.getRedFormClose();

        call.enqueue(new Callback<RedFormClose>() {
            @Override
            public void onResponse(Call<RedFormClose> call, Response<RedFormClose> response) {
                adapter = new RCARedFormAdapter(response.body().getRedformclose(), getApplicationContext());
                recyclerViewRedForm.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RedFormClose> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
