package com.example.jogaforchildren;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameET, emailET, passwordET;
    private Button registrationBtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    private HashMap<String, List<String>> poses = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameET = findViewById(R.id.edit_username_reg);
        emailET = findViewById(R.id.edit_text_login_reg);
        passwordET = findViewById(R.id.edit_text_pass_reg);
        registrationBtn = findViewById(R.id.but_reg);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                final String username = usernameET.getText().toString();
                if(TextUtils.isEmpty(email)) {
                    emailET.setError("Введіть електронну адресу");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passwordET.setError("Введіть пароль");
                    return;
                }
                if (TextUtils.isEmpty(username)){
                    usernameET.setError("Введіть ім'я");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Вас успішно зареєстровано", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("uName", username);
                                user.put("email", email);
                                user.put("date", "");
                                documentReference.set(user);
                                usernameET.setText("");
                                emailET.setText("");
                                passwordET.setText("");
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, "Помилка. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                });

            }
        });
    }
}
