package com.chart.statistics.view.diagram.linear;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chart.statistics.R;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.custom.LinearDiagram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LinearDiagramAdapter extends RecyclerView.Adapter<LinearDiagramAdapter.Holder> {

    private HashMap<String, List<State>> mapNameStates;
    private String timeObservationFinish;

    LinearDiagramAdapter(HashMap<String, List<State>> mapNameStates, String timeFinish) {
        this.mapNameStates = mapNameStates;
        timeObservationFinish = timeFinish;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(
                parent.getContext()
        )
                .inflate(R.layout.item_linear_diagram,
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Object [] arr = mapNameStates.keySet().toArray();
        String key = arr[position].toString();
        Iterator iterator = mapNameStates.keySet().iterator();
        if (iterator.hasNext()) {
            holder.linearDiagram.setSourceData(mapNameStates.get(key), timeObservationFinish);
            holder.linearDiagram.setTitle(key);
        }
    }

    @Override
    public int getItemCount() {
        return mapNameStates.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private LinearDiagram linearDiagram;

        Holder(@NonNull View itemView) {
            super(itemView);
            linearDiagram = itemView.findViewById(R.id.linear_diagram_item);
        }
    }
}
