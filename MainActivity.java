package jp.ac.asojuku.st.batterywarning;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected  void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                if (level <= 15) {
                    int notificationId = intent.getIntExtra("notificationId", 0);
                    NotificationManager myNotification = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(context);
                    builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("バッテリー残量")
                            .setContentText("バッテリ残量が15%になりました!")
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_SOUND);

                    myNotification.notify(notificationId, builder.build());
                }
            }
        }
    }
}

