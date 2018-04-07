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

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateCustomViewHolder>{

    private ArrayList<String> date;
    private ArrayList<Drawable> image;


    Context context;

    public DateAdapter(Context context, ArrayList<String> date,ArrayList<Drawable> image){
        this.date = date;
        this.image = image;
        this.context = context;
    }


    @Override
    public DateCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_list,parent,false);
        return new DateCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DateCustomViewHolder holder, int position) {
        holder.date.setText(date.get(position));
        holder.image.setImageDrawable(image.get(position));
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class DateCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private ImageView image;

        public DateCustomViewHolder(View itemView) {

            super(itemView);

            date = (TextView) itemView.findViewById(R.id.dateAtDateList);
            image = (ImageView)itemView.findViewById(R.id.imageAtDateList);
        }
    }
}
