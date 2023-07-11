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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Year;
import java.util.Map;

public class studentregistrationconfirm extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentregistrationconfirm);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        EditText enterstudentname=(EditText) findViewById(R.id.enterstudentname);
        EditText entermobileno=(EditText) findViewById(R.id.entermobileno);
        EditText enteremail=(EditText) findViewById(R.id.enteremail);
        EditText enterpassword=(EditText) findViewById(R.id.enterpassword);
        EditText enterConfirmpassword=(EditText) findViewById(R.id.enterConfirmpassword);

        Button registeraccount=(Button) findViewById(R.id.registeraccount);

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        registeraccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentname=enterstudentname.getText().toString();
                String mobileno=entermobileno.getText().toString();
                String mail=enteremail.getText().toString();
                String pass=enterpassword.getText().toString();
                String cpass=enterConfirmpassword.getText().toString();

                if(studentname.isEmpty())
                {
                    enterstudentname.setError("Enter Name");

                } else if (mobileno.isEmpty() || mobileno.length()!=10) {
                    entermobileno.setError("Enter 10 Digit no");

                }else if (mail.isEmpty()) {
                    enteremail.setError("Enter email ID");

                }else if (pass.isEmpty()) {
                    enterpassword.setError("Enter Pass");

                }else  if(!pass.matches(regex))
                {
                    enterpassword.setError("8-20 in len 0-9 a-z A-Z Special Symbol");
                }
                else if (cpass.isEmpty()) {
                    enterConfirmpassword.setError("Enter Confirm Pass");

                } else if (!pass.equals(cpass)) {
                    enterConfirmpassword.setError("Confirm Password Not Matched");

                }
                else{
                    auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                firestore.collection("STUDENT")
                                        .document(Check_Class.StudentId+":"+Check_Class.CollageName)
                                        .update("ID",auth.getUid(),
                                                "Name",studentname,
                                                "Mobile No",mobileno,
                                                "ASSIGNED","YES",
                                                "Year",Check_Class.Year,
                                                "Email",mail
                                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(studentregistrationconfirm.this, "Verify Email", Toast.LENGTH_SHORT).show();
                                                auth.getCurrentUser().sendEmailVerification();
                                                finish();
                                                startActivity(new Intent(getApplicationContext(),student_login.class));
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(studentregistrationconfirm.this, "Email Already Used", Toast.LENGTH_SHORT).show();
                            }



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(studentregistrationconfirm.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
}