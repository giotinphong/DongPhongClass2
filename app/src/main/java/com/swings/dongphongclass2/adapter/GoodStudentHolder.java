package com.swings.dongphongclass2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swings.dongphongclass2.R;

/**
 * Created by sonnguyen on 10/19/17.
 */

public class GoodStudentHolder extends RecyclerView.ViewHolder {
    ImageView imgAvatar;
    TextView txtName,txtCount;
    public GoodStudentHolder(View itemView) {
        super(itemView);
        imgAvatar =(ImageView) itemView.findViewById(R.id.item_card_good_student_img_avatar);
        txtCount = (TextView)itemView.findViewById(R.id.item_card_good_student_txt_count);
        txtName = (TextView)itemView.findViewById(R.id.item_card_good_student_txt_name);
    }
}
