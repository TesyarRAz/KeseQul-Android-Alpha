package com.kesequl.app.network;

import com.kesequl.app.model.Entity.EventVoting;
import com.kesequl.app.model.Entity.Siswa;
import com.kesequl.app.model.Entity.Spp;
import com.kesequl.app.model.Entity.TU;
import com.kesequl.app.model.Entity.User;
import com.kesequl.app.model.Entity.VotingTeam;
import com.kesequl.app.model.ResponseApi;
import com.kesequl.app.model.ResponseList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("user/login")
    @FormUrlEncoded
    public Call<ResponseApi<User>> actionLogin(@Field("username") String username, @Field("password") String password);

    @GET("siswa/data")
    public Call<ResponseApi<Siswa>> actionMyDataSiswa(@Query("token") String token);

    @POST("siswa/spp")
    @FormUrlEncoded
    public Call<ResponseApi> actionBayarSpp(@Query("token") String token, @Field("bulan") String bulan);

    @GET("siswa/spp")
    public Call<ResponseApi<Spp>> actionDataSpp(@Query("token") String token);

    @GET("user/data")
    public Call<ResponseApi<User>> actionMyDataUser(@Query("token") String token);

    @POST("tu/topup")
    @FormUrlEncoded
    public Call<ResponseApi> actionTopup(@Query("token") String token, @Field("id_penerima") int id_user, @Field("uang_transfer") int uang);

    @POST("user/transfer")
    @FormUrlEncoded
    public Call<ResponseApi> actionTransfer(@Query("token") String token, @Field("id_penerima") int id_user, @Field("uang_transfer") int uang);

    @POST("user/cekusername")
    @FormUrlEncoded
    public Call<ResponseApi> actionCekUsername(@Query("token") String token, @Field("username") String username);

    @GET("tu/data")
    public Call<ResponseApi<TU>> actionMyDataTU(@Query("token") String token);

    @GET("siswa/kelas")
    public Call<ResponseList<Siswa>> actionGetTemanSekelas(@Query("token") String token);

    @GET("voting/event")
    public Call<ResponseApi<EventVoting>> actionGetEventVoting(@Query("token") String token, @Query("password") String password);

    @GET("voting/team")
    public Call<ResponseList<VotingTeam>> actionGetEventTeam(@Query("token") String token, @Query("id_event_voting") int id_event_voting, @Query("password") String password);
    
    @POST("voting/coblos")
    public Call<ResponseApi> actionCoblos(@Query("token") String token, @Field("id_team_pilihan") int id_team, @Field("password") String password);
}
