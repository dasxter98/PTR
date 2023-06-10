package com.example.ptr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class people extends AppCompatActivity {
    ImageView  patita,users, calendar,coins,chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        patita = (ImageView) findViewById(R.id.patita);
        users = (ImageView) findViewById(R.id.users);
        calendar = (ImageView) findViewById(R.id.calendar);
        coins = (ImageView) findViewById(R.id.coins);
        chat = (ImageView) findViewById(R.id.chat);
        patita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(people.this, hub.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(people.this, calendar.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);

            }
        });
        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(people.this, coins.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(people.this, chat.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });

    }
}