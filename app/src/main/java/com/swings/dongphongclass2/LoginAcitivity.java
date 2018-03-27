package com.swings.dongphongclass2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAcitivity extends AppCompatActivity {
private Button btnLogin;
    private TextView btnRegis,btnForgotPass;
    private EditText edName,edPass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
        btnLogin = (Button) findViewById(R.id.acti_login_btn_login);
        edName = (EditText)findViewById(R.id.acti_login_ed_name);
        edPass = (EditText)findViewById(R.id.acti_login_ed_pass);
        btnRegis = (TextView) findViewById(R.id.acti_login_btn_register);
        btnRegis.setPaintFlags(btnRegis.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnForgotPass = (TextView) findViewById(R.id.acti_login_btn_fogotpass);
        btnForgotPass.setPaintFlags(btnForgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent in = new Intent(LoginAcitivity.this,MainActivity2.class);
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
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String s = user.getUid();
                                    //updateUI(user);
                                    Intent in = new Intent(LoginAcitivity.this,MainActivity2.class);
                                    startActivity(in);
                                    finish();
                                }
                            }
                        });

            }
        });

        btnRegis.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginAcitivity.this,RegisterActivity.class));
                    }
                }
        );
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(LoginAcitivity.this);
                dialog.setContentView(R.layout.dialog_input_note);
                dialog.setTitle("Nhập email");
                final EditText edMail = (EditText) dialog.findViewById(R.id.dialog_input_note_ed_note);
                edMail.setText("Nhập địa chỉ email đã đăng kí");
                final Button btnok = (Button) dialog.findViewById(R.id.dialog_input_note_btn_save);
                final Button btncancel = (Button)dialog.findViewById(R.id.dialog_input_note_btn_cancel);
                dialog.show();
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }


        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(LoginAcitivity.this);
                dialog.setContentView(R.layout.dialog_input_note);
                dialog.setTitle("Nhập email");
                final EditText edMail = (EditText) dialog.findViewById(R.id.dialog_input_note_ed_note);
                edMail.setHint("Nhập địa chỉ email đã đăng kí");
                edMail.setText("");
                final Button btnok = (Button) dialog.findViewById(R.id.dialog_input_note_btn_save);
                final Button btncancel = (Button) dialog.findViewById(R.id.dialog_input_note_btn_cancel);
                dialog.show();
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(edMail.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            Toast.makeText(LoginAcitivity.this,"Thông tin khôi phục đã gởi về email",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                    }
                });
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
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
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
