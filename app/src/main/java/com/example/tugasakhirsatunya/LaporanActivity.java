package com.example.tugasakhirsatunya;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tugasakhirsatunya.Adapter.LaporanAdapter;
import com.example.tugasakhirsatunya.Adapter.TpsAdapter;
import com.example.tugasakhirsatunya.Pojo.Laporan;
import com.example.tugasakhirsatunya.Response.LaporanResponse;
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


public class LaporanActivity extends AppCompatActivity {

    private LaporanAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    BaseUrl url;
    ArrayList<Laporan> laporanList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_laporan);
        progressDialog = new ProgressDialog(LaporanActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        url = new BaseUrl();

        recyclerView = findViewById(R.id.rv2);
        adapter = new LaporanAdapter(getApplicationContext(), laporanList);
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
        Call <LaporanResponse> call =api.getListLaporan();
        call.enqueue(new Callback<LaporanResponse>() {
            @Override
            public void onResponse(Call<LaporanResponse> call, Response<LaporanResponse> response) {
                Log.i("SUCC",response.message());
                Log.i("SUCC",response.body().getMessage());
                Log.i("SUCC", String.valueOf(response.body().getValue()));
                progressDialog.dismiss();
                adapter = new LaporanAdapter(getApplicationContext(),response.body().getValue());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<LaporanResponse> call, Throwable t) {
                Log.i("ERR",t.getMessage());
            }
        });

    }
}


