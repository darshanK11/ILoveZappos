package com.example.ilovezappos.ui.order_book;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovezappos.R;

import java.util.List;

public class TableViewAdapterBids extends RecyclerView.Adapter {

    List<TableModal> tableList;

    public TableViewAdapterBids(List<TableModal> tableList) {
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bids_table_layout, parent, false);
        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;
        holder.setIsRecyclable(false);
        int rowPos = rowViewHolder.getAdapterPosition();
        if(rowPos == 0) {
            rowViewHolder.bids_text_view.setBackgroundColor(Color.DKGRAY);
            rowViewHolder.amount_text_view.setBackgroundColor(Color.DKGRAY);
            rowViewHolder.value_text_view.setBackgroundColor(Color.DKGRAY);
            rowViewHolder.bids_text_view.setTextColor(Color.WHITE);
            rowViewHolder.amount_text_view.setTextColor(Color.WHITE);
            rowViewHolder.value_text_view.setTextColor(Color.WHITE);
            rowViewHolder.bids_text_view.setText(R.string.bids_table_header1);
            rowViewHolder.amount_text_view.setText(R.string.bids_table_header2);
            rowViewHolder.value_text_view.setText(R.string.bids_table_header3);
        }
        else {
            TableModal modal = tableList.get(rowPos - 1);
            rowViewHolder.bids_text_view.setBackgroundColor(Color.WHITE);
            rowViewHolder.amount_text_view.setBackgroundColor(Color.WHITE);
            rowViewHolder.value_text_view.setBackgroundColor(Color.WHITE);
            rowViewHolder.bids_text_view.setText(String.valueOf(modal.getBid_or_ask()));
            rowViewHolder.amount_text_view.setText(String.valueOf(modal.getAmount()));
            rowViewHolder.value_text_view.setText(String.valueOf(modal.getValue()));
        }
    }

    @Override
    public int getItemCount() {
        return tableList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView bids_text_view;
        protected TextView amount_text_view;
        protected TextView value_text_view;
        public RowViewHolder(View itemView) {
            super(itemView);
            bids_text_view = itemView.findViewById(R.id.bids_text_view);
            amount_text_view = itemView.findViewById(R.id.amount_text_view);
            value_text_view = itemView.findViewById(R.id.value_text_view);
        }
    }

}
