package com.zybooks.rentadrivemobile2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UserPostActivity extends AppCompatActivity {
    EditText name, address, description, price;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_user_post);

        name = findViewById(R.id.posterName);
        post = findViewById(R.id.post);
        address = findViewById(R.id.address);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String posterName = name.getText().toString();
                String addr = address.getText().toString();
                String desc = description.getText().toString();
                String mPrice = price.getText().toString();

                if(posterName.isEmpty()){
                    name.setError("Please enter your name.");
                }else if(addr.isEmpty()){
                    address.setError("Please enter an address.");
                }else if(desc.isEmpty()){
                    description.setError("Please enter a description.");
                }else if(mPrice.isEmpty()){
                    price.setError("Please enter a price.");
                }

                addLocationToDB(addr);
            }
        });
    }

    private void addLocationToDB(String addr) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("postings");
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ArrayList<LatLng> addresses = new ArrayList<>();
        List<LatLng> locations;

        if(address2LatLng(addr) != null){
            addresses.add(address2LatLng(addr));
        }else{
            address.setError("Please enter a valid address");
            return;
        }

        locations = addresses;
        Posting p = new Posting(locations, description.getText().toString());

        ref.child(name.getText().toString()).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserPostActivity.this, "Successfully added driveway!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserPostActivity.this, NavigationActivity.class));
                }else{
                    Toast.makeText(UserPostActivity.this, "Failed to add driveway", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private LatLng address2LatLng(String addr){
        Geocoder coder = new Geocoder(this);
        List<Address> addresses;
        LatLng p1 = null;

        try {
            addresses = coder.getFromLocationName(addr, 5);
            if(addresses == null){
                return null;
            }

            Address location = addresses.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException e){
            e.printStackTrace();
        }

        return p1;
    }
}
