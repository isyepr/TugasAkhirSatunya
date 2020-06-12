package com.example.tugasakhirsatunya.Utils;

public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.1.4:8000/api/";

    public static API getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(API.class);
    }
}
