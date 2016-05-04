package com.mx.codehappy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mx.codehappy.R;
import com.mx.codehappy.activity.WebActivity;
import com.mx.codehappy.model.Results;
import com.mx.codehappy.utils.TimeDifferenceUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 16/2/19 02:57
 */
public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.PartViewHolder> {

    private Context context;
    private List<Results> read_list = new ArrayList<>();

    public List<Results> getResults() {
        return read_list;
    }

    public ReadAdapter(Context context, List<Results> read_list) {
        this.context = context;
        if (read_list != null) {
            this.read_list = read_list;
        }
    }

    @Override
    public PartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PartViewHolder holder = new PartViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_read, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(PartViewHolder holder, final int position) {
        holder.textView.setText(read_list.get(position).getDesc());
        String author = read_list.get(position).getWho();
        if (author != null) {
            holder.tv_author.setText(author);
            holder.tv_author.setTextColor(Color.parseColor("#87000000"));
        } else {
            holder.tv_author.setText("");
        }
        String time = read_list.get(position).getCreatedAt();
        if (time != null) {
            holder.tv_time.setText(TimeDifferenceUtils.getTimeDifference(time));
        } else {
            holder.tv_time.setText("");
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", read_list.get(position).getUrl());
                intent.putExtra("desc", read_list.get(position).getDesc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return read_list.size();
    }

    class PartViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView;
        TextView tv_author, tv_time;

        public PartViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = (TextView) itemView.findViewById(R.id.read_tv);
            tv_author = (TextView) itemView.findViewById(R.id.read_tv_author);
            tv_time = (TextView) itemView.findViewById(R.id.read_tv_time);
        }
    }
}
