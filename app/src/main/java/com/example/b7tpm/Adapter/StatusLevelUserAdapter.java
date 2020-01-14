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

import com.example.b7tpm.DetailStatusLevelUserActivity;
import com.example.b7tpm.Model.AllUsers;
import com.example.b7tpm.R;

import java.util.List;

public class StatusLevelUserAdapter extends RecyclerView.Adapter<StatusLevelUserAdapter.ViewHolder> {
    private List<AllUsers> userLevelStatus;
    private Context ctx;

    public StatusLevelUserAdapter(List<AllUsers> userLevelStatus, Context ctx) {
        this.userLevelStatus = userLevelStatus;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_status_users, parent, false);
        return new StatusLevelUserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllUsers user = userLevelStatus.get(position);
        final String nik = user.getNik();
        final String username = user.getUsername();
        final String email = user.getEmail();
        final int isuser = user.getIsuser();
        final int isadmin = user.getIsadmin();
        final int isspv = user.getIsspv();
        String level = "";


        if (isuser == 1 && isadmin == 0 && isspv == 0) {

            level = "User";

        }

        else if (isadmin == 1 && isuser == 0 && isspv == 0) {

            level = "Admin";
        }

        else if(isspv == 1 && isadmin == 0 && isuser == 0) {

            level = "SPV";
        }
        final String islevel = level;
        holder.textViewNik.setText(user.getNik());
        holder.textViewUsername.setText(user.getUsername());
        holder.textViewLevel.setText(level);
        holder.cvListUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveWithDataIntent = new Intent(ctx, DetailStatusLevelUserActivity.class);
                moveWithDataIntent.putExtra(DetailStatusLevelUserActivity.EXTRA_NIK, nik);
                moveWithDataIntent.putExtra(DetailStatusLevelUserActivity.EXTRA_USERNAME, username);
                moveWithDataIntent.putExtra(DetailStatusLevelUserActivity.EXTRA_EMAIL, email);
                moveWithDataIntent.putExtra(DetailStatusLevelUserActivity.EXTRA_ISLEVEL, islevel);
                moveWithDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(moveWithDataIntent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return userLevelStatus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNik;
        public TextView textViewUsername;
        public TextView textViewLevel;
        public CardView cvListUsers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNik = itemView.findViewById(R.id.tv_nik);
            textViewUsername = itemView.findViewById(R.id.tv_username);
            textViewLevel = itemView.findViewById(R.id.tv_level);
            cvListUsers = itemView.findViewById(R.id.cv_list);
        }
    }
}
