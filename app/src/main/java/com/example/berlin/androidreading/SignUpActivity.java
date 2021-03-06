package com.example.berlin.androidreading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.berlin.androidreading.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText rdUsername, rdName, rdPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        rdUsername = findViewById(R.id.rdUsername);
        rdPassword = findViewById(R.id.rdPassword);
        rdName = findViewById(R.id.rdName);

        btnSignUp = findViewById(R.id.btnSignUp);

        // Init Firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Peringatan jika sudah ada
                        if(dataSnapshot.child(rdUsername.getText().toString()).exists())
                        {
                            Toast.makeText(SignUpActivity.this, "This account already register", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            User user = new User(rdName.getText().toString(),rdPassword.getText().toString());
                            table_user.child(rdUsername.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
