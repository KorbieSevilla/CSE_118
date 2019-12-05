package com.zybooks.rentadrivemobile2;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowCloseListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //This is a test comment for git
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 10.0f;
        Marker tosca, SV;

        if(getIntent() != null){
            Posting p = (Posting) getIntent().getSerializableExtra("Posting");
            mMap.addMarker(new MarkerOptions()
                    .position(p.getAddresses().get(0))
                    .title(p.getPrice() + "")
                    .snippet(p.getDescription()));

        }

        // Add a marker in Santa Cruz and move the camera
        LatLng TOSCA = new LatLng(36.980560, -122.060204);
        tosca = mMap.addMarker(new MarkerOptions()
                .position(TOSCA)
                .title("Santa Cruz"));
        tosca.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TOSCA, zoomLevel));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(santaCruz));
        //tosca.hideInfoWindow();

        LatLng scottsValley = new LatLng(37.051102, -122.014702);
        SV = mMap.addMarker(new MarkerOptions()
                .position(scottsValley)
                .title("Scotts Valley"));
                //.snippet("Population: 7 people"));
        SV.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scottsValley, zoomLevel));

        //mMap.setOnInfoWindowClickListener(this);
    }

//    @Override
//    public void onInfoWindowClick(Marker marker){
//        Toast.makeText(this, "InfoWindow has been clicked", )
//    }



}
