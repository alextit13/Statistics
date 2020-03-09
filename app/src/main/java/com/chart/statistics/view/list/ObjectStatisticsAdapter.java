package com.chart.statistics.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chart.statistics.R;
import com.chart.statistics.model.utils.ObjectStatistic;

import java.util.List;

public class ObjectStatisticsAdapter extends RecyclerView.Adapter<ObjectStatisticsAdapter.Holder> {

    List<ObjectStatistic> list;
    private IListObjectStatisticsClickCallback callback;

    ObjectStatisticsAdapter(List<ObjectStatistic> list, IListObjectStatisticsClickCallback callback) {
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_statistics, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.itemView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.onClickObject(list.get(position));
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView name;

        Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvNameStatistics);
        }
    }
}
