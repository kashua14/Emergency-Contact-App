package com.kashua14.emergencyapp.services;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.kashua14.emergencyapp.ContactsActivity;
import com.kashua14.emergencyapp.MainActivity;
import com.kashua14.emergencyapp.R;
import com.squareup.seismic.ShakeDetector;

import java.util.Objects;

public class ContactsService extends Service implements ShakeDetector.Listener {

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "EmergencyContactChannel";
    private int count = 0;

    public ContactsService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        /*
          Shake sensor Implementation
         */
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);

        startForeground();

        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        // Create a Notification Channel first;
        initChannels(this);


        startForeground(NOTIF_ID, new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void hearShake() {

        if (count == 0){
            final Dialog epicDialog = new Dialog(this);
            epicDialog.setContentView(R.layout.confirm_dialog_box);
//        closePopupQuizRules = epicDialog.findViewById(R.id.closePopupQuizRules);
            Button btnYes = epicDialog.findViewById(R.id.btnYes);
            Button btnNo = epicDialog.findViewById(R.id.btnNo);

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epicDialog.dismiss();
                    count = 0;
                }
            });

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epicDialog.dismiss();
                    Intent intent = new Intent(ContactsService.this, ContactsActivity.class);
                    startActivity(intent);
                    count = 0;
                }
            });
            Objects.requireNonNull(epicDialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_TOAST);
            epicDialog.show();
            Toast.makeText(this, "Shake detected!!", Toast.LENGTH_SHORT).show();
            count = 1;
        }
    }

    public void initChannels(Context context){
        if (Build.VERSION.SDK_INT < 26){
            return;
        }
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("EmergencyContactChannel",
                "Emergency ContactsActivity List", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Displays Emergency ContactsActivity when phone is locked.");
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

}
