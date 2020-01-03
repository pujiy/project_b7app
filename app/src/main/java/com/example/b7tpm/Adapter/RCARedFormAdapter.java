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

import com.example.b7tpm.DetailRCARedFormActivity;
import com.example.b7tpm.Model.RedForm;
import com.example.b7tpm.R;

import java.util.List;

public class RCARedFormAdapter extends RecyclerView.Adapter<RCARedFormAdapter.ViewHolder> {

    private List<RedForm> redFormsClose;
    private Context ctx;

    public RCARedFormAdapter(List<RedForm> redFormsClose, Context ctx) {
        this.redFormsClose = redFormsClose;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_rca_red_form, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RedForm redForm = redFormsClose.get(position);
            holder.textViewNomorKontrol.setText(redForm.getNomor_kontrol());
            holder.textViewRCARedFormStatus.setText(redForm.getStatus());
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
                Intent moveWithDataIntent = new Intent(ctx, DetailRCARedFormActivity.class);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_DIPASANGOLEH, dipasangoleh);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_TGLPASANG, tglpasang);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_DESKRIPSI, deskripsi);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_NOMORWORKREQUEST, nomorworkrequest);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_PICFOLLOWUP, picfollowup);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_DUEDATE, duedate);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_CARAPENANGGULANGAN, carapenanggulangan);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_PHOTO, photo);
                moveWithDataIntent.putExtra(DetailRCARedFormActivity.EXTRA_STATUS, status);
                moveWithDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(moveWithDataIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return redFormsClose.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomorKontrol;
        public TextView textViewRCARedFormStatus;
        public CardView cardViewList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomorKontrol = itemView.findViewById(R.id.tv_nomorkontrol);
            textViewRCARedFormStatus = itemView.findViewById(R.id.tv_status);
            cardViewList = itemView.findViewById(R.id.cv_list);

        }
    }
}
