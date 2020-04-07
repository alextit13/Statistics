package com.chart.statistics.model.manager;

import com.chart.statistics.model.utils.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MapDataManager {

    public HashMap<String, List<State>> sortMap(
            HashMap<String, List<State>> sourceMap
    ) {
        Object[] keys = sourceMap.keySet().toArray();
        if (keys.length == 0) {
            return sourceMap;
        }

        for (Object key : keys) {
            List<State> list =
                    new ArrayList<>(
                            Objects.requireNonNull(sourceMap.get(key.toString())));
            Collections.sort(list,
                    new Comparator<State>() {
                        @Override
                        public int compare(State o1, State o2) {
                            return o1.getId().compareTo(o2.getId());
                        }
                    });
            sourceMap.put(key.toString(), list);
        }
        return sourceMap;
    }

    private static MapDataManager instance;

    public static MapDataManager newInstance() {
        if (instance == null)
            instance = new MapDataManager();
        return instance;
    }
}
