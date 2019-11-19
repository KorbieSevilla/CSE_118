package com.zybooks.rentadrivemobile2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserPostActivity extends AppCompatActivity {
    EditText address, description, price;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_user_post);

        post = findViewById(R.id.post);
        address = findViewById(R.id.address);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addr = address.getText().toString();
                String desc = description.getText().toString();
                String mPrice = price.getText().toString();

                if(addr.isEmpty()){
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
        DatabaseReference ref = db.getReference("users");
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref.child(currentUID).child("Addresses").setValue(addr).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}
