package com.swings.dongphongclass2.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private static String GROUP =  "group";

    DatabaseReference mRef;

    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String Uid = user.getUid();
    private static final FirebaseDataHelper ourInstance = new FirebaseDataHelper();

    public static FirebaseDataHelper getInstance() {

        return ourInstance;
    }

    private FirebaseDataHelper() {
        mRef = FirebaseDatabase.getInstance().getReference().child(Uid);
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUid() {
        return Uid;
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
                student[0].setId(dataSnapshot.getKey());
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

    //count
    public int countStudent(){
        final int[] dem = {0};
        mRef.child(STUDENT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren())
                    dem[0]++;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return dem[0];
    }
    //count amount
    public double sumOfAmount(){
        final double[] amount = {0};
        mRef.child(STUDENT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Student st = child.getValue(Student.class);
                    amount[0] += st.getAmount();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return amount[0];
    }
    //count amount received
    public double sumOfAmountReceived(){
        final double[] amount = {0};
        mRef.child(STUDENT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Student st = child.getValue(Student.class);
                    if(st.isFee())
                    amount[0] += st.getAmount();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return amount[0];
    }
    /************************* GROUP *************************/
    //add group
    public String addNewGroup(Group group){
        String key = mRef.child(GROUP).push().getKey();
        mRef.child(GROUP).child(key).setValue(group.getGroupName());
        for(String studentid : group.getGroupMem()){
            String keystudent = mRef.child(GROUP).child(key).push().getKey();
            mRef.child(GROUP).child(key).child(keystudent).setValue(studentid);
        }
        return key;
    }
    //add mem to group
    public String addMemToGroup(String stID, Group gr){
        String keyst = mRef.child(GROUP).child(gr.getId()).push().getKey();
        mRef.child(GROUP).child(gr.getId()).child(keyst).setValue(stID);
        return keyst;
    }
    //edit mem of group
    public void editMemOfGroup(String grID, String stID){}
    public ArrayList<Group> getAllGroup(){
        final ArrayList<Group> result = new ArrayList<>();
        mRef.child(GROUP).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Group gchild = child.getValue(Group.class);
                    ArrayList<String> studentIds = new ArrayList<String>();
                    for(DataSnapshot studentchild : child.getChildren()){
                        String studentid = studentchild.getValue(String.class);
                        studentIds.add(studentid);
                    }
                    result.add(gchild);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return result;
    }
}
