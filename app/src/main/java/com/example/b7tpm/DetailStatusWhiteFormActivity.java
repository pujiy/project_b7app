package com.example.b7tpm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Helper.SharedPrefManager;
import com.example.b7tpm.Model.NewWhiteFormResponse;
import com.example.b7tpm.Model.UpdateStatusWhiteFormResponse;
import com.example.b7tpm.Model.User;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailStatusWhiteFormActivity extends AppCompatActivity {

    public static final String EXTRA_FORMID = "formid";
    public static final String EXTRA_NOMORKONTROL = "nomor_kontrol";
    public static final String EXTRA_BAGIANMESIN = "bagianmesin";
    public static final String EXTRA_DIPASANGOLEH = "dipasangoleh";
    public static final String EXTRA_TGLPASANG = "tglpasang";
    public static final String EXTRA_DESKRIPSI = "deskripsi";
    public static final String EXTRA_DUEDATE = "duedate";
    public static final String EXTRA_CARAPENANGGULANGAN = "cara penanggulangan";
    public static final String EXTRA_PHOTO = "photo";
    public static final String EXTRA_STATUS = "status";
    public String[] listStatus ={"Open", "On Process", "Close"};

    private TextView textViewStatus, textViewNomorKontrol, textViewBagianMesin, textViewDipasangOleh, textViewTglPasang, textViewDeskripsi, textViewDueDate, textViewCaraPenanggulangan;
    private ImageView imageViewPhoto;
    private Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status_white_form);

        textViewNomorKontrol = findViewById(R.id.tv_nomorkontrol);
        textViewBagianMesin = findViewById(R.id.tv_bagianmesin);
        textViewDipasangOleh = findViewById(R.id.tv_dipasangoleh);
        textViewTglPasang = findViewById(R.id.tv_tglpasang);
        textViewDeskripsi = findViewById(R.id.tv_deskripsi);
        textViewDueDate = findViewById(R.id.tv_duedate);
        textViewCaraPenanggulangan = findViewById(R.id.tv_carapenanggulangan);
        textViewStatus = findViewById(R.id.tv_status);
        imageViewPhoto = findViewById(R.id.iv_photo);
        buttonEdit = findViewById(R.id.btn_edit);



        final int formid = getIntent().getIntExtra(EXTRA_FORMID, 0);
        final String nomorkontrol = getIntent().getStringExtra(EXTRA_NOMORKONTROL);
        final String bagianmesin = getIntent().getStringExtra(EXTRA_BAGIANMESIN);
        final String dipasangoleh = getIntent().getStringExtra(EXTRA_DIPASANGOLEH);
        final String tglpasang = getIntent().getStringExtra(EXTRA_TGLPASANG);
        final String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        final String duedate = getIntent().getStringExtra(EXTRA_DUEDATE);
        final String carapenanggulangan = getIntent().getStringExtra(EXTRA_CARAPENANGGULANGAN);
        final String photo = getIntent().getStringExtra(EXTRA_PHOTO);
        final String status = getIntent().getStringExtra(EXTRA_STATUS);

        textViewStatus.setText(status);
        textViewNomorKontrol.setText(nomorkontrol);
        textViewBagianMesin.setText(bagianmesin);
        textViewDipasangOleh.setText(dipasangoleh);
        textViewTglPasang.setText(tglpasang);
        textViewDeskripsi.setText(deskripsi);
        textViewDueDate.setText(duedate);
        textViewCaraPenanggulangan.setText(carapenanggulangan);
        Picasso.get().load(photo).into(imageViewPhoto);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        if (user.getIsuser() == 0) {
            buttonEdit.setEnabled(true);
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailStatusWhiteFormActivity.this);
                builder.setTitle("Update Status");

                builder.setItems(listStatus, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String status = listStatus[which];
                        //building retrofit object
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIUrl.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        //Defining retrofit api service
                        APIService service = retrofit.create(APIService.class);

                        Call<UpdateStatusWhiteFormResponse> call = service.updateStatusWhiteForm(
                                formid,
                                status
                        );

                        //calling the api
                        call.enqueue(new Callback<UpdateStatusWhiteFormResponse>() {
                            @Override
                            public void onResponse(Call<UpdateStatusWhiteFormResponse> call, Response<UpdateStatusWhiteFormResponse> response) {

                                //displaying the message from the response
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<UpdateStatusWhiteFormResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
}
