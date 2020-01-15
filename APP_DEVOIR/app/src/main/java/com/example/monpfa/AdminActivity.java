package com.example.monpfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    MaterialSpinner txtProfile,txtClasse;
    EditText txtNom, txtPrenom, txtPassword, txtEmail, txtTel;
    Button btnSave;
    String UrlAdd = "http://192.168.43.254/android/addprofile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        txtNom = findViewById(R.id.txtNom);
        txtPrenom = findViewById(R.id.txtPrenom);
        txtClasse = findViewById(R.id.txtClasse);
        txtClasse.setItems("4IIR1", "4IIR2", "4IIR3", "MIAGE1",
                "MIAGE2", "MIAGE3","3IIR1", "3IIR2", "3IIR3","2IIR1"
                ,"2IIR2","2IIR3","IIR1","IIR2","IIR3");
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtTel = findViewById(R.id.txtTel);
        txtProfile = findViewById(R.id.txtProfile);
        txtProfile.setItems("Etudiant", "Administrateur", "Professeur");
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.w("wwwwwwwwwwwwwwwwwwwwwww", "OnClick");
                Add();
            }
        });
    }

    public void Add() {
        final String Nom = txtNom.getText().toString().trim();
        final String Prenom = txtPrenom.getText().toString().trim();
        final String Classe = txtClasse.getText().toString().trim();
        final String Email = txtEmail.getText().toString().trim();
        final String Password = txtPassword.getText().toString().trim();
        final String Tel = txtTel.getText().toString().trim();
        final String Profile = txtProfile.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlAdd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    Log.w("EMMMMMMMMMMMMM", Nom);
                    if (success.equals("1")) {

                        Log.w("FFFFFEEEEETTTTTTEEEEE", "conntected " + message);
                        Toast.makeText(AdminActivity.this, "Nouveau Profil Ajouté avec succès !", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AdminActivity.this, "Error au niveau du else  " + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                    Toast.makeText(AdminActivity.this, "Error catch " + e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminActivity.this, "Error volley" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nom", Nom);
                params.put("prenom", Prenom);
                params.put("classe", Classe);
                params.put("email", Email);
                params.put("password", Password);
                params.put("tel", Tel);
                params.put("profile", Profile);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void test1(MenuItem item) {
        Toast.makeText(AdminActivity.this, "KKKKKKKKK", Toast.LENGTH_SHORT).show();

    }

    public void redirectListEtudiant(MenuItem item) {
        Intent intent = new Intent(AdminActivity.this, EtudiantsActivity.class);
        startActivity(intent);
    }

    public void redirectListProfesseurs(MenuItem item) {
        Intent intent = new Intent(AdminActivity.this, ProfesseurActivity.class);
        startActivity(intent);
    }

    public void redirectListAdmin(MenuItem item) {
        Intent intent = new Intent(AdminActivity.this, AdministrateurActivity.class);
        startActivity(intent);
    }
}
