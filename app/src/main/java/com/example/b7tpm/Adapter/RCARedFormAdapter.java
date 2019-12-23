package com.example.b7tpm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return redFormsClose.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomorKontrol;
        public TextView textViewRCARedFormStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomorKontrol = itemView.findViewById(R.id.tv_nomorkontrol);
            textViewRCARedFormStatus = itemView.findViewById(R.id.tv_status);

        }
    }
}
