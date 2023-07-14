package com.example.elective_;

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

import com.google.firebase.firestore.FirebaseFirestore;

public class elective_profile extends AppCompatActivity {

    FirebaseFirestore firestore;

    String code=" ";
    String name=" ";
    String branch_name=" ";
    String year=" ";
    String sem=" ";
    String description=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elective_profile);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        firestore= FirebaseFirestore.getInstance();

        SharedPreferences sh=getSharedPreferences("DETAILS",MODE_PRIVATE);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            Toast.makeText(this, b.get("ID").toString(), Toast.LENGTH_SHORT).show();
            code=b.get("ID").toString();
            name=b.get("Name").toString();
            branch_name=b.get("Email").toString();
            year=b.get("Mobile No").toString();
            sem=b.get("Mobile No").toString();
            description=b.get("Mobile No").toString();
        }

        //Accessing all the Views
        EditText code_=(EditText)findViewById(R.id.code);
        EditText name_=(EditText)findViewById(R.id.name);
        EditText branch_name_=(EditText)findViewById(R.id.branch_name);
        EditText year_=(EditText)findViewById(R.id.year);
        EditText sem_=(EditText)findViewById(R.id.sem);
        EditText description_=(EditText)findViewById(R.id.description);

        code_.setText(code);
        name_.setText(name);
        branch_name_.setText(branch_name);
        year_.setText(year);
        sem_.setText(sem);
        description_.setText(description);



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
                        description_.setEnabled(true);
                    }
                    else if(edit.getText().toString().equals("SAVE"))
                    {
                        edit.setText("EDIT");
//                        code_.setEnabled(false);
                        name_.setEnabled(false);
                        branch_name_.setEnabled(false);
                        year_.setEnabled(false);
                        sem_.setEnabled(false);
                        description_.setEnabled(false);
                        edit.setText("EDIT");

//                        firestore.collection("ELECTIVE")
//                                .whereEqualTo("Code",code)
//                                .whereEqualTo("Branch Name",branch_name)


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