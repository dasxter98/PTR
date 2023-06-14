package com.example.ptr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private OnItemClickListener onItemClickListener;
    private static final int VIEW_TYPE_LIST = 1;
    private static final int VIEW_TYPE_GRID = 2;
    public static List<animal> animalList , filteredList;
    private boolean isGridMode = false;
    private boolean isFiltered = false;

    public static StorageReference storageReference;

    public AnimalAdapter(List<animal> animalList, StorageReference storageReference) {
        this.animalList = animalList;
        this.storageReference = storageReference;
    }


    public void actualizarDatos(List<animal> nuevosDatos) {
        animalList = nuevosDatos;
        notifyDataSetChanged();
    }


    public boolean isFiltered() {
        return isFiltered;
    }

    public int getOriginalPosition(int filteredPosition) {
        if (filteredPosition >= 0 && filteredPosition < filteredList.size()) {
            animal filteredAnimal = filteredList.get(filteredPosition);

            return animalList.indexOf(filteredAnimal);
        }
        return RecyclerView.NO_POSITION;
    }

    public animal getItem(int position) {
        if (isFiltered) {
            return filteredList.get(position);
        } else {
            return animalList.get(position);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (isGridMode) {
            return VIEW_TYPE_GRID;
        } else {
            return VIEW_TYPE_LIST;
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_GRID) {
            View itemView = inflater.inflate(R.layout.item_animal_grid, parent, false);
            return new AnimalViewHolder(itemView);
        } else {
            View itemView = inflater.inflate(R.layout.item_animal_list, parent, false);
            return new AnimalViewHolder(itemView);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull final AnimalViewHolder holder, int position) {
        animal animal = animalList.get(position);
        holder.bind(animal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < animalList.size()) {
                        onItemClickListener.onItemClick(holder.itemView, adapterPosition);
                    }
                }
            }
        });
    }
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }
    public void setGridMode(boolean isGridMode) {
        this.isGridMode = isGridMode;
        notifyDataSetChanged();
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreTextView;
        private TextView edadTextView;
        private TextView chip;
        private TextView vacuna;
        private TextView peso;
        private TextView estado;
        private ImageView genero;
        private ImageView filtro;
        private ImageView foto_item;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.txt_item_nombre);
            edadTextView = itemView.findViewById(R.id.txt_item_edad);
            chip = itemView.findViewById(R.id.txt_chip);
            vacuna = itemView.findViewById(R.id.txt_vacuna);
            peso = itemView.findViewById(R.id.txt_item_peso);
            genero = itemView.findViewById(R.id.img_item_genero);
            estado = itemView.findViewById(R.id.txt_item_estado);
            filtro = itemView.findViewById(R.id.img_item_filtro);
            foto_item=itemView.findViewById(R.id.img_item_mascota);
        }


        public void bind(@NonNull animal animal) {
            //===============================================
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionRef = db.collection("animales");
            //===============================================
            nombreTextView.setText(animal.getNombre());
            //====================================================================
            if (animal.getEdad() >= 12) {
                int anos = animal.getEdad() / 12;
                if (anos < 2) {
                    edadTextView.setText(String.valueOf(anos + " años"));
                } else {
                    edadTextView.setText(String.valueOf(anos + " años"));
                }
            } else {
                edadTextView.setText(String.valueOf(animal.getEdad()) + " meses");
            }
            //====================================================================
            if (animal.getChip()) {
                chip.setText("Sí");
            } else {
                chip.setText("No");
            }
            //====================================================================
            if (animal.getVacuna()) {
                vacuna.setText("Sí");
            } else {
                vacuna.setText("No");
            }
            //====================================================================
            peso.setText(String.valueOf(animal.getPeso()) + " kg");
            //====================================================================
            if (animal.getGenero()) {
                genero.setImageResource(R.drawable.male);
            } else {
                genero.setImageResource(R.drawable.female);
            }
            //====================================================================
            if (animal.getFiltro() == 1) {
                filtro.setImageResource(R.drawable.estado_1);
            } else if (animal.getFiltro() == 2) {
                filtro.setImageResource(R.drawable.estado_2);
            } else if (animal.getFiltro() == 3) {
                filtro.setImageResource(R.drawable.estado_3);
            } else if (animal.getFiltro() == 4) {
                filtro.setImageResource(R.drawable.estado_4);
            } else {
                filtro.setImageResource(R.drawable.estado_5);
            }
            filtro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentFiltro = animal.getFiltro();
                    int newFiltro = (currentFiltro % 5) + 1;
                    switch (newFiltro) {
                        case 1:
                            filtro.setImageResource(R.drawable.estado_1);
                            break;
                        case 2:
                            filtro.setImageResource(R.drawable.estado_2);
                            break;
                        case 3:
                            filtro.setImageResource(R.drawable.estado_3);
                            break;
                        case 4:
                            filtro.setImageResource(R.drawable.estado_4);
                            break;
                        case 5:
                            filtro.setImageResource(R.drawable.estado_5);
                            break;
                    }
                    // Actualizar el valor del filtro en el objeto "animal"
                    animal.setFiltro(newFiltro);
                    actualizarFiltroEnFirestore(animal.getNombre(), newFiltro);


                }
            });
            //====================================================================
            if (animal.getEstado() == 1) {
                estado.setText("Refugio");
            } else if (animal.getEstado() == 2) {
                estado.setText("Acogida");
            } else {
                estado.setText("Adoptado");
            }
            estado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentEstado = animal.getEstado();
                    int newEstado = (currentEstado % 3) + 1;

                    switch (newEstado) {
                        case 1:
                            estado.setText("Refugio");
                            break;
                        case 2:
                            estado.setText("Acogida");
                            break;
                        case 3:
                            estado.setText("Adoptado");
                            break;
                    }
                    animal.setEstado(newEstado);
                    actualizarEstadoEnFirestore(animal.getNombre(), newEstado);

                }
            });
            //====================================================================
            String imagePath = animal.getFotoUrl();
            Context context = itemView.getContext();
            Glide.with(context).load(imagePath).into(foto_item);
            //====================================================================
        }
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