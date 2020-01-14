package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;


import com.example.myapplication.model.User;
import com.example.myapplication.remote.APIUtils;
import com.example.myapplication.remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    UserService userService;
    private List<User> listePersonnes =new ArrayList<User>();
    private MonAdaptateur adapteur;
    private GridView laGrille;

    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref=getSharedPreferences("user",MODE_PRIVATE);
        Log.w("INFO","id : "+pref.getInt("id",0));
        Log.w("INFO","role : " +pref.getString("",""));
        userService= APIUtils.getUserService();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        laGrille = (GridView) findViewById(R.id.grille);

        //PersonneDAO unePersonneDao = new PersonneDAO(this);
        Call<List<User>> call=userService.getUsers();
        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    //listePersonnes = unePersonneDao.selectionnerToutesLesPersonnes();
                    listePersonnes = response.body();
                    adapteur = new MonAdaptateur(MainActivity.this, listePersonnes);

                    laGrille.setAdapter(adapteur);
                    Toast.makeText(MainActivity.this, "Size : " + listePersonnes.size(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Login Error!", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton boutonAjout = (FloatingActionButton) findViewById(R.id.bouton_ajout);
        FloatingActionButton boutonLogout =(FloatingActionButton) findViewById(R.id.bouton_logout);
        boutonAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Lance l'activité d'ajout de personne
                Intent intentionAjout = new Intent(MainActivity.this, AjoutPersonneActivity.class);
                startActivity(intentionAjout);
            }
        });
        boutonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=pref.edit();
                editor.clear();
                editor.commit();
                // Lance l'activité d'ajout de personne
                Intent intent= new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}