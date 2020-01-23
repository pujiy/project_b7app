package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuRCARedFormsCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardViewMesinGuerin, cardViewMesinCompounding, cardViewMesinFilling, cardViewMesinKemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rcared_form_category);

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
            Intent moveDataWithIntent = new Intent (MenuRCARedFormsCategoryActivity.this, RCARedFormActivity.class);
            moveDataWithIntent.putExtra(RCARedFormActivity.EXTRA_FOLDER, "Mesin Guerin");
            moveDataWithIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MenuRCARedFormsCategoryActivity.this.startActivity(moveDataWithIntent);
        }

        else if (view == cardViewMesinCompounding) {
            Intent moveDataWithIntent = new Intent (MenuRCARedFormsCategoryActivity.this, RCARedFormActivity.class);
            moveDataWithIntent.putExtra(RCARedFormActivity.EXTRA_FOLDER, "Mesin Compounding");
            moveDataWithIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MenuRCARedFormsCategoryActivity.this.startActivity(moveDataWithIntent);
        }

        else if (view == cardViewMesinFilling) {
            Intent moveDataWithIntent = new Intent (MenuRCARedFormsCategoryActivity.this, RCARedFormActivity.class);
            moveDataWithIntent.putExtra(RCARedFormActivity.EXTRA_FOLDER, "Mesin Filling");
            moveDataWithIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MenuRCARedFormsCategoryActivity.this.startActivity(moveDataWithIntent);
        }

        else if (view == cardViewMesinKemas) {
            Intent moveDataWithIntent = new Intent (MenuRCARedFormsCategoryActivity.this, RCARedFormActivity.class);
            moveDataWithIntent.putExtra(RCARedFormActivity.EXTRA_FOLDER, "Mesin Kemas");
            moveDataWithIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MenuRCARedFormsCategoryActivity.this.startActivity(moveDataWithIntent);
        }
    }
}
