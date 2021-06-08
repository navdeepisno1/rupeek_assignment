package com.suvidha.rupeekhiring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class Destination extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Intent intent;
    private POI poi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        intent = getIntent();

        poi = new POI();
        poi.setName(intent.getStringExtra("name"));
        poi.setAddress(intent.getStringExtra("address"));
        poi.setLatitude(intent.getStringExtra("latitude"));
        poi.setLongitude(intent.getStringExtra("longitude"));

        ImageView imageView_back = findViewById(R.id.destination_iv_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView textView_name = findViewById(R.id.destination_tv_name);
        if(poi.getName().length()<25)
        {
            textView_name.setText(poi.getName());
        }
        else
        {
            textView_name.setText(poi.getName().substring(0,25) + "...");
        }

        MaterialButton materialButton_showDestination = findViewById(R.id.destination_btn_show_direction);
        materialButton_showDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + poi.getLatitude().substring(0,poi.getLatitude().length()-3) + "," + poi.getLongitude().substring(0,poi.getLongitude().length()-3)));
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(Double.parseDouble(poi.getLatitude().substring(0,poi.getLatitude().length()-3)), Double.parseDouble(poi.getLongitude().substring(0,poi.getLongitude().length()-3)));
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(poi.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));
    }
}