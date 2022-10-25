package com.example.a05_recyclerviewyalertdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.a05_recyclerviewyalertdialog.configs.Constantes;
import com.example.a05_recyclerviewyalertdialog.databinding.ActivityEditToDoBinding;
import com.example.a05_recyclerviewyalertdialog.modelos.ToDo;

public class EditToDoActivity extends AppCompatActivity {
    
    private ActivityEditToDoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditToDoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int posicion = bundle.getInt(Constantes.POSICION);
        ToDo todo = (ToDo) bundle.getSerializable(Constantes.TODO);

        binding.txtTituloEditToDo.setText(todo.getTitulo());
        binding.txtContenidoEditToDo.setText(todo.getContenido());

        binding.btnCancelarEditToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });


        binding.btnActualizarEditToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDo todoActualizado = crearToDo();

                if (todoActualizado != null) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.TODO, todoActualizado);
                    bundle.putInt(Constantes.POSICION, posicion);
                    intent.putExtras(bundle);
                    finish();
                } else {
                    Toast.makeText(EditToDoActivity.this, "Faltan datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.d("ToDo", todo.toString());
    }


    private ToDo crearToDo() {

        if (binding.txtTituloEditToDo.getText().toString().isEmpty() ||
            binding.txtContenidoEditToDo.getText().toString().isEmpty())
            return null;

        return new ToDo(binding.txtTituloEditToDo.getText().toString(),
                binding.txtContenidoEditToDo.getText().toString());
    }
}