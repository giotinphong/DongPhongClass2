package com.swings.dongphongclass2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;

import java.util.ArrayList;

public class StaticActivity extends AppCompatActivity {
    private TextView txtSumStudent,txtSumAmount,txtSumAmountReceived,txtSumNotFeeStudent,txtSumNotFeeAmount;
    private FirebaseDataHelper helper;
    private RecyclerView rvListNotFee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        txtSumStudent = (TextView)findViewById(R.id.acti_static_txt_sumOfStudent);
        txtSumAmount = (TextView)findViewById(R.id.acti_static_txt_sumOfAmount);
        txtSumAmountReceived = (TextView)findViewById(R.id.acti_static_txt_sumOfAmountReceived);
        txtSumNotFeeAmount = (TextView)findViewById(R.id.acti_static_txt_sumOfAmountNotFeeStudent);
        txtSumNotFeeStudent = (TextView)findViewById(R.id.acti_static_txt_sumOfNotFeeStudent);
        rvListNotFee = (RecyclerView)findViewById(R.id.acti_static_listOfNotFee);
        final ArrayList<Student> studentArrayList = new ArrayList<>();
         helper = FirebaseDataHelper.getInstance();
        helper.getmRef().child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double amount = 0,amountRecei= 0;
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Student st = child.getValue(Student.class);
                    if(st.isStudent())
                    studentArrayList.add(st);
                    if(st.isFee()&&st.isStudent()) {
                        amountRecei += st.getAmount();
                        amount += st.getAmount();
                    }
                    }
                txtSumStudent.setText(studentArrayList.size()+"");
                txtSumAmountReceived.setText(amountRecei+"");
                txtSumAmount.setText(amount+"");
                ArrayList<Student> studentNotFeeArraylist = getStudentNotFeeThisMonth(studentArrayList);
                txtSumNotFeeStudent.setText(txtSumNotFeeStudent.getText()+""+studentNotFeeArraylist.size());
                txtSumNotFeeAmount.setText(txtSumNotFeeAmount.getText()+""+SumAmount(studentNotFeeArraylist));
//                //NotFeeAdapter adapter = new NotFeeAdapter(studentNotFeeArraylist,StaticActivity.this);
//                rvListNotFee.setAdapter(adapter);
//                LinearLayoutManager ln = new LinearLayoutManager(StaticActivity.this,LinearLayoutManager.HORIZONTAL,false);
//                rvListNotFee.setLayoutManager(ln);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public ArrayList<Student> getStudentFeedThisMonth(ArrayList<Student> allStudent){
        ArrayList<Student> result = new ArrayList<>();
        for(Student st : allStudent){
            if(st.isFee()&&st.isStudent())
                result.add(st);
        }
        return result;
    }
    public ArrayList<Student> getStudentNotFeeThisMonth(ArrayList<Student> allStudent){
        ArrayList<Student> result = new ArrayList<>();
        for(Student st : allStudent){
            if(!st.isFee()&&st.isStudent())
                result.add(st);
        }
        return result;
    }
    public double SumAmount(ArrayList<Student> allStudent){
        double sum = 0;
        for(Student st : allStudent)
            if(st.isStudent())
            sum +=st.getAmount();
        return sum;
    }

}
