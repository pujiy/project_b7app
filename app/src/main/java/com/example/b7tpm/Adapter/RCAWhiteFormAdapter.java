package com.example.b7tpm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b7tpm.DetailRCAWhiteFormActivity;
import com.example.b7tpm.Model.WhiteForm;
import com.example.b7tpm.R;

import java.util.ArrayList;
import java.util.List;

public class RCAWhiteFormAdapter extends RecyclerView.Adapter<RCAWhiteFormAdapter.ViewHolder> implements Filterable {

    private List<WhiteForm> whiteFormsClose;
    private List<WhiteForm> whiteFormsCloseFilter;
    private Context ctx;

    public RCAWhiteFormAdapter(List<WhiteForm> whiteFormsClose, Context ctx) {
        this.whiteFormsClose = whiteFormsClose;
        whiteFormsCloseFilter = new ArrayList<>(whiteFormsClose);
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
            holder.textViewNamaMesin.setText(whiteForm.getNama_mesin());

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

    @Override
    public Filter getFilter() {
        return rcaWhiteFormsFilter;
    }

    private Filter rcaWhiteFormsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<WhiteForm> filteredWhiteFormClose = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredWhiteFormClose.addAll(whiteFormsCloseFilter);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(WhiteForm item : whiteFormsCloseFilter) {
                    if(item.getNama_mesin().toLowerCase().contains(filterPattern)) {
                        filteredWhiteFormClose.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredWhiteFormClose;

            return  results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            whiteFormsClose.clear();
            whiteFormsClose.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomorKontrol;
        public TextView textViewNamaMesin;
        public TextView textViewRCAWhiteFormStatus;
        public CardView cardViewList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomorKontrol = (TextView)itemView.findViewById(R.id.tv_nomorkontrol);
            textViewNamaMesin = (TextView) itemView.findViewById(R.id.tv_namamesin);
            textViewRCAWhiteFormStatus = (TextView)itemView.findViewById(R.id.tv_status);
            cardViewList = itemView.findViewById(R.id.cv_list);
        }
    }


}
