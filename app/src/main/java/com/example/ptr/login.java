package com.example.ptr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    ImageView bt_register , bt_iniciar;
    EditText edt_email, edt_passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bt_register = findViewById(R.id.bt_register);
        bt_iniciar= findViewById(R.id.bt_iniciar);
        edt_email = findViewById(R.id.edt_email);
        edt_passw = findViewById(R.id.edt_passw);
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        edt_email.setText(email);
        edt_passw.setText(password);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
        bt_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_email.getText().toString().isEmpty() || edt_passw.getText().toString().isEmpty()) {
                    Toast.makeText(login.this, "Introduzca sus datos", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(edt_email.getText().toString(), edt_passw.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(login.this, hub.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(login.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}