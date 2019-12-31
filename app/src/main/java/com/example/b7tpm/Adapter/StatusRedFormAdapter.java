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

import com.example.b7tpm.DetailStatusRedFormActivity;
import com.example.b7tpm.Model.RedForm;
import com.example.b7tpm.R;

import java.util.List;

public class StatusRedFormAdapter extends RecyclerView.Adapter<StatusRedFormAdapter.ViewHolder> {

    private List<RedForm> redFormStatus;
    private Context ctx;

    public StatusRedFormAdapter(List<RedForm> redFormStatus, Context ctx) {
        this.redFormStatus = redFormStatus;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_status_red_form, parent, false);
        return new StatusRedFormAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RedForm redForm = redFormStatus.get(position);
        holder.textViewStatus.setText(redForm.getStatus());
        holder.textViewNomorKontrol.setText(redForm.getNomor_kontrol());
        final int formid = redForm.getForm_id();
        final String nomorkontrol = redForm.getNomor_kontrol();
        final String bagianmesin = redForm.getBagian_mesin();
        final String dipasangoleh = redForm.getDipasang_oleh();
        final String tglpasang = redForm.getTgl_pasang();
        final String deskripsi = redForm.getDeskripsi();
        final String nomorworkrequest = redForm.getNomor_work_request();
        final String picfollowup = redForm.getPic_follow_up();
        final String duedate = redForm.getDue_date();
        final String carapenanggulangan = redForm.getCara_penanggulangan();
        final String photo = redForm.getPhoto();
        final String status = redForm.getStatus();

        holder.cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveWithDataIntent = new Intent(ctx, DetailStatusRedFormActivity.class);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_DIPASANGOLEH, dipasangoleh);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_TGLPASANG, tglpasang);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_DESKRIPSI, deskripsi);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_NOMORWORKREQUEST, nomorworkrequest);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_PICFOLLOWUP, picfollowup);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_DUEDATE, duedate);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_CARAPENANGGULANGAN, carapenanggulangan);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_PHOTO, photo);
                moveWithDataIntent.putExtra(DetailStatusRedFormActivity.EXTRA_STATUS, status);
                moveWithDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(moveWithDataIntent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return redFormStatus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomorKontrol;
        public TextView textViewStatus;
        public CardView cardViewList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomorKontrol = itemView.findViewById(R.id.tv_nomorkontrol);
            textViewStatus = itemView.findViewById(R.id.tv_status);
            cardViewList = itemView.findViewById(R.id.cv_list);
        }
    }
}
