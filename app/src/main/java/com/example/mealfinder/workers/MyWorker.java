package com.example.mealfinder.workers;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mealfinder.R;

public class MyWorker extends Worker {
    public static final String CHANNEL_ID = "WORKER_CHANNEL";
    private Context mContext;
    private static Long WEEK = L;

    public MyWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.mContext = context;
    }

    @Override
    public Result doWork() {

        String username = getInputData().getString("username");

        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        Long lastLogout = sharedPreferences.getLong("lastLogout", System.currentTimeMillis());

        if (System.currentTimeMillis() - lastLogout > WEEK) {
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder( super.getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("What's up " + username + "!")
                    .setContentText("Come check out some new recipes!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            // notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            notificationManager.notify(1, builder.build());
        }

        // Do the work here--in this case, upload the images.


        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}