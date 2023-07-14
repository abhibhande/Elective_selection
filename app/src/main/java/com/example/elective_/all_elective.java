package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.annotations.concurrent.Background;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.Map;

public class all_elective extends AppCompatActivity {
    FirebaseFirestore firestore;
    String Access="";
    String s="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_elective);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        SharedPreferences sp=getSharedPreferences("DETAILS",MODE_PRIVATE);
        //Creating Main Layout object
        LinearLayout main=(LinearLayout)findViewById(R.id.main);

        ImageButton back= (ImageButton) findViewById(R.id.back);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            Access=b.get("Access").toString();
        }

        firestore= FirebaseFirestore.getInstance();



        firestore.collection("ELECTIVE")
                .whereEqualTo("Collage Name",sp.getString("Collage Name",null))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot querySnapshot= task.getResult();

                        for(DocumentSnapshot documentSnapshot:querySnapshot)
                        {
                            if(documentSnapshot.getData()!=null)
                            {
                                Map br=documentSnapshot.getData();

                                //creating Relative layout object and layout params for width,height of View
                                RelativeLayout relativeLayout=new RelativeLayout(getApplicationContext());
                                RelativeLayout.LayoutParams relativeParams= new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.MATCH_PARENT, 650);
                                relativeLayout.setClickable(true);


                                relativeParams.setMargins(53,16,53,14);
                                relativeLayout.setBackground(getDrawable(R.drawable.frame_10));
                                main.addView(relativeLayout,relativeParams);

                                //creating layout params for width,height of TextView
                                RelativeLayout.LayoutParams relativeParams1= new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                relativeParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                relativeParams1.setMargins(0,16,0,0);


                                //Adding Text Teacher ID
                                TextView e_name=new TextView(getApplicationContext());
                                e_name.setText("NUMERICALS METHODS");
                                e_name.setTextAppearance(R.style.elective_text);
                                e_name.setTextSize(18);
                                e_name.setLayoutParams(relativeParams1);
                                relativeLayout.addView(e_name);

                                //Adding Teacher ID from Fire base
                                RelativeLayout.LayoutParams relativeParams2= new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                relativeParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);

                                relativeParams2.setMargins(0,96,0,0);
                                TextView code=new TextView(getApplicationContext());

//                                s1=br.get("Teacher ID")!=null ? br.get("Teacher ID").toString() : "";
//                                id_.setText(s1);
                                code.setText("Code : BAS4601");
                                code.setTextAppearance(R.style.elective_text);
                                code.setTextSize(18);
                                code.setLayoutParams(relativeParams2);
                                relativeLayout.addView(code);


                                //Adding Text Description
                                RelativeLayout.LayoutParams relativeParams3= new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                relativeParams3.setMargins(53,196,53,0);
                                relativeParams3.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                TextView name=new TextView(getApplicationContext());
                                name.setText("DESCRIPTION");
                                name.setTextAppearance(R.style.Text);
                                name.setTextSize(16);
                                name.setLayoutParams(relativeParams3);
                                relativeLayout.addView(name);

                                   //Adding Teacher Name from Fire base
                                RelativeLayout.LayoutParams relativeParams4= new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                relativeParams4.setMargins(15,240,15,10);
                                EditText desc=new EditText(getApplicationContext());
//                                s1=br.get("Teacher Name")!=null ? br.get("Teacher Name").toString() : "";
//                                desc.setText(s1);
//                                desc.setBackground(getDrawable(R.color.purple_200));
                                desc.setText("Concepts and techniques of Numerical Methods to solve systems of linear equations. Numerical techniques to solve integration, ordinary and partial differential equations, and their applications. Open-source software to perform numerical techniques.");
                                desc.setTextAppearance(R.style.Text);
                                desc.setInputType(InputType.TYPE_NULL);
                                desc.setSingleLine(false);
                                desc.setGravity(Gravity.CENTER);
//                                name_.setTextSize(20);
                                desc.setLayoutParams(relativeParams4);
                                relativeLayout.addView(desc);

                                //Setting layout parameters for ImageView
                                RelativeLayout.LayoutParams relativeParamsi1= new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
                                relativeParamsi1.setMargins(15,15,15,15);
                                relativeParamsi1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                                //Adding 3 dots at right corner
                                ImageButton i1=new ImageButton(getApplicationContext());
                                i1.setBackground(getDrawable(R.drawable.baseline_3_dots));
                                i1.setLayoutParams(relativeParamsi1);
                                relativeLayout.addView(i1);

                                relativeLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String s1="";
                                        Intent i=new Intent(getApplicationContext(),elective_profile.class);
                                        i.putExtra("Elective Name",e_name.getText().toString());
                                        s1=br.get("Code")!=null ? br.get("Code").toString() : "";
                                        i.putExtra("Code",s1);
                                        i.putExtra("Description",desc.getText().toString());
                                        i.putExtra("Description",desc.getText().toString());
                                        s1=br.get("Branch")!=null ? br.get("Branch").toString() : "";
                                        i.putExtra("Branch",s1);
                                        s1=br.get("Year")!=null ? br.get("Year").toString() : "";
                                        i.putExtra("Year",s1);
                                        s1=br.get("Sem")!=null ? br.get("Sem").toString() : "";
                                        i.putExtra("Elective No",s1);s1=br.get("Elective No")!=null ? br.get("Sem").toString() : "";
                                        i.putExtra("Elective No",s1);
                                        startActivity(i);

                                    }
                                });
                                i1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        PopupMenu popupMenu=new PopupMenu(getApplicationContext(),i1);

                                        popupMenu.getMenuInflater().inflate(R.menu.delete_menu,popupMenu.getMenu());
                                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                                            @Override
                                            public boolean onMenuItemClick(MenuItem menuItem) {
//                                                firestore.collection("ELECTIVE")
//                                                        .document(id_.getText().toString()+":"+ sp.getString("Collage Name",null))
//                                                        .delete()
//                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                if(task.isSuccessful())
//                                                                {
//                                                                    Toast.makeText(getApplicationContext(), id_.getText().toString()+" is Deleted", Toast.LENGTH_SHORT).show();
//
//                                                                }
//                                                                else
//                                                                {
//                                                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                                recreate();
//                                                            }
//                                                        }).addOnFailureListener(new OnFailureListener() {
//                                                            @Override
//                                                            public void onFailure(@NonNull Exception e) {
//                                                                Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        });
                                                return true;
                                            }
                                        });
                                        popupMenu.show();
                                    }
                                });




                            }else{

                                Toast.makeText(getApplicationContext(), "No Branch Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        //Back To previous activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                if(Access.equals("ADMIN"))
                {
                    startActivity(new Intent(getApplicationContext(),admin_home_page.class));
                }else if(Access.equals("TEACHER"))
                {
//                    startActivity(new Intent(getApplicationContext(),teacher_home_page.class));
                }
            }
        });

    }
}