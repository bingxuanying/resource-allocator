package main.search;

import main.internal.State;

public interface ScheduleSearchStrategy {
    State search(State root, int maxDepth, String masterCountryName) throws Exception;
}
