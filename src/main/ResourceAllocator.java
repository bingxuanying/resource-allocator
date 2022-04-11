package main;

import main.generator.PruningStrategy;
import main.internal.Country;
import main.internal.CountryFactory;
import main.internal.State;
import main.internal.StateFactory;
import main.internal.contant.Constant;
import main.search.MasterCountrySearch;
import main.search.ScheduleSearchContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceAllocator {
    public static void main(String[] args) {
        System.out.println("Hello world");
        CountryFactory countryFactory = new CountryFactory();
        StateFactory stateFactory = new StateFactory();

        String masterCountryName = "self";
        List<String> countryNameList = new ArrayList<>() {{
            add(masterCountryName);
            add("us");
            add("uk");
            add("china");
        }};

        List<Country> countryList = countryNameList
                .stream()
                .map(countryName -> countryFactory.create(countryName))
                .collect(Collectors.toList());

        State rootState = stateFactory.createRootState(countryList);

        ScheduleSearchContext scheduleSearchContext = new ScheduleSearchContext(
                new MasterCountrySearch(
                        new PruningStrategy(),
                        Constant.DEFAULT_RESOURCE_LIST,
                        Constant.MANUFACTURING_INPUT_MANUAL,
                        Constant.MANUFACTURING_OUTPUT_MANUAL
                )
        );

        try {
            scheduleSearchContext.executeStrategy(rootState, 4, masterCountryName);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
