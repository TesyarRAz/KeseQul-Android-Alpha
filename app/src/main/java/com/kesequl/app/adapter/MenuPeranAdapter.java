package com.kesequl.app.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.activity.PeranActivity;
import com.kesequl.app.activity.TemanSekelasActivity;
import com.kesequl.app.activity.TopupActivity;
import com.kesequl.app.activity.TransferActivity;
import com.kesequl.app.activity.VotingActivity;
import com.kesequl.app.model.Entity.Spp;
import com.kesequl.app.model.ResponseApi;
import com.kesequl.app.network.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPeranAdapter extends RecyclerView.Adapter<MenuPeranAdapter.MenuPeranHolder> {
    PeranActivity activity;
    String[] title;
    int[] img;

    int idArrTitle;

    public MenuPeranAdapter(PeranActivity activity, int idArrTitle, int idArrImage) {
        this.activity = activity;

        this.idArrTitle = idArrTitle;
        this.title = activity.getResources().getStringArray(idArrTitle);
        String[] imgName = activity.getResources().getStringArray(idArrImage);
        img = new int[imgName.length];
        for (int i=0; i<imgName.length; i++)
            img[i] = activity.getResources().getIdentifier(imgName[i].toLowerCase(), "drawable", activity.getPackageName());
    }

    @NonNull
    @Override
    public MenuPeranAdapter.MenuPeranHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuPeranHolder(
                LayoutInflater.from(activity).inflate(R.layout.menu_peran, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MenuPeranAdapter.MenuPeranHolder holder, int position) {
        holder.txtTitle.setText(title[position]);
        holder.imgView.setImageResource(img[position]);

        switch (title[position].toLowerCase()) {
            case "bayar sppqu":
                bayarSppEvent(holder);
                break;
            case "topup":
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.startActivity(new Intent(activity, TopupActivity.class));
                    }
                });
                break;
            case "share qr":
                prepareShowBarcode(holder);
                break;
            case "transfer uangqu":
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.startActivity(new Intent(activity, TransferActivity.class));
                    }
                });
                break;
            case "teman sekelasqu":
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.startActivity(new Intent(activity, TemanSekelasActivity.class));
                    }
                });
                break;
            case "q-nyoblos":
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.startActivity(new Intent(activity, VotingActivity.class));
                    }
                });
                break;
        }
    }

    private void prepareShowBarcode(MenuPeranHolder holder) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                new BarcodeGenerator().execute(progressDialog);

            }
        });
    }

    @Override
    public int getItemCount() {
        return title  != null ? title.length : 0;
    }

    class MenuPeranHolder extends RecyclerView.ViewHolder {
        View itemView;

        ImageView imgView;
        TextView txtTitle;

        public MenuPeranHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            imgView = itemView.findViewById(R.id.img_menu_peran);
            txtTitle = itemView.findViewById(R.id.txt_menu_peran);
        }
    }

    private void bayarSppEvent(MenuPeranHolder holder) {
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_bayar_spp, null);
        final TextView txtBayarBulan = dialogView.findViewById(R.id.txt_bayar_spp);
        final EditText edtBayarBulan = dialogView.findViewById(R.id.edt_bayar_spp);

        Client.getApi().actionDataSpp(Global.getUser().getToken()).enqueue(new Callback<ResponseApi<Spp>>() {
            @Override
            public void onResponse(Call<ResponseApi<Spp>> call, Response<ResponseApi<Spp>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        String bulan = response.body().getData().getBulan();
                        txtBayarBulan.setText("Baru Bayar : " + (bulan == null ? 0 : bulan) +" bulan");
                    }
                    else
                        this.onFailure(call, new Throwable(response.body().getPesan()));
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<Spp>> call, Throwable t) {
                t.printStackTrace(System.err);
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setView(dialogView)
                .setTitle("Bayar SPP")
                .setPositiveButton("Bayar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        int bulan = Integer.parseInt(edtBayarBulan.getText().toString());

                        edtBayarBulan.setText("");

                        Client.getApi().actionBayarSpp(Global.getUser().getToken(), String.valueOf(bulan)).enqueue(new Callback<ResponseApi>() {
                            @Override
                            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                                if (response.isSuccessful()) {
                                    if (!Client.isTokenExpired(activity, response.body().getStatus())) {

                                        if (response.body().getStatus() == 1) {
                                            dialogInterface.dismiss();

                                            new AlertDialog.Builder(activity)
                                                    .setMessage("Berhasil")
                                                    .setCancelable(true)
                                                    .show();
                                        } else {
                                            dialogInterface.dismiss();

                                            new AlertDialog.Builder(activity)
                                                    .setMessage(response.body().getPesan())
                                                    .setCancelable(true)
                                                    .show();
                                        }

                                        activity.refreshUser();
                                    }
                                } else {
                                    new AlertDialog.Builder(activity)
                                            .setMessage(response.message())
                                            .show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseApi> call, Throwable t) {
                                new AlertDialog.Builder(activity)
                                        .setMessage(t.getMessage())
                                        .show();
                            }
                        });
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }

    class BarcodeGenerator extends AsyncTask<ProgressDialog, Void, Bitmap> {
        private QRCodeWriter qrCodeWriter = new QRCodeWriter();
        ImageView imageBarcode = new ImageView(activity);
        ProgressDialog progressDialog;

        @Override
        protected Bitmap doInBackground(ProgressDialog[] objects) {
            this.progressDialog = objects[0];

            try {
                BitMatrix bm = qrCodeWriter.encode(Global.getUser().getUsername(), BarcodeFormat.QR_CODE, 500, 500);
                Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
                for (int i=0; i<bitmap.getWidth(); i++) {
                    for (int j=0; j<bitmap.getHeight(); j++) {
                        bitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                    }
                }

                return bitmap;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imageBarcode.setImageBitmap(bitmap);

            progressDialog.hide();
            new AlertDialog.Builder(activity)
                    .setView(imageBarcode)
                    .show();
        }
    }
}
