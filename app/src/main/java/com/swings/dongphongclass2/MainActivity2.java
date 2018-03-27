package com.swings.dongphongclass2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.swings.dongphongclass2.adapter.StudentRecyclerAdapter;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rvListStudent;
    private FirebaseDataHelper firebaseDataHelper;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private TextView txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, StudentControllerActivity.class));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Khai bao doi tuong cho Nav
        txtEmail = (TextView)navigationView.getHeaderView(0).findViewById(R.id.acti_nav_head_txt_email);






        mAuth = FirebaseAuth.getInstance();
        rvListStudent = (RecyclerView) findViewById(R.id.acti_main_rv_student_list);
        firebaseDataHelper = FirebaseDataHelper.getInstance();
        final ArrayList<Student> studentArrayList = new ArrayList<>();
        final StudentRecyclerAdapter adapter = new StudentRecyclerAdapter(studentArrayList, MainActivity2.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity2.this, 3);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,true);
        rvListStudent.setLayoutManager(gridLayoutManager);
        rvListStudent.setAdapter(adapter);
        //firebaseDataHelper.getAllStudentToAdaper(studentArrayList,adapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            firebaseDataHelper.setmRef(uid);
            txtEmail.setText(""+email);
        }
        firebaseDataHelper.getmRef().child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Student> studentArrayList1 = new ArrayList<Student>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Student st = child.getValue(Student.class);
                    st.setId(child.getKey());
                    //*******************

                    if (st.isStudent())
                        studentArrayList1.add(st);

                }

                final StudentRecyclerAdapter adapter = new StudentRecyclerAdapter(studentArrayList1, MainActivity2.this);
                rvListStudent.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_static) {
            startActivity(new Intent(MainActivity2.this,StaticActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_class_list) {

        } else if (id == R.id.nav_calendar) {

        } else if (id == R.id.nav_calendar) {

        } else if (id == R.id.nav_infor) {

        } else if (id == R.id.nav_signout) {

            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
            mAuth.signOut();
            startActivity(new Intent(MainActivity2.this,LoginAcitivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
}
