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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class teacherlogin extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        EditText email=(EditText)findViewById(R.id.email);
        EditText password=(EditText)findViewById(R.id.password);
        Button registerhere=(Button) findViewById(R.id.registerhere);
        Button login=(Button) findViewById(R.id.login);
        Button forgetpassword=(Button)findViewById(R.id.forgetpassword);

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

                }
                else {
                    auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                firestore.collection("TEACHER")
                                        .whereEqualTo("ID",auth.getUid())
                                        .whereEqualTo("Access","TEACHER")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                //Getting User Email Details
                                                QuerySnapshot documentSnapshot = task.getResult();
                                                //Verifing Teacher email is verified or not
                                                if (!documentSnapshot.isEmpty()) {

                                                    if(auth.getCurrentUser().isEmailVerified()) {

                                                        Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                                                        List<DocumentSnapshot> list=documentSnapshot.getDocuments();
                                                        for(DocumentSnapshot documentSnapshot1:list)
                                                        {
                                                            Check_Class.Access=documentSnapshot1.getString("Access");
                                                            Check_Class.CollageName=documentSnapshot1.getString("Collage Name");
                                                            Check_Class.MobileNo=documentSnapshot1.getString("MobileNo");
                                                            Check_Class.TeacharName=documentSnapshot1.getString("Branch");
                                                            Check_Class.TeacherId=documentSnapshot1.getString("Student ID");
                                                        }
                                                    }
                                                    else {
                                                        email.setError("Please Verify Email");
                                                    }

                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Toast.makeText(getApplicationContext(), "Access:" + document.getString("Access"), Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), "Email or Password not valid", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                                        else{
                                                Toast.makeText(getApplicationContext(), "Email or Password not valid", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        });
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(teacherlogin.this, "Email or Password Not Present", Toast.LENGTH_SHORT).show();
                                }
                            });


                    //Deleting Collaction

//                    firestore.collection("TEACHER").document("kFuavUoRryFfw6swXujx").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(teacherlogin.this, "Successful", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(teacherlogin.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });

                }
            }
        });

        //Forget Password
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), com.example.elective_.forgetpassword.class);
                intent.putExtra("Access","TEACHER");

                startActivity(intent);
            }
        });


        //Sending on registration page
        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),teacherregistrationfirst.class);
                startActivity(i);
            }
        });
    }
}