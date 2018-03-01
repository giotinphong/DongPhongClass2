package com.swings.dongphongclass2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.swings.dongphongclass2.StudentControllerActivity;
import com.swings.dongphongclass2.R;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;

import java.util.ArrayList;

/**
 * Created by sonnguyen on 8/15/17.
 */

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentHolder> {
    private ArrayList<Student> studentArrayList = new ArrayList<>();
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
                Intent in = new Intent(context, StudentControllerActivity.class);
                in.putExtra("id",student.getId());
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
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student.setNumberOfClass(student.getNumberOfClass()+1);
                if((student.getNumberOfClass()*1.0 / 8 > student.getNumofFee()) )
                    student.setFee(false);
                helper.updateStudent(student);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

}
