package com.swings.dongphongclass2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;

import java.util.ArrayList;

public class StaticActivity extends AppCompatActivity {
    private TextView txtSumStudent,txtSumAmount,txtSumAmountReceived;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        txtSumStudent = (TextView)findViewById(R.id.acti_static_txt_sumOfStudent);
        txtSumAmount = (TextView)findViewById(R.id.acti_static_txt_sumOfAmount);
        txtSumAmountReceived = (TextView)findViewById(R.id.acti_static_txt_sumOfAmountReceived);

        final ArrayList<Student> studentArrayList = new ArrayList<>();
        FirebaseDataHelper helper = FirebaseDataHelper.getInstance();
        helper.getmRef().child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double amount = 0,amountRecei= 0;
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Student st = child.getValue(Student.class);
                    studentArrayList.add(st);
                    if(st.isFee())
                       amountRecei+=st.getAmount();
                    amount+=st.getAmount();
                }
                txtSumStudent.setText(studentArrayList.size()+"");
                txtSumAmountReceived.setText(amountRecei+"");
                txtSumAmount.setText(amount+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
