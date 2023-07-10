package com.example.elective_;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class add_branch extends AppCompatActivity {

    FirebaseFirestore firestore;

    String Access=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);


        firestore=FirebaseFirestore.getInstance();
        EditText Branch=(EditText) findViewById(R.id.branch);
        ImageButton back=(ImageButton) findViewById(R.id.back);
        Button add=(Button)findViewById(R.id.add_branch);

        SharedPreferences sp=getSharedPreferences("DETAILS",MODE_PRIVATE);

        //Checking which user is calling this page
        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            Access=b.get("Access").toString();
            
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String branch=Branch.getText().toString();
                if(branch.isEmpty())
                {
                    Branch.setError("Enter Branch");
                }
                else {
                    firestore.collection("BRANCH")
                            .document("BRANCH:"+sp.getString("Collage Name",null))
                            .update(branch,branch).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(add_branch.this, "Branch Added", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(add_branch.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

        //Back To previous activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if(Access.equals("ADMIN"))
                {
                    startActivity(new Intent(getApplicationContext(),admin_home_page.class));
                }else if(Access.equals("TEACHER"))
                {
//                    startActivity(new Intent(getApplicationContext(),teacher_home_page.class));
                }
            }
        });


    }
}