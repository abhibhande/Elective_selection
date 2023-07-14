package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class add_teacher extends AppCompatActivity {

    FirebaseFirestore firestore;
    String Access="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        firestore=FirebaseFirestore.getInstance();


        EditText teacher_id=(EditText) findViewById(R.id.teacher_id);
        Button add_teacher=(Button) findViewById(R.id.add_teacher);
        ImageButton back=(ImageButton)findViewById(R.id.back);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            Access=b.get("Access").toString();

        }

        add_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID=teacher_id.getText().toString();

                if(ID.isEmpty())
                {
                    add_teacher.setError("Required");
                    Toast.makeText(getApplicationContext(), "ID Required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Map t=new ArrayMap();
                    t.put("Teacher ID",ID);
                    t.put("Teacher Name"," ");
                    t.put("Email"," ");
                    t.put("Mobile No"," ");
                    t.put("Collage Name",getSharedPreferences("DETAILS",MODE_PRIVATE).getString("Collage Name",""));
                    t.put("ASSIGNED","NOT");
                    t.put("Access","TEACHER");
                    firestore.collection("TEACHER")
                            .document(ID+":"+ getSharedPreferences("DETAILS",MODE_PRIVATE).getString("Collage Name",""))
                            .set(t, SetOptions.merge())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "Teacher Id Added", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Teacher Id Not Added", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                teacher_id.setText("");
            }
        });
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