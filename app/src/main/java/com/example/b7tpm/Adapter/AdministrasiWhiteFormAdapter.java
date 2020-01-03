package com.example.b7tpm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b7tpm.DetailWhiteFormActivity;
import com.example.b7tpm.Model.WhiteForm;
import com.example.b7tpm.R;

import java.util.List;

public class AdministrasiWhiteFormAdapter extends RecyclerView.Adapter<AdministrasiWhiteFormAdapter.ViewHolder> {

    private List<WhiteForm> allWhiteForm;
    private Context ctx;



    public AdministrasiWhiteFormAdapter(List<WhiteForm> allWhiteForm, Context ctx) {
        this.allWhiteForm = allWhiteForm;
        this.ctx = ctx;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_administrasi_white_form, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WhiteForm whiteForm = allWhiteForm.get(position);
        holder.textViewNomorKontrol.setText(whiteForm.getNomor_kontrol());
        final int formid = whiteForm.getForm_id();
        final String nomorkontrol = whiteForm.getNomor_kontrol();
        final String bagianmesin = whiteForm.getBagian_mesin();
        final String dipasangoleh = whiteForm.getDipasang_oleh();
        final String tglpasang = whiteForm.getTgl_pasang();
        final String deskripsi = whiteForm.getDeskripsi();
        final String duedate = whiteForm.getDue_date();
        final String carapenanggulangan = whiteForm.getCara_penanggulangan();
        final String photo = whiteForm.getPhoto();
        holder.cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveWithDataIntent = new Intent(ctx, DetailWhiteFormActivity.class);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_DIPASANGOLEH, dipasangoleh);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_TGLPASANG, tglpasang);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_DESKRIPSI, deskripsi);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_DUEDATE, duedate);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_CARAPENANGGULANGAN, carapenanggulangan);
                moveWithDataIntent.putExtra(DetailWhiteFormActivity.EXTRA_PHOTO, photo);
                moveWithDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(moveWithDataIntent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return allWhiteForm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomorKontrol;
        public TextView textViewStatus;
        public CardView cardViewList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomorKontrol = (TextView)itemView.findViewById(R.id.tv_nomorkontrol);
            textViewStatus = (TextView)itemView.findViewById(R.id.tv_status);
            cardViewList = itemView.findViewById(R.id.cv_list);
        }
    }


}
