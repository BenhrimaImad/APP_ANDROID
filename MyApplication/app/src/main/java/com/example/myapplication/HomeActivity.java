package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences pref;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref=getSharedPreferences("user",MODE_PRIVATE);
        Log.w("INFO","id : "+pref.getInt("id",0));
        Log.w("INFO","role : " +pref.getString("",""));
        if(pref.getAll().containsKey("id")){
            String role=pref.getString("role","");

            Toast.makeText(HomeActivity.this, "role : "+role, Toast.LENGTH_SHORT).show();

            if(role.equals("ADMIN")){
                intent = new Intent(this,MainActivity.class);

                startActivity(intent);
            }else if(role.equals("ETUD")){
                intent = new Intent(this,MainActivity.class);

                startActivity(intent);
            }else if(role.equals("PROF")){
                intent = new Intent(this,MainActivity.class);

                startActivity(intent);
            }

        }else{
            intent = new Intent(this,LoginActivity.class);
            //intent.putExtra("user_name", "");
            startActivity(intent);
        }
        //setContentView(R.layout.activity_home);
        finish();
    }
}
