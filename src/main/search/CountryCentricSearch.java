package main.search;

import main.internal.Country;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class CountryCentricSearch implements ScheduleSearchStrategy{

    @Override
    public List<String> search(Country country, int depth) {
        Queue<String> solutions = new PriorityQueue<>();
        Queue<String> frontier = new PriorityQueue<>();
        return null;
    }
}
