package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewFormActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView cardViewNewWhiteForm, cardViewNewRedForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_form);

        cardViewNewWhiteForm = findViewById(R.id.cardview_new_white_form);
        cardViewNewRedForm = findViewById(R.id.cardview_new_red_form);

        cardViewNewWhiteForm.setOnClickListener(this);
        cardViewNewRedForm.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(view == cardViewNewWhiteForm) {
            newWhiteForm();
        }

        else if (view == cardViewNewRedForm) {
            newRedForm();
        }
    }

    private void newRedForm() {
        Intent intent = new Intent(NewFormActivity.this, NewRedFormActivity.class);
        startActivity(intent);
    }

    private void newWhiteForm() {
        Intent intent = new Intent(NewFormActivity.this, NewWhiteFormActivity.class);
        startActivity(intent);
    }
}
