package ru.reliableteam.pushmessageapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class Notificator {

    private static final int NOTIFY_ID = 1;             //разделение уведомлений
    private static final String CHANNEL_ID = "CHANNEL_ID";

    private NotificationManager notificationManager;
    private Context context;

    public Notificator(Context context) {
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.context = context;
    }


    /**
     * When the method "createNotification(....)" is called,
     * the message is displayed in the notification line.
     * (ru: Когда вызывается метод «createNotification (....)»,
     * сообщение отображается в строке уведомлений.)
     *
     * if the "notifyId" of the displayed notification matches the "notifyId" of the new,
     * the displayed message will be replaced by the new one.
     * (ru: если "notifyId" у выведенного уведомления совпадет с "notifyId" у нового,
     * то выведенное сообщение будет заменено новым.)
     *
     * @param message   notification message
     * @param title     notification title
     * @param notifyId  notification id to separate alerts
     */
    public void createNotification(String message, String title, int notifyId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //построитель уведомлений
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)        //базовая иконка андроид
                        .setWhen(System.currentTimeMillis())                    //передача текущего времени
                        .setContentIntent(pendingIntent)                        //отложенный интент с флагом обновления
                        .setContentTitle(title)                           //заголовок уведомления
                        .setContentText(message)            //текст самого уведомления
                        .setPriority(PRIORITY_HIGH);                            //приоритет, настраивается

        createChannelIfNeeded(notificationManager);                     //для работы с более поздники версиями андроид
        notificationManager.notify(notifyId, notificationBuilder.build());
    }

    //создание канала для разных версий api
    public void createChannelIfNeeded(NotificationManager manager) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){     //при использовании версии выше, чем OREO (api-26)
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
