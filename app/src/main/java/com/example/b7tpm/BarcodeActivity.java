package com.example.b7tpm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarcodeActivity extends AppCompatActivity implements View.OnClickListener {

    //View Objects
    private Button buttonScan;
    private TextView textViewNomorMesin, textViewNamaMesin, textViewMerkMesin, textViewKapasitasMesin, textViewJenisMesin, textViewFungsiMesin;
    //qr code scanner object
    private IntentIntegrator qrScan;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data); }
    }

    @Override
    public void onClick(View v) {
        qrScan.initiateScan();

    }
}
