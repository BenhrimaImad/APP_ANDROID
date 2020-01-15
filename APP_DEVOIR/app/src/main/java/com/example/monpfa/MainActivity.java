package com.example.monpfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    MaterialSpinner filtre;
    EditText txt_email, txt_pwd;
    Button btn_cnx;
    RelativeLayout rellay1;
    String UrlLogin = "http://192.168.43.254/android/signin.php";
    ProgressBar loading;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rellay1 = findViewById(R.id.rellay1);
        handler.postDelayed(runnable, 2000);

        txt_email = findViewById(R.id.txt_email);
        txt_pwd = findViewById(R.id.txt_pwd);
        btn_cnx = findViewById(R.id.btn_cnx);
        loading = findViewById(R.id.loading);

        btn_cnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("DDDDDDDDDDDDDDDDD", "onClick");
                Login();
            }
        });
    }

    private void Login() {
        final String Email = txt_email.getText().toString().trim();
        final String Pwd = txt_pwd.getText().toString().trim();
        Log.w("EMMMMMMMMMMMMM", Email);
        Log.w("EMMMMMMMMMMMMM", Pwd);
        //final String T = filtre.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    Log.w("DDDDDDDDDDD",response);
                    //String success = jsonObject.getString("success");
                   // String message = jsonObject.getString("message");
                    //Log.w("ddddddddddddddd",success);
                    JSONArray etudiants = jsonObject.getJSONArray("etudiants");
                    Log.w("kkkkkkkkkkkk",""+etudiants);
                        for(int i = 0 ; i<etudiants.length();i++) {
                            JSONObject etudiant = etudiants.getJSONObject(i);
                            Log.w("kkkkkkkkkkkk",""+etudiant);
                            String type = etudiant.getString("Type");
                            Etudiant e = new Etudiant();
                            e.setType(type);
                            if(e.getType().equals("Administrateur")){
                                Intent homepage = new Intent(MainActivity.this, AdminActivity.class);
                                startActivity(homepage);
                            }
                            else if(e.getType().equals("Professeur")){
                                Intent homepage = new Intent(MainActivity.this, ProfActivity.class);
                                startActivity(homepage);
                            }
                            else{
                                Intent homepage = new Intent(MainActivity.this, EtudActivity.class);
                                startActivity(homepage);
                            }
                        }
                        /*JSONObject user = jsonObject.getJSONObject("profil");
                        String type=user.getString("Type");
                        Log.w("DDDDDDDDDDdd",type);*/
                        //String test=user.getString("profil");
                        //Log.w("DDDDDDDDDDDDDD" ,test);
                        // String type = user.getString("Type");
                        Log.w("FFFFFEEEEETTTTTTEEEEE", "conntected ");
                        Toast.makeText(MainActivity.this, "success Login. ", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    //.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error catch " + e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error volley" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", Email);
                params.put("pwd", Pwd);
               // params.put("type", T);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public class Etudiant {
        private String Nom;
        private String Prenom;
        private String Email;
        private String Password;
        private String Tel;
        private String Classe;
        private String Type;
        private String Image;

        public Etudiant(String nom, String prenom, String email, String password, String tel, String classe, String type,String image) {
            Nom = nom;
            Prenom = prenom;
            Email = email;
            Password = password;
            Tel = tel;
            Classe = classe;
            Type = type;
            Image=image;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            this.Image = image;
        }

        public Etudiant() {
        }

        public String getClasse() {
            return Classe;
        }

        public void setClasse(String classe) {
            Classe = classe;
        }

        public String getNom() {
            return Nom;
        }

        public void setNom(String nom) {
            Nom = nom;
        }

        public String getPrenom() {
            return Prenom;
        }

        public void setPrenom(String prenom) {
            Prenom = prenom;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }
    }


}
