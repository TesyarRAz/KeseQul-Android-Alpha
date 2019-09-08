package com.kesequl.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.kesequl.app.Global;
import com.kesequl.app.R;
import com.kesequl.app.adapter.MenuVotingAdapter;
import com.kesequl.app.model.Entity.VotingTeam;
import com.kesequl.app.model.ResponseList;
import com.kesequl.app.network.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotingTeamActivity extends AppCompatActivity {
    TextView txtVotingTeam;
    RecyclerView listVotingTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_team);

        txtVotingTeam = findViewById(R.id.txt_voting_team);
        listVotingTeam = findViewById(R.id.list_voting_team);
        listVotingTeam.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();

        String id = intent.getStringExtra("id_event_voting");
        if (id == null)
            return;
        
        int id_event_voting = Integer.parseInt(id);

        if (id_event_voting != -1)
            Client.getApi().actionGetEventTeam(Global.getUser().getToken(), id_event_voting, intent.getStringExtra("password")).enqueue(new Callback<ResponseList<VotingTeam>>() {
                @Override
                public void onResponse(Call<ResponseList<VotingTeam>> call, Response<ResponseList<VotingTeam>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            listVotingTeam.setAdapter(new MenuVotingAdapter(VotingTeamActivity.this, intent.getStringExtra("password"), response.body().getData()));
                        } else {
                            new AlertDialog.Builder(VotingTeamActivity.this)
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
                }

                @Override
                public void onFailure(Call<ResponseList<VotingTeam>> call, Throwable t) {
                    new AlertDialog.Builder(VotingTeamActivity.this)
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
        else
            finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setMessage("Yakin Ingin Ke menu awal ?")
            .setCancelable(false)
            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
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
