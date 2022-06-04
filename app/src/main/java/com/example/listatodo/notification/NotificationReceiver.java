package com.example.listatodo.notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.listatodo.R;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "notify")
                .setSmallIcon(R.drawable.ic_baseline_add_24)
                .setContentTitle("Przypomnienie o zblizajacym sie zadaniu")
                .setContentText("Do roboty")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify((int) Math.floor(Math.random()*(200 +1)+0),notification.build());
    }
}
