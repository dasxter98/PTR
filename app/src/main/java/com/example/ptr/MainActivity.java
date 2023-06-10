package com.example.ptr;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView  bt_sig,bt_com,faro0, faro1, faro2,title,hola,title_bases,title_plantillas,title_chat,title_calendario,dog,papers,hearth,women,skip,crea,manten,gestiona,realiza,indicador1,indicador2,indicador3,indicador4,indicador5,bt_back;
    private View activity;
    int animacionActual ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //================================================================
        faro0 = findViewById(R.id.faro0);
        faro1 = findViewById(R.id.faro1);
        faro2 = findViewById(R.id.faro2);
        title = findViewById(R.id.title);
        hola = findViewById(R.id.hola);
        bt_back =findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviusAnimation();
            }
        });
        bt_com = findViewById(R.id.bt_comenzar);
        bt_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });
        bt_sig = findViewById(R.id.bt_siguiente);
        bt_sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextAnimation();
            }
        });
        activity = findViewById(R.id.activity_main);
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });
        title_bases = findViewById(R.id.title_bases);
        title_calendario = findViewById(R.id.title_calendario);
        title_plantillas= findViewById(R.id.title_plantillas);
        title_chat = findViewById(R.id.title_chat);
        dog = findViewById(R.id.dog_man);
        papers= findViewById(R.id.papers);
        hearth= findViewById(R.id.hearth);
        women = findViewById(R.id.women);
        crea= findViewById(R.id.crea);
        manten = findViewById(R.id.manten);
        gestiona= findViewById(R.id.gestiona);
        realiza= findViewById(R.id.realiza);
        indicador1 = findViewById(R.id.indicador1);
        indicador2 = findViewById(R.id.indicador2);
        indicador3 = findViewById(R.id.indicador3);
        indicador4 = findViewById(R.id.indicador4);
        indicador5 = findViewById(R.id.indicador5);
        //================================================================
        faro0.setAlpha(0f);
        faro1.setAlpha(0f);
        faro2.setAlpha(0f);
        title.setAlpha(0f);
        hola.setAlpha(0f);
        bt_sig.setAlpha(0f);
        bt_back.setAlpha(0f);
        bt_com.setAlpha(0f);
        title_bases.setAlpha(0f);
        title_chat.setAlpha(0f);
        title_plantillas.setAlpha(0f);
        title_calendario.setAlpha(0f);
        dog.setAlpha(0f);
        papers.setAlpha(0f);
        hearth.setAlpha(0f);
        women.setAlpha(0f);
        skip.setAlpha(0f);
        crea.setAlpha(0f);
        manten.setAlpha(0f);
        gestiona.setAlpha(0f);
        realiza.setAlpha(0f);
        indicador1.setAlpha(0f);
        indicador2.setAlpha(0f);
        indicador3.setAlpha(0f);
        indicador4.setAlpha(0f);
        indicador5.setAlpha(0f);
        //================================================================
        Drawable gris = new ColorDrawable(Color.GRAY);
        Drawable blanco = new ColorDrawable(Color.WHITE);
        Drawable fondo = getResources().getDrawable(R.drawable.background);
        TransitionDrawable transicion = new TransitionDrawable(new Drawable[] {gris , blanco });
        TransitionDrawable transicion2 = new TransitionDrawable(new Drawable[] {blanco , fondo });
        //================================================================
        faro0.animate().alpha(1f).setDuration(700).setInterpolator(new AccelerateDecelerateInterpolator()).withEndAction(() -> {
                    faro0.animate().alpha(0f).setDuration(700);
                }).start();
        //================================================================
        faro1.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.setBackground(transicion);
                transicion.startTransition(1000);
                faro1.animate().alpha(1f).setDuration(700).setInterpolator(new AccelerateDecelerateInterpolator()).withEndAction(() -> {
                    faro1.animate().alpha(0f).setDuration(700);
                }).start();
            }
        }, 700);
        //================================================================
        faro2.postDelayed(new Runnable() {
            @Override
            public void run() {
                faro2.animate().alpha(1f).setDuration(700).withEndAction(() -> {
                            activity.setBackground(transicion2);
                            transicion2.startTransition(1000);
                            title.animate().alpha(1f).setDuration(700);
                            title.setTranslationY(1000f);
                            title.animate().translationY(0f).setDuration(700);
                            hola.animate().alpha(1f).setDuration(700);
                            hola.animate().translationYBy(-30).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                            faro2.animate().translationYBy(-120).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                            bt_sig.animate().alpha(1f).setDuration(700);
                            bt_sig.animate().translationYBy(-10).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                            skip.animate().alpha(1f).setDuration(700);
                            indicador1.animate().alpha(1f).setDuration(700);
                            animacionActual = 1;
                }).start();
            }
        }, 1400);
        //================================================================
    }


    private void animacionFaro() {
        //entrada faro

        title.setTranslationX(-500f);
        title.animate().translationX(0f).setDuration(700);
        title.animate().alpha(1f).setDuration(500);

        faro2.setTranslationX(500f);
        faro2.animate().translationX(0f).setDuration(700);
        faro2.animate().alpha(1f).setDuration(500);

        hola.setTranslationX(-500f);
        hola.animate().translationX(0f).setDuration(700);
        hola.animate().alpha(1f).setDuration(500);

        indicador1.animate().alpha(1f).setDuration(700);

        //=============================================================
        //salida perro

        title_bases.setTranslationX(0f);
        title_bases.animate().translationX(-500f).setDuration(700);
        title_bases.animate().alpha(0f).setDuration(500);

        dog.setTranslationX(0f);
        dog.animate().translationX(500f).setDuration(700);
        dog.animate().alpha(0f).setDuration(500);


        crea.setTranslationX(-0f);
        crea.animate().translationX(-500f).setDuration(700);
        crea.animate().alpha(0f).setDuration(500);

        indicador2.animate().alpha(0f).setDuration(700);
        bt_back.animate().alpha(0f).setDuration(700);
        skip.animate().alpha(1f).setDuration(700);

        animacionActual = 1;
    }
    private void animacionBases() {
        //salida faro
        title.setTranslationX(0f);
        title.animate().translationX(-500f).setDuration(700);
        title.animate().alpha(0f).setDuration(500);

        faro2.setTranslationX(0f);
        faro2.animate().translationX(500f).setDuration(700);
        faro2.animate().alpha(0f).setDuration(500);

        hola.setTranslationX(0f);
        hola.animate().translationX(-500f).setDuration(700);
        hola.animate().alpha(0f).setDuration(500);

        indicador1.animate().alpha(0f).setDuration(700);

        //=============================================================

        //salida hearth
        title_plantillas.setTranslationX(0f);
        title_plantillas.animate().translationX(-500f).setDuration(700);
        title_plantillas.animate().alpha(0f).setDuration(500);

        hearth.setTranslationX(0f);
        hearth.animate().translationX(500f).setDuration(700);
        hearth.animate().alpha(0f).setDuration(500);

        realiza.setTranslationX(0f);
        realiza.animate().translationX(-500f).setDuration(700);
        realiza.animate().alpha(0f).setDuration(500);

        indicador3.animate().alpha(0f).setDuration(700);

        //=============================================================

        //entrada bases
        title_bases.setTranslationX(500f);
        title_bases.animate().translationX(0f).setDuration(700);
        title_bases.animate().alpha(1f).setDuration(500);

        dog.setTranslationX(-500f);
        dog.animate().translationX(0f).setDuration(700);
        dog.animate().alpha(1f).setDuration(500);


        crea.setTranslationX(500f);
        crea.animate().translationX(0f).setDuration(700);
        crea.animate().alpha(1f).setDuration(500);

        indicador2.animate().alpha(1f).setDuration(700);
        bt_back.animate().alpha(1f).setDuration(700);

        animacionActual = 2;
    }

    private void animacionPlantillas() {
        //salida women
        title_chat.setTranslationX(0f);
        title_chat.animate().translationX(-500f).setDuration(700);
        title_chat.animate().alpha(0f).setDuration(500);

        women.setTranslationX(0f);
        women.animate().translationX(500f).setDuration(700);
        women.animate().alpha(0f).setDuration(500);

        manten.setTranslationX(0f);
        manten.animate().translationX(-500f).setDuration(700);
        manten.animate().alpha(0f).setDuration(500);

        indicador4.animate().alpha(0f).setDuration(700);

        //=============================================================

        //salida dog
        title_bases.setTranslationX(0f);
        title_bases.animate().translationX(-500f).setDuration(700);
        title_bases.animate().alpha(0f).setDuration(500);

        dog.setTranslationX(0f);
        dog.animate().translationX(500f).setDuration(700);
        dog.animate().alpha(0f).setDuration(500);

        crea.setTranslationX(0f);
        crea.animate().translationX(-500f).setDuration(700);
        crea.animate().alpha(0f).setDuration(500);

        indicador2.animate().alpha(0f).setDuration(700);

        //================================================================
        //entrada hearth
        title_plantillas.setTranslationX(500f);
        title_plantillas.animate().translationX(0f).setDuration(700);
        title_plantillas.animate().alpha(1f).setDuration(500);

        hearth.setTranslationX(-500f);
        hearth.animate().translationX(0f).setDuration(700);
        hearth.animate().alpha(1f).setDuration(500);

        realiza.setTranslationX(500f);
        realiza.animate().translationX(0f).setDuration(700);
        realiza.animate().alpha(1f).setDuration(500);

        indicador3.animate().alpha(1f).setDuration(700);

        animacionActual = 3;
    }

    private void animacionChat() {

        //salida papers
        title_calendario.setTranslationX(0f);
        title_calendario.animate().translationX(-500f).setDuration(700);
        title_calendario.animate().alpha(0f).setDuration(500);

        papers.setTranslationX(0f);
        papers.animate().translationX(500f).setDuration(700);
        papers.animate().alpha(0f).setDuration(500);


        gestiona.setTranslationX(0f);
        gestiona.animate().translationX(-500f).setDuration(700);
        gestiona.animate().alpha(0f).setDuration(500);

        indicador5.animate().alpha(0f).setDuration(700);

        //=============================================================
        //salida hearth

        title_plantillas.setTranslationX(0f);
        title_plantillas.animate().translationX(-500f).setDuration(700);
        title_plantillas.animate().alpha(0f).setDuration(500);

        hearth.setTranslationX(0f);
        hearth.animate().translationX(500f).setDuration(700);
        hearth.animate().alpha(0f).setDuration(500);

        realiza.setTranslationX(0f);
        realiza.animate().translationX(-500f).setDuration(700);
        realiza.animate().alpha(0f).setDuration(500);

        indicador3.animate().alpha(0f).setDuration(700);

        //================================================================
        //entrada women
        title_chat.setTranslationX(500f);
        title_chat.animate().translationX(0f).setDuration(700);
        title_chat.animate().alpha(1f).setDuration(500);

        women.setTranslationX(-500f);
        women.animate().translationX(0f).setDuration(700);
        women.animate().alpha(1f).setDuration(500);

        manten.setTranslationX(500f);
        manten.animate().translationX(0f).setDuration(700);
        manten.animate().alpha(1f).setDuration(500);

        indicador4.animate().alpha(1f).setDuration(700);

        bt_sig.animate().alpha(1f).setDuration(700);
        bt_sig.setClickable(true);
        bt_com.animate().alpha(0f).setDuration(700);

        animacionActual = 4;

        skip.animate().alpha(1f).setDuration(700);
        skip.setClickable(true);
    }

    private void animacionCalendario() {
        //salida women
        title_chat.setTranslationX(0f);
        title_chat.animate().translationX(-500f).setDuration(700);
        title_chat.animate().alpha(0f).setDuration(500);

        women.setTranslationX(0f);
        women.animate().translationX(500f).setDuration(700);
        women.animate().alpha(0f).setDuration(500);

        manten.setTranslationX(0f);
        manten.animate().translationX(-500f).setDuration(700);
        manten.animate().alpha(0f).setDuration(500);

        indicador4.animate().alpha(0f).setDuration(700);
        //================================================================
        //entrada papers
        title_calendario.setTranslationX(-500f);
        title_calendario.animate().translationX(0f).setDuration(700);
        title_calendario.animate().alpha(1f).setDuration(500);

        papers.setTranslationX(500f);
        papers.animate().translationX(0f).setDuration(700);
        papers.animate().alpha(1f).setDuration(500);


        gestiona.setTranslationX(-500f);
        gestiona.animate().translationX(0f).setDuration(700);
        gestiona.animate().alpha(1f).setDuration(500);

        indicador5.animate().alpha(1f).setDuration(700);
        bt_sig.animate().alpha(0f).setDuration(700);
        bt_sig.setClickable(false);
        bt_com.animate().alpha(1f).setDuration(700);

        animacionActual = 5;

        skip.animate().alpha(0f).setDuration(700);
        skip.setClickable(false);
    }

    private void showNextAnimation(){
        switch (animacionActual) {
            case 1:
                animacionBases();
                break;
            case 2:
                animacionPlantillas();
                break;
            case 3:
                animacionChat();
                break;
            case 4:
                animacionCalendario();
                break;
            default:
                break;
        }
    }

    private void showPreviusAnimation() {
        switch (animacionActual) {
            case 2:
                animacionFaro();
                break;
            case 3:
                animacionBases();
                break;
            case 4:
                animacionPlantillas();
                break;
            case 5:
                animacionChat();
                break;
            default:
                break;
        }
    }

}