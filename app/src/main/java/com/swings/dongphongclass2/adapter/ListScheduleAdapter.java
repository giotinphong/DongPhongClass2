package com.swings.dongphongclass2.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.swings.dongphongclass2.R;
import com.swings.dongphongclass2.data.StudentSchedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sonnguyen on 3/2/18.
 */

public class ListScheduleAdapter extends ArrayAdapter<StudentSchedule> {
    ArrayList<StudentSchedule> studentScheduleArrayList = new ArrayList<>();
    Context context;
    private TextView txtDate,txtStatus,txtNote;
    public ListScheduleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<StudentSchedule> objects) {
        super(context, resource, objects);
        this.studentScheduleArrayList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        StudentSchedule studentSchedule = studentScheduleArrayList.get(position);
        txtDate = (TextView)convertView.findViewById(R.id.item_schedule_txt_date);
        txtNote = (TextView)convertView.findViewById(R.id.item_schedule_txt_note);
        txtStatus = (TextView)convertView.findViewById(R.id.item_schedule_txt_status);
        Date date = new Date(studentSchedule.getDate());
        txtDate.setText(txtDate.getText().toString()+date.getDay()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900));
        txtNote.setText(txtNote.getText().toString()+studentSchedule.getNote());
        txtStatus.setText(txtStatus.getText().toString()+(studentSchedule.isPresent()?"Đi học" : "Vắng"));
        return convertView;

    }

    @Override
    public int getCount() {
        if(studentScheduleArrayList==null)
            return 0;
        return studentScheduleArrayList.size();
    }
}
