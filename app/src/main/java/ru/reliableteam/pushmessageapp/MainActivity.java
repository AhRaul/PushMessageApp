package ru.reliableteam.pushmessageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;             //разделение уведомлений
    private static final String CHANNEL_ID = "CHANNEL_ID";
    Button summNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        summNotification = findViewById(R.id.summNotification);

        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        summNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //построитель уведомлений
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)        //базовая иконка андроид
                        .setWhen(System.currentTimeMillis())                    //передача текущего времени
                        .setContentIntent(pendingIntent)                        //отложенный интент с флагом обновления
                        .setContentTitle("Заголовок")                           //заголовок уведомления
                        .setContentText("Какой то текст...........")            //текст самого уведомления
                        .setPriority(PRIORITY_HIGH);                            //приоритет, настраивается

                createChannelIfNeeded(notificationManager);                     //для работы с более мпоздники версиями андроид
                notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
            }
        });
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){     //при использовании версии выше, чем OREO (api-26)
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
