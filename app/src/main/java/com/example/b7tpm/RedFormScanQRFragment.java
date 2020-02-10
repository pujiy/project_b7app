package com.example.b7tpm;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.b7tpm.Api.APIService;
import com.example.b7tpm.Api.APIUrl;
import com.example.b7tpm.Model.DataMesin;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class RedFormScanQRFragment extends Fragment {


    public RedFormScanQRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_red_form_scan_qr, container, false);


        scanFromFragment();

        return view;
    }

    private void scanFromFragment() {
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
                String nomorqr = result.getContents().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService service = retrofit.create(APIService.class);

                Call<DataMesin> call = service.getDataMesin(nomorqr);

                call.enqueue(new Callback<DataMesin>() {
                    @Override
                    public void onResponse(Call<DataMesin> call, Response<DataMesin> response) {
                        String nomor_mesin = response.body().getNomor_mesin();
                        String nama_mesin = response.body().getNama_mesin();
                        String merk_mesin = response.body().getMerk_mesin();
                        String kapasitas_mesin = response.body().getKapasitas_mesin();
                        String jenis_mesin = response.body().getJenis_mesin();
                        String fungsi_mesin = response.body().getFungsi_mesin();

                        Intent moveWithDataIntent = new Intent(getActivity(), NewRedFormActivity.class);
                        moveWithDataIntent.putExtra(NewWhiteFormActivity.EXTRA_NOMORMESIN, nomor_mesin);
                        moveWithDataIntent.putExtra(NewWhiteFormActivity.EXTRA_NAMAMESIN, nama_mesin);

                        moveWithDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startActivity(moveWithDataIntent);

                    }

                    @Override
                    public void onFailure(Call<DataMesin> call, Throwable t) {

                    }
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
