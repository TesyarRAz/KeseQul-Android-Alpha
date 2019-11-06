package com.kesequl.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.Result;
import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.model.Entity.EventVoting;
import com.kesequl.app.model.ResponseApi;
import com.kesequl.app.network.Client;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotingActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerVoting;
    private EditText edtPassword;
    private Button btnEksekusi;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        scannerVoting = findViewById(R.id.scanner_barcode);
        scannerVoting.setAutoFocus(true);

        edtPassword = findViewById(R.id.edt_data_barcode);
        btnEksekusi = findViewById(R.id.btn_eksekusi_barcode);

        edtPassword.setHint("Masukan Password Tujuan");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);

        btnEksekusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = edtPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(password)) {
                    validasiTeam(password);
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Meloading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerVoting.setResultHandler(this);
        scannerVoting.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerVoting.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        validasiTeam(result.getText());
    }

    private void validasiTeam(String password) {

        if (password != null && !TextUtils.isEmpty(password))
        {
            progressDialog.show();
            Client.getApi().actionGetEventVoting(Global.getUser().getToken(), password).enqueue(new Callback<ResponseApi<EventVoting>>() {
                @Override
                public void onResponse(Call<ResponseApi<EventVoting>> call, Response<ResponseApi<EventVoting>> response) {
                    progressDialog.hide();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            EventVoting eventVoting = response.body().getData();
                            
                            Intent intent = new Intent(VotingActivity.this, VotingTeamActivity.class);
                            intent.putExtra("id_event_voting", eventVoting.getIdEventVoting());
                            intent.putExtra("nama_event", eventVoting.getNama());
                            intent.putExtra("password", eventVoting.getPassword());

                            startActivity(intent);
                            finish();
                        } else {
                            new AlertDialog.Builder(VotingActivity.this)
                                .setMessage(response.body().getPesan())
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        finish();
                                    }
                                })
                                .show();
                        }
                    } else {
                        new AlertDialog.Builder(VotingActivity.this)
                                .setMessage(response.message())
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseApi<EventVoting>> call, Throwable t) {
                    new AlertDialog.Builder(VotingActivity.this)
                            .setMessage(t.getMessage())
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    finish();
                                }
                            })
                            .show();
                }
            });
        }
    }
}
