package com.swings.dongphongclass2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.swings.dongphongclass2.StudentControllerActivity;
import com.swings.dongphongclass2.R;
import com.swings.dongphongclass2.StudentInformationActivity;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;
import com.swings.dongphongclass2.data.StudentSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sonnguyen on 8/15/17.
 */

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentHolder> {
    private ArrayList<Student> studentArrayList = new ArrayList<>();
    String note = "";
//    private ArrayList<StudentSchedule> schedulesArrayList = new ArrayList<>();
    private Context context;
    FirebaseDataHelper helper;
    public StudentRecyclerAdapter(ArrayList<Student> studentArrayList, Context context) {
        this.studentArrayList = studentArrayList;
        this.context = context;

    }

    @Override
    public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        helper = FirebaseDataHelper.getInstance();
        View v = LayoutInflater.from(context).inflate(R.layout.item_card_student,parent,false);
        return new StudentHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentHolder holder, int position) {
        final Student student = studentArrayList.get(position);
//        schedulesArrayList = helper.getListSchedules(student);
        holder.txtName.setText(student.getName());
//        holder.txtBeginDay.setText(helper.convertLongDateToString(student.getBeginday()));
        holder.txtNumOfClasses.setText(student.getNumberOfClass()+""+" Buổi");
//        holder.cbFee.setChecked(student.isFee());
//        if(holder.cbFee.isChecked()){
//            holder.cbFee.setEnabled(false);
//        }
        Glide.with(context)
                .load(student.getAvatarLink())
                .placeholder(R.drawable.student_icon)
                .into(holder.imgAvatar);
        holder.cardMain.setCardBackgroundColor(
                student.isFee()? holder.cardMain.getResources().getColor(R.color.fee) : holder.cardMain.getResources().getColor(R.color.notfee));
        holder.cardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, StudentInformationActivity.class);
                in.putExtra("studentID",student.getId());
                context.startActivity(in);
            }
        });
//        holder.cbFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                student.setFee(isChecked);
//
//                helper.updateStudent(student);
//            }
//        });

        //create dialog to input note
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_input_note);
        final EditText edNote = (EditText)dialog.findViewById(R.id.dialog_input_note_ed_note);
        final Button btnCancel = (Button)dialog.findViewById(R.id.dialog_input_note_btn_cancel);
        final Button btnSave = (Button)dialog.findViewById(R.id.dialog_input_note_btn_save);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        holder.btnPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        note = edNote.getText().toString()+"";
                        dialog.dismiss();
                        Date date = Calendar.getInstance().getTime();
                        StudentSchedule studentSchedule = new StudentSchedule(note,date.getTime(),true);

                        helper.addSchedule(studentSchedule,student);
                        student.setNumberOfClass(student.getNumberOfClass()+1);
                        if((student.getNumberOfClass()*1.0 / 8 > student.getNumofFee()) )
                            student.setFee(false);

                        helper.updateStudent(student);

                    }
                });

            }
        });
        holder.btnAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        note = edNote.getText().toString()+"";
                        dialog.dismiss();
                        Date date = Calendar.getInstance().getTime();
                        StudentSchedule studentSchedule = new StudentSchedule(note,date.getTime(),false);
                        helper.addSchedule(studentSchedule,student);
                        if((student.getNumberOfClass()*1.0 / 8 > student.getNumofFee()) )
                            student.setFee(false);
                        helper.updateStudent(student);
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

}
