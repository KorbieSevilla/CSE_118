package com.zybooks.rentadrivemobile2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

//Registration activity where users set up their information
public class Register extends AppCompatActivity {
    EditText name, password, email, phone;
    Button registerPoster, registerRenter;
    FirebaseAuth uAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        registerPoster = findViewById(R.id.registerPoster);
        registerRenter = findViewById(R.id.registerRenter);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        uAuth = FirebaseAuth.getInstance();

        registerRenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser("rent");
            }
        });

        registerPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser("post");
            }
        });
    }

    //Checks that information inputted is valid and adds user to Firebase Authentication and
    //adds other user information to the database using a custom User class
    private void registerUser(final String activity){
        final String uName = name.getText().toString().trim();
        final String uPW = password.getText().toString().trim();
        final String uEmail = email.getText().toString().trim();
        final String uPhone = phone.getText().toString().trim();

        if(uPW.isEmpty() || uPW.length() < 6){
            password.setError("Please input a password of length at least 6 characters");
            password.requestFocus();
            return;
        }


        if(uName.isEmpty() || uEmail.isEmpty() || uPhone.isEmpty()){
            Toast.makeText(this, "Please fill in all fields properly", Toast.LENGTH_LONG).show();
            return;
        }

        if(uPhone.length() != 10) {
            Toast.makeText(this, "Please enter your 10 digit phone number.", Toast.LENGTH_LONG).show();
            phone.setError("Please enter 10 digit phone number");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        uAuth.createUserWithEmailAndPassword(uEmail, uPW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(Register.this, "Auth properly passed", Toast.LENGTH_LONG).show();

                if(task.isSuccessful()){
                    User user = new User(uName, uEmail, uPhone, null);
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "User added successfully!", Toast.LENGTH_LONG).show();
                                if(activity.equals("post")){
                                    startActivity(new Intent(Register.this, UserPostActivity.class));
                                }else if(activity.equals("rent")){
                                    startActivity(new Intent(Register.this, NavigationActivity.class));
                                }
                            }else{
                                Toast.makeText(Register.this, "User failed to be added to database", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });
                }else{
                    Toast.makeText(Register.this, "User failed to be authenticated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
