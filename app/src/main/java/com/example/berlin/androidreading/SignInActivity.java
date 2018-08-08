package com.example.berlin.androidreading;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.berlin.androidreading.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText rdUsername, rdPassword, rdName;
    TextView linkSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btnSignIn);

        rdUsername = findViewById(R.id.rdUsername);
        rdPassword = findViewById(R.id.rdPassword);

        linkSignUp = findViewById(R.id.linkSignUp);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Check if user not exist in database

                        if(dataSnapshot.child(rdUsername.getText().toString()).exists()){

                        //Get User Information
                            User user = dataSnapshot.child(rdUsername.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(rdPassword.getText().toString()))
                            {
                                Toast.makeText(SignInActivity.this, "Sign In successfully !", Toast.LENGTH_SHORT).show();
                            }

                            // Apabila Password Salah
                            else
                            {
                                Toast.makeText(SignInActivity.this, "Sign In fail ! ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // Apabila Phone salah
                        else
                        {
                            Toast.makeText(SignInActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(signUp);
            }
        });



    }
}
