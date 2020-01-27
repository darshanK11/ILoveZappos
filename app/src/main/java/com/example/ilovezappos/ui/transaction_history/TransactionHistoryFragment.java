package com.example.ilovezappos.ui.transaction_history;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ilovezappos.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

public class TransactionHistoryFragment extends Fragment {

    private LineChart lineChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_transaction_history, container, false);

        lineChart = root.findViewById(R.id.line_chart);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.getDescription().setText("");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawLimitLinesBehindData(true);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setDrawZeroLine(false);
        yAxis.setDrawLimitLinesBehindData(false);

        lineChart.getAxisRight().setEnabled(false);

        ArrayList<Entry> values = new ArrayList<>();

        try {
            values = new getJSONData().execute("").get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        LineDataSet lineDataSet;
        if(lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();

        }
        else {
            lineDataSet = new LineDataSet(values, "Transaction History");
            lineDataSet.setDrawIcons(false);
            lineDataSet.setColor(Color.DKGRAY);
            lineDataSet.setCircleColor(Color.DKGRAY);
            lineDataSet.setLineWidth(1f);
            lineDataSet.setCircleRadius(1.5f);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setValueTextSize(9f);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFormLineWidth(1f);
            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            lineDataSet.setFormSize(15.f);
            lineDataSet.setFillColor(Color.DKGRAY);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);
            LineData lineData = new LineData(dataSets);
            lineChart.setData(lineData);
        }

        return root;
    }
}

class getJSONData extends AsyncTask<String, Void, ArrayList> {

    private static String readAll(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();
    }

    @Override
    protected ArrayList doInBackground(String... url) {
        ArrayList<Entry> values = new ArrayList<>();
        try {
            InputStream inputStream = new URL("https://www.bitstamp.net/api/v2/transactions/btcusd/").openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            JSONArray jsonArray = new JSONArray(readAll(bufferedReader));
            String firstDate = "0";
            for(int i = jsonArray.length() - 1; i >= 0; i--) {
                JSONObject jsonObjectInternal = jsonArray.getJSONObject(i);
                String dateString = jsonObjectInternal.getString("tid");
                String priceString = jsonObjectInternal.getString("price");
                if(i == jsonArray.length() - 1) {
                    firstDate = dateString;
                }
                dateString = String.valueOf(Integer.parseInt(dateString) - Integer.parseInt(firstDate));
                Entry entry = new Entry(Integer.parseInt(dateString), Float.parseFloat(priceString));
                values.add(entry);
            }
            return values;
        }
        catch (Exception e) {
            e.printStackTrace();
            values.add(new Entry(1, 50));
            values.add(new Entry(2, 100));
            values.add(new Entry(3, 80));
            values.add(new Entry(4, 120));
            values.add(new Entry(5, 110));
            values.add(new Entry(7, 150));
            values.add(new Entry(8, 250));
            values.add(new Entry(9, 190));
            return values;
        }
    }
}