package com.example.ptr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    ImageView go_back,bt_regiter;
    EditText edt_nombre, edt_passw_register,edt_email_register;
    TextView txt_iniciar;

    ProgressBar load_register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        go_back = findViewById(R.id.bt_goback);
        bt_regiter =  findViewById(R.id.bt_register_register);
        txt_iniciar = findViewById(R.id.txt_register_iniciar);
        edt_nombre = findViewById(R.id.edt_nombre);
        edt_passw_register = findViewById(R.id.edt_passw_register);
        edt_email_register = findViewById(R.id.edt_email_register);
        load_register = findViewById(R.id.load_register);
        load_register.setVisibility(View.INVISIBLE);
        txt_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
        bt_regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_nombre.getText().toString().isEmpty() || edt_email_register.getText().toString().isEmpty()|| edt_email_register.getText().toString().isEmpty()){
                    Toast.makeText(register.this, "Introduzca sus datos", Toast.LENGTH_SHORT).show();
                }else{

                    load_register.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(edt_email_register.getText().toString(),edt_passw_register.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // El usuario se creó correctamente
                                        Toast.makeText(register.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                                        String email = edt_email_register.getText().toString();
                                        String password = edt_passw_register.getText().toString();
                                        Intent intent = new Intent(register.this, login.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("password", password);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(register.this, "Error al crear el usuario: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        load_register.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }
            }
        });

    }

}