package com.example.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button signUpBtn;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordRe;
    private ImageButton btViewPass;
    private ImageButton btViewPassRe;
    boolean showingPass = false;
    boolean showingRe = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signUpBtn = findViewById(R.id.btSignIn);
        btViewPass = findViewById(R.id.btViewPassword);
        btViewPassRe = findViewById(R.id.btViewPasswordRe);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordRe = findViewById(R.id.etPasswordRe);
        TextView tvLoginLink = findViewById(R.id.tvLoginLink);

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

        btViewPassRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!showingRe){
                    etPasswordRe.setInputType(0x00000091);
                    showingRe = true;
                }
                else{
                    etPasswordRe.setInputType(0x00000081);
                    showingRe = false;
                }

            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordRe = etPasswordRe.getText().toString();


                if(!password.equals(passwordRe)){
                    etPasswordRe.setError("Passwords do not match");
                }

                if(!isValidPassword(password)){
                    etPassword.setError("Password must contain 8 or more characters with a mix of letters, numbers & symbols");
                }

                if(password.equals(passwordRe) && isValidPassword(password)){
                    Log.d("Accepted Password", password);
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d("Sign Up", "createUserWithEmailAndPassword:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(SignUpActivity.this,"Email sent to: " + email, Toast.LENGTH_LONG).show();
                                                            Log.d("Sign Up","Email sent.");
                                                        }
                                                    }
                                                });
                                    }
                                    else{
                                        Log.w("Sign Up", "createUserWithEmail:failure", task.getException());
                                    }
                                }
                            });
                }
            }
        });

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent loginIntent = new Intent(v.getContext(), SignInActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    public static boolean isValidPassword(final String password) {
        //8 characters, 1 alphabet, 1 number, 1 special character
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}
