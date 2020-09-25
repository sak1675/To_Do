package com.example.tasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.Viewholder> {

    Context context;
    ArrayList<items> item;
    public taskAdapter(Context c, ArrayList<items> list){
        context = c;
        item = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.items_to_do, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int i) {
        holder.task.setText(item.get(i).getTask());
        holder.desc.setText(item.get(i).getDesc());
        holder.due.setText(item.get(i).getDue());

        final String gettasks = item.get(i).getTask();
        final String getdesc = item.get(i).getDesc();
        final String getdue = item.get(i).getDue();
        final String gettaskName = item.get(i).getTaskName();


        holder.itemView.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(context,editTask.class);
                        intent.putExtra("task", gettasks);
                        intent.putExtra("desc", getdesc);
                        intent.putExtra("due", getdue);
                        intent.putExtra("taskName", gettaskName);
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

        class Viewholder extends RecyclerView.ViewHolder{
            TextView task,desc,due;
            public Viewholder( View itemView) {

                super(itemView);
                task = (TextView) itemView.findViewById(R.id.task);
                desc  = (TextView) itemView.findViewById(R.id.desc);
                due   = (TextView) itemView.findViewById(R.id.due);
            }
        }

}
