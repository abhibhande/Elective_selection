package com.example.elective_;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class student_registration extends AppCompatActivity {

    Check_Class ch=new Check_Class();
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        firestore=FirebaseFirestore.getInstance();

        AutoCompleteTextView collagename=(AutoCompleteTextView) findViewById(R.id.Select_collage);
        AutoCompleteTextView branch=(AutoCompleteTextView) findViewById(R.id.selectbranch);
        AutoCompleteTextView year=(AutoCompleteTextView) findViewById(R.id.enteryear);
        Button next=(Button) findViewById(R.id.next);
        EditText student_id=(EditText)findViewById(R.id.enterstudentid);

        //Adding Collage List
        List<String> l,branch_list,year_list;
        branch_list=new ArrayList<>();
        year_list=new ArrayList<>();
        l=new ArrayList<>();

        //Adding Year in Year list
        year_list.add("I Year");
        year_list.add("II Year");
        year_list.add("III Year");
        year_list.add("IV Year");

        ArrayAdapter<String> Year_List=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,year_list);
        year.setThreshold(1);
        year.setAdapter(Year_List);




        //Adding collage list from Firebase firestore
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




        collagename.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                firestore.collection("BRANCH")
                        .document("BRANCH:"+collagename.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                branch_list.clear();
                                if(task.isSuccessful())
                                {
                                    //Adding Collage list from Firebase of perticular collage
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    //CHecking if admin has added branch or not
                                    if(documentSnapshot.getData()!=null)
                                    {
                                        Map br=documentSnapshot.getData();
                                        Set set=br.entrySet();
                                        Iterator it= set.iterator();

                                        while(it.hasNext())
                                        {
                                            Map.Entry entry=(Map.Entry)it.next();
                                            branch_list.add(entry.getValue().toString());
                                        }
                                    }
                                    else{
                                        branch.setError("No Branch Added");
                                        Toast.makeText(student_registration.this, "No Branch Added", Toast.LENGTH_SHORT).show();
                                    }

                                }


                            }
                        });

                ArrayAdapter<String> branchlist=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,branch_list);
                branch.setThreshold(1);
                branch.setAdapter(branchlist);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String collage_name=collagename.getText().toString();
                String branch_name=branch.getText().toString();
                String year_=year.getText().toString();
                String Student_id=student_id.getText().toString();

                //Validating Inputs
                if(collage_name.isEmpty())
                {
                    collagename.setError("Select Collage");

                } else if (branch_name.isEmpty()) {
                    branch.setError("Select Branch");

                } else if (year_.isEmpty()) {
                    year.setError("Select Year");

                } else if (Student_id.isEmpty()) {
                    student_id.setError("Enter ID");

                }
                else{
                    //Checking Existance of ID
                    firestore.collection("STUDENT")
                            .document(Student_id+":"+collage_name)

                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        DocumentSnapshot result= task.getResult();

                                        if(result.exists() && result.getString("Collage Name").equals(collage_name))
                                        {
                                            if(result.getString("ASSIGNED").equals("NOT"))
                                            {
                                                Check_Class.CollageName=collage_name;
                                                Check_Class.Branch=branch_name;
                                                Check_Class.Year=year_;
                                                Check_Class.StudentId=Student_id;


                                                Intent i=new Intent(getApplicationContext(),studentregistrationconfirm.class);
                                                startActivity(i);
                                            }
                                            else {
                                                student_id.setError("Already Registered");
                                            }
                                        }
                                        else {
                                            student_id.setError("No ID Present");
                                        }

                                    }
                                    else {
                                        Toast.makeText(student_registration.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(student_registration.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });
    }
}