package com.example.tugasakhirsatunya;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhirsatunya.Adapter.TpsAdapter;
import com.example.tugasakhirsatunya.Pojo.Tps;
import com.example.tugasakhirsatunya.Response.TpsResponse;
import com.example.tugasakhirsatunya.Utils.API;
import com.example.tugasakhirsatunya.Utils.BaseUrl;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TpsActivity extends AppCompatActivity {

    private TpsAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    BaseUrl url;
    ArrayList<Tps> tpsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tps);
        progressDialog = new ProgressDialog(TpsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        url = new BaseUrl();

        recyclerView = findViewById(R.id.rv);
        adapter = new TpsAdapter(getApplicationContext(), tpsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api =retrofit.create(API.class);
        Call <TpsResponse> call =api.getListTps();
        call.enqueue(new Callback<TpsResponse>() {
            @Override
            public void onResponse(Call<TpsResponse> call, Response<TpsResponse> response) {
                Log.i("SUCC",response.message());
                Log.i("SUCC",response.body().getMessage());
                Log.i("SUCC", String.valueOf(response.body().getValue().size()));
                progressDialog.dismiss();
                adapter = new TpsAdapter(getApplicationContext(),response.body().getValue());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<TpsResponse> call, Throwable t) {
                Log.i("ERR",t.getMessage());
            }
        });
    }

}


