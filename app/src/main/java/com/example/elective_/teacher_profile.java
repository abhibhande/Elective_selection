package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
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

import org.w3c.dom.Text;

public class teacher_profile extends AppCompatActivity {

    FirebaseFirestore firestore;
    String Id=" ";
    String Name=" ";
    String Email=" ";
    String Mobile_no=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        firestore=FirebaseFirestore.getInstance();

        SharedPreferences sh=getSharedPreferences("DETAILS",MODE_PRIVATE);





        //reading data sent from previous activity
        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            Toast.makeText(this, b.get("ID").toString(), Toast.LENGTH_SHORT).show();
            Id=b.get("ID").toString();
            Name=b.get("Name").toString();
            Email=b.get("Email").toString();
            Mobile_no=b.get("Mobile No").toString();
        }

        //Accessing all the Views
        EditText id=(EditText)findViewById(R.id.id);
        EditText name=(EditText)findViewById(R.id.name);
        EditText email=(EditText)findViewById(R.id.email);
        EditText mobile_no=(EditText)findViewById(R.id.mobile_no);

        TextView collage_name=(TextView) findViewById(R.id.collage_name);
        collage_name.setText(sh.getString("Collage Name",null));

        //Displaying data stored in variable
        id.setText(Id);
        name.setText(Name);
        email.setText(Email);
        mobile_no.setText(Mobile_no);

        Button edit=(Button) findViewById(R.id.edit);
        ImageButton login=(ImageButton)findViewById(R.id.login);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit.getText().toString().equals("EDIT")) {
                    //Changing Edittext to Editable mode
                    name.setEnabled(true);
                    mobile_no.setEnabled(true);
                    edit.setText("SAVE");

                } else if (edit.getText().toString().equals("SAVE")) {
                    //Changing Edittext to non Editable mode
                    name.setEnabled(false);
                    mobile_no.setEnabled(false);
                    edit.setText("EDIT");

//                    Toast.makeText(getApplicationContext(), sp.getString("Collage Name",null), Toast.LENGTH_LONG).show();
                    firestore.collection("TEACHER")
                            .document(Check_Class.TeacherId+":"+sh.getString("Collage Name",null))
                            .update("Teacher Name",name.getText().toString(),
                                    "Mobile No",mobile_no.getText().toString())
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