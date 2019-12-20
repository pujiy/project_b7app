package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class InformasiTPMActivity extends AppCompatActivity {

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_tpm);

        pdfView = findViewById(R.id.pdfviewer);
        pdfView.fromAsset("demo.pdf").load();
    }
}
