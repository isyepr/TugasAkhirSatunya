package com.example.tugasakhirsatunya.Pojo;

import java.sql.Timestamp;

public class Tps {

    private Integer id;
    private Integer jalan_id;
    private String tps_nama;
    private String tps_jenis;
    private String tps_volume;
    private Float tps_lattitude;
    private Float tps_longitude;
    private String tps_photo_url;
    private String created_at;
    private String updated_at;
    private Integer is_tps;

    public String getId() {
        return id.toString();
    }

    public Integer getJalan_id() {
        return jalan_id;
    }

    public String getTps_nama() {
        return tps_nama;
    }

    public String getTps_jenis() {
        return tps_jenis;
    }

    public String getTps_volume() {
        return tps_volume;
    }

    public Float getTps_lattitude() {
        return tps_lattitude;
    }

    public Float getTps_longitude() {
        return tps_longitude;
    }

    public String getTps_photo_url() {
        return tps_photo_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Integer getIs_tps() {
        return is_tps;
    }
}
