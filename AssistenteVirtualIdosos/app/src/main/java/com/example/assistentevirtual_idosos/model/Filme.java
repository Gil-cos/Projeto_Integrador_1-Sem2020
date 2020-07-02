package com.example.assistentevirtual_idosos.model;

import android.support.annotation.NonNull;

public class Filme {

    private String filme;

    public String getFilme() {
        return filme;
    }

    public void setFilme(String filme) {
        this.filme = filme;
    }

    @NonNull
    @Override
    public String toString() {
        return getFilme();
    }
}