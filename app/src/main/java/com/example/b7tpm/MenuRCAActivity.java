package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuRCAActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_FOLDER = "folder";

    private CardView cardViewWhiteForm, cardViewRedForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rca);

        cardViewWhiteForm = findViewById(R.id.cardview_rca_white_form);
        cardViewRedForm = findViewById(R.id.cardview_rca_red_form);

        final String folder = getIntent().getStringExtra(EXTRA_FOLDER);

        cardViewWhiteForm.setOnClickListener(this);
        cardViewRedForm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == cardViewWhiteForm) {
            rcaWhiteForm();
        }

        else if (view == cardViewRedForm) {
            rcaRedForm();
        }
    }

    private void rcaRedForm() {
        Intent intent = new Intent(MenuRCAActivity.this, MenuRCARedFormsCategoryActivity.class);
        startActivity(intent);

    }

    private void rcaWhiteForm() {
        Intent intent = new Intent(MenuRCAActivity.this, MenuRCAWhiteFormsCategoryActivity.class);
        startActivity(intent);
    }
}
