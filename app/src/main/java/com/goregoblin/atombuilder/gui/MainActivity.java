package com.goregoblin.atombuilder.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.goregoblin.atombuilder.logic.MainActivityListener;
import com.goregoblin.atombuilder.R;

public class MainActivity extends AppCompatActivity {


    MainActivityListener mainActivityListener;

    Button btnNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnNew = findViewById(R.id.btnNew);

        mainActivityListener = new MainActivityListener(this);

        btnNew.setOnClickListener(mainActivityListener);

    }
}
