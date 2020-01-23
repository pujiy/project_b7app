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

import com.example.b7tpm.DetailRedFormActivity;
import com.example.b7tpm.Model.RedForm;
import com.example.b7tpm.R;

import java.util.List;

public class AdministrasiRedFormAdapter extends RecyclerView.Adapter<AdministrasiRedFormAdapter.ViewHolder> {

    private List<RedForm> allRedForm;
    private Context ctx;

    public AdministrasiRedFormAdapter(List<RedForm> allRedForm, Context ctx) {
        this.allRedForm = allRedForm;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_administrasi_red_form, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RedForm redForm = allRedForm.get(position);
        holder.textViewNomorKontrol.setText(redForm.getNomor_kontrol());
        final int formid = redForm.getForm_id();
        final String nomorkontrol = redForm.getNomor_kontrol();
        final String bagianmesin = redForm.getBagian_mesin();
        final String namamesin = redForm.getNama_mesin();
        final String nomormesin = redForm.getNomor_mesin();
        final String dipasangoleh = redForm.getDipasang_oleh();
        final String tglpasang = redForm.getTgl_pasang();
        final String deskripsi = redForm.getDeskripsi();
        final String duedate = redForm.getDue_date();
        final String nomorworkrequest = redForm.getNomor_work_request();
        final String picfollowup = redForm.getPic_follow_up();
        final String carapenanggulangan = redForm.getCara_penanggulangan();
        final String photo = redForm.getPhoto();
        holder.cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveWithDataIntent = new Intent(ctx, DetailRedFormActivity.class);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_NAMAMESIN, namamesin);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_NOMORMESIN, nomormesin);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_DIPASANGOLEH, dipasangoleh);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_TGLPASANG, tglpasang);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_DESKRIPSI, deskripsi);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_NOMORWORKREQUEST, nomorworkrequest);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_PICFOLLOWUP, picfollowup);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_DUEDATE, duedate);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_CARAPENANGGULANGAN, carapenanggulangan);
                moveWithDataIntent.putExtra(DetailRedFormActivity.EXTRA_PHOTO, photo);
                moveWithDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(moveWithDataIntent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return allRedForm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomorKontrol;
        public CardView cardViewList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomorKontrol = itemView.findViewById(R.id.tv_nomorkontrol);
            cardViewList = itemView.findViewById(R.id.cv_list);
        }
    }
}
