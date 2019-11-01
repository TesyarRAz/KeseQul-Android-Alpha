package com.kesequl.app.network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.kesequl.app.Global;
import com.kesequl.app.activity.LoginActivity;
import com.kesequl.app.model.Entity.User;
import com.kesequl.app.model.ResponseApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
//    public static final String BASE_HOST = "https://kesequl.exposei.com/";
    public static final String BASE_HOST = "http://192.168.10.141/kesequl/";
    public static final String HOST_API = BASE_HOST + "Api/";
    public static final String BASE_IMAGE_UPLOAD = BASE_HOST + "assets/upload/image/";
    
    private static Retrofit retrofit;
    private static  ApiInterface apiInterface;

    public static Retrofit getConnection() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(HOST_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }

    public static ApiInterface getApi() {
        if (apiInterface == null)
            apiInterface = getConnection().create(ApiInterface.class);

        return apiInterface;
    }

    public static boolean isTokenExpired(final Context context, int status) {
        class BoolVal {
            boolean val;
        }
        if (status == 3) {
            if (Global.getUser() != null) {
                final BoolVal boolVal = new BoolVal();
                boolVal.val = false;
                getApi().actionLogin(Global.getUser().getUsername(), Global.getUser().getPassword()).enqueue(new Callback<ResponseApi<User>>() {
                    @Override
                    public void onResponse(Call<ResponseApi<User>> call, Response<ResponseApi<User>> response) {
                        boolVal.val = response.body().getStatus() == 1;
                        if (response.body().getStatus() == 2) {
                            new AlertDialog.Builder(context)
                                    .setCancelable(false)
                                    .setTitle("Notification")
                                    .setMessage("Akun anda kena ban hubungi admin")
                                    .show();
                        } else if (response.body().getStatus() == 3 || response.body().getStatus() == 0) {
                            new AlertDialog.Builder(context)
                                    .setMessage("Ada yang login akun anda atau terjadi masalah lain")
                                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            context.startActivity(new Intent(context, LoginActivity.class));
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApi<User>> call, Throwable t) {
                        boolVal.val = true;
                        new AlertDialog.Builder(context)
                                .setCancelable(false)
                                .setTitle("Error")
                                .setMessage(t.getMessage())
                                .show();
                    }
                });

                return boolVal.val;
            }
        }

        return false;
    }
}
