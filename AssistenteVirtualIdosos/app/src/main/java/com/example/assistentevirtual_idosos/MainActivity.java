package com.example.assistentevirtual_idosos;

import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private WifiManager wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);
        findViewById(R.id.microphone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catchSpeech();
            }
        });
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

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

        if(speech.toUpperCase().equals("PESQUISAR")){
            openURL();
        }

        if (speech.toUpperCase().equals("ATIVAR BLUETOOTH")){

            enableBluetooth();
        }

        if (speech.toUpperCase().equals("DESATIVAR BLUETOOTH")){

            disableBluetooth();

        }

        if(speech.toUpperCase().equals("ATIVAR WI-FI")){

            enableWifi();

        }

        if (speech.toUpperCase().equals("DESATIVAR WI-FI")){

            disableWifi();

        }

    }

    private void openURL() {
        String URL = "http://www.google.com";

        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(URL));
        startActivity(intent);
    }



    private void enableBluetooth() {
        // Comando para ativar o bluetooth
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.enable();
        Toast.makeText(getApplicationContext(), "Bluetooth ativado", Toast.LENGTH_LONG).show();

    }

    private void disableBluetooth() {
        //Comando para desativar o bluetooth
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.disable();
        Toast.makeText(getApplicationContext(), "Bluetooth desativado", Toast.LENGTH_LONG).show();

    }

    private void enableWifi(){
        //Comando para ativar o wi-fi
        wifi.setWifiEnabled(true);
        Toast.makeText(getApplicationContext(), "Wi-fi ativado", Toast.LENGTH_LONG).show();

    }

    private void disableWifi(){
        //Comando para desativar o wi-fi
        wifi.setWifiEnabled(false);
        Toast.makeText(getApplicationContext(), "Wi-fi desativado", Toast.LENGTH_LONG).show();

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