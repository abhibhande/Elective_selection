package com.example.elective_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class all_branch extends AppCompatActivity {

    FirebaseFirestore firestore;
    String Access=null;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_branch);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        //Creating Main Layout object
        LinearLayout main=(LinearLayout)findViewById(R.id.main);

        ImageButton back= (ImageButton) findViewById(R.id.back);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            Access=b.get("Access").toString();
        }

        firestore=FirebaseFirestore.getInstance();
        SharedPreferences sp=getSharedPreferences("DETAILS",MODE_PRIVATE);

        firestore.collection("BRANCH")
                .document("BRANCH:"+sp.getString("Collage Name",null))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot.getData()!=null)
                        {
                            Map br=documentSnapshot.getData();
                            Set set=br.entrySet();
                            Iterator it= set.iterator();

                            while(it.hasNext())
                            {
                                Map.Entry entry=(Map.Entry)it.next();
//                                branch_list.add(entry.getValue().toString());

                                //creating Relative layout object and layout params for width,height of View
                                RelativeLayout relativeLayout=new RelativeLayout(getApplicationContext());
                                RelativeLayout.LayoutParams relativeParams= new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.MATCH_PARENT, 300);

                                //creating layout params for width,height of TextView
                                RelativeLayout.LayoutParams relativeParams1= new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                relativeParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                relativeParams1.addRule(RelativeLayout.CENTER_VERTICAL);


                                relativeParams.setMargins(53,16,53,14);
                                relativeLayout.setBackground(getDrawable(R.drawable.frame_7));
                                main.addView(relativeLayout,relativeParams);

                                TextView t1=new TextView(getApplicationContext());
                                t1.setText(entry.getValue().toString());
                                t1.setTextAppearance(R.style.boldText);
                                t1.setGravity(Gravity.CENTER);
                                t1.setLayoutParams(relativeParams1);
                                relativeLayout.addView(t1);
                            }

                        }else{

                            Toast.makeText(getApplicationContext(), "No Branch Added", Toast.LENGTH_SHORT).show();
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