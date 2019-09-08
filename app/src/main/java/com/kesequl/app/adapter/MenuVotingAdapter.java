package com.kesequl.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kesequl.app.R;
import com.kesequl.app.activity.VotingDetailActivity;
import com.kesequl.app.model.Entity.VotingTeam;

import java.util.List;

public class MenuVotingAdapter extends RecyclerView.Adapter<MenuVotingAdapter.MenuVotingHolder> {
    private Activity activity;
    private String password;
    private List<VotingTeam> listTeam;

    public MenuVotingAdapter(Activity activity, String password, List<VotingTeam> listTeam) {
        this.activity = activity;
        this.password = password;
        this.listTeam = listTeam;
    }
    @NonNull
    @Override
    public MenuVotingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuVotingHolder(
                LayoutInflater.from(activity).inflate(R.layout.menu_voting_team, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MenuVotingHolder holder, int position) {
        VotingTeam team = listTeam.get(position);

        holder.txtNama.setText(team.getNama());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, VotingDetailActivity.class);
                intent.putExtra("id_nominasi_team", team.getIdNominasiTeam());
                intent.putExtra("nama_ketua", team.getNamaKetua());
                intent.putExtra("nama_wakil", team.getNamaWakil());
                intent.putExtra("kelas_ketua", team.getKelasKetua());
                intent.putExtra("kelas_wakil", team.getKelasWakil());
                intent.putExtra("visi", team.getVisi());
                intent.putExtra("misi", team.getMisi());
                intent.putExtra("password", password);

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTeam.size();
    }

    class MenuVotingHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView txtNama;

        public MenuVotingHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            txtNama = itemView.findViewById(R.id.txt_voting_team);
        }
    }
}
