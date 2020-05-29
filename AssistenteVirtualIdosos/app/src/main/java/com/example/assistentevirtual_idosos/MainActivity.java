package com.example.assistentevirtual_idosos;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);
        findViewById(R.id.microphone).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                catchSpeech();

            }
        });

         findViewById(R.id.icon3).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), list_view.class);
                 startActivity(intent);
             }
         });




    }




    @Override
    public void onDestroy(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10){

            if (resultCode == RESULT_OK && null != data) {

                ArrayList<String> result =
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                String speech = result.get(0);
                processMachineLearning(speech);
                
            }
        }
    }


    private void processMachineLearning(String speech) {

        if(speech.toUpperCase().contains("PESQUISAR")){
            openURL();
        }


    }



    private void openURL() {
        String URL = "http://www.google.com";

        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(URL));
        startActivity(intent);
    }

    private void catchSpeech() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Fale naturalmente...");

        try{
            startActivityForResult(intent, 10);
        }catch (ActivityNotFoundException a){
            Toast.makeText(this,"Reconhecimento de voz não suportado",Toast.LENGTH_SHORT).show();
        }
    }

}