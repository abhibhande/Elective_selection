package com.example.elective_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    //For Current user
//    FirebaseUser user=auth.getCurrentUser();


    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Button gettingstarted=(Button)findViewById(R.id.getting_started);
        Button choice=(Button)findViewById(R.id.choice);




//        Button logout=(Button) findViewById(R.id.logout);

        firestore=FirebaseFirestore.getInstance();
//        auth=FirebaseAuth.getInstance();

        Map<String,String> users=new HashMap<>();

        users.put("fname","abhi");
        users.put("lname","bh");

//        firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this, "Failuer", Toast.LENGTH_SHORT).show();
//            }
//        });


        //setting name and email after login

//        if(user != null)
//        {
//            name.setText(user.getDisplayName());
//            email.setText(user.getEmail());
//        }


        //logout

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                auth.signOut();
//                startActivity(new Intent(MainActivity.this,loginactivity.class));
//        finish();
//            }
//        });


        //Updating user details



        gettingstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.setBackgroundResource(R.drawable.rectangle_2_);

                if(choice.getText().equals("Student"))
                {
                    Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(), student_login.class);
                    startActivity(i);
                } else if (choice.getText().equals("Admin")) {
                    Intent i=new Intent(getApplicationContext(),adminlogin.class);
                    startActivity(i);

                } else if (choice.getText().equals("Teacher")) {
                    {
                        Intent i=new Intent(getApplicationContext(), teacherlogin.class);
                        startActivity(i);
                    }


                }


            }
        });



        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundResource(R.drawable.rectangle_4_);
                PopupMenu popupMenu=new PopupMenu(getApplicationContext(),choice);

                popupMenu.getMenuInflater().inflate(R.menu.as_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        choice.setText(menuItem.getTitle());
                        choice.setBackgroundResource(R.drawable.rectangle_4);
                       return true;
                    }
                });
                popupMenu.show();
            }

        });


    }

    //User Exist or not

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if(auth.getCurrentUser() != null)
//        {
////            startActivity();
//            finish();
//        }
//    }




    //User is login or not

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if(user == null)
//        {
//            startActivity(new Intent(this,login.class));
//            finish();
//        }
//    }


//    public void createUser()
//    {
//        auth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            Toast.makeText(MainActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(MainActivity.this,"Error :"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

//    public void signIn()
//    {
//        auth.signInWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
//                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//                               // startActivity(intent);
//                              update();
//                        }else {
//                            Toast.makeText(MainActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }


    //public void update()
//    {
//        UserProfileChangeRequest changeRequest=new UserProfileChangeRequest.Builder()
//                .setDisplayName(name).build();
//
//        auth.getCurrentUser().updateProfile(changeRequest);
//
//        openLogin();
//    }


//    public void openLogin()
//    {
//        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//        finish();
//    }



}