package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class teacherregistrationfirst extends AppCompatActivity {
    Check_Class ch=new Check_Class();
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherregistrationfirst);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        firestore=FirebaseFirestore.getInstance();

        AutoCompleteTextView collagename=(AutoCompleteTextView) findViewById(R.id.Select_Category);
        EditText teacherid=(EditText)findViewById(R.id.enterteacherid);
        EditText teachername=(EditText)findViewById(R.id.enterteachername);



        //Adding Collage List
        List<String> l;
        l=new ArrayList<>();

        firestore.collection("ADMIN")
                .whereEqualTo("Access","ADMIN")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult())
                        {
                            l.add(documentSnapshot.getString("Collage Name"));
                        }
                    }
                });


//Showing List
        ArrayAdapter<String> CollageName=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,l);
        collagename.setThreshold(1);
        collagename.setAdapter(CollageName);


        Button next=(Button) findViewById(R.id.next1);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String collage_name=collagename.getText().toString();
                String id=teacherid.getText().toString();
                String name=teachername.getText().toString();
                if(collage_name.isEmpty())
                {
                    Toast.makeText(teacherregistrationfirst.this, "Please Select Collage", Toast.LENGTH_SHORT).show();;
                } else if (id.isEmpty()) {
                    teacherid.setError("Enter Teacher ID");
                } else if (name.isEmpty()) {
                    teachername.setError("Enter Teacher Name");
                }
                else {
                    Check_Class.CollageName=collage_name;
                    Check_Class.TeacherId=id;
                    Check_Class.TeacharName=name;

                    startActivity(new Intent(getApplicationContext(), teacher_registration.class));
                }
            }
        });
    }
}