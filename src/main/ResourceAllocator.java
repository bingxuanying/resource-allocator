package main;

import main.generator.PruningStrategy;
import main.internal.Country;
import main.internal.CountryFactory;
import main.internal.State;
import main.internal.StateFactory;
import main.internal.contant.Constant;
import main.score.DiscountedRewardStrategy;
import main.search.MasterCountrySearch;
import main.search.ScheduleSearchContext;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceAllocator {
    public static void main(String[] args) {
        CountryFactory countryFactory = new CountryFactory();
        StateFactory stateFactory = new StateFactory();

        String masterCountryName = "self";
        List<String> countryNameList = new ArrayList<>() {{
            add(masterCountryName);
            add("U.S");
        }};

        List<Country> countryList = countryNameList
                .stream()
                .map(countryFactory::create)
                .collect(Collectors.toList());

        State rootState = stateFactory.createRootState(countryList);

        ScheduleSearchContext scheduleSearchContext = new ScheduleSearchContext(
                new MasterCountrySearch(
                        new PruningStrategy(),
                        new DiscountedRewardStrategy(),
                        Constant.DEFAULT_RESOURCE_LIST,
                        Constant.MANUFACTURING_INPUT_MANUAL,
                        Constant.MANUFACTURING_OUTPUT_MANUAL
                )
        );

        Instant start = Instant.now();
        try {
            State finalState = scheduleSearchContext.executeStrategy(rootState, 5, masterCountryName);
            finalState.printHistorySteps();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Time takes: " + timeElapsed);
    }
}
