package com.example.oliver.sqlite_proyecto2_2_3;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    FloatingActionButton fab;
    DBAdapter bd;
    int idAux[];

    AdaptadorListView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.lista);
        fab=(FloatingActionButton)findViewById(R.id.floatingActionButton);

        activity_agregar();

        bd=new DBAdapter(getApplicationContext());
        llenarLista();

        eliminar();
        actualizar();

    }

    private void llenarLista(){
        bd.open();

        int n=bd.lengthQuery();
        String [] nombre = new String[n];
        String [] email = new String[n];
        String [] telefono = new String[n];
        int ids[] = new int[n];
        int i=0;
        Cursor result=bd.getAllContactsAZ();
        result.moveToFirst();
        while (!result.isAfterLast()) {
            int id=result.getInt(0);
            String name=result.getString(1);
            String email1=result.getString(2);
            String phone=result.getString(3);

            ids[i]=id;
            nombre[i]=name;
            email[i]=email1;
            telefono[i]=phone;

            i++;

            result.moveToNext();
        }
        result.close();
        idAux=ids;
        adapter = new AdaptadorListView(this, nombre,email,telefono);
        lista.setAdapter(adapter);
        bd.close();
    }

    public void activity_agregar(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Insert.class));
            }
        });
    }

    public void eliminar(){
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                bd.open();
                if(bd.deleteContact(idAux[pos])){
                    Toast.makeText(getApplicationContext(),"Eliminado",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
                bd.close();
                llenarLista();
                return true;
            }
        });
    }


    public void actualizar(){
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent intent = new Intent(MainActivity.this, Actualizar.class);
                intent.putExtra("id",idAux[i]);
                startActivity(intent);
            }
        });
    }
}