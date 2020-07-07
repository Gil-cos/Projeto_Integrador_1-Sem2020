package com.example.assistentevirtual_idosos.service;

import android.os.AsyncTask;
import com.example.assistentevirtual_idosos.model.Filme;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HTTPService extends AsyncTask<Void, Void, Filme> {

    @Override
    protected Filme doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        try {
            URL url = new URL("https://r4u.herokuapp.com/getFilme/8");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()){
                resposta.append(scanner.nextline());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(resposta.toString(), Filme.class);
    }
}