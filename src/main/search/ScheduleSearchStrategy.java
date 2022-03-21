package main.search;

import main.internal.Country;

import java.util.List;

public interface ScheduleSearchStrategy {
    public List<String> search(Country country, int depth);
}
