package com.example.tugasakhirsatunya.Utils;

import android.content.Intent;

import com.example.tugasakhirsatunya.Response.DetailLaporanResponse;
import com.example.tugasakhirsatunya.Response.KomentarResponse;
import com.example.tugasakhirsatunya.Response.LaporanResponse;
import com.example.tugasakhirsatunya.Response.TpsResponse;
//import com.example.tugasakhirsatunya.Response.LoginResponse;
import com.example.tugasakhirsatunya.Pojo.Tps;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

//    @FormUrlEncoded
//    @POST("listtps")
//    Call<TpsResponse> tpsRequest(
//            @Field("tps_nama") String tps_nama,
//            @Field("tps_volume") String tps_volume,
//            @Field("tps_alamat") String tps_alamat
//    );

    @GET("detail")
    Call <TpsResponse> getListTps();

    @GET("laporan")
    Call <LaporanResponse> getListLaporan();

//    @GET("detail/laporan/{komentar}")
//    Call <DetailLaporanResponse> getListDetailLaporan(@Path("komentar") String komentar);

    @FormUrlEncoded
    @POST("laporan/store")
    Call<LaporanResponse> tambahLaporan(
            @Field("pelapor_id") Long pelapor_id,
            @Field("tps_id") String tps_id,
            @Field("laporan_judul") String laporan_judul,
            @Field("laporan_isi") String laporan_isi,
            @Field("laporan_foto") String laporan_foto,
            @Field("laporan_lattitude") double laporan_lattitude,
            @Field("laporan_longitude") double laporan_longitude
    );

    @FormUrlEncoded
    @POST("komentar/store")
    Call<KomentarResponse> tambahKomentar(
            @Field("laporan_id") Integer laporan_id,
            @Field("pelapor_id") Long pelapor_id,
            @Field("komentar_isi") String komentar_isi

    );

    @GET("detail/laporan/{komentar}")
        Call <DetailLaporanResponse> getListDetailLaporan(@Path("komentar") String komentar);
}
