package com.android.prince.attendancetracker.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.prince.attendancetracker.R;

import java.util.ArrayList;

/**
 * Created by prince on 7/4/18.
 */

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.AttendanceCustomViewHolder>{

    private ArrayList<String> paperName;
    private ArrayList<String> percentage;
    private ArrayList<Drawable> image;

    Context context;

    public StudentAttendanceAdapter(Context context, ArrayList<String> paperName, ArrayList<String> percentage,ArrayList<Drawable> image){
        this.paperName = paperName;
        this.percentage = percentage;
        this.image = image;

        this.context = context;
    }

    @Override
    public AttendanceCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_list,parent,false);
        return new AttendanceCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceCustomViewHolder holder, int position) {

        if(position<percentage.size()) {
            holder.paperName.setText(paperName.get(position));
            holder.percentage.setText("PERCENTAGE  :  " + percentage.get(position) + "%");
            holder.image.setImageDrawable(image.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return paperName.size();
    }

    public class AttendanceCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView paperName;
        private TextView percentage;
        private ImageView image;

        public AttendanceCustomViewHolder(View itemView) {

            super(itemView);

            paperName = (TextView) itemView.findViewById(R.id.subjectNameAtAttendanceList);
            percentage = (TextView) itemView.findViewById(R.id.percentageAtStudentList);
            image = (ImageView)itemView.findViewById(R.id.imageAtStudentList);
        }
    }
}
