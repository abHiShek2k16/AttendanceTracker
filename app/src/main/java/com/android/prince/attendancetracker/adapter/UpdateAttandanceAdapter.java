package com.android.prince.attendancetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(UpdateAttendanceCustomViewHolder holder, int position) {

        String rollStr = rollNo.get(position);

        holder.roll.setText(rollStr);

    }

    @Override
    public int getItemCount() {
        return rollNo.size();
    }

    public class UpdateAttendanceCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView roll;


        public UpdateAttendanceCustomViewHolder(View itemView) {
            super(itemView);
            roll = (TextView) itemView.findViewById(R.id.rollNoAtItemStudentList);
        }
    }
}
