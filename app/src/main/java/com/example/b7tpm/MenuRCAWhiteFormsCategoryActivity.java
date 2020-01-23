package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuRCAWhiteFormsCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardViewMesinGuerin, cardViewMesinCompounding, cardViewMesinFilling, cardViewMesinKemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rcawhite_forms_category);

        cardViewMesinGuerin = findViewById(R.id.cv_mesinguerin);
        cardViewMesinCompounding = findViewById(R.id.cv_mesincompounding);
        cardViewMesinFilling = findViewById(R.id.cv_mesinfilling);
        cardViewMesinKemas = findViewById(R.id.cv_mesinkemas);

        cardViewMesinGuerin.setOnClickListener(this);
        cardViewMesinCompounding.setOnClickListener(this);
        cardViewMesinFilling.setOnClickListener(this);
        cardViewMesinKemas.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == cardViewMesinGuerin) {
            Intent moveDataWithIntent = new Intent (MenuRCAWhiteFormsCategoryActivity.this, RCAWhiteFormActivity.class);
            moveDataWithIntent.putExtra(RCAWhiteFormActivity.EXTRA_FOLDER, "Mesin Guerin");
            moveDataWithIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MenuRCAWhiteFormsCategoryActivity.this.startActivity(moveDataWithIntent);
        }

        else if (view == cardViewMesinCompounding) {
            Intent moveDataWithIntent = new Intent (MenuRCAWhiteFormsCategoryActivity.this, RCAWhiteFormActivity.class);
            moveDataWithIntent.putExtra(RCAWhiteFormActivity.EXTRA_FOLDER, "Mesin Compounding");
            moveDataWithIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MenuRCAWhiteFormsCategoryActivity.this.startActivity(moveDataWithIntent);
        }

        else if (view == cardViewMesinFilling) {
            Intent moveDataWithIntent = new Intent (MenuRCAWhiteFormsCategoryActivity.this, RCAWhiteFormActivity.class);
            moveDataWithIntent.putExtra(RCAWhiteFormActivity.EXTRA_FOLDER, "Mesin Filling");
            moveDataWithIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MenuRCAWhiteFormsCategoryActivity.this.startActivity(moveDataWithIntent);
        }

        else if (view == cardViewMesinKemas) {
            Intent moveDataWithIntent = new Intent (MenuRCAWhiteFormsCategoryActivity.this, RCAWhiteFormActivity.class);
            moveDataWithIntent.putExtra(RCAWhiteFormActivity.EXTRA_FOLDER, "Mesin Kemas");
            moveDataWithIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MenuRCAWhiteFormsCategoryActivity.this.startActivity(moveDataWithIntent);
        }

    }
}
