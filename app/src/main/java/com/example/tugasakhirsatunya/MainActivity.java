package com.example.tugasakhirsatunya;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.tugasakhirsatunya.Pojo.Tps;
import com.example.tugasakhirsatunya.Response.LaporanResponse;
import com.example.tugasakhirsatunya.Response.TpsResponse;
import com.example.tugasakhirsatunya.Utils.API;
import com.example.tugasakhirsatunya.Utils.BaseUrl;
import com.example.tugasakhirsatunya.Utils.RetrofitClient;
import com.example.tugasakhirsatunya.Utils.UtilsApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener{

    FloatingActionButton listFab;
    FloatingActionButton tanggapanFab;
    Button kameraButton;
    Spinner tpsSpinner;
    ProgressDialog loading;
    GoogleMap mMap;
    BaseUrl url;
    double lat, lng;
    EditText inputJudul, inputIsi;
    String tps_id, laporan_judul, laporan_isi, base64;
    String laporan_latitude;
    String laporan_longitude;
    List<String> listSpinner = new ArrayList<String>();
    Context mContext;
    API api;
    ImageView imageTemp;
    private ArrayList<Tps> tpsList = new ArrayList<>();
    Map<String, String> mMarkerMap = new HashMap<>();


    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    private Marker marker;
    private float zoom = 15f;
    private LocationManager locationManager;
    private Location currentLocation;
    private Marker currentMarker;
//    private MyLocationListener locationListener;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        url = new BaseUrl();
        checkLocationPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        locationListener = new MyLocationListener();
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 1, locationListener);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tpsSpinner = (Spinner) findViewById(R.id.spinnerTps);
        mContext = this;

        imageTemp = findViewById(R.id.imageTemp);
        inputJudul = findViewById(R.id.inputJudul);
        inputIsi =  findViewById(R.id.inputIsi);
        tpsSpinner = findViewById(R.id.spinnerTps);

        api = UtilsApi.getAPIService();

        inputJudul.addTextChangedListener(kameraTextWatcher);
        inputIsi.addTextChangedListener(kameraTextWatcher);

         ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, listSpinner);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tpsSpinner.setAdapter(spinnerArrayAdapter);
        initSpinner();
        tpsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                tpsSpinner.setSelection(position);
                tps_id = item.toString();
                Log.i("iobj",item.toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("iobj","no");
            }
        });

        kameraButton = (Button) this.findViewById(R.id.kameraButton);
        kameraButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                laporan_isi = inputIsi.getText().toString();
                laporan_judul = inputJudul.getText().toString();
                laporan_latitude = Double.toString(currentLocation.getLatitude());
                laporan_longitude = Double.toString(currentLocation.getLongitude());
                inputJudul.getText().clear();
                inputIsi.getText().clear();

                dispatchTakePictureIntent();
            }
        });

    }

    private TextWatcher kameraTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String judulInput = inputJudul.getText().toString().trim();
            String isiInput = inputIsi.getText().toString().trim();

            kameraButton.setEnabled(!judulInput.isEmpty() && !isiInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION },
                        TAG_CODE_PERMISSION_LOCATION);
            }
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageTemp.setImageBitmap(imageBitmap);
            base64 = convert(imageBitmap);
            Log.i("base",base64);
            toNik();
        }
    }

    private void toNik(){
        Intent intent = new Intent(getApplicationContext(), NikActivity.class);
        intent.putExtra("tps_id",tps_id);
        intent.putExtra("base64",base64);
        intent.putExtra("laporan_judul",laporan_judul);
        intent.putExtra("laporan_isi",laporan_isi);
        intent.putExtra("laporan_latitude", laporan_latitude);
        intent.putExtra("laporan_longitude",laporan_longitude);
        startActivity(intent);
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    private void initSpinner() {
        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);
        api.getListTps().enqueue(new Callback<TpsResponse>() {
            @Override
            public void onResponse(Call<TpsResponse> call, Response<TpsResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    ArrayList<Tps> TpsItems = response.body().getValue();
                    for (int i = 0; i < TpsItems.size(); i++) {
                        listSpinner.add(TpsItems.get(i).getId() + "|" + TpsItems.get(i).getTps_nama());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item, listSpinner);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    tpsSpinner.setAdapter(spinnerArrayAdapter);
                    spinnerArrayAdapter.notifyDataSetChanged();


                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil  TPS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TpsResponse> call, Throwable t) {
                loading.dismiss();
                Log.i("ERRR 500",t.getMessage());
                Log.i("ERRR 500",t.getCause().getMessage());
                Log.i("ERRR 500",t.toString());
                Log.i("ERRR 500",t.getLocalizedMessage());
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        tanggapanFab = findViewById(R.id.tanggapanFab);
        listFab = findViewById(R.id.listFab);
        listFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TpsActivity.class);
                startActivity(intent);
            }
        });

        tanggapanFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LaporanActivity.class);
                startActivity(intent);
            }
        });

    }
    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = map;
        mMap = googleMap;

        getAllDataLocationLatLng();


        currentMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                .title("Posisi saya")
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_person_pin_circle_black_24dp))));
        double lat = currentLocation.getLatitude();
        double lng = currentLocation.getLongitude();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), zoom));

    }

    private void getAllDataLocationLatLng() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Menampilkan data marker ..");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
//        API api= RetrofitClient.getClient().create(API.class);
        API api =retrofit.create(API.class);
        Call<TpsResponse> call = api.getListTps();
        call.enqueue(new Callback<TpsResponse>() {
            @Override
            public void onResponse(Call<TpsResponse> call, Response<TpsResponse> response) {
                dialog.dismiss();
                tpsList = response.body().getValue();
                initMarker(tpsList);
            }

            @Override
            public void onFailure(Call<TpsResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void initMarker(final ArrayList<Tps> tpsList) {

        for (int i=0; i<tpsList.size(); i++){
            //set latlng nya
            LatLng location = new LatLng((tpsList.get(i).getTps_lattitude()),
                   (tpsList.get(i).getTps_longitude()));

            if (tpsList.get(i).getTps_jenis().equals("TPS Resmi")){
                marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(tpsList.get(i).getTps_nama())
                        .snippet(tpsList.get(i).getTps_jenis())
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker_tps))));
            }

            else if (tpsList.get(i).getTps_jenis().equals("TPS Liar")) {
                marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(tpsList.get(i).getTps_nama())
                        .snippet(tpsList.get(i).getTps_jenis())
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker_tps3))));
            }
            else{
                marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(tpsList.get(i).getTps_nama())
                        .snippet(tpsList.get(i).getTps_jenis()) //nanti ini ganti jadi jalan ajaaaaa apa gausah ya mager
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker_tps2))));
            }

            //tambahkan markernya

            //set latlng index ke 0
            LatLng latLng = new LatLng((tpsList.get(0).getTps_lattitude()),
                    (tpsList.get(0).getTps_longitude()));
            //lalu arahkan zooming ke marker index ke 0
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), 17.0f));
            mMarkerMap.put(marker.getId(), tpsList.get(i).getId());


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    int i = 0;
                    String tps_id = mMarkerMap.get(marker.getId());
                    String tps_nama = marker.getTitle();
                    String tps_jenis = marker.getSnippet();
                    String tps_volume = tpsList.get(i).getTps_volume();
                    Intent intent = new Intent(MainActivity.this, DetailTpsActivity.class);
                    intent.putExtra("tps_nama", tps_nama);
                    intent.putExtra("tps_jenis", tps_jenis);
                    intent.putExtra("tps_volume", tps_volume);
                    intent.putExtra("tps_id", tps_id);
                    startActivity(intent);

                    return false;
                }
            });
        }
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }



    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();



        @Override
        public void onLocationChanged(Location location) {
            double lat1 = location.getLongitude();
            double lng1= location.getLatitude();
//            try {
//                getAlamat(lat1,lng1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }


//    public void getAlamat(Double latitude, Double longitude) throws IOException {
//
//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(this, Locale.getDefault());
//
//        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName();
//
//        Log.i("KECAMATAN", knownName);
////        Nametext.setText(knownName);
//    }


}


