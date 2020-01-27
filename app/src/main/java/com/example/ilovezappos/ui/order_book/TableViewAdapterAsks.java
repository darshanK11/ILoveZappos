package com.example.ilovezappos.ui.order_book;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovezappos.R;

import org.w3c.dom.Text;

import java.util.List;

public class TableViewAdapterAsks extends RecyclerView.Adapter {

    List<TableModal> tableList;

    public TableViewAdapterAsks(List<TableModal> tableList) {
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.asks_table_layout, parent, false);
        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;
        holder.setIsRecyclable(false);
        int rowPos = rowViewHolder.getAdapterPosition();
        if(rowPos == 0) {
            rowViewHolder.asks_text_view.setBackgroundColor(Color.DKGRAY);
            rowViewHolder.amount_text_view.setBackgroundColor(Color.DKGRAY);
            rowViewHolder.value_text_view.setBackgroundColor(Color.DKGRAY);
            rowViewHolder.asks_text_view.setTextColor(Color.WHITE);
            rowViewHolder.amount_text_view.setTextColor(Color.WHITE);
            rowViewHolder.value_text_view.setTextColor(Color.WHITE);
            rowViewHolder.asks_text_view.setText(R.string.bids_table_header1);
            rowViewHolder.amount_text_view.setText(R.string.bids_table_header2);
            rowViewHolder.value_text_view.setText(R.string.bids_table_header3);
        }
        else {
            TableModal modal = tableList.get(rowPos - 1);
            rowViewHolder.asks_text_view.setBackgroundColor(Color.WHITE);
            rowViewHolder.amount_text_view.setBackgroundColor(Color.WHITE);
            rowViewHolder.value_text_view.setBackgroundColor(Color.WHITE);
            rowViewHolder.asks_text_view.setText(String.valueOf(modal.getBid_or_ask()));
            rowViewHolder.amount_text_view.setText(String.valueOf(modal.getAmount()));
            rowViewHolder.value_text_view.setText(String.valueOf(modal.getValue()));
        }
    }

    @Override
    public int getItemCount() {
        return tableList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView asks_text_view;
        protected TextView amount_text_view;
        protected TextView value_text_view;
        public RowViewHolder(View itemView) {
            super(itemView);
            asks_text_view = itemView.findViewById(R.id.asks_text_view);
            amount_text_view = itemView.findViewById(R.id.asks_amount_text_view);
            value_text_view = itemView.findViewById(R.id.asks_value_text_view);
        }
    }

}
