package com.swings.dongphongclass2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.swings.dongphongclass2.data.FirebaseDataHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText edname,edPass,edRepass;
    private Button btnSubmit;
    private FirebaseAuth mAuth;
    private TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
        //khoi tao bien
        edname = (EditText) findViewById(R.id.acti_regis_ed_name);
        edPass = (EditText) findViewById(R.id.acti_regis_ed_pass);
        edRepass = (EditText) findViewById(R.id.acti_regis_ed_repass);
        btnSubmit = (Button) findViewById(R.id.acti_regis_btn_submit);
        txtResult = (TextView) findViewById(R.id.acti_regis_txt_result);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edname.getText().toString();
                String pass = edPass.getText().toString();
                String repass = edRepass.getText().toString();
                if (!pass.equals(repass) && pass == "" && pass == null)

                    txtResult.setText("mật khẩu không trùng nhau");
                else {
                    final ProgressDialog progressBar = new ProgressDialog(RegisterActivity.this);
                    progressBar.setTitle(R.string.login);
                    progressBar.setMessage(String.valueOf(R.string.wait));
                    progressBar.setCancelable(false);
                    mAuth = FirebaseAuth.getInstance();
                    progressBar.show();
                    mAuth.createUserWithEmailAndPassword(name, pass)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        progressBar.dismiss();
                                        txtResult.setText("Khởi tạo tài khoản thất bại");
                                    } else {
                                        progressBar.setMessage("Thành công, đang đăng nhập");
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }
                                    // ...
                                }
                            });

                }
            }
        });
    }
}
