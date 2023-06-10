package com.example.ptr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class crear extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private int currentFiltro = 1;
    private int currentEstado = 1;
    private boolean genero,chip,vacuna = false;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION =123 ;
    private ImageView foto,bt_camara,bt_upload,bt_back,img_crear_filtro,img_crear_genero,bt_guardar,img_crear_vacuna,img_crear_chip;
    private EditText edt_nombre_crear, edt_edad_crear,edt_peso_crear;
    private TextView txt_estado_crear,txt_genero_crear,txt_crear_chip,txt_crear_vacuna;
    private String currentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        //=============================================================================================================================================
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        obtenerDetallesAnimal(nombre);
        //=============================================================================================================================================
        bt_guardar= (ImageView) findViewById(R.id.bt_guardar);
        img_crear_vacuna= (ImageView) findViewById(R.id.img_crear_vacuna);
        img_crear_chip= (ImageView) findViewById(R.id.img_crear_chip);
        img_crear_filtro= (ImageView) findViewById(R.id.img_crear_filtro);
        img_crear_genero= (ImageView) findViewById(R.id.img_crear_genero);
        edt_nombre_crear= (EditText) findViewById(R.id.edt_nombre_crear);
        edt_edad_crear= (EditText) findViewById(R.id.edt_edad_crear);
        edt_peso_crear= (EditText) findViewById(R.id.edt_peso_crear);
        txt_estado_crear = (TextView) findViewById(R.id.txt_estado_crear);
        txt_genero_crear = (TextView) findViewById(R.id.txt_genero_crear);
        txt_crear_chip = (TextView) findViewById(R.id.txt_crear_chip);
        txt_crear_vacuna = (TextView) findViewById(R.id.txt_crear_vacuna);
        bt_upload = (ImageView)findViewById(R.id.bt_upload);
        bt_camara = (ImageView)findViewById(R.id.bt_camara);
        foto= (ImageView) findViewById(R.id.foto);
        bt_back = (ImageView)findViewById(R.id.bt_rotate);
        //=============================================================================================================================================
        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreAnimal = String.valueOf(edt_nombre_crear.getText());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference animalRef = db.collection("animales").document(nombreAnimal);

                // Verificar si el animal ya existe
                animalRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // El animal ya existe, ejecutar operación de actualización
                                actualizarAnimal(nombreAnimal, vacuna, chip, genero, Integer.parseInt(String.valueOf(edt_edad_crear.getText())), Integer.parseInt(String.valueOf(edt_peso_crear.getText())), currentEstado, currentFiltro, foto);
                            } else {
                                // El animal no existe, ejecutar operación de añadir
                                añadirAnimal(nombreAnimal, vacuna, chip, genero, Integer.parseInt(String.valueOf(edt_edad_crear.getText())), Integer.parseInt(String.valueOf(edt_peso_crear.getText())), currentEstado, currentFiltro, foto);
                            }
                        } else {
                            // Error al obtener el documento, manejar el error según sea necesario
                        }
                    }
                });
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(crear.this, hub.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
            }
        });
        bt_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(crear.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Crear un archivo temporal para guardar la imagen capturada
                    File photoFile = createImageFile();
                    if (photoFile != null) {
                        Uri photoUri = FileProvider.getUriForFile(crear.this, "com.example.android.fileprovider", photoFile);
                        currentPhotoPath = photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        takePictureLauncher.launch(takePictureIntent);
                    }
                } else {
                    ActivityCompat.requestPermissions(crear.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
            }
        });
        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(crear.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(crear.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Si tienes permisos, crea el intent para seleccionar una imagen de la galería
                    Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickImageLauncher.launch(pickImageIntent);
                } else {
                    // Si no tienes permisos, solicítalos
                    ActivityCompat.requestPermissions(crear.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
                }
            }
        });
        //=============================================================================================================================================
        img_crear_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newFiltro = (currentFiltro % 5) + 1;
                switch (newFiltro) {
                    case 1:
                        img_crear_filtro.setImageResource(R.drawable.estado_1);
                        break;
                    case 2:
                        img_crear_filtro.setImageResource(R.drawable.estado_2);
                        break;
                    case 3:
                        img_crear_filtro.setImageResource(R.drawable.estado_3);
                        break;
                    case 4:
                        img_crear_filtro.setImageResource(R.drawable.estado_4);
                        break;
                    case 5:
                        img_crear_filtro.setImageResource(R.drawable.estado_5);
                        break;
                }
                currentFiltro = newFiltro;
            }
        });
        //===============================================
        if(currentEstado==1){
            txt_estado_crear.setText("Refugio");
        } else if (currentEstado==2) {
            txt_estado_crear.setText("Acogida");
        }else{
            txt_estado_crear.setText("Adoptado");
        }
        txt_estado_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newEstado = (currentEstado % 3) + 1;

                switch (newEstado) {
                    case 1:
                        txt_estado_crear.setText("Refugio");
                        break;
                    case 2:
                        txt_estado_crear.setText("Acogida");
                        break;
                    case 3:
                        txt_estado_crear.setText("Adoptado");
                        break;
                }
                currentEstado = newEstado;
            }
        });
        //==============================================
        txt_crear_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chip = !chip;
                if (chip) {
                    txt_crear_chip.setText("Chip: Sí");
                } else {
                    txt_crear_chip.setText("Chip: No");
                }
            }
        });
        img_crear_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chip = !chip;
                if (chip) {
                    txt_crear_chip.setText("Chip: Sí");
                } else {
                    txt_crear_chip.setText("Chip: No");
                }
            }
        });
        //===============================================
        img_crear_vacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacuna = !vacuna;
                if (vacuna) {
                    txt_crear_vacuna.setText("Vacuna: Sí");
                } else {
                    txt_crear_vacuna.setText("Vacuna: No");
                }
            }
        });
        txt_crear_vacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacuna = !vacuna;
                if (vacuna) {
                    txt_crear_vacuna.setText("Vacuna: Sí");
                } else {
                    txt_crear_vacuna.setText("Vacuna: No");
                }
            }
        });
        //===============================================
        img_crear_genero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genero = !genero;
                if (genero) {
                    txt_genero_crear.setText("macho");
                    img_crear_genero.setImageResource(R.drawable.male);

                } else {
                    txt_genero_crear.setText("hembra");
                    img_crear_genero.setImageResource(R.drawable.female);
                }
            }
        });
        //=============================================================================================================================================
    }
    //=============================================================================================================================================
    public void añadirAnimal(String nombre, boolean vacuna, boolean chip, boolean genero, int edad, int peso, int estado, int filtro, ImageView foto) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference animalRef = db.collection("animales").document(nombre);

        // Obtener la URL de la foto desde el ImageView
        subirImagenAFirebase(foto, nombre, imageUrl -> {
            // Aquí puedes utilizar la URL de la imagen para añadirla al animal
            Map<String, Object> animalData = new HashMap<>();
            animalData.put("nombre", nombre);
            animalData.put("vacuna", vacuna);
            animalData.put("chip", chip);
            animalData.put("genero", genero);
            animalData.put("edad", edad);
            animalData.put("peso", peso);
            animalData.put("estado", estado);
            animalData.put("filtro", filtro);
            animalData.put("fotoUrl", imageUrl);

            animalRef.set(animalData)
                    .addOnSuccessListener(aVoid -> {
                        // Éxito al añadir el animal a Firebase
                        Toast.makeText(crear.this, "Animal añadido correctamente", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(crear.this, detalles.class);
                        intent.putExtra("nombre", nombre);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // Error al añadir el animal a Firebase
                        Toast.makeText(crear.this, "Error al añadir el animal", Toast.LENGTH_SHORT).show();
                    });
        });
    }
    public void actualizarAnimal(String nombre, boolean vacuna, boolean chip, boolean genero, int edad, int peso, int estado, int filtro, ImageView foto) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference animalRef = db.collection("animales").document(nombre);

        // Obtener la URL de la foto desde el ImageView
        subirImagenAFirebase(foto, nombre, imageUrl -> {
            // Aquí puedes utilizar la URL de la imagen para actualizar el animal
            Map<String, Object> animalData = new HashMap<>();
            animalData.put("nombre", nombre);
            animalData.put("vacuna", vacuna);
            animalData.put("chip", chip);
            animalData.put("genero", genero);
            animalData.put("edad", edad);
            animalData.put("peso", peso);
            animalData.put("estado", estado);
            animalData.put("filtro", filtro);
            animalData.put("fotoUrl", imageUrl);

            animalRef.set(animalData)
                    .addOnSuccessListener(aVoid -> {
                        // Éxito al actualizar el animal en Firebase
                        Toast.makeText(crear.this, "Animal actualizado correctamente", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(crear.this, detalles.class);
                        intent.putExtra("nombre", nombre);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // Error al actualizar el animal en Firebase
                        Toast.makeText(crear.this, "Error al actualizar el animal", Toast.LENGTH_SHORT).show();
                    });
        });
    }
    private void subirImagenAFirebase(ImageView foto, String nombreImagen, OnImageUrlGeneratedListener listener) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("fotos/" + nombreImagen + ".jpg");

        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                listener.onImageUrlGenerated(imageUrl);
            });
        }).addOnFailureListener(e -> {
        });
    }
    interface OnImageUrlGeneratedListener {
        void onImageUrlGenerated(String imageUrl);
    }
    //=============================================================================================================================================
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //=============================================================================================================================================
    private ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == crear.RESULT_OK) {
                    // Obtener la ruta del archivo temporal
                    File photoFile = new File(currentPhotoPath);

                    // Redimensionar la imagen capturada
                    Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    foto.setImageBitmap(imageBitmap);
                }
            }
    );
    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == crear.RESULT_OK) {
                    Uri selectedImageUri = result.getData().getData();
                    String imagePath = getRealPathFromUri(selectedImageUri);

                    // Decodificar la imagen con un tamaño reducido
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath, options);

                    // Redimensionar la imagen seleccionada
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, foto.getWidth(), foto.getHeight(), false);

                    foto.setImageBitmap(resizedBitmap);
                }
            }
    );
    public String getRealPathFromUri(Uri uri) {
        String filePath;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            filePath = uri.getPath();
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }
    //=============================================================================================================================================
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, hub.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_animation, R.anim.fade_animation);
        finish();
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
                        edt_nombre_crear.setText(animal.getNombre());
                        //===============================================
                        edt_edad_crear.setText(String.valueOf(animal.getEdad()));
                        //===============================================
                        if(animal.getChip()) {
                            txt_crear_chip.setText("Chip: Sí");
                        }else {
                            txt_crear_chip.setText("Chip: No");
                        }
                        //===============================================
                        if(animal.getVacuna()) {
                            txt_crear_vacuna.setText("Vacunas: Sí");
                        }else {
                            txt_crear_vacuna.setText("Vacunas: No");
                        }
                        //===============================================
                        edt_peso_crear.setText(String.valueOf(animal.getPeso()));
                        //===============================================
                        if(animal.getGenero()) {
                            img_crear_genero.setImageResource(R.drawable.male);
                            txt_genero_crear.setText("macho");
                        }else {
                            img_crear_genero.setImageResource(R.drawable.female);
                            txt_genero_crear.setText("hembra");
                        }
                        //===============================================
                        if(animal.getFiltro()==1){
                            img_crear_filtro.setImageResource(R.drawable.estado_1);
                        } else if (animal.getFiltro()==2) {
                            img_crear_filtro.setImageResource(R.drawable.estado_2);
                        }else if (animal.getFiltro()==3) {
                            img_crear_filtro.setImageResource(R.drawable.estado_3);
                        }else if (animal.getFiltro()==4) {
                            img_crear_filtro.setImageResource(R.drawable.estado_4);
                        }else {
                            img_crear_filtro.setImageResource(R.drawable.estado_5);
                        }
                        //====================================================================
                        if(animal.getEstado()==1){
                            txt_estado_crear.setText("Refugio");
                        } else if (animal.getEstado()==2) {
                            txt_estado_crear.setText("Acogida");
                        }else{
                            txt_estado_crear.setText("Adoptado");
                        }
                        //====================================================================
                        String imagePath = animal.getFotoUrl();
                        Context context = foto.getContext();
                        Glide.with(context).load(imagePath).into(foto);
                    }
                }
            }
        });
    }
}