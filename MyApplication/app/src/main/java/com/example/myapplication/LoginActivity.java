package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.model.User;
import com.example.myapplication.remote.APIUtils;
import com.example.myapplication.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText txt_email,txt_pwd;
    Button btn_cnx;
    LinearLayout linlay1;
    UserService userService;
    SharedPreferences pref;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            linlay1.setVisibility(View.VISIBLE);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref=getSharedPreferences("user",MODE_PRIVATE);
        Log.w("INFO","id : "+pref.getInt("id",0));
        Log.w("INFO","role : " +pref.getString("",""));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService=APIUtils.getUserService();

        linlay1 = findViewById(R.id.linlay1);
        handler.postDelayed(runnable, 2000);

        txt_email = findViewById(R.id.input_email);
        txt_pwd   = findViewById(R.id.input_password);
        btn_cnx   = findViewById(R.id.btn_login);


        btn_cnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("DDDDDDDDDDDDDDDDD","onCick");
                Login();
            }
        });
    }

    private void Login() {
        if (!validate()) {
            Toast.makeText(LoginActivity.this, "Login Error!", Toast.LENGTH_SHORT).show();
            return;
        }
        final String email = txt_email.getText().toString().trim();
        final String password = txt_pwd.getText().toString().trim();
        Call<User> call=userService.getUser(email);
        call.enqueue(new Callback<User>(){

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    User user=response.body();
                    if(user!=null){
                        if(password.equals(user.getPassword())){
                            Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putInt("id",user.getId());
                            editor.putString("role",user.getRole());
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);

                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Error!", Toast.LENGTH_SHORT).show();

                        }

                    }else {
                        Toast.makeText(LoginActivity.this, "Login Error!", Toast.LENGTH_SHORT).show();

                    }


                }else{
                    Toast.makeText(LoginActivity.this, "Login Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });


       // Toast.makeText(LoginActivity.this, "Finish !", Toast.LENGTH_SHORT).show();

    }
    public boolean validate() {
        boolean valid = true;

        String email = txt_email.getText().toString();
        String password = txt_pwd.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt_email.setError("enter a valid email address");
            valid = false;
        } else {
            txt_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            txt_pwd.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            txt_pwd.setError(null);
        }

        return valid;
    }
}
