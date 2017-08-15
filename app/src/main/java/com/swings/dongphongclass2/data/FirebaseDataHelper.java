package com.swings.dongphongclass2.data;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swings.dongphongclass2.adapter.StudentRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sonnguyen on 8/15/17.
 */

public class FirebaseDataHelper {
    private static String STUDENT =  "student";
    DatabaseReference mRef;
    private static final FirebaseDataHelper ourInstance = new FirebaseDataHelper();

    public static FirebaseDataHelper getInstance() {
        return ourInstance;
    }

    private FirebaseDataHelper() {
        mRef = FirebaseDatabase.getInstance().getReference();
    }


    //get All student
    public ArrayList<Student> getAllStudent(){
        final ArrayList<Student> studentArrayList = new ArrayList<>();
        mRef.child(STUDENT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Student student = child.getValue(Student.class);
                    studentArrayList.add(student);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return studentArrayList;
    }
    //get all student to Adapter
    public void getAllStudentToAdaper(final ArrayList<Student> studentArrayList, final StudentRecyclerAdapter adapter){
        mRef.child(STUDENT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                student.setId(dataSnapshot.getKey());
                studentArrayList.add(student);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                student.setId(dataSnapshot.getKey());
                for(int i = 0; i < studentArrayList.size(); i++){
                    if(studentArrayList.get(i).getId()==student.getId()){
                        studentArrayList.remove(i);
                        studentArrayList.add(i,student);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //get student by ID
    public Student getStudentByID(String id){
        final Student[] student = {new Student()};
        mRef.child(STUDENT).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student[0] = dataSnapshot.getValue(Student.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return student[0];
    }

    //add student
    public  void addStudent(Student st){
        DatabaseReference keyHandle = mRef.child(STUDENT).push();
        keyHandle.setValue(st);
    }

    //edit student
    public void updateStudent(Student st){
        mRef.child(STUDENT).child(st.getId()).setValue(st);
    }

    //delete student
    public void deleteStudent(Student st){
        mRef.child(STUDENT).child(st.getId()).removeValue();
    }

    public DatabaseReference getmRef() {
        return mRef;
    }
    public String convertLongDateToString(long time){
        Date date = new Date();
        date.setTime(time);
        return ""+date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900);
    }
}
