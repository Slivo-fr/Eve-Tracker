package fr.slivo.evetracker.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import fr.slivo.evetracker.api.ServiceApi;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, ServiceApi.class);
        context.startService(i);
    }

    public static void setAlarm (Context context) {

        Log.d("AlarmReceiver", "Set Alarm");
        Intent intent = new Intent(context, AlarmReceiver.class);

        final PendingIntent pIntent = PendingIntent.getBroadcast(
            context,
            123456789,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis()+ AlarmManager.INTERVAL_FIFTEEN_MINUTES / 10,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pIntent
        );
    }
}
