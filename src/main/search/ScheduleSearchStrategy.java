package main.search;

import main.internal.State;

import java.util.List;

public interface ScheduleSearchStrategy {
    List<State> search(State root, int depth, String masterCountryName) throws Exception;
}
