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
    TextView forgotPasswordTV;
    Button signInButton;
    TextView registerFromLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        forgotPasswordTV = (TextView) findViewById(R.id.forgotPasswordTV);
        signInButton = (Button) findViewById(R.id.signInButton);
        registerFromLoginTextView = (TextView) findViewById(R.id.registerFromLoginTextView);

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Forgot Password", Toast.LENGTH_SHORT).show();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editTextEmail.getText().toString().equals("")
                        && !editTextPassword.getText().toString().equals("")) {

                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    StringRequest sr = new StringRequest(Request.Method.POST, "http://merchantaliabbas.pythonanywhere.com/login", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG, "onResponse: " + response);
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getInt("success") == 1) {
                                    Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "onErrorResponse: ", error);
                        }
                    }) {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email_id", editTextEmail.getText().toString());
                            Log.e(TAG, "getParams: Password " + editTextPassword.getText().toString() );
                            String hash = null;
                            try {
                                hash = getHash(editTextPassword.getText().toString());
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                            Log.e(TAG, "getParams: hash " + hash);
                            params.put("password_hash", hash);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/x-www-form-urlencoded");
                            return params;
                        }
                    };
                    queue.add(sr);
                } else {
                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
        registerFromLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    String getHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return String.format("%064x", new BigInteger(1, digest));
    }
    private String get_message(int val) {
        switch (val) {
            case 111:
                return "No such email_id has been registered";
            case 222:
                return "Wrong Password!";
            case 333:
                return "Your email id has not been verified as yet.\nPlease go to the verification link sent to you via email.";
            case 200:
                return "Welcome!";
            default:
                return "An error has occurred. Please try again..";
        }
    }
}
