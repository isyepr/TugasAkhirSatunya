package com.example.tugasakhirsatunya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.OkHttpClient;

import com.example.tugasakhirsatunya.Adapter.KomentarAdapter;
import com.example.tugasakhirsatunya.Adapter.LaporanAdapter;
import com.example.tugasakhirsatunya.Pojo.DetailLaporan;
import com.example.tugasakhirsatunya.Pojo.Laporan;
import com.example.tugasakhirsatunya.Pojo.Tps;
import com.example.tugasakhirsatunya.Response.DetailLaporanResponse;
import com.example.tugasakhirsatunya.Response.KomentarResponse;
import com.example.tugasakhirsatunya.Response.LaporanResponse;
import com.example.tugasakhirsatunya.Response.TpsResponse;
import com.example.tugasakhirsatunya.Utils.API;
import com.example.tugasakhirsatunya.Utils.BaseUrl;
import com.example.tugasakhirsatunya.Utils.Session;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailLaporanActivity extends AppCompatActivity {
    BaseUrl url;
    
    String idLaporan;
    ImageButton send;
//    String NIK;
    String nik;
    EditText editTextKomentar;
    String laporan_id;
    ProgressDialog dialog;
    private DetailLaporan detail_laporan;
    TextView title;
    TextView subtitle;
    TextView no_komentar, isi, textViewId;
    private RecyclerView recyclerView;
    private KomentarAdapter adapter;
    RecyclerView rv3;
    String laporan_judul, laporan_isi, komentar;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_laporan);
        title = findViewById(R.id.textViewLaporanJudul);
        subtitle = findViewById(R.id.textViewLaporanIsi);
        no_komentar = findViewById(R.id.no_komentar);
        textViewId = findViewById(R.id.textViewId);
        send = findViewById(R.id.imageButtonSend);
        isi = findViewById(R.id.isi);
        session = new Session(getApplicationContext());

        url = new BaseUrl();
        final Intent fromDataList = getIntent();

        final String nik = session.getNik();
        Log.i("FROM NIK", nik);
        laporan_id = getIntent().getStringExtra("laporan_id");
        editTextKomentar = findViewById(R.id.editTextKomentar);
        idLaporan= fromDataList.getStringExtra("laporan_judul");
        getDetail();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String komentar = editTextKomentar.getText().toString();

                laporan_id = getIntent().getStringExtra("laporan_id");
                Log.i("NIKK EX", nik);
                Log.i("NIKK komen", komentar);

                sendKomentar(laporan_id, nik, komentar);
            }
        });
    }

    private void getDetail() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api =retrofit.create(API.class);
        Call<DetailLaporanResponse> call = api.getListDetailLaporan(laporan_id);

        call.enqueue(new Callback<DetailLaporanResponse>() {

            @Override
            public void onResponse(Call<DetailLaporanResponse> call, Response<DetailLaporanResponse> response) {

                detail_laporan = response.body().getValue();
                if ((detail_laporan.getKomentar().isEmpty())) {

                    laporan_judul = detail_laporan.getLaporan_judul();
                    laporan_isi = detail_laporan.getLaporan_isi();

                    title.setText(laporan_judul);
                    subtitle.setText(laporan_isi);
                    textViewId.setText(laporan_id);
                    no_komentar.setVisibility(View.VISIBLE);
                }

                else{
                    Log.i("DETAIL LAPORAN", "Judul_laporan + " + detail_laporan.getLaporan_judul());
                    Log.i("DETAIL LAPORAN", "isi + " + detail_laporan.getLaporan_isi());
                    Log.i("DETAIL LAPORAN", "komentar + " + detail_laporan.getKomentar().get(0).getKomentar_isi());
                    laporan_judul = detail_laporan.getLaporan_judul();
                    laporan_isi = detail_laporan.getLaporan_isi();
                    title.setText(laporan_judul);
                    subtitle.setText(laporan_isi);
                    textViewId.setText(laporan_id);
                    no_komentar.setVisibility(View.GONE);

                    recyclerView = findViewById(R.id.rv3);
                    adapter = new KomentarAdapter(getApplicationContext(), detail_laporan.getKomentar());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

//                    adapter = new KomentarAdapter(getApplicationContext(), detail_laporan = response.body().getValue());
//                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DetailLaporanResponse> call, Throwable t) {
                Toast.makeText(DetailLaporanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void sendKomentar(  String laporan_id, String nik, String komentar) {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api =retrofit.create(API.class);
        Log.i("CEL NIK",nik);
        Call<KomentarResponse> update = api.tambahKomentar(Integer.parseInt(laporan_id), Long.parseLong(nik), komentar);
        update.enqueue(new Callback<KomentarResponse>() {
            @Override
            public void onResponse(Call<KomentarResponse> call, Response<KomentarResponse> response) {
                Log.i("SUCCC", response.message());
                Log.i("SUCCC", response.body().getMessage());

                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Berhasil mengirim komentar", Toast.LENGTH_SHORT).show();
                    DetailLaporanActivity.this.finish();

                }else{
                    Toast.makeText(getApplicationContext(),"Gagal mengirim komentar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KomentarResponse> call, Throwable t) {
                Toast.makeText(DetailLaporanActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                Log.i("GAGAL", t.getMessage());
            }
        });

    }





}
