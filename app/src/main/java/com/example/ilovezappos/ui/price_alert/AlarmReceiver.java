package com.example.ilovezappos.ui.price_alert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.ilovezappos.MainActivity;
import com.example.ilovezappos.R;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationChannel notificationChannel = new NotificationChannel("ZapposChannel", "ILoveZappos", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification from ILoveZappos")
                .setContentText("Price dropped below stored price.")
                .setWhen(when)
                .setContentIntent(pendingIntent)
                .setChannelId("ZapposChannel");

        float current_price = 0f;
        try {
            current_price = new getJSONData().execute("").get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        final SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);
        if (current_price <= sharedPreferences.getFloat("stored_price", 0f)) {
            notificationManager.notify(0, builder.build());
        }

    }

}

class getJSONData extends AsyncTask<String, Void, Float> {

    private static String readAll(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();
    }

    @Override
    protected Float doInBackground(String... url) {
        float current_price = 0f;
        try {
            InputStream inputStream = new URL("https://www.bitstamp.net/api/v2/ticker_hour/btcusd/").openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            JSONObject jsonObject = new JSONObject(readAll(bufferedReader));
            current_price = Float.parseFloat(jsonObject.getString("last"));
            return current_price;
        }
        catch (Exception e) {
            return current_price;
        }
    }
}