package com.example.playandroid.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.playandroid.R;
import com.example.playandroid.acticle.ArticleDetailActivity;

import static com.example.playandroid.util.Constants.ArticleDetailConstant.TITLE;
import static com.example.playandroid.util.Constants.ArticleDetailConstant.URL;
import static com.example.playandroid.util.Constants.NotificationConstant.CHANNEL_ID;
import static com.example.playandroid.util.Constants.NotificationConstant.CHANNEL_NAME;

/**
 * AlarmManager发出的广播的接收者.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager mManager;

    private void channelHelper(Context context) {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mNotificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW);
            if (mManager != null) {
                mManager.createNotificationChannel(mNotificationChannel);
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        channelHelper(context);
        Notification.Builder builder;
        //若安卓版本大于8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
            builder.setPriority(Notification.PRIORITY_LOW);
        }
        Intent detailIntent = new Intent(context, ArticleDetailActivity.class);
        detailIntent.putExtra(TITLE,"LiveData 数据倒灌：别问，问就是不可预期");
        detailIntent.putExtra(URL,"https://juejin.im/post/5f0e7932e51d4534c36d9c01");
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,detailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle("测试");
        builder.setContentText("这是一条测试的通知");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        mManager.notify(1, builder.build());
        
    }
}
