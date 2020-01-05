package com.example.b7tpm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.DataMesin;
import com.example.b7tpm.Model.ResultDataMesin;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarcodeActivity extends AppCompatActivity implements View.OnClickListener {

    //View Objects
    private Button buttonScan;
    private TextView textViewNomorMesin, textViewNamaMesin, textViewMerkMesin, textViewKapasitasMesin, textViewJenisMesin, textViewFungsiMesin;
    //qr code scanner object
    private IntentIntegrator qrScan;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        buttonScan = findViewById(R.id.btn_scanqr);
        textViewNomorMesin = findViewById(R.id.tv_nomormesin);
        textViewNamaMesin = findViewById(R.id.tv_namamesin);
        textViewMerkMesin = findViewById(R.id.tv_merkmesin);
        textViewKapasitasMesin = findViewById(R.id.tv_kapasitasmesin);
        textViewJenisMesin = findViewById(R.id.tv_jenismesin);
        textViewFungsiMesin = findViewById(R.id.tv_fungsimesin);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                String nomorqr = result.getContents().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService service = retrofit.create(APIService.class);

                Call<DataMesin> call = service.getDataMesin();

                call.enqueue(new Callback<DataMesin>() {
                    @Override
                    public void onResponse(Call<DataMesin> call, Response<DataMesin> response) {

                        String nomor_mesin = response.body().getNomor_mesin();
                        String nama_mesin = response.body().getNama_mesin();
                        String merk_mesin = response.body().getMerk_mesin();
                        String kapasitas_mesin = response.body().getKapasitas_mesin();
                        String jenis_mesin = response.body().getJenis_mesin();
                        String fungsi_mesin = response.body().getFungsi_mesin();
                        textViewNamaMesin.setText(nama_mesin);
                        textViewNomorMesin.setText(nomor_mesin);
                        textViewMerkMesin.setText(merk_mesin);
                        textViewKapasitasMesin.setText(kapasitas_mesin);
                        textViewJenisMesin.setText(jenis_mesin);
                        textViewFungsiMesin.setText(fungsi_mesin);
                    }

                    @Override
                    public void onFailure(Call<DataMesin> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Get Data Fail", Toast.LENGTH_LONG).show();


                    }
                });


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data); }
    }



    @Override
    public void onClick(View v) {
        qrScan.initiateScan();

    }
}
