package com.example.monpfa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;


public class EtudActivity extends AppCompatActivity {
    MaterialSpinner filtre;
    GridView gridView;
    String urlList = "http://192.168.43.254/android/listEtudiants.php";
    String urlList2="http://192.168.43.254/android/filtreEtudiants.php?classe=";
    List <Etudiant> stds;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etud);
        stds = new ArrayList<>();
        gridView = findViewById(R.id.gridview);
        filtre = findViewById(R.id.filtre);
        filtre.setItems("4IIR1", "4IIR2", "4IIR3", "MIAGE1", "MIAGE2", "MIAGE3", "3IIR1", "3IIR2", "3IIR3");

        filtre.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String classe = filtre.getItems().get(filtre.getSelectedIndex()).toString();
                Log.w("KKKKKKKKKKKK", classe);
                test2(classe);
            }
        });
        Log.w("Yoooooooooooooooooooooo", urlList);
        test();
    }

    public void test2(String classe){
        stds.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlList2+classe, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray etudiants = jsonObject.getJSONArray("etudiants");
                    for(int i = 0 ; i<etudiants.length();i++){
                        JSONObject etudiant = etudiants.getJSONObject(i);

                        String nom = etudiant.getString("Nom");
                        String prenom = etudiant.getString("Prenom");
                        String Tel = etudiant.getString("Tel");
                        String Email = etudiant.getString("Email");
                        String Classe = etudiant.getString("Classe");
                        String imgs = etudiant.getString("image");
                        Etudiant e = new Etudiant();
                        e.setNom(nom);
                        e.setPrenom(prenom);
                        e.setTel(Tel);
                        e.setEmail(Email);
                        e.setClasse(Classe);
                        e.setImage(imgs);
                        Log.w("YOOOOOOOOOOOOOOO",e.getNom());
                        Log.w("YOOOOOOOOOOOOOOO",e.getNom());
                        stds.add(e);
                        Log.w("YOOOOOOOOOOOOOOO",stds.get(i).getNom());
                    }
                    customAdapter = new CustomAdapter(EtudActivity.this,stds);
                    gridView.setAdapter(customAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(),GridActivity.class);
                            intent.putExtra("nom",stds.get(position).getNom());
                            intent.putExtra("prenom",stds.get(position).getPrenom());
                            intent.putExtra("email",stds.get(position).getEmail());
                            intent.putExtra("classe",stds.get(position).getClasse());
                            intent.putExtra("tel",stds.get(position).getTel());
                            intent.putExtra("image",stds.get(position).getImage());
                            startActivity(intent);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EtudActivity.this, "Error catch " + e.toString(), Toast.LENGTH_SHORT).show();

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
    public void test(){
        stds.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray etudiants = jsonObject.getJSONArray("etudiants");
                    for(int i = 0 ; i<etudiants.length();i++){
                        JSONObject etudiant = etudiants.getJSONObject(i);

                        String nom = etudiant.getString("Nom");
                        String prenom = etudiant.getString("Prenom");
                        String Tel = etudiant.getString("Tel");
                        String Email = etudiant.getString("Email");
                        String Classe = etudiant.getString("Classe");
                        String Image = etudiant.getString("image");
                        Etudiant e = new Etudiant();
                        e.setNom(nom);
                        e.setPrenom(prenom);
                        e.setTel(Tel);
                        e.setEmail(Email);
                        e.setClasse(Classe);
                        e.setImage(Image);
                        Log.w("YOOOOOOOOOOOOOOO",e.getNom());
                        stds.add(e);
                        Log.w("Test list Nom: ",stds.get(i).getNom());
                        Log.w("Test list Email :",stds.get(i).getEmail());
                        Log.w("taille de liste :", " "+stds.size() );

                    }
                    customAdapter = new CustomAdapter(EtudActivity.this,stds);
                    gridView.setAdapter(customAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(),GridActivity.class);
                            intent.putExtra("nom",stds.get(position).getNom());
                            intent.putExtra("prenom",stds.get(position).getPrenom());
                            intent.putExtra("email",stds.get(position).getEmail());
                            intent.putExtra("classe",stds.get(position).getClasse());
                            intent.putExtra("tel",stds.get(position).getTel());
                            intent.putExtra("image",stds.get(position).getImage());
                            startActivity(intent);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EtudActivity.this, "Error catch " + e.toString(), Toast.LENGTH_SHORT).show();

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
    private class CustomAdapter extends ArrayAdapter<Etudiant>{

        int t ;

        public CustomAdapter(@NonNull Context context, List<Etudiant> stds) {
            super(context, R.layout.row_data,stds);
            t = stds.size();
        }

        @Override
        public int getCount() {

            return t;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v1 = getLayoutInflater().inflate(R.layout.row_data, null);
            TextView name = v1.findViewById(R.id.nom);
            TextView prenom = v1.findViewById(R.id.prenom);
            ImageView image = v1.findViewById(R.id.image);
            String uri = "@mipmap/"+this.getItem(position).getImage();
            Log.w("DDDDDDDDDDDdd",uri);
            int imageRessource = getResources().getIdentifier(uri,null,getPackageName());



            prenom.setText(this.getItem(position).getPrenom());
            name.setText(this.getItem(position).getNom());
            image.setImageResource(imageRessource);
            return v1;

        }
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
