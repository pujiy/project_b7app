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

import com.example.b7tpm.DetailRCAWhiteFormActivity;
import com.example.b7tpm.Model.WhiteForm;
import com.example.b7tpm.R;

import java.util.List;

public class RCAWhiteFormAdapter extends RecyclerView.Adapter<RCAWhiteFormAdapter.ViewHolder> {

    private List<WhiteForm> whiteFormsClose;
    private Context ctx;

    public RCAWhiteFormAdapter(List<WhiteForm> whiteFormsClose, Context ctx) {
        this.whiteFormsClose = whiteFormsClose;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.list_rca_white_form, parent, false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WhiteForm whiteForm = whiteFormsClose.get(position);
            holder.textViewNomorKontrol.setText(whiteForm.getNomor_kontrol());
            holder.textViewRCAWhiteFormStatus.setText(whiteForm.getStatus());

        final int formid = whiteForm.getForm_id();
        final String nomorkontrol = whiteForm.getNomor_kontrol();
        final String bagianmesin = whiteForm.getBagian_mesin();
        final String namamesin = whiteForm.getNama_mesin();
        final String nomormesin = whiteForm.getNomor_mesin();
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
                Intent moveWithDataIntent = new Intent(ctx, DetailRCAWhiteFormActivity.class);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_FORMID, formid);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_NOMORKONTROL, nomorkontrol);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_BAGIANMESIN, bagianmesin);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_NAMAMESIN, namamesin);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_NOMORMESIN, nomormesin);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_DIPASANGOLEH, dipasangoleh);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_TGLPASANG, tglpasang);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_DESKRIPSI, deskripsi);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_DUEDATE, duedate);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_CARAPENANGGULANGAN, carapenanggulangan);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_PHOTO, photo);
                moveWithDataIntent.putExtra(DetailRCAWhiteFormActivity.EXTRA_STATUS, status);
                moveWithDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(moveWithDataIntent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return whiteFormsClose.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomorKontrol;
        public TextView textViewRCAWhiteFormStatus;
        public CardView cardViewList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomorKontrol = (TextView)itemView.findViewById(R.id.tv_nomorkontrol);
            textViewRCAWhiteFormStatus = (TextView)itemView.findViewById(R.id.tv_status);
            cardViewList = itemView.findViewById(R.id.cv_list);
        }
    }


}
