package com.zybooks.rentadrivemobile2.ui.home;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.rentadrivemobile2.NavigationActivity;
import com.zybooks.rentadrivemobile2.R;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.zybooks.rentadrivemobile2.UserPostActivity;

//public class HomeFragment extends Fragment implements OnMapReadyCallback
public class HomeFragment extends Fragment implements OnMapReadyCallback{
    GoogleMap mMap;
    private HomeViewModel homeViewModel;
    public HomeFragment() {

    }

    // DO NOT TOUCH EVER
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    // Make all map adjustments in here
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.homeMap);
        mapFragment.getMapAsync(this);

        FloatingActionButton postButton = getView().findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserPostActivity.class));
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 10.0f;
        Marker tosca, SV;

//         Add a marker in Santa Cruz and move the camera
        LatLng TOSCA = new LatLng(36.980560, -122.060204);
        tosca = mMap.addMarker(new MarkerOptions()
                .position(TOSCA)
                .title("Santa Cruz"));
        tosca.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TOSCA, zoomLevel));

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(santaCruz));
        tosca.hideInfoWindow();

        LatLng scottsValley = new LatLng(37.051102, -122.014702);
        SV = mMap.addMarker(new MarkerOptions()
                .position(scottsValley)
                .title("Scotts Valley"));
        //.snippet("Population: 7 people"));
        SV.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scottsValley, zoomLevel));

//        mMap.setOnInfoWindowClickListener(this);
    }
}