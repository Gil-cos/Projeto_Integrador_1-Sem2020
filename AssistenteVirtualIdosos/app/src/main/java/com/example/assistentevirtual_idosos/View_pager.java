package com.example.assistentevirtual_idosos;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class View_pager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        //Mencionar o item viewPager com o id do layout
        ViewPager viewPager = findViewById(R.id.viewPagery);

        //Configurar a adaptação da ViewPager

        viewPager.setAdapter(new CustomPageAdapter(this));


    }
}
