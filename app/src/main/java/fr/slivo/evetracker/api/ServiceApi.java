package fr.slivo.evetracker.api;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import fr.slivo.evetracker.R;
import fr.slivo.evetracker.storage.StorageManager;
import fr.slivo.evetracker.activities.MainActivity;
import fr.slivo.evetracker.entities.Account;
import fr.slivo.evetracker.entities.Character;
import fr.slivo.evetracker.entities.EveNotif;
import fr.slivo.evetracker.staticdata.NotificationData;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class ServiceApi extends IntentService {

    private NotificationManager mNotificationManager;
    public ServiceApi(){
        super("ServiceApi");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        task();
        Log.d("ServiceApi", "Service Running");
    }

    public void task(){
        iterateNotifications();
    }

    private void sendNotification(String notificationType, String notificationId, String senderName, String characterName) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, MainActivity.class),
                0
        );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                    .setContentTitle(characterName + " - " + notificationType)
                    .setContentText("From " + senderName);

        mBuilder.setContentIntent(contentIntent);

        mNotificationManager.notify(Integer.parseInt(notificationId), mBuilder.build());
    }

    protected void iterateNotifications() {

        Account account = new Account(StorageManager.getStoredApi(this.getApplicationContext()));
        account.retrieveCharacters();

        for(int i = 0 ; i < account.getListChar().size(); i++){

            Character character = account.getListChar().get(i);
            character.retrieveNotifications(account.getApi());

            for (int j = 0 ; j < character.getListNotif().size(); j++){

                EveNotif notification = character.getListNotif().get(j);
                Integer notificationType = Integer.parseInt(notification.getNotifType());
                String text;

                if(NotificationData.notificationTypes.containsKey(notificationType)){
                    text = NotificationData.notificationTypes.get(notificationType);
                } else {
                    text = "Unknown notification type : " + notification.getNotifType();
                }

                if (StorageManager.isNotificationNew(this,notification.getNotifId())) {

                    sendNotification(
                        text,
                        notification.getNotifId(),
                        notification.getSenderName(),
                        character.getName()
                    );

                    StorageManager.saveNotificationId(this,notification.getNotifId());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ServiceApi", "Service Destroyed");
    }
}