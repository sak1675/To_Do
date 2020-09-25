package com.example.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editTask extends AppCompatActivity {
    EditText title_txt, desc_txt, dues_txt;
    Button ed_confirm_btn , ed_cancel_btn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        title_txt   = findViewById(R.id.ed_title_txt);
        desc_txt    = findViewById(R.id.ed_desc_txt);
        dues_txt    = findViewById(R.id.ed_dues_txt);
        ed_confirm_btn = findViewById(R.id.ed_confirm_btn);
        ed_cancel_btn  = findViewById(R.id.ed_cancel_btn);

        //value from main
        title_txt.setText(getIntent().getStringExtra("task"));
        desc_txt.setText(getIntent().getStringExtra("desc"));
        dues_txt.setText(getIntent().getStringExtra("due"));
        String taskName = getIntent().getStringExtra("taskName");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("setTask").child("Tasks" + taskName);


        //btn
        ed_confirm_btn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("task").setValue(title_txt.getText().toString());
                                dataSnapshot.getRef().child("desc").setValue(desc_txt.getText().toString());
                                dataSnapshot.getRef().child("due").setValue(dues_txt.getText().toString());
                                dataSnapshot.getRef().child("taskName").setValue(taskName);

                                Intent intent = new Intent(editTask.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Intent intent = new Intent(editTask.this, MainActivity.class);
                                startActivity(intent);

                            }
                        });

                    }
                }
        );

        ed_cancel_btn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v){
                        databaseReference.removeValue().addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(editTask.this, MainActivity.class);
                                            startActivity(intent);
                                    }
                                }
                        );
                    }

                }
        );


    }
}