package com.kesequl.app.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;
import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.model.ResponseApi;
import com.kesequl.app.network.Client;

import java.text.NumberFormat;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerTransfer;
    private EditText edtUsername;
    private Button btnEksekusi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);

        setContentView(R.layout.activity_barcode);

        scannerTransfer = findViewById(R.id.scanner_barcode);
        scannerTransfer.setAutoFocus(true);

        edtUsername = findViewById(R.id.edt_data_barcode);
        btnEksekusi = findViewById(R.id.btn_eksekusi_barcode);

        edtUsername.setHint("Masukan Username Tujuan");

        btnEksekusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();

                if (!TextUtils.isEmpty(username)) {
                    validasiTransfer(username);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerTransfer.setResultHandler(this);
        scannerTransfer.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerTransfer.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        String username = result.getText();

        validasiTransfer(username);
    }

    private void validasiTransfer(String username) {
        if (username != null && !TextUtils.isEmpty(username)) {
            Client.getApi().actionCekUsername(Global.getUser().getToken(), username).enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, final Response<ResponseApi> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            View dialog_topup = getLayoutInflater().inflate(R.layout.dialog_topup, null);
                            final Spinner spnUang = dialog_topup.findViewById(R.id.spn_uang_topup);
                            final int numList = 20;
                            final int[] listUang = new int[numList];
                            String[] tampilUang = new String[numList];
                            listUang[0] = 10000;
                            listUang[1] = 25000;
                            listUang[2] = 50000;
                            listUang[3] = 75000;
                            listUang[4] = 100000;
                            for (int i=5; i<numList; i++) {
                                listUang[i] = listUang[i - 1] + 100000;
                            }
                            for (int i=0; i<numList; i++) {
                                tampilUang[i] = "Rp." + NumberFormat.getNumberInstance().format(listUang[i]);
                            }

                            spnUang.setAdapter(new ArrayAdapter<String>(TransferActivity.this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, tampilUang));

                            new AlertDialog.Builder(TransferActivity.this)
                                    .setView(dialog_topup)
                                    .setCancelable(false)
                                    .setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Client.getApi().actionTransfer(Global.getUser().getToken(), Integer.parseInt(response.body().getPesan()), listUang[spnUang.getSelectedItemPosition()]).enqueue(new Callback<ResponseApi>() {
                                                @Override
                                                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                                                    if (response.isSuccessful()) {
                                                        new AlertDialog.Builder(TransferActivity.this)
                                                                .setMessage(response.body().getPesan())
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
                                                public void onFailure(Call<ResponseApi> call, Throwable t) {
                                                    new AlertDialog.Builder(TransferActivity.this)
                                                            .setMessage("Error : " + t.getMessage())
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
                                    })
                                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            finish();
                                        }
                                    })
                                    .show();
                        } else {
                            onFailure(call, new Throwable(response.body().getPesan()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseApi> call, Throwable t) {
                    new AlertDialog.Builder(TransferActivity.this)
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
