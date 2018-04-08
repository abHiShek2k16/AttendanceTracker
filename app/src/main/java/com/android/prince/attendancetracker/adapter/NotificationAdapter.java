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
 * Created by prince on 8/4/18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NoticeCustomViewHolder>{

    private ArrayList<String> date;
    private ArrayList<String> notice;


    Context context;

    public NotificationAdapter(Context context, ArrayList<String> date,ArrayList<String> notice){
        this.date = date;
        this.notice = notice;
        this.context = context;
    }

    @Override
    public NoticeCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list,parent,false);
        return new NoticeCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeCustomViewHolder holder, int position) {
        holder.date.setText(date.get(position));
        holder.notice.setText(notice.get(position));
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class NoticeCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView notice;

        public NoticeCustomViewHolder(View itemView) {

            super(itemView);

            date = (TextView) itemView.findViewById(R.id.dateViewAtNotice);
            notice = (TextView)itemView.findViewById(R.id.showNotificationTextView);
        }
    }
}
