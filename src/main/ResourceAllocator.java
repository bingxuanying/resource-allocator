package main;

import main.generator.PruningStrategy;
import main.internal.*;
import main.internal.contant.Constant;
import main.score.DiscountedRewardStrategy;
import main.search.MasterCountrySearch;
import main.search.ScheduleSearchContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ResourceAllocator {
    public static void main(String[] args) {
        System.out.println("Hello world");
        CountryFactory countryFactory = new CountryFactory();
        StateFactory stateFactory = new StateFactory();

        String masterCountryName = "self";
        double gama = 0.5;
        List<String> countryNameList = new ArrayList<>() {{
            add(masterCountryName);
            add("us");
        }};

        List<Country> countryList = countryNameList
                .stream()
                .map(countryName -> countryFactory.create(countryName))
                .collect(Collectors.toList());

        State rootState = stateFactory.createRootState(countryList);

        ScheduleSearchContext scheduleSearchContext = new ScheduleSearchContext(
                new MasterCountrySearch(
                        new PruningStrategy(),
                        new DiscountedRewardStrategy(gama),
                        Constant.DEFAULT_RESOURCE_LIST,
                        Constant.MANUFACTURING_INPUT_MANUAL,
                        Constant.MANUFACTURING_OUTPUT_MANUAL
                )
        );

        State finalStep = null;
        try {
            finalStep = scheduleSearchContext.executeStrategy(rootState, 3, masterCountryName);
        } catch (Exception exception) {
            System.out.println(exception);
        }

        Stack<String> stepQueue = new Stack<>();

        State currentStep = finalStep;
        State parentStep = currentStep.getParent();
        while (parentStep != null) {
            Operation operation = currentStep.getOperation();
            String stepDescription = operation.toString();
            stepQueue.push(stepDescription);
            currentStep = parentStep;
            parentStep = currentStep.getParent();
        }

        while (!stepQueue.isEmpty()) {
            String stepDescription = stepQueue.pop();
            System.out.println(stepDescription);
        }
    }
}
