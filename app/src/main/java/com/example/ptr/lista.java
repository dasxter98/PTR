package com.example.ptr;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class lista extends AppCompatActivity implements AnimalAdapter.OnItemClickListener {
    ImageView  patita,users, calendar,coins,chat;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ImageView back, list_mode,img_noAnimales;
    private boolean isListMode = true;
    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private List<animal> animalList;
    boolean isGridMode = false;
    EditText busqueda;
    boolean isFirstExecution=true;
    Spinner bt_filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        img_noAnimales = findViewById(R.id.img_noAnimales);
        recyclerView = findViewById(R.id.recyclerView);
        busqueda = findViewById(R.id.search_bar);
        back = findViewById(R.id.back);
        list_mode = findViewById(R.id.list_mode);
        bt_filtro = findViewById(R.id.bt_filtro);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        animalList = new ArrayList<>();
        adapter = new AnimalAdapter(animalList, storageReference);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        patita = (ImageView) findViewById(R.id.patita);
        users = (ImageView) findViewById(R.id.users);
        calendar = (ImageView) findViewById(R.id.calendar);
        coins = (ImageView) findViewById(R.id.coins);
        chat = (ImageView) findViewById(R.id.chat);

        //Botones============================================================================================
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lista.this, hub.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        list_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isListMode = !isListMode;
                if (isListMode) {
                    list_mode.setImageResource(R.drawable.list_h);
                    isGridMode = false;
                } else {
                    list_mode.setImageResource(R.drawable.list_grid);
                    isGridMode = true;
                }
                adapter.setGridMode(isGridMode);
                recyclerView.setLayoutManager(isGridMode ? new GridLayoutManager(lista.this, 2) : new LinearLayoutManager(lista.this));
            }
        });
        //Nav_bar============================================================================================
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lista.this, people.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lista.this, calendar.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);

            }
        });
        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lista.this, coins.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lista.this, chat.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        //====================================================================================================
        obtenerNuevosDatos();
        //Filtros===================================================================================================
        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String searchTerm = s.toString().trim();
                filtrarElementosPorBusqueda(searchTerm);
            }
        });
        int[] filtros = {R.drawable.filter, R.drawable.estado_1, R.drawable.estado_2, R.drawable.estado_3, R.drawable.estado_4, R.drawable.estado_5};
        filtroAdapter adapterF = new filtroAdapter(this, filtros);
        bt_filtro.setAdapter(adapterF);
        bt_filtro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private int currentPosition = 0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstExecution) {
                    Intent intent = getIntent();
                    position = intent.getIntExtra("filtro", 0);
                    Integer selectedImage = adapterF.getItem(position);
                    Drawable selectedDrawable = getResources().getDrawable(selectedImage);
                    bt_filtro.setBackground(selectedDrawable);
                    isFirstExecution = false;
                }
                if (position != currentPosition) {
                    Integer selectedImage = adapterF.getItem(position);
                    Drawable selectedDrawable = getResources().getDrawable(selectedImage);
                    bt_filtro.setBackground(selectedDrawable);
                    filtrarElementosPorFiltro(position);
                    currentPosition = position;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada en este caso
            }
        });

    }
    //FIREBASE THINGS=================================================================================================
    private void obtenerNuevosDatos() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (userId != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference animalesRef = db.collection("animales");
            Query query = animalesRef.whereEqualTo("UID_usuario", userId);
            query.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        animalList.clear();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            animal animal = documentSnapshot.toObject(animal.class);
                            animalList.add(animal);
                        }
                        adapter.notifyDataSetChanged();

                        if (animalList.size() == 0) {
                            Log.d("MiApp", "====================================Size: " + animalList.size());
                            img_noAnimales.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "No tienes animales aún", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("MiApp", "====================================Size: " + animalList.size());
                            img_noAnimales.setVisibility(View.INVISIBLE);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error al obtener los nuevos datos", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void filtrarElementosPorFiltro(int filtro) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference animalesRef = db.collection("animales");

        if (filtro != 0) {
            Query query = animalesRef.whereEqualTo("filtro", filtro).whereEqualTo("UID_usuario", userId);
            query.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        // Se ejecuta cuando la consulta es exitosa
                        List<animal> resultados = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Obtener los datos del documento y crear objetos Animal
                            animal animal = documentSnapshot.toObject(animal.class);
                            resultados.add(animal);
                        }
                        // Actualizar el adaptador con los resultados de búsqueda
                        adapter.actualizarDatos(resultados);
                    })
                    .addOnFailureListener(e -> {
                        // Se ejecuta cuando ocurre un error en la consulta
                        Toast.makeText(this, "Error al realizar la búsqueda", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Query query = animalesRef.whereEqualTo("UID_usuario", userId);
            query.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<animal> resultados = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Obtener los datos del documento y crear objetos Animal
                            animal animal = documentSnapshot.toObject(animal.class);
                            resultados.add(animal);
                        }
                        adapter.actualizarDatos(resultados);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error al realizar la búsqueda", Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void filtrarElementosPorBusqueda(String searchTerm) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference animalesRef = db.collection("animales");

        animalesRef.whereEqualTo("UID_usuario", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Se ejecuta cuando la consulta es exitosa
                    List<animal> resultados = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Obtener los datos del documento y crear objetos Animal
                        animal animal = documentSnapshot.toObject(animal.class);
                        // Comparar letra por letra en el campo "nombre"
                        if (esCoincidencia(animal.getNombre(), searchTerm)) {
                            resultados.add(animal);
                        }
                    }
                    // Actualizar el adaptador con los resultados de búsqueda
                    adapter.actualizarDatos(resultados);
                })
                .addOnFailureListener(e -> {
                    // Se ejecuta cuando ocurre un error en la consulta
                    Toast.makeText(this, "Error al realizar la búsqueda", Toast.LENGTH_SHORT).show();
                });
    }
    private boolean esCoincidencia(String texto, String searchTerm) {
        String textoLowerCase = texto.toLowerCase();
        String searchTermLowerCase = searchTerm.toLowerCase();

        for (int i = 0; i < searchTermLowerCase.length(); i++) {
            if (textoLowerCase.length() <= i || textoLowerCase.charAt(i) != searchTermLowerCase.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onItemClick(View itemView, int position){
        if (position >= 0 && position < animalList.size()) {
            animal animal = adapter.getItem(position);
            if (adapter.isFiltered()) {
                int originalPosition = adapter.getOriginalPosition(position);
                animal = adapter.getItem(originalPosition);
            }

            Intent intent = new Intent(itemView.getContext(), detalles.class);
            intent.putExtra("nombre", animal.getNombre());
            itemView.getContext().startActivity(intent);
        }
    }

    //========================================================================================================

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, hub.class);
        startActivity(intent);
        finish();
    }
}