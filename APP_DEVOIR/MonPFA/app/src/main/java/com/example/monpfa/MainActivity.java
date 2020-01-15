package com.example.monpfa;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText txt_email,txt_pwd;
    Button btn_cnx;
    RelativeLayout rellay1;
    String UrlLogin="http://192.168.1.4/android/signin.php";
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
        txt_pwd   = findViewById(R.id.txt_pwd);
        btn_cnx   = findViewById(R.id.btn_cnx);
        loading   = findViewById(R.id.loading);

        btn_cnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("DDDDDDDDDDDDDDDDD","onCick");
                Login();
            }
        });
    }

    private void Login() {
        final String Email = txt_email.getText().toString().trim();
        final String Pwd = txt_pwd.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message= jsonObject.getString("message");
                    Log.w("EMMMMMMMMMMMMM",Email);
                    if(success.equals("1")){

                            Log.w("FFFFFEEEEETTTTTTEEEEE","conntected "+message );
                            Toast.makeText(MainActivity.this,"success Login. ",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(MainActivity.this,"Error au niveau du else  "+message ,Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    //.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Error catch " + e.toString(),Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error volley" + error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",Email);
                params.put("pwd",Pwd);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
