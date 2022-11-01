package com.example.a05_recyclerviewyalertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.a05_recyclerviewyalertdialog.adapters.ToDoAdapter;
import com.example.a05_recyclerviewyalertdialog.configs.Constantes;
import com.example.a05_recyclerviewyalertdialog.modelos.ToDo;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import com.example.a05_recyclerviewyalertdialog.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private ArrayList<ToDo> toDoList;
    private ToDoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ActivityResultLauncher<Intent> editTodoLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        toDoList = new ArrayList<>();

        inicializaLaunchers();

        // crearToDos();


        adapter = new ToDoAdapter(toDoList, R.layout.todo_view_model, MainActivity.this, editTodoLauncher);
        binding.contentMain.contenedor.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        //layoutManager = new GridLayoutManager(MainActivity.this, 2);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo("Nueva Tarea").show();
            }
        });
    }

    private void inicializaLaunchers() {
        editTodoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                if (result.getData().getExtras() != null && result.getData().getExtras().getSerializable(Constantes.TODO) != null) {
                                    ToDo todo = (ToDo) result.getData().getExtras().getSerializable(Constantes.TODO);
                                    int posicion = result.getData().getExtras().getInt(Constantes.POSICION);
                                    toDoList.set(posicion, todo);
                                    adapter.notifyItemChanged(posicion);
                                    Toast.makeText(MainActivity.this, "CAMBIATO", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
    }

    private AlertDialog createToDo(String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(titulo);

        View contenido = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_todo_alert_dialog, null);
        EditText txtTitulo = contenido.findViewById(R.id.txtTituloAddToDo);
        EditText txtContenido = contenido.findViewById(R.id.txtContenidoAddToDo);

        builder.setView(contenido);
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToDo todo = new ToDo(txtTitulo.getText().toString(), txtContenido.getText().toString());
                toDoList.add(todo);
                adapter.notifyDataSetChanged();
            }
        });
        return builder.create();

    }

    private void crearToDos() {
        for (int i = 0; i < 1000; i++) {
            toDoList.add(new ToDo("Titúlo " + i, "Contenido " + i));
        }
    }

}