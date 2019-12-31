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

import com.example.b7tpm.DetailStatusWhiteFormActivity;
import com.example.b7tpm.Model.WhiteForm;
import com.example.b7tpm.R;

import java.util.List;

public class StatusWhiteFormAdapter extends RecyclerView.Adapter<StatusWhiteFormAdapter.ViewHolder> {

    private List<WhiteForm> whiteFormStatus;
    private Context ctx;

    public StatusWhiteFormAdapter(List<WhiteForm> whiteFormStatus, Context ctx) {
        this.whiteFormStatus = whiteFormStatus;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_status_white_form, parent, false);
        return new StatusWhiteFormAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WhiteForm whiteForm = whiteFormStatus.get(position);
        holder.textViewStatus.setText(whiteForm.getStatus());
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
        final String status = whiteForm.getStatus();
        holder.cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveWithDataIntent = new Intent(ctx, DetailStatusWhiteFormActivity.class);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_DIPASANGOLEH, dipasangoleh);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_TGLPASANG, tglpasang);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_DESKRIPSI, deskripsi);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_DUEDATE, duedate);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_CARAPENANGGULANGAN, carapenanggulangan);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_PHOTO, photo);
                moveWithDataIntent.putExtra(DetailStatusWhiteFormActivity.EXTRA_STATUS, status);
                moveWithDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(moveWithDataIntent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return whiteFormStatus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewStatus, textViewNomorKontrol;
        public CardView cardViewList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.tv_status);
            textViewNomorKontrol = itemView.findViewById(R.id.tv_nomorkontrol);
            cardViewList = itemView.findViewById(R.id.cv_list);
        }
    }
}
