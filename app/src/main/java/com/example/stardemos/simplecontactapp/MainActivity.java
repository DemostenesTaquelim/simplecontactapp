package com.example.stardemos.simplecontactapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String>contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<String>();
        contacts.add("Bruno Ribeiro | 123456789");
        contacts.add("rui | 1234567890");
        contacts.add("pp | 098765432");
        // busca referencia
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, contacts);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter2);
    }

    public void onClick_search(View view) {

        //ir buscar referência para edittext, spinner e listview

        EditText et = (EditText) findViewById(R.id.editText_search);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ListView lv = (ListView) findViewById(R.id.listView);


        //pesquisar o termo nos contactos e guardar o resultado da pesquisa numa lista

        String termo = et.getText().toString();

        if (termo.equals("")) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, contacts);

            lv.setAdapter(adapter);
            Toast.makeText(MainActivity.this, "Showing all contacts.", Toast.LENGTH_SHORT).show();

        } else {
            String itemSeleccionado = sp.getSelectedItem().toString();


            ArrayList<String> resultados = new ArrayList<>();
            if (itemSeleccionado.equals("All")) {
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);
                    boolean contem = c.contains(termo);

                    if (contem)
                        resultados.add(c);

                    //mostrar na listview a listview nova que contém o resultado da pesquisa
                }
            } else if (itemSeleccionado.equals("Name")) {
                //codigo da pesquisa nome
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);
                    String[] s = c.split("\\|");
                    String name = s[0];
                    boolean contem = name.contains(termo);
                    if (contem)
                        resultados.add(c);
                }
            } else if (itemSeleccionado.equals("Phone")) {
                // codigo da pesquisa phone
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);
                    String[] s = c.split("\\|");
                    String number = s[1];
                    boolean contem = number.contains(termo);
                    if (contem)
                        resultados.add(c);
                }
            }

            boolean vazia = resultados.isEmpty();

            if (vazia == true) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, contacts);

                lv.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "No results found. Showing all contacts.", Toast.LENGTH_SHORT).show();
            } else {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, resultados);

                lv.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "Showing all results.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void onClick_add(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.addcontact, null));
        // Add action buttons
        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //vai buscar as edittext
                AlertDialog al = (AlertDialog) dialog;
                EditText etName = (EditText) al.findViewById (R.id.edittext_Name);
                EditText etPhone = (EditText) al.findViewById (R.id.edittext_Phone);
                //ir buscar as strings
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                //criar contacto
                String contact = name + "|" + phone;
                //addicionar contacto
                contacts.add(contact);
                //mostrar contacto na lista
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, contacts);
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "Contacto gravado", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog

                Toast.makeText(MainActivity.this, "Contacto foi de vela", Toast.LENGTH_SHORT).show();
            }
        });
// Set other dialog properties
        builder.setTitle("New contact");
        builder.setMessage("Enter new contact");



// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
