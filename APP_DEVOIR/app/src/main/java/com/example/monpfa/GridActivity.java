package com.example.monpfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {
    ImageButton btnCall;
    TextView nom,prenom,email,tel,classe;
    ImageView image;
    String Num="";
    String urlList = "http://192.168.43.254/android/listEtudiants.php";
    List<Etudiant> stds;
    String t ="";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        btnCall = findViewById(R.id.btnCall);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        image = findViewById(R.id.image);
        email = findViewById(R.id.email);
        classe = findViewById(R.id.classe);
        tel = findViewById(R.id.tel);
        stds = new ArrayList<>();

        intent = getIntent();
        nom.setText(intent.getStringExtra("nom"));
        test();
        prenom.setText(intent.getStringExtra("prenom"));
        email.setText(intent.getStringExtra("email"));
        tel.setText(intent.getStringExtra("tel"));
        classe.setText(intent.getStringExtra("classe"));
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEtud();
            }
        });
    }

    private void callEtud() {
        Intent iCall = new Intent(Intent.ACTION_DIAL);
        iCall.setData(Uri.parse("tel:"+getIntent().getStringExtra("tel")));
        Log.w("ffffffffffffffffff",Num.toString());
        startActivity(iCall);
    }

    public void test(){
        Num="";
        stds.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray etudiants = jsonObject.getJSONArray("etudiants");
                    Etudiant e = new Etudiant();
                    for(int i = 0 ; i<etudiants.length();i++) {
                        JSONObject etudiant = etudiants.getJSONObject(i);

                        String nom = etudiant.getString("Nom");
                        String prenom = etudiant.getString("Prenom");
                        String Tel = etudiant.getString("Tel");
                        String Email = etudiant.getString("Email");
                        String Classe = etudiant.getString("Classe");
                        String Image = etudiant.getString("image");
                        e.setNom(nom);
                        e.setPrenom(prenom);
                        e.setTel(Tel);
                        e.setEmail(Email);
                        e.setClasse(Classe);
                        e.setImage(Image);
                        //t=
                        Log.w("YOOOOOOOOOOOOOOO", e.getNom());
                        stds.add(e);
                        Log.w("Test list Nom: ", stds.get(i).getNom());
                        Log.w("Test list Email :", stds.get(i).getEmail());
                        Num += (e.getTel());
                        Log.w("taille de liste :", " " + stds.size());
                        Log.w("taille de liste :", " " + stds.get(i).getImage());


                        String uri = "@mipmap/"+stds.get(i).getImage();
                        Log.w("DDDDDDDDDDDdd",uri);
                        int imageRessource = getResources().getIdentifier(uri,null,getPackageName());
                        image.setImageResource(imageRessource);


                    }

                    // String uri = "@mipmap/"+this.getItem(position).getImage();
                     /*Log.w("DDDDDDDDDDDdd",uri);
                     int imageRessource = getResources().getIdentifier(uri,null,getPackageName());
                     Drawable res = getResources().getDrawable(imageRessource);*/

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(GridActivity.this, "Error catch " + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

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
