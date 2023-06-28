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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class adminforgetpassword extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    Boolean check=true;

    String regex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminforgetpassword);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        EditText email=(EditText) findViewById(R.id.enteremail);
        Button resetpassword=(Button) findViewById(R.id.resetpassword);

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String mail=email.getText().toString();
                //checking For Empty Field
                if(!mail.isEmpty())
                {
                    auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(adminforgetpassword.this, "Reset Password Link Sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(),adminlogin.class));
                            }
                        }
                    });
                }
                else {
                    email.setError("Enter Email");
                }
            }
        });


    }
}