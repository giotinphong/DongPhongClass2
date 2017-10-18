package com.swings.dongphongclass2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroductActivity extends AppCompatActivity {
    private Button btnShowList,btnStatic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduct);
        btnShowList = (Button) findViewById(R.id.acti_introduct_btn_showlist);
        btnStatic = (Button)findViewById(R.id.acti_introduct_btn_showstatic);

        btnShowList.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               startActivity(new Intent(IntroductActivity.this,MainActivity.class));
                                           }
                                       }
        );
        btnStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductActivity.this,StaticActivity.class));
            }
        });
    }
}
