package com.example.ptr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class hub extends AppCompatActivity {

    ImageView  patita,users, calendar,coins,chat;
    ImageView sanando,perdidos,proceso,adoptados,todos,crear,adopcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        todos = (ImageView) findViewById(R.id.bt_todos);
        adopcion = (ImageView) findViewById(R.id.bt_adopcion);
        sanando = (ImageView) findViewById(R.id.bt_sanando);
        perdidos = (ImageView) findViewById(R.id.bt_perdidos);
        proceso = (ImageView) findViewById(R.id.bt_proceso);
        adoptados = (ImageView) findViewById(R.id.bt_adoptados);
        crear = (ImageView) findViewById(R.id.bt_crea);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, crear.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, lista.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, lista.class);
                intent.putExtra("filtro", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, lista.class);
                intent.putExtra("filtro", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        sanando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, lista.class);
                intent.putExtra("filtro", 5);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        perdidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, lista.class);
                intent.putExtra("filtro", 4);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        proceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, lista.class);
                intent.putExtra("filtro", 2);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        adoptados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, lista.class);
                intent.putExtra("filtro", 3);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });

        patita = (ImageView) findViewById(R.id.patita);
        users = (ImageView) findViewById(R.id.users);
        calendar = (ImageView) findViewById(R.id.calendar);
        coins = (ImageView) findViewById(R.id.coins);
        chat = (ImageView) findViewById(R.id.chat);

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, people.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, calendar.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);

            }
        });
        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, coins.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hub.this, chat.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, hub.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
        finish();
    }

}