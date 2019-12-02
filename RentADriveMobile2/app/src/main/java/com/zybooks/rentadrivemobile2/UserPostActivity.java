package com.zybooks.rentadrivemobile2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

public class UserPostActivity extends AppCompatActivity {
    EditText name, address, description, price;
    Button post, pickImage, addImage;
    Uri filepath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageRef;

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
        pickImage = findViewById(R.id.chooseImage);
        addImage = findViewById(R.id.uploadImage);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

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
        DatabaseReference ref = db.getReference();
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
        Map<String, Posting> postings = new HashMap<>();
        postings.put(name.getText().toString(), p);
        ref.child("postings").setValue(postings).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("UserPostACtivity", task.getException().getMessage());
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
            }else if(addresses.size() < 1){
                Toast.makeText(this, "The address you entered is invalid, please try again.", Toast.LENGTH_LONG).show();
                return null;
            }else{
                Address location = addresses.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return p1;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
           && data.getData() != null){
            filepath = data.getData();
        }
    }

    private void uploadImage() {
        if(filepath != null){

            StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UserPostActivity.this, "Image uploaded successfully!", Toast.LENGTH_LONG);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserPostActivity.this, "Image failed to upload.", Toast.LENGTH_LONG);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        }else{
            Toast.makeText(this, "filepath is null", Toast.LENGTH_LONG);
        }
    }


}
