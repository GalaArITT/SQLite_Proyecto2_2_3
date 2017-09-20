package com.example.oliver.sqlite_proyecto2_2_3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Actualizar extends AppCompatActivity {

    TextView registro;
    EditText nombre, celular, correo;
    Button actualizar;
    DBAdapter bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        registro = (TextView) findViewById(R.id.textView);
        nombre = (EditText) findViewById(R.id.txt_nombre);
        celular = (EditText) findViewById(R.id.txt_cel);
        correo = (EditText) findViewById(R.id.txt_correo);
        actualizar = (Button) findViewById(R.id.btn_actualizar);

        bd=new DBAdapter(this);
        int id=0;

        registro.setText("Actualizar este contacto");

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            id = parametros.getInt("id");
        }

        bd.open();
        Cursor result=bd.getContact(id);
        bd.close();
        String name="",email="",phone="";
        result.moveToFirst();
        while (!result.isAfterLast()) {
            name=result.getString(1);
            email=result.getString(2);
            phone=result.getString(3);
            result.moveToNext();
        }
        result.close();

        final int auxid=id;
        nombre.setText(name);
        celular.setText(phone);
        correo.setText(email);

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=nombre.getText().toString();
                String t=celular.getText().toString();
                String c=correo.getText().toString();

                bd.open();
                bd.updateContact(auxid,n,c,t);
                bd.close();
                //updateContact(long rowId, String name, String email, String phone)
                Toast.makeText(getApplicationContext(),"Actualizado",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Actualizar.this, MainActivity.class));
            }
        });
    }
}
