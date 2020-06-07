package com.example.assistentevirtual_idosos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class Alarme extends AppCompatActivity {

    private CheckBox segunda,terça, quarta, quinta, sexta, sabado, domingo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);

        segunda = findViewById(R.id.segunda);
        terça = findViewById(R.id.terça);
        quarta = findViewById(R.id.quarta);
        quinta = findViewById(R.id.quinta);
        sexta = findViewById(R.id.sexta);
        sabado = findViewById(R.id.sabado);
        domingo = findViewById(R.id.domingo);


    }
    public void checkbox () {
        boolean teste = segunda.isChecked();


    }

}
