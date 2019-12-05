package com.zybooks.rentadrivemobile2.ui.home;





//Test
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import com.zybooks.rentadrivemobile2.DrivewayDialog;
import com.zybooks.rentadrivemobile2.Posting;
import com.zybooks.rentadrivemobile2.R;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.zybooks.rentadrivemobile2.UserPostActivity;



//public class HomeFragment extends Fragment implements OnMapReadyCallback
public class HomeFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap mMap;
    private HomeViewModel homeViewModel;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private Posting newPosting;


    private LatLngBounds locations = new LatLngBounds(
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

        // Positions the camera with the specific bounds. Refer to line 55
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations.getCenter(), 9.5f));

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot posting : dataSnapshot.getChildren()) {
                    Posting p = posting.getValue(Posting.class);
                    LatLng location = new LatLng(p.getLat(), p.getLong());

                    Marker m = mMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(p.getPrice() + "")
                            .snippet(p.getDescription())
                    );
                    if(!m.isInfoWindowShown()){
                        m.showInfoWindow();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        ref.addValueEventListener(listener);




        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                DrivewayDialog dialog = new DrivewayDialog();
                dialog.show(getFragmentManager(), "driveway dialog");

                return true;
            }
        });
    }

}