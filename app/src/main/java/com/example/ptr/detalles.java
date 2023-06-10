package com.example.ptr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class detalles extends AppCompatActivity {
    private ImageView img_animal_detalles,go_back,img_loc_detalles,img_detalles_genero,img_detalles_chip,img_detalles_filtro,bt_borrar,bt_editar;
    private TextView txt_nombre_detalles,txt_detalles_edad,txt_detalles_estado,txt_detalles_genero,txt_detalles_peso,txt_detalles_chip,txt_detalles_vacuna;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        //===================================================
        img_animal_detalles = (ImageView)findViewById(R.id.img_animal_detalles);
        go_back = (ImageView)findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detalles.this, lista.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        bt_editar= (ImageView)findViewById(R.id.bt_editar);
        bt_borrar = (ImageView)findViewById(R.id.bt_borrar);
        img_loc_detalles = (ImageView)findViewById(R.id.img_loc_detalles);
        img_detalles_genero = (ImageView)findViewById(R.id.img_detalles_genero);
        img_detalles_chip = (ImageView)findViewById(R.id.img_detalles_chip);
        img_detalles_filtro = (ImageView)findViewById(R.id.img_detalles_filtro);
        //===================================================
        txt_nombre_detalles = (TextView)findViewById(R.id.txt_nombre_detalles);
        txt_detalles_edad = (TextView)findViewById(R.id.txt_detalles_edad);
        txt_detalles_estado = (TextView)findViewById(R.id.txt_detalles_estado);
        txt_detalles_genero = (TextView)findViewById(R.id.txt_detalles_genero);
        txt_detalles_peso = (TextView)findViewById(R.id.txt_detalles_peso);
        txt_detalles_chip = (TextView)findViewById(R.id.txt_detalles_chip);
        txt_detalles_vacuna = (TextView)findViewById(R.id.txt_detalles_vacuna);
        bt_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detalles.this, crear.class);
                intent.putExtra("nombre", txt_nombre_detalles.getText());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        obtenerDetallesAnimal(nombre);

        bt_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarAnimal(nombre);
            }
        });
    }
    private void borrarAnimal(String nombre) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("animales")
                .whereEqualTo("nombre", nombre)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Eliminar el documento del animal encontrado
                            db.collection("animales")
                                    .document(document.getId())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(detalles.this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(detalles.this, lista.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(detalles.this, "Error al borrar animal", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        Toast.makeText(detalles.this, "Error al borrar animal, consulta SQL", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void obtenerDetallesAnimal(String nombre) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference animalesRef = db.collection("animales");
        Query query = animalesRef.whereEqualTo("nombre", nombre);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    animal animal = documentSnapshot.toObject(animal.class);
                    if (animal != null) {
                        //===============================================
                        txt_nombre_detalles.setText(animal.getNombre());
                        //===============================================
                        if(animal.getEdad()>=12){
                            int anos = animal.getEdad()/12;
                            if(anos<2) {
                                txt_detalles_edad.setText(String.valueOf(anos + " años"));
                            }else{
                                txt_detalles_edad.setText(String.valueOf(anos+ " años"));
                            }
                        }else{
                            txt_detalles_edad.setText(String.valueOf(animal.getEdad())+" meses");
                        }
                        //===============================================
                        if(animal.getChip()) {
                            txt_detalles_chip.setText("Chip: Sí");
                        }else {
                            txt_detalles_chip.setText("Chip: No");
                        }
                        //===============================================
                        if(animal.getVacuna()) {
                            txt_detalles_vacuna.setText("Vacunas: Sí");
                        }else {
                            txt_detalles_vacuna.setText("Vacunas: No");
                        }
                        //===============================================
                        txt_detalles_peso.setText(String.valueOf(animal.getPeso())+" kg");
                        //===============================================
                        if(animal.getGenero()) {
                            img_detalles_genero.setImageResource(R.drawable.male);
                            txt_detalles_genero.setText("macho");
                        }else {
                            img_detalles_genero.setImageResource(R.drawable.female);
                            txt_detalles_genero.setText("hembra");
                        }
                        //===============================================
                        if(animal.getFiltro()==1){
                            img_detalles_filtro.setImageResource(R.drawable.estado_1);
                        } else if (animal.getFiltro()==2) {
                            img_detalles_filtro.setImageResource(R.drawable.estado_2);
                        }else if (animal.getFiltro()==3) {
                            img_detalles_filtro.setImageResource(R.drawable.estado_3);
                        }else if (animal.getFiltro()==4) {
                            img_detalles_filtro.setImageResource(R.drawable.estado_4);
                        }else {
                            img_detalles_filtro.setImageResource(R.drawable.estado_5);
                        }
                        img_detalles_filtro.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int currentFiltro = animal.getFiltro();
                                int newFiltro = (currentFiltro % 5) + 1;
                                switch (newFiltro) {
                                    case 1:
                                        img_detalles_filtro.setImageResource(R.drawable.estado_1);
                                        break;
                                    case 2:
                                        img_detalles_filtro.setImageResource(R.drawable.estado_2);
                                        break;
                                    case 3:
                                        img_detalles_filtro.setImageResource(R.drawable.estado_3);
                                        break;
                                    case 4:
                                        img_detalles_filtro.setImageResource(R.drawable.estado_4);
                                        break;
                                    case 5:
                                        img_detalles_filtro.setImageResource(R.drawable.estado_5);
                                        break;
                                }
                                // Actualizar el valor del filtro en el objeto "animal"
                                animal.setFiltro(newFiltro);
                                actualizarFiltroEnFirestore(animal.getNombre(),newFiltro);
                            }
                        });
                        //====================================================================
                        if(animal.getEstado()==1){
                            txt_detalles_estado.setText("Refugio");
                        } else if (animal.getEstado()==2) {
                            txt_detalles_estado.setText("Acogida");
                        }else{
                            txt_detalles_estado.setText("Adoptado");
                        }

                        txt_detalles_estado.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int currentEstado = animal.getEstado();
                                int newEstado = (currentEstado % 3) + 1;

                                switch (newEstado) {
                                    case 1:
                                        txt_detalles_estado.setText("Refugio");
                                        break;
                                    case 2:
                                        txt_detalles_estado.setText("Acogida");
                                        break;
                                    case 3:
                                        txt_detalles_estado.setText("Adoptado");
                                        break;
                                }
                                animal.setEstado(newEstado);
                                actualizarEstadoEnFirestore(animal.getNombre(),newEstado);
                            }
                        });
                        //====================================================================
                        String imagePath = animal.getFotoUrl();
                        Context context = img_animal_detalles.getContext();
                        Glide.with(context).load(imagePath).into(img_animal_detalles);

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(detalles.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, lista.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
        finish();
    }


    private void actualizarFiltroEnFirestore(String nombre, int newFiltro) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference animalRef = db.collection("animales").document(nombre);
        animalRef.update("filtro", newFiltro);
    }
    private void actualizarEstadoEnFirestore(String nombre, int newEstado) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference animalRef = db.collection("animales").document(nombre);
        animalRef.update("estado", newEstado);
    }
}