package com.kesequl.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.adapter.MenuTemanSiswaAdapter;
import com.kesequl.app.model.Entity.Siswa;
import com.kesequl.app.model.ResponseApi;
import com.kesequl.app.model.ResponseList;
import com.kesequl.app.network.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemanSekelasActivity extends AppCompatActivity {
    RecyclerView listTemanSekelas;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teman_sekelas);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);


        listTemanSekelas = findViewById(R.id.list_teman_sekelas);
        listTemanSekelas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Client.getApi().actionGetTemanSekelas(Global.getUser().getToken()).enqueue(new Callback<ResponseList<Siswa>>() {
            @Override
            public void onResponse(Call<ResponseList<Siswa>> call, Response<ResponseList<Siswa>> response) {
                if (response.isSuccessful()) {
                    ResponseList<Siswa> responseList = response.body();

                    if (responseList.getStatus() == 1) {
                        List<Siswa> list = responseList.getData();

                        listTemanSekelas.setAdapter(new MenuTemanSiswaAdapter(TemanSekelasActivity.this, list));
                    }
                }

                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<ResponseList<Siswa>> call, Throwable t) {
                progressDialog.hide();
                new AlertDialog.Builder(TemanSekelasActivity.this)
                        .setMessage(t.getMessage())
                        .show();
            }
        });
    }
}
