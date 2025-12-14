package org.example.Model;

import java.util.ArrayList;
import java.util.Set;

public interface MapObserver {
    public void onMapChanged(ArrayList<String> lvls, Set<String> completedLvls, Set<String> availableLvls);
}
