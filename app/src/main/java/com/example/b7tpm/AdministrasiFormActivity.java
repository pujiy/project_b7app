package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdministrasiFormActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardViewWhiteForm, cardViewRedForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrasi_form);

        cardViewWhiteForm = findViewById(R.id.cardview_administrasi_white_form);
        cardViewRedForm = findViewById(R.id.cardview_adminstrasi_red_form);

        cardViewRedForm.setOnClickListener(this);
        cardViewWhiteForm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == cardViewWhiteForm) {
            administrasiWhiteForm();
        }
        else if(view == cardViewRedForm) {
            administrasiRedForm();
        }


    }

    private void administrasiRedForm() {

    }

    private void administrasiWhiteForm() {
        Intent intent = new Intent(AdministrasiFormActivity.this, AdministrasiWhiteFormActivity.class);
        startActivity(intent);
    }
}
