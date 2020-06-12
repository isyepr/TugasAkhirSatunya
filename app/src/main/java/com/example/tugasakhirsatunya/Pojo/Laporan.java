package com.example.tugasakhirsatunya.Pojo;

import java.sql.Timestamp;
import java.util.Date;

public class Laporan {

    private Integer id;
    private Long pelapor_id;
    private Integer tps_id;
    private Float laporan_longitude;
    private Float laporan_latittude;
    private String laporan_judul;
    private String laporan_isi;
    private String laporan_foto;
    private String laporan_tanggal;
    private String created_at;
    private String updated_at;

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Integer getId() {
        return id;
    }

    public Long getPelapor_id() {
        return pelapor_id;
    }

    public Integer getTps_id() {
        return tps_id;
    }

    public Float getLaporan_longitude() {
        return laporan_longitude;
    }

    public Float getLaporan_latittude() {
        return laporan_latittude;
    }

    public String getLaporan_judul() {
        return laporan_judul;
    }

    public String getLaporan_isi() {
        return laporan_isi;
    }

    public String getLaporan_tanggal() { return laporan_tanggal;}

    public String getLaporan_foto() {
        return laporan_foto;
    }





}
