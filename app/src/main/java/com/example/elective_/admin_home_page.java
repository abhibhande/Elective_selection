package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class admin_home_page extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        SharedPreferences sh=getSharedPreferences("DETAILS",MODE_PRIVATE);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        TextView collagename=(TextView) findViewById(R.id.collage_name);
        collagename.setText(sh.getString("Collage Name",null));
        ImageButton login=(ImageButton)findViewById(R.id.login);

        RelativeLayout branch=(RelativeLayout) findViewById(R.id.branch);
        RelativeLayout teacher=(RelativeLayout) findViewById(R.id.teacher);
        RelativeLayout elective=(RelativeLayout) findViewById(R.id.elective);
        RelativeLayout student=(RelativeLayout) findViewById(R.id.student);

        ImageButton add_branch=(ImageButton) findViewById(R.id.add_branch);
        ImageButton add_teacher=(ImageButton) findViewById(R.id.add_teacher);
        ImageButton add_elective=(ImageButton) findViewById(R.id.add_elective);
        ImageButton add_student=(ImageButton) findViewById(R.id.add_student);

        TextView branch_count=(TextView)findViewById(R.id.branch_count);
        TextView teacher_count=(TextView)findViewById(R.id.teacher_count);
        TextView elective_count=(TextView)findViewById(R.id.elective_count);
        TextView student_count=(TextView)findViewById(R.id.student_count);

        //For Clicking on branch and sending on Branch_page for showing all branches
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if (!branch_count.getText().toString().equals(0))
                {
                    Intent i=new Intent(getApplicationContext(),all_branch.class);
                    i.putExtra("Access","ADMIN");
                    startActivity(i);
                }
            }
        });



        //adding new branches
        add_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Intent i=new Intent(getApplicationContext(),add_branch.class);
                i.putExtra("Access","ADMIN");
                startActivity(i);
            }
        });

        //For Clicking on elective and sending on elective_page for showing all elective subjects
        elective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),all_elective.class);

                startActivity(i);
                Toast.makeText(admin_home_page.this, "Elective", Toast.LENGTH_SHORT).show();
            }
        });

        //adding new elective
        add_elective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(admin_home_page.this, "add_Elective", Toast.LENGTH_SHORT).show();
            }
        });

        //For Clicking on teacher and sending on teacher_page for showing all teacher
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i=new Intent(getApplicationContext(),all_teacher.class);
                i.putExtra("Access","ADMIN");
                startActivity(i);
            }
        });

        //adding new teacher
        add_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i=new Intent(getApplicationContext(), com.example.elective_.add_teacher.class);
                i.putExtra("Access","ADMIN");
                startActivity(i);
            }
        });

        //For Clicking on student and sending on student_page for showing all students
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(admin_home_page.this, "Student", Toast.LENGTH_SHORT).show();
            }
        });

        //adding new students
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(admin_home_page.this, "add_student", Toast.LENGTH_SHORT).show();
            }
        });


        //Calculating values of Branch,Teacher,Elective,Students

        //Counting Branch and setting it to the branch_count

        firestore.collection("BRANCH")
                .document("BRANCH:"+sh.getString("Collage Name",null))
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map b=documentSnapshot.getData();
                        if(b!=null)
                        {
                            branch_count.setText(String.valueOf(b.size()));
                        }
                        else
                        {
                            branch_count.setText(String.valueOf(0));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_home_page.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //counting teacher and setting it to the teacher_count
            firestore.collection("TEACHER")
                    .whereEqualTo("Collage Name",sh.getString("Collage Name",null).toString())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                QuerySnapshot querySnapshot=task.getResult();
                                teacher_count.setText(String.valueOf(querySnapshot.size()));
                            }
                        }
                    });

            //counting elective and setting it to the elective_count

            firestore.collection("ELECTIVE")
                    .whereEqualTo("Collage Name",sh.getString("Collage Name",null))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                QuerySnapshot querySnapshot= task.getResult();
                                elective_count.setText(String.valueOf(querySnapshot.size()));
                            }
                            else
                            {
                                elective_count.setText(String.valueOf(0));
                            }
                        }
                    });

            //counting Students and setting it to the student_count
        firestore.collection("STUDENT")
                .whereEqualTo("Collage Name",sh.getString("Collage Name",null))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            QuerySnapshot querySnapshot= task.getResult();
                            student_count.setText(String.valueOf(querySnapshot.size()));
                        }
                        else
                        {
                            student_count.setText(String.valueOf(0));
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