package com.kesequl.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.adapter.MenuPeranAdapter;
import com.kesequl.app.model.Entity.DaoData;
import com.kesequl.app.model.Entity.Siswa;
import com.kesequl.app.model.Entity.TU;
import com.kesequl.app.model.Entity.User;
import com.kesequl.app.model.ResponseApi;
import com.kesequl.app.network.Client;

import java.io.IOException;
import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeranActivity extends AppCompatActivity {
    User user;

    SwipeRefreshLayout refreshLayout;

    TextView txtNama;
    TextView txtMoney;
    RecyclerView listMenuPeran;
    TextView btnLogout;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peran);

        refreshLayout = findViewById(R.id.refresh_peran);
        txtNama = findViewById(R.id.txt_peran_nama);
        txtMoney = findViewById(R.id.txt_peran_money);
        listMenuPeran = findViewById(R.id.list_menu_peran);
        btnLogout = findViewById(R.id.btn_logout);

        user = Global.getUser();

        listMenuPeran.setHasFixedSize(true);
        listMenuPeran.setLayoutManager(new GridLayoutManager(this, 2));
        listMenuPeran.setLayoutFrozen(false);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Meloading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();

        refreshUser();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    refreshUser();
                } catch (Exception ex) {
                    new AlertDialog.Builder(PeranActivity.this)
                            .setMessage(ex.getMessage())
                            .show();
                }

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(PeranActivity.this)
                        .setMessage("Yakin Ingin Keluar ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getSharedPreferences("login_setting", Context.MODE_PRIVATE);
                                sharedPreferences.edit().remove("username");
                                sharedPreferences.edit().remove("password");
                                sharedPreferences.edit().apply();

                                Intent intent = new Intent(PeranActivity.this, LoginActivity.class);
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
        });
    }

    private void createDatadiri() throws IOException {
        String namaLevel = getIntent().getStringExtra("peran");

        switch (namaLevel) {
            case "SISWA":
                dataDiri(Siswa.class);
                break;
            case "TU":
                dataDiri(TU.class);
                break;
        }
    }

    private <T extends DaoData> void dataDiri(Class<T> _class) {
        if (_class == TU.class)
            Client.getApi().actionMyDataTU(user.getToken()).enqueue(new CallbackData<>());
        else if (_class == Siswa.class)
            Client.getApi().actionMyDataSiswa(user.getToken()).enqueue(new CallbackData<>());

    }

    public void refreshUser() {
        Client.getApi().actionMyDataUser(Global.getUser().getToken()).enqueue(new Callback<ResponseApi<User>>() {
            @Override
            public void onResponse(Call<ResponseApi<User>> call, Response<ResponseApi<User>> response) {
                progressDialog.hide();
                if (!Client.isTokenExpired(PeranActivity.this, response.body().getStatus())) {
                    if (response.body().getStatus() == 1) {
                        String token = Global.getUser().getToken();
                        String password = Global.getUser().getPassword();
                        Global.setUser(response.body().getData());
                        Global.getUser().setToken(token);
                        Global.getUser().setPassword(password);

                        txtMoney.setText("SeQul Cash : Rp. " + NumberFormat.getNumberInstance().format(Global.getUser().getUang()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<User>> call, Throwable t) {
                progressDialog.hide();
                new AlertDialog.Builder(PeranActivity.this)
                        .setMessage(t.getMessage())
                        .show();
            }
        });
        try {
            createDatadiri();
        } catch (Exception ex) {
            new AlertDialog.Builder(this)
                    .setMessage(ex.getMessage())
                    .show();
        }
    }

    class CallbackData<T extends DaoData> implements Callback<ResponseApi<T>> {
        @Override
        public void onResponse(Call<ResponseApi<T>> call, Response<ResponseApi<T>> response) {
            if (response.isSuccessful()) {
                if (!Client.isTokenExpired(PeranActivity.this, response.body().getStatus())) {
                    if (response.body().getStatus() == 1) {
                        DaoData data = response.body().getData();

                        txtNama.setText(data.getNama());
                        listMenuPeran.setAdapter(new MenuPeranAdapter(PeranActivity.this, data.getResIdText(), data.getResIdImage()));
                    }
                    refreshLayout.setRefreshing(false);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseApi<T>> call, Throwable t) {
            refreshUser();
        }
    }

    @Override
    public void onBackPressed() {
        if (Global.getUser().getPeran().size() == 1) {
            new AlertDialog.Builder(this)
                    .setMessage("Yakin Ingin Keluar ?")
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences sharedPreferences = getSharedPreferences("login_setting", Context.MODE_PRIVATE);
                            sharedPreferences.edit().remove("username");
                            sharedPreferences.edit().remove("password");
                            sharedPreferences.edit().apply();

                            Intent intent = new Intent(PeranActivity.this, LoginActivity.class);
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
        } else {
            finish();
        }
    }
}
