package com.kesequl.app.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.model.Entity.Peran;
import com.kesequl.app.model.Entity.User;
import com.kesequl.app.model.ResponseApi;
import com.kesequl.app.network.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edtUsername.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();

                login(username, password, true);
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
            login(username, password, false);
    }

    public void login(final String username, final String password, final boolean showError) {

        if (TextUtils.isEmpty(username)) {
            edtUsername.setError("Tidak boleh kosong");
            return;
        } else if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Tidak boleh kosong");
            return;
        }

        progressDialog.show();
        Client.getApi().actionLogin(username, password).enqueue(new Callback<ResponseApi<User>>() {
            @Override
            public void onResponse(Call<ResponseApi<User>> call, Response<ResponseApi<User>> response) {
                ResponseApi<User> responseLogin = response.body();

                if (response.isSuccessful()) {
                    if (responseLogin.getStatus() == 1) {
                        Global.setUser(responseLogin.getData());
                        Global.getUser().setPassword(password);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("username", Global.getUser().getUsername());
                        edit.putString("password", Global.getUser().getPassword());
                        sharedPreferences.edit().apply();

                        progressDialog.hide();

                        if (Global.getUser().getPeran().size() == 1) {
                            Intent intent = new Intent(LoginActivity.this, PeranActivity.class);
                            intent.putExtra("peran", Global.getUser().getPeran().get(0).getNamaLevel());

                            startActivity(intent);
                        } else {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                        finish();
                    } else {
                        progressDialog.hide();
                        if (showError)
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Kesalahan")
                                    .setMessage("Username atau password mungkin salah")
                                    .show();

                        edtUsername.setText("");
                        edtPassword.setText("");
                        edtUsername.requestFocus();
                    }
                } else {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Kesalahan")
                            .setMessage(response.message())
                            .show();

                    progressDialog.hide();
                }

            }

            @Override
            public void onFailure(Call<ResponseApi<User>> call, Throwable t) {
                progressDialog.hide();
                t.printStackTrace(System.err);
                if (showError)
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Error")
                            .setMessage("Terjadi Kesalahan harap hubungi admin\r\nError: " + t.getMessage())
                            .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Yakin Ingin Keluar ?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
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
