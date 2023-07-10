package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class student_login extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        //Initialize Firebaseauth and FireStore
        auth= FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();

        //For Getting Inputs
        EditText email=(EditText)findViewById(R.id.email);
        EditText password=(EditText)findViewById(R.id.password);
        Button login=(Button)findViewById(R.id.login);
        Button registerhere=(Button) findViewById(R.id.registerhere);
        Button forgetpassword=(Button) findViewById(R.id.forgetpassword);

        //Sedning on new registration page
        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),student_registration.class);
                startActivity(i);
            }
        });

        //Forget Password
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), com.example.elective_.forgetpassword.class);
                intent.putExtra("Access","STUDENT");

                startActivity(intent);
            }
        });






        //Student Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail=email.getText().toString();
                String pass=password.getText().toString();



                if(mail.isEmpty())
                {
                    email.setError("Enter Email");
                } else if (pass.isEmpty()) {

                    password.setError("Enter Password");

                }else if(pass.length()<8 || pass.length()>20)
                {
                    password.setError("Length 8 to 20");
                }
                else {
                    //Firebase Authentication
                    auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                //Checking User Details In FireStore whose id is matched with entered email address
                                firestore.collection("STUDENT")
                                        .whereEqualTo("ID",auth.getUid())
                                        .whereEqualTo("Access","STUDENT")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                if (task2.isSuccessful()) {
                                                    //Getting User Email Details
                                                    QuerySnapshot documentSnapshot = task2.getResult();
                                                    //Verifing Admin email is verified or not
                                                    if (!documentSnapshot.isEmpty()) {

                                                        if(auth.getCurrentUser().isEmailVerified()) {
                                                            Toast.makeText(student_login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                                            //Reading Collage name of Admin
                                                            List<DocumentSnapshot> list=documentSnapshot.getDocuments();
                                                            for(DocumentSnapshot documentSnapshot1:list)
                                                            {
                                                                Check_Class.Access=documentSnapshot1.getString("Access");
                                                                Check_Class.CollageName=documentSnapshot1.getString("Collage Name");
                                                                Check_Class.Branch=documentSnapshot1.getString("Branch");
                                                                Check_Class.MobileNo=documentSnapshot1.getString("MobileNo");
                                                                Check_Class.StudentName=documentSnapshot1.getString("Branch");
                                                                Check_Class.StudentId=documentSnapshot1.getString("Student ID");
                                                                Check_Class.Year=documentSnapshot1.getString("Year");

                                                                Toast.makeText(student_login.this, documentSnapshot1.getString("Collage Name"), Toast.LENGTH_SHORT).show();
                                                            }

                                                            startActivity(new Intent(getApplicationContext(),admin_home_page.class));
                                                        }
                                                        else {
                                                            email.setError("Please Verify Email");
                                                        }


                                                    }
                                                    else {
                                                        Toast.makeText(student_login.this, "Email or Password not valid", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                else{
                                                    Toast.makeText(student_login.this, "Email or Password not valid", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(student_login.this, "Error"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(student_login.this, "Email or Password not valid", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
}