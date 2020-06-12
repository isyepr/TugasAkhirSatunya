package com.example.tugasakhirsatunya.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Session {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String nik;
    Context _context;

    private String Session;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "session";

    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setNik(String nik) {
        this.nik = nik;
        editor.putString("nik", nik);
        editor.commit();
    }

    public String getNik() {
        String nik= pref.getString("nik","-");
        return nik;
    }


}
