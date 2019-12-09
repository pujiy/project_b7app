package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.b7tpm.Adapter.GridMenuAdapter;
import com.example.b7tpm.Adapter.MenuGridData;
import com.example.b7tpm.Model.MenuGrid;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rv_menugrid;
    private ArrayList<MenuGrid> list = new ArrayList<>();
    private ImageView imageViewLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageViewLogout = findViewById(R.id.iv_logout);

        findViewById(R.id.iv_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        rv_menugrid = findViewById(R.id.rv_menu_grid);
        rv_menugrid.setHasFixedSize(true);

        list.addAll(MenuGridData.getListData());
        showRecyclerGrid();
    }

    private void showRecyclerGrid() {

        rv_menugrid.setLayoutManager(new GridLayoutManager(this, 3));
        GridMenuAdapter gridHeroAdapter = new GridMenuAdapter(list);
        rv_menugrid.setAdapter(gridHeroAdapter);
    }
}
