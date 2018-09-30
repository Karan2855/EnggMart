package com.example.user.enggmart;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public class MyNotificationManager {
    public static final int NOTIFICATION_ID = 234;
    private Context ctx;

    public MyNotificationManager(Context ctx) {
        this.ctx = ctx;
    }

    public void showNotification(String from, String notification, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
        Notification mNotification1 = builder.setSmallIcon(R.mipmap.logomainapp)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(from)
                .setContentText(notification)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.logomainapp))
                .build();
        mNotification1.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mNotification1);
    }
}
