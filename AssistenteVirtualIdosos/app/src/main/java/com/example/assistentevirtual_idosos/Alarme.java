package com.example.assistentevirtual_idosos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Alarme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);
        findViewById(R.id.microphone).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                catchSpeech();
            }
        });

    }

    public static void main(String[] args){
        Map<String,String> map = new HashMap<>();
        map.put("segunda-feira", "MONDAY");
        map.put("terça-feira", "TUESDAY");
        map.put("quarta-feira", "WEDNESDAY");
        map.put("quinta-feira", "THURSDAY");
        map.put("sexta-feira", "FRIDAY");
        map.put("sábado", "SATURDAY");
        map.put("domingo", "SUNDAY");
        

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


        if (speech.toUpperCase().contains("TITULO")) {


        }

        if (speech.toUpperCase().contains("HORÁRIO")) {


        }

        if (speech.toUpperCase().contains("DIAS DA SEMANA ")) {


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
