package com.example.b7tpm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return whiteFormsClose.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomorKontrol;
        public TextView textViewRCAWhiteFormStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomorKontrol = (TextView)itemView.findViewById(R.id.tv_nomorkontrol);
            textViewRCAWhiteFormStatus = (TextView)itemView.findViewById(R.id.tv_status);
        }
    }


}
