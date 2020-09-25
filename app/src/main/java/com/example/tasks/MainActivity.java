package com.example.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView title, subtitle;
    DatabaseReference databaseReference;
    RecyclerView todo_task;
    ArrayList<items> item;
    taskAdapter taskAdapter;
    ImageButton add_task_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        add_task_btn = findViewById(R.id.add_task);

        todo_task = findViewById(R.id.to_do_tasks);
        todo_task.setLayoutManager(new LinearLayoutManager(this));
        item = new ArrayList<items>();

        //Setting up for button
        add_task_btn.setOnClickListener(
                new ImageButton.OnClickListener(){
                    public void  onClick(View v){
                        Intent intent = new Intent(MainActivity.this,setTask.class);
                        startActivity(intent);
                    }
                }
        );

        //firebase data retrive
        databaseReference = FirebaseDatabase.getInstance().getReference().child("setTask");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap:dataSnapshot.getChildren()){
                    items i = dataSnap.getValue(items.class);
                    item.add(i);
                }
                taskAdapter = new taskAdapter(MainActivity.this,item);
                todo_task.setAdapter(taskAdapter);
                taskAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"No Data", Toast.LENGTH_SHORT).show();

            }
        });


    }
}