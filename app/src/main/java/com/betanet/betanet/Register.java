package com.betanet.betanet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import net.schmizz.sshj.common.Buffer;
import net.schmizz.sshj.common.Buffer.PlainBuffer;
import net.schmizz.sshj.common.KeyType;

import net.schmizz.sshj.signature.Signature;
import net.schmizz.sshj.signature.SignatureDSA;
import net.schmizz.sshj.signature.SignatureRSA;

import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import static net.schmizz.sshj.common.KeyType.RSA;

public class Register extends AppCompatActivity {

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

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        registerButton = (Button) findViewById(R.id.registerButton);
        signInFromRegisterButton = (TextView) findViewById(R.id.signInFromRegisterButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, QuickBio.class);
                startActivity(intent);
            }
        });
        signInFromRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });


        int iterations = 40000;
        int keySizeInBits = 156;

        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[20];
        random.nextBytes(salt);

        char password[] = {'p', 'a', 's', 's', 'w', 'o', 'r','d'};
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        byte[] bytes = PBEParametersGenerator.PKCS5PasswordToBytes(password);
        generator.init(bytes, salt, iterations);
        KeyParameter key = (KeyParameter)generator.generateDerivedMacParameters(keySizeInBits);

    }

    //https://android-developers.googleblog.com/2013/02/using-cryptography-to-store-credentials.html
    //https://proandroiddev.com/secure-data-in-android-encryption-in-android-part-2-991a89e55a23
    //https://developer.android.com/reference/java/security/SecureRandom

    public static SecretKey generateKey(char[] passphraseOrPin, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Number of PBKDF2 hardening rounds to use. Larger values increase
        // computation time. You should select a value that causes computation
        // to take >100ms.
        final int iterations = 1000;

        // Generate a 256-bit key
        final int outputKeyLength = 256;

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(passphraseOrPin, salt, iterations, outputKeyLength);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        return secretKey;
    }

}
