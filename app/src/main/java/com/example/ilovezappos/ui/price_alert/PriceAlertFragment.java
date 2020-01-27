package com.example.ilovezappos.ui.price_alert;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ilovezappos.MainActivity;
import com.example.ilovezappos.R;

import java.util.Calendar;

public class PriceAlertFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_price_alert, container, false);

        final EditText editText_price = root.findViewById(R.id.price_edit_text);
        Button button_storePrice = root.findViewById(R.id.store_price_button);

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editText_price.setText(String.valueOf(sharedPreferences.getFloat("stored_price", 0f)));

        button_storePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceString = editText_price.getText().toString();
                editor.putFloat("stored_price", Float.parseFloat(priceString));
                editor.commit();
                Toast toast = Toast.makeText(getActivity(), "Price Stored: " + priceString + "In SP: " + sharedPreferences.getFloat("stored_price", 0f), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60, pendingIntent);

        return root;
    }
}