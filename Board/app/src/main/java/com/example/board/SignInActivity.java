package com.example.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btSignIn;
    private EditText etEmail;
    private EditText etPassword;
    private ImageButton btViewPass;
    Intent loginIntent;
    Intent signupIntent;
    Intent homePageIntent;
    boolean showingPass = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btSignIn = findViewById(R.id.btSignIn);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btViewPass = findViewById(R.id.btViewPasswordIn);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView t2 = findViewById(R.id.tvLinkSignUp);
        ImageView logoClickable = findViewById(R.id.logoImage);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(currentUser!=null){
            //loginIntent = new Intent(this, HomePage.class)
            //startActivity(loginIntent)
        }

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Sign In", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    loginIntent = new Intent(SignInActivity.this,HomeActivity.class);
                                    startActivity(loginIntent);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Sign In", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });

        btViewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!showingPass) {
                    etPassword.setInputType(0x00000091);
                    showingPass = true;
                }
                else{
                    etPassword.setInputType( 0x00000081);
                    showingPass = false;
                }
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signupIntent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(signupIntent);
            }
        });

        logoClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageIntent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(homePageIntent);
            }
        });

    }
}
