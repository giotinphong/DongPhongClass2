package com.swings.dongphongclass2.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.swings.dongphongclass2.R;

/**
 * Created by sonnguyen on 8/15/17.
 */

public class StudentHolder extends RecyclerView.ViewHolder {
     ImageView imgAvatar;
     TextView txtName,txtBeginDay,txtNumOfClasses;
     CheckBox cbFee;
    CardView cardMain;
    public StudentHolder(View itemView) {
        super(itemView);
        imgAvatar = (ImageView)itemView.findViewById(R.id.item_card_student_img_avatar);
        txtName = (TextView)itemView.findViewById(R.id.item_card_student_txt_student_name);
        txtBeginDay = (TextView)itemView.findViewById(R.id.item_card_student_txt_beginday);
        txtNumOfClasses = (TextView)itemView.findViewById(R.id.item_card_student_txt_number_of_class);
        cbFee = (CheckBox)itemView.findViewById(R.id.item_card_student_checkbox_fee);
        cardMain = (CardView)itemView.findViewById(R.id.item_card_student_cardmain);
    }

}
