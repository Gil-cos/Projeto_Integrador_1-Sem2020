package com.example.assistentevirtual_idosos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class Contato extends AppCompatActivity {

    private EditText nome;
    private EditText numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        nome = findViewById(R.id.adicionar_nome);
        numero = findViewById(R.id.adicionar_numero);

        findViewById(R.id.mic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catchSpeech();
            }
        });

        findViewById(R.id.adicionar_contato).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {

            if (resultCode == RESULT_OK && null != data) {

                ArrayList<String> result =
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                String speech = result.get(0);
                processMachineLearning(speech);

            }
        }
    }

    private void processMachineLearning(String speech) {
        String valores[];

        if (speech.toUpperCase().contains("NOME")) {
            valores = speech.split(" ");
            nome.setText(valores[1]);
            return;
        }

        if (speech.toUpperCase().contains("NÚMERO")) {
            valores = speech.split(" ");
            numero.setText(valores[1]);
            return;
        }

        else{
            Toast.makeText(this, "Funcionalidade não existente, diga o nome e número do contato.", Toast.LENGTH_LONG).show();
        }
    }

    private void addContact(){
        String name = null;

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            Toast.makeText(this, "Contato adicionado.", Toast.LENGTH_LONG).show();
        }
    }

    private void catchSpeech() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale naturalmente...");

        try {
            startActivityForResult(intent, 10);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Reconhecimento de voz não suportado", Toast.LENGTH_SHORT).show();
        }
    }
}