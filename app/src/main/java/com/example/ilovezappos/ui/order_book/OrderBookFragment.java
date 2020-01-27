package com.example.ilovezappos.ui.order_book;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovezappos.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class OrderBookFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order_book, container, false);

        RecyclerView recyclerViewBids = root.findViewById(R.id.bids_recycler_view);
        TableViewAdapterBids adapterBids = new TableViewAdapterBids(getTableList("bids"));
        LinearLayoutManager linearLayoutManagerBids = new LinearLayoutManager(getActivity());
        recyclerViewBids.setLayoutManager(linearLayoutManagerBids);
        recyclerViewBids.setAdapter(adapterBids);

        RecyclerView recyclerViewAsks = root.findViewById(R.id.asks_recycler_view);
        TableViewAdapterAsks adapterAsks = new TableViewAdapterAsks(getTableList("asks"));
        LinearLayoutManager linearLayoutManagerAsks = new LinearLayoutManager(getActivity());
        recyclerViewAsks.setLayoutManager(linearLayoutManagerAsks);
        recyclerViewAsks.setAdapter(adapterAsks);

        return root;

    }

    private List<TableModal> getTableList(String type) {
        List<TableModal> tableList = new ArrayList<>();
        try {
            tableList = new getJSONData().execute(type).get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tableList;
    }
}

class getJSONData extends AsyncTask<String, Void, List<TableModal>> {

    private static String readAll(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();
    }

    @Override
    protected List<TableModal> doInBackground(String... url) {
        List<TableModal> tableList = new ArrayList<>();
        try {
            InputStream inputStream = new URL("https://www.bitstamp.net/api/v2/order_book/btcusd/").openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            JSONObject jsonObject = new JSONObject(readAll(bufferedReader));
            JSONArray jsonArray = jsonObject.getJSONArray(url[0]);
            for (int i = 0; i < jsonArray.length(); i ++) {
                JSONArray jsonArrayInternal = jsonArray.getJSONArray(i);
                tableList.add(new TableModal(Float.parseFloat(jsonArrayInternal.getString(0)), Float.parseFloat(jsonArrayInternal.getString(1))));
            }
            return tableList;
        }
        catch (Exception e) {
            e.printStackTrace();
            tableList.add(new TableModal(1f, 3f));
            tableList.add(new TableModal(2f, 5f));
            return tableList;
        }
    }
}