package main.search;

import main.internal.State;

import java.util.List;

public class ScheduleSearchContext {
    private final ScheduleSearchStrategy strategy;

    public ScheduleSearchContext(ScheduleSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<State> executeStrategy(State root, int depth, String masterCountryName) throws Exception {
        return this.strategy.search(root, depth, masterCountryName);
    }
}
