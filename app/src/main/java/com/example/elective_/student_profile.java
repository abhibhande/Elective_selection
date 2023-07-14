package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class student_profile extends AppCompatActivity {

    FirebaseFirestore firestore;

    String id=" ";
    String name=" ";
    String branch_name=" ";
    String year=" ";
    String sem=" ";
    String email=" ";
    String mobile_no=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        firestore= FirebaseFirestore.getInstance();

        SharedPreferences sh=getSharedPreferences("DETAILS",MODE_PRIVATE);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            Toast.makeText(this, b.get("ID").toString(), Toast.LENGTH_SHORT).show();
            id=b.get("ID").toString();
            name=b.get("Name").toString();
            branch_name=b.get("Email").toString();
            year=b.get("Year").toString();
            sem=b.get("Sem").toString();
            email=b.get("Email").toString();
            mobile_no=b.get("Mobile No").toString();

        }

        //Accessing all the Views
        EditText id_=(EditText)findViewById(R.id.id);
        EditText name_=(EditText)findViewById(R.id.name);
        EditText branch_name_=(EditText)findViewById(R.id.branch_name);
        EditText year_=(EditText)findViewById(R.id.year);
        EditText sem_=(EditText)findViewById(R.id.sem);
        EditText email_=(EditText)findViewById(R.id.email);
        EditText mobile_no_=(EditText)findViewById(R.id.mobile_no);

        id_.setText(id);
        name_.setText(name);
        branch_name_.setText(branch_name);
        year_.setText(year);
        sem_.setText(sem);
        email_.setText(email);
        mobile_no_.setText(mobile_no);



        TextView collage_name=(TextView) findViewById(R.id.collage_name);
        collage_name.setText(sh.getString("Collage Name",null));

        Button edit=(Button) findViewById(R.id.edit);
        ImageButton login=(ImageButton)findViewById(R.id.login);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.getText().toString().equals("EDIT"))
                {
                    edit.setText("SAVE");
//                        code_.setEnabled(true);
                    name_.setEnabled(true);
                    branch_name_.setEnabled(true);
                    year_.setEnabled(true);
                    sem_.setEnabled(true);
                    //email_.setEnabled(true);
                    mobile_no_.setEnabled(true);

                }
                else if(edit.getText().toString().equals("SAVE"))
                {
                    edit.setText("EDIT");
//                        code_.setEnabled(false);
                    name_.setEnabled(false);
                    branch_name_.setEnabled(false);
                    year_.setEnabled(false);
                    sem_.setEnabled(false);
                    //email_.setEnabled(true);
                    mobile_no_.setEnabled(false);
                    edit.setText("EDIT");


//                    Toast.makeText(getApplicationContext(), sp.getString("Collage Name",null), Toast.LENGTH_LONG).show();
                    firestore.collection("STUDENT")
                            .document(Check_Class.StudentId+":"+sh.getString("Collage Name",null))
                            .update("Student Name",name_.getText().toString(),
                                    "Branch Name",branch_name_.getText().toString(),
                                    "Year",year_.getText().toString(),
                                    "Sem",sem_.getText().toString(),
                                    "Mobile No",mobile_no_.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });




                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(getApplicationContext(),login);

                popupMenu.getMenuInflater().inflate(R.menu.admin_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        SharedPreferences.Editor editor=sh.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                        startActivity(new Intent(getApplicationContext(),adminlogin.class));
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
}