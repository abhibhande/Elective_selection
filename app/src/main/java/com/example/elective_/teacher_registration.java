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
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.FirestoreGrpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class teacher_registration extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        EditText mobileno=(EditText) findViewById(R.id.entermobileno);
        EditText email=(EditText) findViewById(R.id.enteremail);
        EditText password=(EditText) findViewById(R.id.enterpassword);
        EditText confirmpassword=(EditText) findViewById(R.id.enterConfirmpassword);
        Button register=(Button) findViewById(R.id.registeraccount);

        //Password Validation
        //Contain 0-9 a-z A-Z Special Symbol 8-20 len

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";


        //Register New Admin
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobno=mobileno.getText().toString();
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                String cpass=confirmpassword.getText().toString();
                //checking For Empty Field
                if(!mobno.isEmpty() || !mail.isEmpty() || !pass.isEmpty() || !cpass.isEmpty())
                {
                    if(pass.matches(regex))
                    {
                        if (pass.equals(cpass)) {
                            check = true;
                            //Creating Admin Account
                            auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())  {
                                        check = false;

                                        //sending Verify Email
                                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                    Toast.makeText(getApplicationContext(), "Verify Your Email", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                        //Uploading Teacher Details
                                        firestore.collection("TEACHER").document(Check_Class.TeacherId+":"+Check_Class.CollageName).update(
                                                "ASSIGNED","YES",
                                                "ID",auth.getUid(),
                                                "Access","TEACHER",
                                                "Teacher Name",Check_Class.TeacharName,
                                                "Mobile No",mobno,
                                                "Email",mail
                                        );
//

                                        finish();
                                        //Going back to Login Acativity
                                        Intent intent=new Intent(new Intent(getApplicationContext(),teacherlogin.class));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (check) {
                                        Toast.makeText(getApplicationContext(), "Email is Already Used", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//                            auth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                                    if(task.isSuccessful())
//                                    {
//                                        Toast.makeText(getApplicationContext(),"User Already Exists",Toast.LENGTH_SHORT).show();
//                                    }
//                                    else {
//                                        Toast.makeText(getApplicationContext(),"User Not Exists",Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });

                        } else {
                            confirmpassword.setError("Password not matched");
                        }

                    }
                    else {
                        password.setError("8-20 in len 0-9 a-z A-Z Special Symbol");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}