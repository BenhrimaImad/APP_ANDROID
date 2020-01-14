package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.example.myapplication.model.User;
import com.example.myapplication.remote.APIUtils;
import com.example.myapplication.remote.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AjoutPersonneActivity extends AppCompatActivity {

    TextView nomTexteAjout;
    TextView prenomTexteAjout;
    TextView numTelTexteAjout;
    TextView courrielTexteAjout;
    Spinner role;
    TextView passwordTexteAjout;
    ImageView photoPersonneAjout;
    View v;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userService= APIUtils.getUserService();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_personne);

        Toolbar laToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(laToolbar);
        v = (LinearLayout) findViewById(R.id.layoutAjout);
        nomTexteAjout = (TextView) findViewById(R.id.nomTexteAjout);
        prenomTexteAjout = (TextView) findViewById(R.id.prenomTexteAjout);
        numTelTexteAjout = (TextView) findViewById(R.id.numTelTexteAjout);
        courrielTexteAjout = (TextView) findViewById(R.id.courrielTexteAjout);
        role = (Spinner) findViewById(R.id.role);
        passwordTexteAjout = (TextView) findViewById(R.id.passwordTexteAjout);
        photoPersonneAjout = (ImageView) findViewById(R.id.photoPersonneAjout);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ajout, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.retour:
                Intent intentionRetour = new Intent(AjoutPersonneActivity.this, MainActivity.class);
                AjoutPersonneActivity.this.startActivity(intentionRetour);
                finish();
                return true;
            case R.id.parcourir:

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, 1);
                return true;
            case R.id.ajout:

                String unNom = nomTexteAjout.getText().toString();
                String unPrenom = prenomTexteAjout.getText().toString();
                String unNumTel = numTelTexteAjout.getText().toString();
                String unCourriel = courrielTexteAjout.getText().toString();
                String unRole = role.getSelectedItem().toString();
                String unPassword = passwordTexteAjout.getText().toString();
                String unNomPhoto = "";//photoPersonneAjout.getContentDescription().toString();
                User user=new User();
                user.setNom(unNom);
                user.setPrenom(unPrenom);
                user.setEmail(unCourriel);
                user.setPassword(unPassword);
                user.setImage(unNomPhoto);
                user.setRole(unRole);
                user.setClasses(null);
                user.setTelephone(unNumTel);
                Call<User> call=userService.addUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AjoutPersonneActivity.this, "Add Success!", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.getStackTrace();
                        Toast.makeText(AjoutPersonneActivity.this, "Add Success!", Toast.LENGTH_SHORT).show();
                    }
                });
                intentionRetour = new Intent(AjoutPersonneActivity.this, MainActivity.class);
                AjoutPersonneActivity.this.startActivity(intentionRetour);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri uriData = data.getData();
        String uriDataString = uriData.toString();

       // Snackbar.make(v, uriDataString, Snackbar.LENGTH_LONG).show();
        String nomPhoto = uriDataString.substring(uriDataString.lastIndexOf("/") + 1);



        try {

            Path pathData = Paths.get(uriDataString);
            Path pathTarget = Paths.get(AjoutPersonneActivity.this.getFilesDir() + nomPhoto);
            Files.copy(pathData, pathTarget);
            File imgFile = new File(AjoutPersonneActivity.this.getFilesDir(), nomPhoto);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            photoPersonneAjout.setContentDescription(nomPhoto);
            photoPersonneAjout.setImageBitmap(myBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}