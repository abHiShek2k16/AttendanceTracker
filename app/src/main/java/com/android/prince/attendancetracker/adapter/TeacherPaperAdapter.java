package com.android.prince.attendancetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.prince.attendancetracker.R;

import java.util.ArrayList;

/**
 * Created by prince on 6/4/18.
 */

public class TeacherPaperAdapter extends RecyclerView.Adapter<TeacherPaperAdapter.AuxionCustomViewHolder> {

    private ArrayList<String> paperName;
    private ArrayList<String> sem;

    Context context ;

    public TeacherPaperAdapter(Context context,ArrayList<String> paperName,ArrayList<String> sem){
            this.paperName = paperName;
            this.sem = sem;
            this.context = context;
        }


    @Override
    public AuxionCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paper_list,parent,false);
        return new AuxionCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuxionCustomViewHolder holder, int position) {

        String nameStr = paperName.get(position);
        String semStr = sem.get(position);

        holder.name.setText(nameStr);
        holder.sem.setText(semStr);
    }

    @Override
    public int getItemCount() {
                return paperName.size();
        }

    public class AuxionCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView sem;

        public AuxionCustomViewHolder(View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.paperTextViewAtItemPaperList);
            sem = (TextView) itemView.findViewById(R.id.semTextViewAtItemPaperList);
        }
    }
}
