package com.kesequl.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.activity.PeranActivity;
import com.kesequl.app.model.Entity.Peran;

public class MenuHomeAdapter extends RecyclerView.Adapter<MenuHomeAdapter.MenuHomeHolder> {
    Activity activity;

    public MenuHomeAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuHomeHolder(
                LayoutInflater.from(activity).inflate(R.layout.menu_home, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHomeHolder holder, int position) {
        Peran peran = Global.getUser().getPeran().get(position);

        int imageId = 0;
        final String namaLevel = peran.getNamaLevel();

        if (imageId != 0)
            holder.imageView.setImageResource(imageId);


        holder.txtTitle.setText(Character.toUpperCase(namaLevel.charAt(0)) + namaLevel.substring(1).toLowerCase());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PeranActivity.class);
                intent.putExtra("peran", namaLevel);

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Global.getUser().getPeran().size();
    }

    class MenuHomeHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView imageView;
        TextView txtTitle;

        public MenuHomeHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.img_home);
            txtTitle = itemView.findViewById(R.id.txt_home_title);
        }
    }
}
