package com.seymour.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seymour.todolist.DB.DBAdapter;
import com.seymour.todolist.Model.Task;

import java.util.ArrayList;

/**
 * Created by seymour on 2016/05/27.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    Context c;
    ArrayList<Task> tasks;

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        TextView titleTextView,contentTextView,dateTextView,idTextView;

        public RecyclerHolder(View itemView) {
            super(itemView);

            this.titleTextView= (TextView) itemView.findViewById(R.id.row_title);
            this.contentTextView= (TextView) itemView.findViewById(R.id.row_content);
            this.dateTextView= (TextView) itemView.findViewById(R.id.row_date);
            this.idTextView= (TextView) itemView.findViewById(R.id.row_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditActivity.class);
                    String title=titleTextView.getText().toString();
                    String content=contentTextView.getText().toString();
                    String date=dateTextView.getText().toString();
                    String id = idTextView.getText().toString();

                    intent.putExtra("extra_type","rewrite");
                    intent.putExtra("extra_id",id);
                    intent.putExtra("extra_title",title);
                    intent.putExtra("extra_content",content);
                    intent.putExtra("extra_date",date);

                    DBAdapter db=new DBAdapter(c);
                    db.openDB();
                    db.delete(id);
                    db.closeDB();

                    v.getContext().startActivity(intent);
                    ((Activity)v.getContext()).finish();
                }
            });
        }
    }





    public RecyclerAdapter(Context c, ArrayList<Task> tasks) {
        this.c = c;
        this.tasks = tasks;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        RecyclerHolder holder=new RecyclerHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.titleTextView.setText(tasks.get(position).getTitle());
        holder.contentTextView.setText(tasks.get(position).getContent());
        holder.dateTextView.setText(tasks.get(position).getDate());
        holder.idTextView.setText(tasks.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void deleteTask(int pos) {
        //GET ID
        Task t=tasks.get(pos);
        String id=t.getId();

        //DELETE FROM DB
        DBAdapter db=new DBAdapter(c);
        db.openDB();
        if(db.delete(id)) {
            tasks.remove(pos);
        }else{
            Toast.makeText(c,"Unable To Delete",Toast.LENGTH_SHORT).show();
        }
        db.closeDB();
        this.notifyItemRemoved(pos);
    }

}