package com.swings.dongphongclass2;

import android.app.DatePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StudentControllerActivity extends AppCompatActivity {
    private EditText edName,edBithday,edBeginDay,edNumOfClasses,edPhoneNum,edAmount,edNumOfFee,edAvatarLink;
    private CheckBox cbFee;
    private Button btnPlus,btnMinus,btnSubmit;
    private Calendar cal;
    private Student newStudent;
    private SimpleDateFormat dft;
    private FirebaseDataHelper helper;
    private boolean isEdit;
    private Button btnGetOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_student);
        edName = (EditText)findViewById(R.id.acti_add_new_student_ed_name);
        edBithday = (EditText)findViewById(R.id.acti_add_new_student_ed_bithday);
        edBeginDay = (EditText)findViewById(R.id.acti_add_new_student_ed_beginday);
        edNumOfClasses = (EditText)findViewById(R.id.acti_add_new_student_ed_number_of_classes);
        edPhoneNum = (EditText)findViewById(R.id.acti_add_new_student_ed_phonenum);
        edAmount = (EditText)findViewById(R.id.acti_add_new_student_ed_amount);
        edNumOfFee = (EditText)findViewById(R.id.acti_add_new_student_ed_numoffe);
        cbFee = (CheckBox)findViewById(R.id.acti_add_new_student_cb_fee);
        btnPlus = (Button)findViewById(R.id.acti_add_new_student_btn_plus);
        btnMinus = (Button)findViewById(R.id.acti_add_new_student_numOfAmountFeed_btn_plus);
        btnSubmit = (Button)findViewById(R.id.acti_add_new_student_btn_submit);
        btnGetOut = (Button)findViewById(R.id.acti_add_new_student_btn_out);
        edAvatarLink = (EditText)findViewById(R.id.acti_add_new_student_ed_avatarLink);
        helper = FirebaseDataHelper.getInstance();

        try{
            String id = getIntent().getExtras().getString("id");
            helper.getmRef().child("student").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newStudent  = dataSnapshot.getValue(Student.class);
                    newStudent.setId(dataSnapshot.getKey());
                    edName.setText(newStudent.getName());
                    edBithday.setText(helper.convertLongDateToString(newStudent.getBithday()));
                    edBeginDay.setText(helper.convertLongDateToString(newStudent.getBeginday()));
                    edNumOfClasses.setText(newStudent.getNumberOfClass() + "");
                    edPhoneNum.setText(newStudent.getPhonenum());
                    edAmount.setText(newStudent.getAmount()+"");
                    edNumOfFee.setText(newStudent.getNumofFee()+"");
                    cbFee.setChecked(newStudent.isFee());

                    isEdit = true;
                    btnGetOut.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        catch (Exception e){
            newStudent = new Student();
            isEdit = false;
            btnGetOut.setVisibility(View.INVISIBLE);

        }


        cal= Calendar.getInstance();

        //Định dạng kiểu ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());

        //set begin day and bithday click -> date picker dialog
        edBeginDay.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(StudentControllerActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear+1, dayOfMonth);
                            edBeginDay.setText(dft.format(newDate.getTime()));

                            //set time to student

                            newStudent.setBeginday(newDate.getTimeInMillis());
                        }
                    },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
                }

        });
        edBithday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(StudentControllerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear+1, dayOfMonth);
                        edBithday.setText(dft.format(newDate.getTime()));

                        //set time to student

                        newStudent.setBithday(newDate.getTimeInMillis());
                    }
                },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        if(!edAvatarLink.getText().toString().isEmpty()){
            newStudent.setAvatarLink(edAvatarLink.getText().toString());
        }


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                try{
                     num = Integer.parseInt(edNumOfClasses.getText().toString());
                }
                catch (Exception e){
                    num = 0;
                }
                finally {
                    if(num<=99){
                        num+=1;
                        edNumOfClasses.setText(num+"");
                    }
                }
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                try{
                    num = Integer.parseInt(edNumOfClasses.getText().toString());
                }
                catch (Exception e){
                    num = 0;
                }
                finally {
                    if(num>0){
                        num-=1;
                        edNumOfClasses.setText(num+"");
                    }
                }

            }
        });


        cbFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                newStudent.setFee(isChecked);
            }
        });

        //submit to Firebase
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String name = edName.getText().toString();
                    String phone  = edPhoneNum.getText().toString();
                    int numOfClasses = Integer.parseInt(edNumOfClasses.getText().toString());
                    //set
                    newStudent.setName(name);
                    newStudent.setPhonenum(phone);
                    newStudent.setNumberOfClass(numOfClasses);
                    int numoffee = Integer.parseInt(edNumOfFee.getText().toString());
                    newStudent.setNumofFee(numoffee);
                    double amount = Double.parseDouble(edAmount.getText().toString());
                    if(amount!=0&&amount<1000)
                        amount = amount*1000;
                    newStudent.setAmount(amount);
                    if(isEdit){
                        helper.updateStudent(newStudent);
                    }
                    else
                    helper.addStudent(newStudent);
                    finish();
                }
                catch (Exception e){
                    AlertDialog alertDialog = new AlertDialog.Builder(StudentControllerActivity.this)
                            .setMessage("Nhập dữ liệu thiếu/sai")
                            .show();
                }
            }
        });
        btnGetOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newStudent.setStudent(false);
                helper.updateStudent(newStudent);
                finish();
            }
        });

}


}
