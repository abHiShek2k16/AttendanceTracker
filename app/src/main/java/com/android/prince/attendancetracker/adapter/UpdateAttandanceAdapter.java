package com.android.prince.attendancetracker.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.prince.attendancetracker.R;
import com.android.prince.attendancetracker.UpdateAttendance;

import java.util.ArrayList;

/**
 * Created by prince on 7/4/18.
 */

public class UpdateAttandanceAdapter extends RecyclerView.Adapter<UpdateAttandanceAdapter.UpdateAttendanceCustomViewHolder>{

    private ArrayList<String> rollNo;

    Context context ;

    public UpdateAttandanceAdapter(Context context,ArrayList<String> rollNo){
        this.rollNo = rollNo;
        this.context = context;
    }


    @Override
    public UpdateAttendanceCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_list,parent,false);
        return new UpdateAttendanceCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UpdateAttendanceCustomViewHolder holder, int position) {

        String rollStr = rollNo.get(position);
        holder.roll.setText(rollStr);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.imageView.getVisibility() == View.INVISIBLE){
                    holder.imageView.setVisibility(View.VISIBLE);
                }else {
                    holder.imageView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rollNo.size();
    }

    public class UpdateAttendanceCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView roll;
        private ImageView imageView;
        private LinearLayout layout;


        public UpdateAttendanceCustomViewHolder(View itemView) {
            super(itemView);
            roll = (TextView) itemView.findViewById(R.id.rollNoAtItemStudentList);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewAtStudentList);
            layout = (LinearLayout)itemView.findViewById(R.id.layoutAttudentList);
        }
    }
}
