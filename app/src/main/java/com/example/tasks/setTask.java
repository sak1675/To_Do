package com.example.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class setTask extends AppCompatActivity {
    EditText task_txt, desc_txt, dues_txt;
    Button confirm_btn , cancel_btn;
    DatabaseReference databaseReference;
    Integer task_no = new Random().nextInt();
    String taskName = Integer.toString(task_no);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_task);
        task_txt   = findViewById(R.id.task_txt);
        desc_txt    = findViewById(R.id.desc_txt);
        dues_txt    = findViewById(R.id.due_txt);
        confirm_btn = findViewById(R.id.confirm_btn);
        cancel_btn  = findViewById(R.id.cancel_btn);



            //Setting up for Confirm button
            confirm_btn.setOnClickListener(
                    new Button.OnClickListener() {
                        public void onClick(View v) {

                            if (task_txt.getText().toString().equals("") || desc_txt.getText().toString().equals("") || dues_txt.getText().toString().equals("")) {
                                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();

                            } else {
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("setTask").child("Tasks" + task_no);
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange( DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().child("task").setValue(task_txt.getText().toString());
                                        dataSnapshot.getRef().child("desc").setValue(desc_txt.getText().toString());
                                        dataSnapshot.getRef().child("due").setValue(dues_txt.getText().toString());
                                        dataSnapshot.getRef().child("taskName").setValue(taskName);

                                        Intent intent = new Intent(setTask.this, MainActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Intent intent = new Intent(setTask.this, MainActivity.class);
                                        startActivity(intent);

                                    }
                                });
                            }
                        }
                    }
            );
        //Setting up for cancel button
        cancel_btn.setOnClickListener(
                new Button.OnClickListener(){
                    public void  onClick(View v){
                        Intent intent = new Intent(setTask.this, MainActivity.class);
                        startActivity(intent);

                    }
                }
        );

    }
}