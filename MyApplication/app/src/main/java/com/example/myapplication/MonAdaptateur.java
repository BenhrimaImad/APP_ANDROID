package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MonAdaptateur extends BaseAdapter {
    private Context contexte;
    private List<User> listePersonne;

    public MonAdaptateur(Context contexte, List<User> listePersonne) {
        this.contexte = contexte;
        this.listePersonne = listePersonne;
    }

    @Override
    public int getCount() {
        return listePersonne.size();
    }

    @Override
    public Object getItem(int i) {
        return listePersonne.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(contexte).inflate(R.layout.liste_personnes,viewGroup,false);
        }

        view.setClickable(true);

        final User unePersonne = (User) this.getItem(i);

        TextView nomTexte= (TextView) view.findViewById(R.id.nomTexte);
        TextView roleTexte= (TextView) view.findViewById(R.id.roleTexte);
        TextView numTelTexte= (TextView) view.findViewById(R.id.numTelTexte);
        TextView courrielTexte= (TextView) view.findViewById(R.id.courrielTexte);
        ImageView photoPersonne = (ImageView) view.findViewById(R.id.photoPersonne);

        //BIND
        String nom = unePersonne.getNom() + " " + unePersonne.getPrenom();
        String role = unePersonne.getRole();
        final int unId = unePersonne.getId();

        nomTexte.setText(nom);
        roleTexte.setText(role);
        numTelTexte.setText(unePersonne.getTelephone());
        courrielTexte.setText(unePersonne.getEmail());

//        File imgFile = new File(contexte.getFilesDir(), unePersonne.getImage());
//
//        if(imgFile.exists()){
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            photoPersonne.setImageBitmap(myBitmap);
//        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDialog
               // Intent intentionModif = new Intent(contexte, ModifPersonneActivity.class);
                //intentionModif.putExtra("idPersonne", unId);
                //contexte.startActivity(intentionModif);
            }
        });

        return view;
    }
}