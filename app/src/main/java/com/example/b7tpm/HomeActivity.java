package com.example.b7tpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Adapter.GridMenuAdapter;
import com.example.b7tpm.Adapter.MenuGridData;
import com.example.b7tpm.Helper.SharedPrefManager;
import com.example.b7tpm.Model.MenuGrid;
import com.example.b7tpm.Model.User;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rv_menugrid;
    private ArrayList<MenuGrid> list = new ArrayList<>();
    private Button buttonLogout;
    private TextView textViewUsername, textViewLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewUsername = findViewById(R.id.tv_username);
        textViewLevel = findViewById(R.id.tv_level);
        buttonLogout = findViewById(R.id.btn_logout);

        if(getIntent().getBooleanExtra("EXITAPP", false)) {
            this.finish();
        }

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        if (user.getIsuser() == 1) {
            textViewLevel.setText("User");
        } else if (user.getIsspv() == 1) {
            textViewLevel.setText("SPV");
        } else if (user.getIsadmin() == 1) {
            textViewLevel.setText("Admin");
        }

        //setting the values to the textviews

        textViewUsername.setText("Selamat Datang, " + user.getUsername());

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(getApplicationContext()).logout();
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

        gridHeroAdapter.setOnItemClickCallback(new GridMenuAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MenuGrid data) {
                showSelectedMenu(data);
            }
        });
    }


    private void showSelectedMenu(MenuGrid menuGrid) {
        if (menuGrid.getName() == "Buat Form Baru") {
            Intent intent = new Intent(HomeActivity.this, NewFormActivity.class);
            startActivity(intent);
        }

        if (menuGrid.getName() == "Administrasi Form") {
            Intent intent = new Intent(HomeActivity.this, AdministrasiFormActivity.class);
            startActivity(intent);
        }

        if (menuGrid.getName() == "Status Form") {
            Intent intent = new Intent(HomeActivity.this, StatusFormMenuActivity.class);
            startActivity(intent);
        }

        if (menuGrid.getName() == "Informasi Mesin dan Scan QR") {
            Intent intent = new Intent(HomeActivity.this, BarcodeActivity.class);
            startActivity(intent);
        }

        if (menuGrid.getName() == "Informasi TPM") {
            Intent intent = new Intent(HomeActivity.this, InformasiTPMActivity.class);
            startActivity(intent);
        }

        if (menuGrid.getName() == "Menu RCA") {
            Intent intent = new Intent(HomeActivity.this, MenuRCAActivity.class);
            startActivity(intent);
        }

        if (menuGrid.getName() == "Users") {
            Intent intent = new Intent(HomeActivity.this, StatusLevelUserActivity.class);
            startActivity(intent);
        }
    }

}
