package com.example.assistentevirtual_idosos;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String phoneNumber = "192";
    private WifiManager wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        findViewById(R.id.microphone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catchSpeech();
            }
        });
        findViewById(R.id.icon3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), View_pager.class);
                startActivity(intent);
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
            searchGoogle();
            return;
        }

        if (speech.toUpperCase().equals("AJUDA")) {
            callSOS();
            return;
        }

        if (speech.toUpperCase().equals("TIRAR FOTO")) {
            openCamera();
            return;
        }

        if (speech.toUpperCase().equals("ATIVAR BLUETOOTH")){
            enableBluetooth();
            return;
        }

        if (speech.toUpperCase().equals("DESATIVAR BLUETOOTH")){
            disableBluetooth();
            return;
        }

        if(speech.toUpperCase().equals("ATIVAR INTERNET")){
            enableWifi();
            return;
        }

        if (speech.toUpperCase().equals("DESATIVAR INTERNET")) {
            disableWifi();
            return;
        }

        if(speech.toUpperCase().equals("ADICIONAR ALARME")){
            openAlarm();
            return;
        }
        if(speech.toUpperCase().equals("ADICIONAR EVENTO")){
            openEvent();
        }

        else{
            Toast.makeText(this, "Funcionalidade não existente, veja a lista de funções no ícone abaixo do microfone.", Toast.LENGTH_LONG).show();
        }
    }

    private void callSOS() {
        //Método para ligar para Emergência
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
            Toast.makeText(this, "Chamando a Emergência", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }

    private void openCamera(){
        //Método para abrir a câmera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Toast.makeText(this, "Abrindo Câmera", Toast.LENGTH_LONG).show();
            startActivityForResult(takePictureIntent, 1);
        }
    }

    private void searchGoogle() {
        //Método para realizar pesquisas
        Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak please");
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Reconhecimento de Voz para Pesquisa Aberto" +  "\n\nDiga sua Pesquisa", Toast.LENGTH_LONG).show();
    }

    private void enableBluetooth() {
        //Comando para ativar o bluetooth
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

    private void openAlarm() {
        Intent intent = new Intent(this, Alarme.class);
        startActivity(intent);
    }
    private void openEvent(){
        Intent intent = new Intent(this, activityEvent.class);
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