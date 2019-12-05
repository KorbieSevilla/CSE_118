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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login, register;
    FirebaseAuth fAuth;


    // MAPS TESTING PURPOSES
    Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.loginUser);
        password = findViewById(R.id.loginPass);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


        // MAPS TESTING PURPOSES
        map = findViewById(R.id.map);


        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pw = password.getText().toString();
                String uEmail = email.getText().toString();

                if(uEmail.isEmpty()){
                    email.setError("Email required");
                    return;
                }
                if(pw.length() < 6){
                    password.setError("Enter a password of length at least 6 characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(uEmail,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            startActivity((new Intent(MainActivity.this, NavigationActivity.class)));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Login Failed. Check email or password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });



    }
}
