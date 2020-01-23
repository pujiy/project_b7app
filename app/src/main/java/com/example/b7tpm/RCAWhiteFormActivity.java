package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.b7tpm.Adapter.RCAWhiteFormAdapter;
import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.WhiteFormClose;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RCAWhiteFormActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_FOLDER = "folder";

    private CardView cardViewWhiteForm, cardViewRedForm;

    private RecyclerView recyclerViewWhiteForms;
    private RCAWhiteFormAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rca_white_form);

        recyclerViewWhiteForms = findViewById(R.id.rv_rcawhiteform);
        recyclerViewWhiteForms.setHasFixedSize(true);
        recyclerViewWhiteForms.setLayoutManager(new LinearLayoutManager(this));

        final String folder = getIntent().getStringExtra(EXTRA_FOLDER);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<WhiteFormClose> call = service.getWhiteFormClose();

        call.enqueue(new Callback<WhiteFormClose>() {
            @Override
            public void onResponse(Call<WhiteFormClose> call, Response<WhiteFormClose> response) {
                adapter = new RCAWhiteFormAdapter(response.body().getWhiteformclose(), getApplicationContext());
                /*String newText = "Mesin Guerin";
                adapter.getFilter().filter(newText); */
                recyclerViewWhiteForms.setAdapter(adapter);
                adapter.getFilter().filter(folder);

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<WhiteFormClose> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {

    }

    @Override
    public void onClick(View v) {

    }
}
