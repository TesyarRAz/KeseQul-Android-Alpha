package com.kesequl.app.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kesequl.app.R;
import com.kesequl.app.model.Entity.Siswa;

import java.util.List;

public class MenuTemanSiswaAdapter extends RecyclerView.Adapter<MenuTemanSiswaAdapter.MenuTemanSiswaHolder> {
    private Activity activity;
    private List<Siswa> listTeman;

    public MenuTemanSiswaAdapter(Activity activity, List<Siswa> listTeman) {
        this.activity = activity;
        this.listTeman = listTeman;
    }

    @NonNull
    @Override
    public MenuTemanSiswaAdapter.MenuTemanSiswaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuTemanSiswaHolder(
                LayoutInflater.from(activity).inflate(R.layout.menu_siswa_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MenuTemanSiswaAdapter.MenuTemanSiswaHolder holder, int position) {
        Siswa teman = listTeman.get(position);

        holder.txtNama.setText("Nama : " + teman.getNama());
        holder.txtNisn.setText("NISN : " + teman.getNisn());
        holder.txtAbsen.setText("( " + (position + 1) + " )");
        holder.txtAbsen.setText("( " + (position + 1) + " )");
    }

    @Override
    public int getItemCount() {
        return listTeman.size();
    }

    class MenuTemanSiswaHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView txtNisn;
        TextView txtNama;
        TextView txtAbsen;

        public MenuTemanSiswaHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;

            txtNisn = itemView.findViewById(R.id.txt_siswa_nisn);
            txtNama = itemView.findViewById(R.id.txt_siswa_nama);
            txtAbsen = itemView.findViewById(R.id.txt_siswa_absen);
        }
    }
}
