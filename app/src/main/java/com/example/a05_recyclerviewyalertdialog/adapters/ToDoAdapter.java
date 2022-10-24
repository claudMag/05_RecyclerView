package com.example.a05_recyclerviewyalertdialog.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a05_recyclerviewyalertdialog.MainActivity;
import com.example.a05_recyclerviewyalertdialog.R;
import com.example.a05_recyclerviewyalertdialog.modelos.ToDo;

import java.util.List;


//vh es de view holder
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVH> {

    //la unica clase que se relaciona con la pantalla --> activity

    private List<ToDo> objects;
    private int resource;
    private Context context;


    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    /**
     * ALGO !!! NO ME IMPORTA QUIEN llamará este método para crear una nueva fila
     * @param parent
     * @param viewType
     * @return Objeto ViewHolder
     */
    @NonNull
    @Override
    public ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(context).inflate(resource, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        todoView.setLayoutParams(lp);
        return new ToDoVH(todoView);
    }

    /**
     * A partir de un ViewHolder --> Asignar valores a los elementos
     * @param holder --> fila a configurar
     * @param position --> elemento de la lista a mostrar
     */
    @Override
    public void onBindViewHolder(@NonNull ToDoVH holder, int position) {
        ToDo todo = objects.get(position);
        holder.lblTitulo.setText(todo.getTitulo());
        holder.lblContenido.setText(todo.getContenido());
        holder.lblFecha.setText(todo.getFecha().toString());
        if (todo.isCompletado()) //si fuera una imagen mia seria R.drawable ecc..
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        else
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        //toda generacion de eventos va en este metodo

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               confirmaCambioEstado("Estás seguro de cambiar el estado?", todo).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        //retorna el numero de los elementos que hay que instanciar
        return objects.size();
    }


    private AlertDialog confirmaCambioEstado(String mensaje, ToDo toDo){
        //3 botones base: positivo, negativo, neutro. solo aparecer los que configuro

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mensaje);
        builder.setCancelable(false); //si el usuario toca fuera del mensaje, que se cierre.
        //siendo false, la unica forma de cancelar es toca uno de los botones

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }


    /**
     * Objeto que se instancia cada vez que tengo que mostrar un To-Do en el Recycler
     * solo se instancian los que caben en la pantalla + 1 o 2
     */
    public class ToDoVH extends RecyclerView.ViewHolder {

        TextView lblTitulo, lblContenido, lblFecha;
        ImageButton btnCompletado;

        public ToDoVH(@NonNull View itemView) {
            //constructor que recibe una vista, el layout del view model.
            super(itemView);
            lblTitulo = itemView.findViewById(R.id.lblTituloToDoModelView);
            lblContenido = itemView.findViewById(R.id.lblContenidoToDoModelView);
            lblFecha = itemView.findViewById(R.id.lblFechaToDoModelView);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoModelView);
        }
    }
}
