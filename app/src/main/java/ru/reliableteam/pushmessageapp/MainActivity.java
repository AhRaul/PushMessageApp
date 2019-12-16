package ru.reliableteam.pushmessageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bSummonNotification;
    Button bSummonNotification2;

    //TODO необходимо создать Notificator
    Notificator notificator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bSummonNotification = findViewById(R.id.bSummonNotification);
        bSummonNotification2 = findViewById(R.id.bSummonNotification2);

        //TODO необходимо инициализировать Notificator
        notificator = new Notificator(getApplicationContext());

        bSummonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO вызов уведомления
                notificator.createNotification("Какой то текст...........", "Заголовок", 1);
            }
        });

        bSummonNotification2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO вызов уведомления
                notificator.createNotification("Другой текст...........", "Другой заголовок", 2);
            }
        });
    }


}
