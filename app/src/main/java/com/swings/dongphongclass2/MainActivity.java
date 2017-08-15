package com.swings.dongphongclass2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.swings.dongphongclass2.adapter.StudentRecyclerAdapter;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvListStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddNewStudentActivity.class));
            }
        });

        rvListStudent = (RecyclerView)findViewById(R.id.acti_main_rv_student_list);
        FirebaseDataHelper firebaseDataHelper = FirebaseDataHelper.getInstance();
        final ArrayList<Student> studentArrayList = new ArrayList<>();
        final StudentRecyclerAdapter adapter = new StudentRecyclerAdapter(studentArrayList,MainActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,true);
        rvListStudent.setLayoutManager(linearLayoutManager);
        rvListStudent.setAdapter(adapter);
        //firebaseDataHelper.getAllStudentToAdaper(studentArrayList,adapter);
        firebaseDataHelper.getmRef().child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Student> studentArrayList1 = new ArrayList<Student>();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Student st = child.getValue(Student.class);
                    studentArrayList1.add(st);
                }
                final StudentRecyclerAdapter adapter = new StudentRecyclerAdapter(studentArrayList1,MainActivity.this);
                rvListStudent.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
