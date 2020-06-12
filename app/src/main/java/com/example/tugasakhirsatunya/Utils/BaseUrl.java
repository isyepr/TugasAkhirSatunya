package com.example.tugasakhirsatunya.Utils;

public class BaseUrl {
    private String url = "http://192.168.1.4:8000/api/";

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getNik() {
        return nik;
    }

    public static String setNik(String nik) {
        return nik;
    }

    private static String nik = "";
    public String getUrl() {
        return url;
    }

}
