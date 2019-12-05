package com.zybooks.rentadrivemobile2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.annotation.Nullable;

public class NavigationActivity extends AppCompatActivity {
    private URI imageUri;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        setSupportActionBar(toolbar);
//      FloatingActionButton postButton = findViewById(R.id.postButton);
        final TextView navUserName = (TextView) headerView.findViewById(R.id.nav_username);
        TextView navUserEmail = (TextView) headerView.findViewById(R.id.nav_userEmail);
        ImageView profilePic = (ImageView) headerView.findViewById(R.id.imageView);
        final ImageView imageView = headerView.findViewById(R.id.imageView);
        String pathString = "profile_pics/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(pathString);


        //get reference to firebase dataBase
        //get Users email and firebaseAuth userID
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Glide.with(NavigationActivity.this)
                        .load(task.getResult())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageView);
            }
        });



        //get Username from firebase realtime
        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("userName");
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.getValue(String.class);
                navUserName.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        navUserEmail.setText(userEmail);

        //Post Button goes to the userPostActivity page
//        postButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(NavigationActivity.this, UserPostActivity.class));
//            }
//        });

        //userProfile changin
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent, "Pick an image"), 1);

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            NavigationView mNavigationView = findViewById(R.id.nav_view);
            View headerView = mNavigationView.getHeaderView(0);
            final ImageView imageView = headerView.findViewById(R.id.imageView);
            String pathString = "profile_pics/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(pathString);

            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                uploadImageAndSaveUri(bitmap);

                storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Glide.with(NavigationActivity.this)
                                .load(task.getResult())
                                .apply(RequestOptions.circleCropTransform())
                                .into(imageView);
                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImageAndSaveUri(Bitmap bitmap) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        final ImageView imageView = headerView.findViewById(R.id.imageView);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String pathString = "profile_pics/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(pathString);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte image[] = baos.toByteArray();
        UploadTask uploadTask = storageRef.putBytes(image);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                String pathString = "profile_pics/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Toast.makeText(NavigationActivity.this, "Image saved", Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
