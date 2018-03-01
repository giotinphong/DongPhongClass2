package com.swings.dongphongclass2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAcitivity extends AppCompatActivity {
private Button btnLogin;
    private EditText edName,edPass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
        btnLogin = (Button) findViewById(R.id.acti_login_btn_login);
        edName = (EditText)findViewById(R.id.acti_login_ed_name);
        edPass = (EditText)findViewById(R.id.acti_login_ed_pass);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent in = new Intent(LoginAcitivity.this,MainActivity.class);
                    startActivity(in);
                } else {
                    // User is signed out

                }
                // ...
            }
        };
        final ProgressDialog progressBar = new ProgressDialog(LoginAcitivity.this);
        progressBar.setTitle(R.string.login);
        progressBar.setMessage(String.valueOf(R.string.wait));
        progressBar.setCancelable(true);
        progressBar.setIndeterminate(true);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // btnLogin.setEnabled(false);
                progressBar.show();
                String email = edName.getText().toString();
                String password = edPass.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginAcitivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.dismiss();

                               // btnLogin.setEnabled(true);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                    Toast.makeText(LoginAcitivity.this, "Đăng nhập thất bại",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{

                                    Intent in = new Intent(LoginAcitivity.this,MainActivity.class);
                                    startActivity(in);
                                    finish();
                                }
                            }
                        });

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
