package com.betanet.betanet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    EditText editTextEmail;
    EditText editTextPassword;
    TextView forgotPasswordTextView;
    Button signInButton;
    TextView registerFromLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTV);
        signInButton = findViewById(R.id.signInButton);
        registerFromLoginTextView = findViewById(R.id.registerFromLoginTextView);

        forgotPasswordTextView.setOnClickListener((view) ->
            Toast.makeText(Login.this, "Forgot Password", Toast.LENGTH_SHORT).show()
        );

        signInButton.setOnClickListener((view) -> {

            if (!editTextEmail.getText().toString().equals("")
                    && !editTextPassword.getText().toString().equals("")) {

                signInButton.setEnabled(false);

                RequestQueue queue = Volley.newRequestQueue(Login.this);
                StringRequest sr = new StringRequest(Request.Method.POST,
                    "https://shielded-spire-76277.herokuapp.com/login/",
                    (response) -> {
                        signInButton.setEnabled(true);
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);

                            if (jsonObj.get("status").equals(200)) {
                                Toast.makeText(Login.this, "Logged in", Toast.LENGTH_LONG).show();
                                JSONObject user = (JSONObject) jsonObj.get("user");
                                Log.e(TAG, "onResponse: " + user);

//                                Intent intent = new Intent(Login.this, QuickBio.class);
//                                intent.putExtra("user_name", user.get("user_name").toString());
//                                intent.putExtra("email", editTextEmail.getText().toString());
//                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(Login.this,
                                        jsonObj.get("status") + ": " + jsonObj.get("err"),
                                        Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }, (error) -> {
                    signInButton.setEnabled(true);
                    Log.e(TAG, "onErrorResponse: ", error);
                }) {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("email_id", editTextEmail.getText().toString());
                        try {
                            String hash = getHash(editTextPassword.getText().toString());
                            params.put("password_hash", hash);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                sr.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(sr);
            } else {
                Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
            }
        });

        registerFromLoginTextView.setOnClickListener((view) -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    String getHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return String.format("%064x", new BigInteger(1, digest));
    }
}