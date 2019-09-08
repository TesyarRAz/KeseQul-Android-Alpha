package com.kesequl.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.model.ResponseApi;
import com.kesequl.app.network.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotingDetailActivity extends AppCompatActivity {
    TextView txtNamaTeam, txtKetua, txtKetuaKelas, txtWakil, txtWakilKelas, txtVisi, txtMisi;
    Button btnCoblos, btnBatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_detail);

        txtNamaTeam = findViewById(R.id.txt_team_nama);
        txtKetua = findViewById(R.id.txt_team_ketua);
        txtKetuaKelas = findViewById(R.id.txt_team_kelas_ketua);
        txtWakil = findViewById(R.id.txt_team_wakil);
        txtWakilKelas = findViewById(R.id.txt_team_kelas_wakil);
        txtVisi = findViewById(R.id.txt_team_visi);
        txtMisi = findViewById(R.id.txt_team_misi);

        btnCoblos = findViewById(R.id.btn_team_coblos);
        btnBatal = findViewById(R.id.btn_team_batal);

        final Intent intent = getIntent();
        txtNamaTeam.setText(intent.getStringExtra("nama_team"));
        txtKetua.setText(intent.getStringExtra("nama_ketua"));
        txtKetuaKelas.setText(intent.getStringExtra("kelas_ketua"));
        txtWakil.setText(intent.getStringExtra("nama_wakil"));
        txtWakilKelas.setText(intent.getStringExtra("kelas_wakil"));
        txtVisi.setText(intent.getStringExtra("visi"));
        txtMisi.setText(intent.getStringExtra("misi"));

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCoblos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(VotingDetailActivity.this)
                        .setMessage("Yakin Ingin Memilih Yang Ini ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Client.getApi().actionCoblos(Global.getUser().getToken(), intent.getStringExtra("id_nominasi_team"), intent.getStringExtra("password")).enqueue(new Callback<ResponseApi>() {
                                        @Override
                                        public void onResponse(Call<ResponseApi> call, final Response<ResponseApi> response) {
                                            if (response.isSuccessful()) {
                                                new AlertDialog.Builder(VotingDetailActivity.this)
                                                        .setMessage(response.body().getPesan())
                                                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                            @Override
                                                            public void onDismiss(DialogInterface dialogInterface) {
                                                                setResult(RESULT_OK, null);
                                                                finishActivity(1);
                                                            }
                                                        })
                                                        .show();
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
                                                                    finishActivity(1);
                                                                }
                                                            })
                                                    .show();
                                        }
                                    });
                                }
                        })
                        .setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                        })
                        .show();
            }
        });
    }
}
