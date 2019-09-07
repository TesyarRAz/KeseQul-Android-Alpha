package com.kesequl.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.kesequl.app.R;

public class VotingDetailActivity extends AppCompatActivity {
    TextView txtKetua, txtKetuaKelas, txtWakil, txtWakilKelas, txtVisi, txtMisi;
    Button btnCoblos, btnBatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_detail);

        txtKetua = findViewById(R.id.txt_team_ketua);
        txtKetuaKelas = findViewById(R.id.txt_team_kelas_ketua);
        txtWakil = findViewById(R.id.txt_team_wakil);
        txtWakilKelas = findViewById(R.id.txt_team_kelas_wakil);
        txtVisi = findViewById(R.id.txt_team_visi);
        txtMisi = findViewById(R.id.txt_team_misi);

        btnCoblos = findViewById(R.id.btn_team_coblos);
        btnBatal = findViewById(R.id.btn_team_batal);

        Intent intent = getIntent();
        txtKetua.setText(intent.getStringExtra("nama_ketua"));
        txtKetuaKelas.setText(intent.getStringExtra("kelas_ketua"));
        txtWakil.setText(intent.getStringExtra("nama_wakil"));
        txtWakilKelas.setText(intent.getStringExtra("kelas_wakil"));
        txtVisi.setText(intent.getStringExtra("visi"));
        txtMisi.setText(intent.getStringExtra("misi"));



    }
}
