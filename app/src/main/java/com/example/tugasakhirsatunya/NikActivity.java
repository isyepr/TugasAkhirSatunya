package com.example.tugasakhirsatunya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tugasakhirsatunya.Pojo.DetailLaporan;
import com.example.tugasakhirsatunya.Response.LaporanResponse;
import com.example.tugasakhirsatunya.Utils.API;
import com.example.tugasakhirsatunya.Utils.BaseUrl;
import com.example.tugasakhirsatunya.Utils.Session;
import com.example.tugasakhirsatunya.Utils.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NikActivity extends AppCompatActivity {

    Button kirimButton;
    EditText inputNIK, inputJudul, inputIsi;
    API api;
    Long nik;
    double lat;
    double lng;
    String tps_id;
    String base64;
    String laporan_judul;
    String laporan_isi;
    String laporan_latitude;
    String laporan_longitude;
    ProgressDialog loading;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nik);
        kirimButton = findViewById(R.id.kirimButton);
        inputNIK = findViewById(R.id.inputNik);
        inputJudul = findViewById(R.id.inputJudul);
        inputIsi = findViewById(R.id.inputIsi);
        session = new Session(getApplicationContext());
        inputNIK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nikInput = inputNIK.getText().toString().trim();
                kirimButton.setEnabled(!nikInput.isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tps_id = getIntent().getStringExtra("tps_id");
        base64 = getIntent().getStringExtra("base64");
        laporan_judul = getIntent().getStringExtra("laporan_judul");
        laporan_isi = getIntent().getStringExtra("laporan_isi");
        laporan_latitude = getIntent().getStringExtra("laporan_latitude");
        laporan_longitude = getIntent().getStringExtra("laporan_longitude");

        Log.i("TEST", tps_id);
        Log.i("TEST", base64);
        Log.i("TEST", laporan_judul);
        Log.i("TEST", laporan_isi);
        Log.i("TEST", laporan_latitude);
        Log.i("TEST", laporan_longitude);
//        openBerhasil();

        if(session.getNik() != "-"){
            inputNIK.setText(session.getNik());
        }

        kirimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nik = Long.valueOf(inputNIK.getText().toString());
                session.setNik(String.valueOf(nik));
                Log.i("NIK", String.valueOf(nik));
                String tps_id = getIntent().getStringExtra("tps_id");
                String laporan_judul = getIntent().getStringExtra("laporan_judul");
                String laporan_isi = getIntent().getStringExtra("laporan_isi");
                String laporan_latitude = getIntent().getStringExtra("laporan_latitude");
                String laporan_longitude = getIntent().getStringExtra("laporan_longitude");
                kirimButton.setVisibility(View.GONE);
                submitLaporan(laporan_judul, nik, laporan_isi, tps_id, laporan_latitude, laporan_longitude);
                Intent intent = new Intent(getApplicationContext(), BerhasilActivity.class);
                startActivity(intent);

            }
        });


    }




    private void submitLaporan(String laporan_isi, Long nik, String laporan_judul, String tps_id, String laporan_latitude, String laporan_longitude) {
        Log.i("INPUT", laporan_isi + " 0 " + tps_id);
        loading = ProgressDialog.show(NikActivity.this, null, "harap tunggu...", true, false);
        api = UtilsApi.getAPIService();

        api.tambahLaporan(nik, tps_id, laporan_isi,laporan_judul, base64, Double.parseDouble(laporan_latitude), Double.parseDouble(laporan_longitude)).enqueue(new Callback<LaporanResponse>() {
            @Override
            public void onResponse(Call<LaporanResponse> call, Response<LaporanResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Log.i("SERRR", response.body().getMessage());
                    // Set hasil result json ke dalam adapter spinner
                } else {
                    loading.dismiss();
                    Toast.makeText(NikActivity.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
                Log.i("SERRR", response.message());
                Log.i("SERRR", String.valueOf(response.code()));
                Log.i("SERRR", String.valueOf(response.body()));
            }


            @Override
            public void onFailure(Call<LaporanResponse> call, Throwable t) {
                Log.i("SERRR", t.getMessage());
            }
        });
        toKomentar();
    }
    private void toKomentar(){
        Intent intent = new Intent(getApplicationContext(), DetailLaporanActivity.class);
        String nik_str = String.valueOf(nik);
        intent.putExtra("nik", nik_str);
        startActivity(intent);
    }


//    private void openBerhasil() {
//        kirimButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BerhasilActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}
