package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Intent loginIntent;
    Intent signupIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            //loginIntent = new Intent(this, HomePage.class)
            //startActivity(loginIntent)
        }

        TextView t2 = findViewById(R.id.tvLinkSignUp);

        t2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signupIntent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(signupIntent);
            }
        });

    }
}
