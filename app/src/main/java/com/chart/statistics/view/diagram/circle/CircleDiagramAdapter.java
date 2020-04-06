package com.chart.statistics.view.diagram.circle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chart.statistics.R;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.custom.CircleDiagram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CircleDiagramAdapter extends RecyclerView.Adapter<CircleDiagramAdapter.Holder> {

    private HashMap<String, List<State>> mapNameStates;
    private String timeObservationStart;
    private String timeObservationFinish;

    CircleDiagramAdapter(
            HashMap<String, List<State>> mapNameStates,
            String timeObservationStart,
            String timeObservationFinish
    ) {
        this.mapNameStates = mapNameStates;
        this.timeObservationStart = timeObservationStart;
        this.timeObservationFinish = timeObservationFinish;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_circle_diagram, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Object[] arr = mapNameStates.keySet().toArray();
        String key = arr[position].toString();
        Iterator iterator = mapNameStates.keySet().iterator();
        if (iterator.hasNext()) {
            holder.circleDiagram.setSourceData(
                    mapNameStates.get(key), timeObservationStart, timeObservationFinish);
            holder.titleDiagramm.setText(key);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mapNameStates.size();
    }

    void setMapNameStates(HashMap<String, List<State>> mapNameStates) {
        this.mapNameStates = mapNameStates;
    }

    class Holder extends RecyclerView.ViewHolder {

        private CircleDiagram circleDiagram;
        private TextView titleDiagramm;

        Holder(@NonNull View itemView) {
            super(itemView);
            circleDiagram = itemView.findViewById(R.id.circle_diagram);
            titleDiagramm = itemView.findViewById(R.id.title_circle_diagram_tv);
        }
    }
}
