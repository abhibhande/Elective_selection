package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class all_teacher extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String Access="";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teacher);
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

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();


        firestore.collection("TEACHER")
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
                                                    RelativeLayout.LayoutParams.MATCH_PARENT, 450);
                                            relativeLayout.setClickable(true);


                                            relativeParams.setMargins(53,16,53,14);
                                            relativeLayout.setBackground(getDrawable(R.drawable.frame_7));
                                            main.addView(relativeLayout,relativeParams);

                                            //creating layout params for width,height of TextView
                                            RelativeLayout.LayoutParams relativeParams1= new RelativeLayout.LayoutParams(
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                            relativeParams1.setMargins(53,16,0,0);


                                            //Adding Text Teacher ID
                                            TextView id=new TextView(getApplicationContext());
                                            id.setText("Teacher ID :");
                                            id.setTextAppearance(R.style.create_an_a);
                                            id.setTextSize(20);
                                            id.setLayoutParams(relativeParams1);
                                            relativeLayout.addView(id);

                                            //Adding Teacher ID from Fire base
                                        RelativeLayout.LayoutParams relativeParams2= new RelativeLayout.LayoutParams(
                                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        relativeParams2.setMargins(390,16,0,0);
                                            TextView id_=new TextView(getApplicationContext());
                                            String s1;
                                        s1=br.get("Teacher ID")!=null ? br.get("Teacher ID").toString() : "";
                                        id_.setText(s1);
                                            id_.setTextAppearance(R.style.create_an_a);
                                            id_.setTextSize(20);
                                            id_.setLayoutParams(relativeParams2);
                                            relativeLayout.addView(id_);

                                        //Adding Text Teacher Name
                                        RelativeLayout.LayoutParams relativeParams3= new RelativeLayout.LayoutParams(
                                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        relativeParams3.setMargins(53,116,0,0);
                                        TextView name=new TextView(getApplicationContext());
                                        name.setText("Name         :");
                                        name.setTextAppearance(R.style.create_an_a);
                                        name.setTextSize(20);
                                        name.setLayoutParams(relativeParams3);
                                        relativeLayout.addView(name);

                                        //Adding Teacher Name from Fire base
                                        RelativeLayout.LayoutParams relativeParams4= new RelativeLayout.LayoutParams(
                                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        relativeParams4.setMargins(390,116,10,0);
                                        TextView name_=new TextView(getApplicationContext());
                                        s1=br.get("Teacher Name")!=null ? br.get("Teacher Name").toString() : "";
                                        name_.setText(s1);
                                        name_.setSingleLine();
                                        name_.setTextAppearance(R.style.create_an_a);
                                        name_.setTextSize(20);
                                        name_.setLayoutParams(relativeParams4);
                                        relativeLayout.addView(name_);


                                        //Adding Text Teacher Email
                                        RelativeLayout.LayoutParams relativeParams5= new RelativeLayout.LayoutParams(
                                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        relativeParams5.setMargins(53,216,0,0);
                                        TextView email=new TextView(getApplicationContext());
                                        email.setText("Email         :");
                                        email.setTextAppearance(R.style.create_an_a);
                                        email.setTextSize(20);
                                        email.setLayoutParams(relativeParams5);
                                        relativeLayout.addView(email);

                                        //Adding Teacher Email from Fire base
                                        RelativeLayout.LayoutParams relativeParams6= new RelativeLayout.LayoutParams(
                                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        relativeParams6.setMargins(390,216,10,0);
                                        TextView email_=new TextView(getApplicationContext());
                                        s1=br.get("Email")!=null ? br.get("Email").toString() : "";
                                        email_.setText(s1);
                                        email_.setTextAppearance(R.style.create_an_a);
                                        email_.setSingleLine();
                                        email_.setTextSize(20);
                                        email_.setLayoutParams(relativeParams6);
                                        relativeLayout.addView(email_);

                                        //Adding Text Teacher Mobile No
                                        RelativeLayout.LayoutParams relativeParams7= new RelativeLayout.LayoutParams(
                                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        relativeParams7.setMargins(53,316,0,0);
                                        TextView no=new TextView(getApplicationContext());
                                        no.setText("Mobile No :");
                                        no.setTextAppearance(R.style.create_an_a);
                                        no.setTextSize(20);
                                        no.setLayoutParams(relativeParams7);
                                        relativeLayout.addView(no);

                                        //Adding Teacher Email from Fire base
                                        RelativeLayout.LayoutParams relativeParams8= new RelativeLayout.LayoutParams(
                                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        relativeParams8.setMargins(390,316,0,0);
                                        TextView no_=new TextView(getApplicationContext());
                                        s1=br.get("Mobile No")!=null ? br.get("Mobile No").toString() : "";
                                        no_.setText(s1);
                                        no_.setTextAppearance(R.style.create_an_a);
                                        no_.setTextSize(20);
                                        no_.setLayoutParams(relativeParams8);
                                        relativeLayout.addView(no_);


                                            //Setting layout parameters for ImageView
                                            RelativeLayout.LayoutParams relativeParamsi1= new RelativeLayout.LayoutParams(
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

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
                                                Intent i=new Intent(getApplicationContext(),teacher_profile.class);
                                                i.putExtra("ID",id_.getText().toString());
                                                Check_Class.TeacherId=id_.getText().toString();
                                                i.putExtra("Name",name_.getText().toString());
                                                i.putExtra("Email",email_.getText().toString());
                                                i.putExtra("Mobile No",no_.getText().toString());
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
                                                            firestore.collection("TEACHER")
                                                                    .document(id_.getText().toString()+":"+ sp.getString("Collage Name",null))
                                                                    .delete()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful())
                                                                            {
                                                                                Toast.makeText(getApplicationContext(), id_.getText().toString()+" is Deleted", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                            else
                                                                            {
                                                                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                            recreate();
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
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