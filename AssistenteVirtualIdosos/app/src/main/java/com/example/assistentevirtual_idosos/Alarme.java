package com.example.assistentevirtual_idosos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.AlarmClock;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Alarme extends AppCompatActivity {
   private EditText titulo;
   private EditText diasSemana;
   private EditText horario;
   private String array_hour[];
   private String valoresDias[];

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

        titulo = findViewById(R.id.editText9);
        diasSemana = findViewById(R.id.editText11);
        horario = findViewById(R.id.editText10);
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


        if (speech.toUpperCase().contains("TÍTULO")) {
            valores = speech.split(" ");
            String title = "";
            for (int i = 1;i < valores.length;i++){
                title += valores[i] + " ";
            }
            titulo.setText(title);
            return;
        }

        if (speech.toUpperCase().contains("HORÁRIO")) {
            valores = speech.split(" ");
            horario.setText(valores[1]);
            return;
        }

        if (speech.toUpperCase().contains("DIAS")) {
            valoresDias = speech.split(" ");
            String dias = "";
            for (int i = 1; i < valoresDias.length; i++){
                dias += valoresDias[i] + " ";
            }
            diasSemana.setText(dias);
            return;

        }
        if (speech.toUpperCase().contains("ADICIONAR")){
            createAlarm();
            return;
        }
        else{
            Toast.makeText(this, "Informe um dia, horário e um título", Toast.LENGTH_LONG).show();
        }
    }

    private void createAlarm() {
        Map<String,Integer> map = new HashMap<>();
        map.put("segunda", Calendar.MONDAY);
        map.put("terça", Calendar.TUESDAY);
        map.put("quarta", Calendar.WEDNESDAY);
        map.put("quinta", Calendar.THURSDAY);
        map.put("sexta", Calendar.FRIDAY);
        map.put("sábado", Calendar.SATURDAY);
        map.put("domingo", Calendar.SUNDAY);

        ArrayList<Integer> days = new ArrayList<>();
        for (int i = 1; i < valoresDias.length; i++){
            days.add(map.get(valoresDias[i]));
        }

        array_hour = horario.getText().toString().split(":");

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, titulo.getText().toString())
                .putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(array_hour[0]))
                .putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(array_hour[1]))
                .putExtra(AlarmClock.EXTRA_DAYS, days);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
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