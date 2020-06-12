package com.example.tugasakhirsatunya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugasakhirsatunya.Pojo.Komentar;
import com.example.tugasakhirsatunya.Response.KomentarResponse;
import com.example.tugasakhirsatunya.Utils.API;
import com.example.tugasakhirsatunya.Utils.BaseUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailTpsActivity extends AppCompatActivity {
    Button kembaliButton;
    BaseUrl baseUrl = new BaseUrl();
    String idTps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        kembaliButton = findViewById(R.id.kembaliButton);


        final Intent fromDataList = getIntent();

        

        TextView Dtps_nama = findViewById(R.id.textviewTpsNama);
        TextView Dtps_jenis = findViewById(R.id.textViewTpsJenis);
        TextView Dtps_volume = findViewById(R.id.textViewTpsVolume);
        TextView Dtps_jalan = findViewById(R.id.textViewJalan);

        Dtps_nama.setText(fromDataList.getStringExtra("tps_nama"));
        Dtps_jenis.setText(fromDataList.getStringExtra("tps_jenis"));
        Dtps_volume.setText(fromDataList.getStringExtra("tps_volume"));
        Dtps_jalan.setText(fromDataList.getStringExtra("tps_jalan"));

        idTps = fromDataList.getStringExtra("tps_nama");



        openMain();
    }

    private void openMain() {
        kembaliButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }




}
