package com.swings.dongphongclass2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.swings.dongphongclass2.adapter.ListScheduleAdapter;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;
import com.swings.dongphongclass2.data.StudentSchedule;

import java.util.ArrayList;
import java.util.Date;

public class StudentInformationActivity extends AppCompatActivity {
    private FirebaseDataHelper helper;
    private ArrayList<StudentSchedule> studentScheduleArrayList;
    private ListView listSchedule;
    private TextView txtName,txtBeginDay,txtCountClass,txtCountFee;
    private Button btnEdit;
    private ImageView imgAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);
        listSchedule = (ListView)findViewById(R.id.acti_student_infor_list_schedule);
        //khoi tao helper
        helper = FirebaseDataHelper.getInstance();
        //lay thong tin hoc sinh
        final String studentID = getIntent().getExtras().getString("studentID");

        //lay danh sach lich nghi/hoc
        //studentScheduleArrayList = helper.getListSchedules(studentID);
       //do data vao listview
        studentScheduleArrayList = new ArrayList<>();
        final ListScheduleAdapter adapter = new ListScheduleAdapter(StudentInformationActivity.this,R.layout.item_schedule,studentScheduleArrayList);
        listSchedule.setAdapter(adapter);

        helper.getmRef().child("schedules").child(studentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    StudentSchedule studentSchedule = child.getValue(StudentSchedule.class);
                    studentScheduleArrayList.add(studentSchedule);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //setup cac phan con lai
        btnEdit = (Button)findViewById(R.id.acti_studen_infor_btn_edit);
        txtName = (TextView)findViewById(R.id.acti_studen_infor_txt_name);
        txtBeginDay = (TextView)findViewById(R.id.acti_studen_infor_txt_beginday);
        txtCountClass = (TextView)findViewById(R.id.acti_studen_infor_txt_countclass);
        txtCountFee = (TextView)findViewById(R.id.acti_studen_infor_txt_countfee);
        imgAvatar = (ImageView)findViewById(R.id.acti_studen_infor_img_avatar);

        //setup data
         helper.getmRef().child("student").child(studentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);

                Glide.with(getApplicationContext())
                        .load(student.getAvatarLink())
                        .placeholder(R.drawable.student_icon)
                        .into(imgAvatar);
                txtName.setText(student.getName());
                Date beginDay = new Date (student.getBeginday());
                txtBeginDay.setText(txtBeginDay.getText().toString()+beginDay.getDay()+"/"+(beginDay.getMonth()+1)+"/"+(beginDay.getYear()+1900));
                txtCountClass.setText(txtCountClass.getText().toString()+""+student.getNumberOfClass());
                txtCountFee.setText(txtCountFee.getText().toString()+""+student.getNumofFee());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(StudentInformationActivity.this,StudentControllerActivity.class);
                in.putExtra("id",studentID);
                startActivity(in);

            }
        });
    }
}
