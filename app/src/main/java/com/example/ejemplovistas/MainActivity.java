package com.example.ejemplovistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter adapter;
    ArrayList<String> listaDias;
    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list);
        sv = (SearchView) findViewById(R.id.search);
        listaDias = new ArrayList<String>();
        listaDias.addAll(Arrays.asList(getResources().getStringArray(R.array.lista_dias)));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDias);
        lv.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        Button btnTextureView = findViewById(R.id.btn_texture_view);

        btnTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), texture_view.class);
                startActivity(intent);
            }
        });
    }


}