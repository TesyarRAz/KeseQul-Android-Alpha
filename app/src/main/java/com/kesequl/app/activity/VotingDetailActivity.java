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

        final Intent intent = getIntent();
        txtKetua.setText(intent.getStringExtra("nama_ketua"));
        txtKetuaKelas.setText(intent.getStringExtra("kelas_ketua"));
        txtWakil.setText(intent.getStringExtra("nama_wakil"));
        txtWakilKelas.setText(intent.getStringExtra("kelas_wakil"));
        txtVisi.setText(intent.getStringExtra("visi"));
        txtMisi.setText(intent.getStringExtra("misi"));

        btnCoblos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(VotingDetailActivity.this)
                        .setMessage("Yakin Ingin Memilih Yang Ini ?")
                        .setCancelabel(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Client.getApi().actionCoblos(Global.getUser().getToken(), intent.getStringExtra("id_nominasi_team"), intent.getStringExtra("password")).enqueue(new Callback<ResponseApi>() {
                                        @Override
                                        public void onResponse(Call<ResponseApi> call, final Response<ResponseApi> response) {
                                            if (response.isSuccessful()) {
                                                if (response.body().getStatus() == 1) {
                                                    new AlertDialog.Builder(VotingDetailActivity.this)
                                                            .setMessage(response.getPesan())
                                                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                                @Override
                                                                public void onDismiss(DialogInterface dialogInterface) {
                                                                    setResult(RESULT_OK, null);
                                                                    finish();
                                                                }
                                                            })
                                                            .show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseApi> call, Throwable t) {
                                            new AlertDialog.Builder(VotingDetailActivity.this)
                                                    .setMessage(t.getMessage())
                                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                                @Override
                                                                public void onDismiss(DialogInterface dialogInterface) {
                                                                    setResult(RESULT_OK, null);
                                                                    finish();
                                                                }
                                                            })
                                                    .show();
                                        }
                                    });
                                });
                        })
                        .setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                        })
                        .show();
            }
        });
    }
}
