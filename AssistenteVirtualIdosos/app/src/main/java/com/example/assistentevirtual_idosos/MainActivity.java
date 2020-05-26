package com.example.assistentevirtual_idosos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.microphone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catchSpeech();
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

        if (speech.toUpperCase().equals("PESQUISAR")) {
            openURL();
        }
        if (speech.toUpperCase().equals("TIRAR FOTO")) {
            Cameraopen();
        }

    }

    private void Cameraopen(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Toast.makeText(this, "Abrindo Câmera", Toast.LENGTH_LONG).show();
            startActivityForResult(takePictureIntent, 1);
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale naturalmente...");

        try {
            startActivityForResult(intent, 10);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Reconhecimento de voz não suportado", Toast.LENGTH_SHORT).show();
        }
    }
}







