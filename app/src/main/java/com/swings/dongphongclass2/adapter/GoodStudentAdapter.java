package com.swings.dongphongclass2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.swings.dongphongclass2.R;
import com.swings.dongphongclass2.data.FirebaseDataHelper;
import com.swings.dongphongclass2.data.Student;

import java.util.ArrayList;

/**
 * Created by sonnguyen on 10/19/17.
 */

public class GoodStudentAdapter extends RecyclerView.Adapter<GoodStudentHolder>
{
    private ArrayList<Student> studentArrayList = new ArrayList<>();
    private Context context;

    public GoodStudentAdapter(ArrayList<Student> studentArrayList, Context context) {
        this.studentArrayList = studentArrayList;
        this.context = context;
    }
    @Override
    public GoodStudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_good_student,parent,false);
        return new GoodStudentHolder(v);
    }

    @Override
    public void onBindViewHolder(GoodStudentHolder holder, int position) {
        final Student student = studentArrayList.get(position);
        Glide.with(context)
                .load(student.getAvatarLink())
                .placeholder(R.drawable.student_icon)
                .into(holder.imgAvatar);
        holder.txtName.setText(student.getName());
        holder.txtCount.setText(student.getNumberOfClass()+"");
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

}
