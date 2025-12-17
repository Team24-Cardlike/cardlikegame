package org.example.Model;

import java.util.ArrayList;

public interface MapObserver {
    public void onMapChanged(ArrayList<String> lvls, int currentOp);
}
