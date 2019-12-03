package com.zybooks.rentadrivemobile2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zybooks.rentadrivemobile2.R;
import com.google.android.gms.maps.OnMapReadyCallback;

public class HomeFragment extends FragmentActivity implements OnMapReadyCallback{
    GoogleMap mMap;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.homeMap);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 10.0f;
        Marker tosca, SV;

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
}