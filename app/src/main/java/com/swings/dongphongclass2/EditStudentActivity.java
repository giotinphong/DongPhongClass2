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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditStudentActivity extends AppCompatActivity {
    private EditText edName, edBithday, edBeginDay, edNumOfClasses, edPhoneNum;
    private CheckBox cbFee;
    private Button btnPlus, btnMinus, btnGalery, btnSubmit;
    private ImageButton btnCapture;
    private Calendar cal;
    private Student newStudent;
    private SimpleDateFormat dft;
    private FirebaseDataHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        edName = (EditText) findViewById(R.id.acti_add_new_student_ed_name);
        edBithday = (EditText) findViewById(R.id.acti_add_new_student_ed_bithday);
        edBeginDay = (EditText) findViewById(R.id.acti_add_new_student_ed_beginday);
        edNumOfClasses = (EditText) findViewById(R.id.acti_add_new_student_ed_number_of_classes);
        edPhoneNum = (EditText) findViewById(R.id.acti_add_new_student_ed_phonenum);
        cbFee = (CheckBox) findViewById(R.id.acti_add_new_student_cb_fee);
        btnPlus = (Button) findViewById(R.id.acti_add_new_student_btn_plus);
        btnMinus = (Button) findViewById(R.id.acti_add_new_student_btn_minus);
        btnGalery = (Button) findViewById(R.id.acti_add_new_student_btn_galery);
        btnCapture = (ImageButton) findViewById(R.id.acti_add_new_student_ibtn_capture);
        btnSubmit = (Button) findViewById(R.id.acti_add_new_student_btn_submit);

        newStudent = new Student();
        helper = FirebaseDataHelper.getInstance();
        String id = getIntent().getExtras().getString("id");
        newStudent.setId(id);
        helper.getmRef().child("student").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student st = dataSnapshot.getValue(Student.class);
                st.setId(dataSnapshot.getKey());
                newStudent = st;
                edName.setText(st.getName());
                edBithday.setText(helper.convertLongDateToString(st.getBithday()));
                edBeginDay.setText(helper.convertLongDateToString(st.getBeginday()));
                edNumOfClasses.setText(st.getNumberOfClass() + "");
                edPhoneNum.setText(st.getPhonenum());
                cbFee.setChecked(st.isFee());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        cal= Calendar.getInstance();

        //Định dạng kiểu ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());

        //set begin day and bithday click -> date picker dialog
        edBeginDay.setOnClickListener(new View.OnClickListener() {
            //            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditStudentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditStudentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        edBithday.setText(dft.format(newDate.getTime()));

                        //set time to student

                        newStudent.setBithday(newDate.getTimeInMillis());
                    }
                },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });



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
                    helper.updateStudent(newStudent);
                    finish();
                }
                catch (Exception e){
                    AlertDialog alertDialog = new AlertDialog.Builder(EditStudentActivity.this)
                            .setMessage("Nhập dữ liệu thiếu/sai")
                            .show();
                }
            }
        });


    }
}
