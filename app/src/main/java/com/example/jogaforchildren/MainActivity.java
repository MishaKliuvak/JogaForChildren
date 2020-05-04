package com.example.jogaforchildren;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    Button but;
    TextView registration, forgot;
    private FirebaseAuth fAuth;
    private EditText emailET, passwordET;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but = findViewById(R.id.but);
        registration = findViewById(R.id.reg_click);
        forgot = findViewById(R.id.forgot);
        emailET = findViewById(R.id.edit_text_login);
        passwordET = findViewById(R.id.edit_text_pass);
        fAuth = FirebaseAuth.getInstance();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Const.setWidth(displayMetrics.widthPixels / displayMetrics.density);
        Const.setHeight(displayMetrics.heightPixels / displayMetrics.density);

        final Intent profile = new Intent(MainActivity.this, Profile.class);
        final Intent reg = new Intent(MainActivity.this, RegistrationActivity.class);
        final Intent fg = new Intent(MainActivity.this, ForgotPassword.class);


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                final String password = passwordET.getText().toString().trim();
                if(TextUtils.isEmpty(email)) {
                    emailET.setError("Введіть електронну адресу");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordET.setError("Введіть пароль");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            emailET.setText("");
                            passwordET.setText("");
                            profile.putExtra("id", fAuth.getCurrentUser().getUid());
                            fAuth.signOut();
                            startActivity(profile);
                        }
                        else Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                //profile.putExtra("1", "sdfsf");
                //startActivity(profile);
            }
        });


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(reg);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(fg);
            }
        });
        //loginLayout = findViewById(R.id.lay_login);
       // registerLayout = findViewById(R.id.lay_regist);


    }
}
