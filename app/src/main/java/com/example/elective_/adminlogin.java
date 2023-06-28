package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

public class adminlogin extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        //Initialize Firebaseauth and FireStore
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        //for Setting Progress Bar


        //For Getting Inputs
        EditText email=(EditText)findViewById(R.id.email);
        EditText password=(EditText)findViewById(R.id.password);

        Button login=(Button)findViewById(R.id.login);

        Button registerhere=(Button) findViewById(R.id.registerhere);

        Button forgetpassword=(Button)findViewById(R.id.forgetpassword);

        //Forget Password
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),adminforgetpassword.class));
            }
        });

        //Going on register page
        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),admin_registration.class);
                startActivity(i);
            }
        });

        //Admin Login
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
                                firestore.collection("ADMIN")
                                        .whereEqualTo("ID",auth.getUid())
                                        .whereEqualTo("Access","ADMIN")
                                        .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                        if (task2.isSuccessful()) {
                                                            //Checking Valid Access="ADMIN"
                                                            QuerySnapshot documentSnapshot = task2.getResult();
                                                            if (!documentSnapshot.isEmpty()) {
                                                                //Verifing Admin email is verified or not
                                                                if(auth.getCurrentUser().isEmailVerified()) {

                                                                    Toast.makeText(adminlogin.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else {
                                                                    email.setError("Please Verify Email");
                                                                }

                                                                for (QueryDocumentSnapshot document : task2.getResult()) {
                                                                    Toast.makeText(getApplicationContext(), "Access:" + document.getString("Access"), Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                            else {
                                                                Toast.makeText(adminlogin.this, "Email or Password not valid", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                        else{
                                                            Toast.makeText(adminlogin.this, "Email or Password not valid", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(adminlogin.this, "Error"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(adminlogin.this, "Email or Password not valid", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}