package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.b7tpm.Adapter.StatusLevelUserAdapter;
import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.StatusUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusLevelUserActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListUsers;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_level_user);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        recyclerViewListUsers = findViewById(R.id.rv_statusleveluser);
        recyclerViewListUsers.setHasFixedSize(true);
        recyclerViewListUsers.setLayoutManager(new LinearLayoutManager(this));

        //Building Retrofit Object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<StatusUser> call = service.getStatusUser();

        call.enqueue(new Callback<StatusUser>() {
            @Override
            public void onResponse(Call<StatusUser> call, Response<StatusUser> response) {
                progressDialog.dismiss();
                adapter = new StatusLevelUserAdapter(response.body().getUsers(), getApplicationContext());
                recyclerViewListUsers.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<StatusUser> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
