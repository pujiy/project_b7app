package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StatusFormMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardViewStatusWhiteForm, cardViewStatusRedForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_form_menu);

        cardViewStatusWhiteForm = findViewById(R.id.cardview_status_white_form);
        cardViewStatusRedForm = findViewById(R.id.cardview_status_red_form);

        cardViewStatusWhiteForm.setOnClickListener(this);
        cardViewStatusRedForm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == cardViewStatusWhiteForm) {
            statusWhiteForm();
        }

        else if (view == cardViewStatusRedForm) {
            statusRedForm();
        }
    }

    private void statusRedForm() {
        Intent intent = new Intent(StatusFormMenuActivity.this, StatusRedFormActivity.class);
        startActivity(intent);
    }

    private void statusWhiteForm() {
        Intent intent = new Intent(StatusFormMenuActivity.this, StatusWhiteFormActivity.class);
        startActivity(intent);
    }
}
