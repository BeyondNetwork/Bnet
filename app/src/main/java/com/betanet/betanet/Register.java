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
import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    Button registerButton;
    TextView signInFromRegisterButton;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.registerButton);
        signInFromRegisterButton = findViewById(R.id.signInFromRegisterButton);

        registerButton.setOnClickListener((view) -> {

            if (!editTextEmail.getText().toString().equals("")
                    && !editTextName.getText().toString().equals("")
                    && !editTextPassword.getText().toString().equals("")) {

                registerButton.setEnabled(false);

                RequestQueue queue = Volley.newRequestQueue(Register.this);
                StringRequest sr = new StringRequest(Request.Method.POST,
                    "https://shielded-spire-76277.herokuapp.com/register/",
                    (response) -> {
                        registerButton.setEnabled(true);
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);

                            if (jsonObj.get("status").equals(200)) {
                                Toast.makeText(Register.this, "Registered", Toast.LENGTH_LONG).show();
                                JSONObject user = (JSONObject) jsonObj.get("user");
                                Log.e(TAG, "onResponse: " + user);

    //                            Intent intent = new Intent(Register.this, QuickBio.class);
    //                            intent.putExtra("user_name", editTextName.getText().toString());
    //                            intent.putExtra("email", editTextEmail.getText().toString());
    //                            startActivity(intent);
                            }
                            else {
                                Toast.makeText(Register.this,
                                        jsonObj.get("status") + ": " + jsonObj.get("err"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, (error) -> {
                        registerButton.setEnabled(true);
                        Log.e(TAG, "onErrorResponse: ", error);
                    }) {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_name", editTextName.getText().toString());
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
                Toast.makeText(Register.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
            }
        });

        signInFromRegisterButton.setOnClickListener((view) -> {
            Intent intent = new Intent(Register.this, Login.class);
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
//    public static String generateStrongPasswordHashWithSHA256(String password) {
//        try {
//            char[] chars = password.toCharArray();
//            byte[] salt = getSalt();
//
//            PBEKeySpec spec = new PBEKeySpec(chars, salt, 1010101, 20 * Byte.SIZE);
//            SecretKeyFactory skf = SecretKeyFactory
//                    .getInstance("PBKDF2WithHmacSHA1");
//            byte[] hash = skf.generateSecret(spec).getEncoded();
//
//            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
//            hash = sha256.digest();
//
//            return toHex(salt) + ":" + toHex(hash);
//        } catch (Exception e) {
//            System.out.println("Exception: Error in generating password"
//                    + e.toString());
//        }
//        return "";
//    }


//    int iterations = 40000;
//    int keySizeInBits = 156;
//
//    SecureRandom random = new SecureRandom();
//    byte salt[] = new byte[20];
//        random.nextBytes(salt);
//
//                char password[]={'p','a','s','s','w','o','r','d'};
//                PKCS5S2ParametersGenerator generator=new PKCS5S2ParametersGenerator(new SHA256Digest());
//                byte[]bytes=PBEParametersGenerator.PKCS5PasswordToBytes(password);
//                generator.init(bytes,salt,iterations);
//                KeyParameter key=(KeyParameter)generator.generateDerivedMacParameters(keySizeInBits);
//
//                }

//https://android-developers.googleblog.com/2013/02/using-cryptography-to-store-credentials.html
//https://proandroiddev.com/secure-data-in-android-encryption-in-android-part-2-991a89e55a23
//https://developer.android.com/reference/java/security/SecureRandom

    public static SecretKey generateKey(char[] passphraseOrPin, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
// Number of PBKDF2 hardening rounds to use. Larger values increase
// computation time. You should select a value that causes computation
// to take >100ms.
        final int iterations = 1000;
        final int outputKeyLength = 256;

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(passphraseOrPin, salt, iterations, outputKeyLength);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        return secretKey;
    }
}