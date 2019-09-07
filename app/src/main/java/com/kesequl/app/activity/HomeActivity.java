package com.kesequl.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kesequl.app.R;
import com.kesequl.app.adapter.MenuHomeAdapter;

public class HomeActivity extends AppCompatActivity {
    RecyclerView listMenuHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listMenuHome = findViewById(R.id.list_menu_home);

        listMenuHome.setAdapter(new MenuHomeAdapter(this));
        listMenuHome.setHasFixedSize(true);
        listMenuHome.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Yakin Ingin Keluar ?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
                        sharedPreferences.edit().remove("username");
                        sharedPreferences.edit().remove("password");
                        sharedPreferences.edit().apply();

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
