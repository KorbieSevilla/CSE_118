package com.zybooks.rentadrivemobile2.ui.home;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zybooks.rentadrivemobile2.NavigationActivity;
import com.zybooks.rentadrivemobile2.Posting;
import com.zybooks.rentadrivemobile2.R;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.zybooks.rentadrivemobile2.UserPostActivity;

import java.util.List;


// For getting lat/lng in onMapReady
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


//public class HomeFragment extends Fragment implements OnMapReadyCallback
public class HomeFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap mMap;
    private HomeViewModel homeViewModel;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private Posting newPosting;

    private LatLngBounds location = new LatLngBounds(
            new LatLng(36.974117, -122.030792), new LatLng(37.0471707, -122.0152397));

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.homeMap);
        mapFragment.getMapAsync(this);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("postings");

        if (getArguments() != null && getArguments().getSerializable("Posting") != null) {
            newPosting = (Posting) getArguments().getSerializable("Posting");
        }

        FloatingActionButton postButton = getView().findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserPostActivity.class));
            }
        });


    }


    // onMapReady
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 10.0f;
        Marker SV;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.getCenter(), 9.5f));

//        LatLng scottsValley = new LatLng(37.051102, -122.014702);
//        SV = mMap.addMarker(new MarkerOptions()
//                .position(scottsValley)
//                .title("Scotts Valley"));
//        //.snippet("Population: 7 people"));
//        SV.showInfoWindow();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scottsValley, zoomLevel));


//         mMap.clear();
//         MarkerOptions mp = new MarkerOptions();
//         mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
//         mp.title("My position!!");
//         mMap.addMarker(mp);
//
//         mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                 new LatLng(location.getLatitude(), location.getLongitude()), 16));

    }
}





//        if(newPosting != null){
//            Toast.makeText(getActivity(), "Posting being placed", Toast.LENGTH_LONG).show();
//            mMap.addMarker(new MarkerOptions()
//                .position(newPosting.getAddresses().get(0))
//                .title(newPosting.getPrice() + "")
//                .snippet(newPosting.getDescription()));
//        }






////////////////////////////////////////////////////////////////////////////////////////////////////
//        mMap.clear();
//        MarkerOptions mp = new MarkerOptions();
//        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
//        mp.title("My position!!");
//        mMap.addMarker(mp);
//
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                new LatLng(location.getLatitude(), location.getLongitude()), 16));
////////////////////////////////////////////////////////////////////////////////////////////////////
//        if(newPosting != null){
//            Toast.makeText(getActivity(), "Posting found", Toast.LENGTH_LONG).show();
//            mMap.addMarker(new MarkerOptions()
//                .position(newPosting.getAddresses().get(0))
//                .title(newPosting.getPrice() + "")
//                .snippet(newPosting.getDescription()));
//        }
//
////////////////////////////////////////////////////////////////////////////////////////////////////
//        float zoomLevel = 10.0f;
//        Marker SV;
//
//        LatLng scottsValley = new LatLng(37.051102, -122.014702);
//        SV = mMap.addMarker(new MarkerOptions()
//                .position(scottsValley)
//                .title("Scotts Valley"));
//        //.snippet("Population: 7 people"));
//        SV.showInfoWindow();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scottsValley, zoomLevel));
//        mMap.clear();
//        MarkerOptions mp = new MarkerOptions();
//        mp.position(new LatLng(googleMap.getLatitude(), googleMap.getLongitude()));
//        mp.title("My position!!");
//        mMap.addMarker(mp);
//
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//            new LatLng(googleMap.getLatitude(), googleMap.getLongitude()), 16));