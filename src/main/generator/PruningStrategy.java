package main.generator;

import main.internal.Country;
import main.internal.Operation;
import main.internal.Resource;
import main.internal.State;
import main.internal.type.OperationType;
import main.internal.type.ResourceType;
import main.state.Trade;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PruningStrategy implements SuccessorsGeneratorStrategy {

    private final Trade trade = new Trade();

    @Override
    public List<State> execute(
            String targetCountryName,
            State currentState,
            List<Resource> resourceList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) {

        getTransformSuccessors(
                targetCountryName, currentState, resourceList, manufacturingInputManual, manufacturingOutputManual);
        getTransferSuccessors(
                targetCountryName, currentState, resourceList, manufacturingInputManual, manufacturingOutputManual);

        return currentState.getChildren();
    }

    // Loop through possible future states with quality as score
    // Operation - transfer
    private void getTransferSuccessors(
            String targetCountryName,
            State currentState,
            List<Resource> resourceList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) {
        Map<String, Country> currentCountryStates = currentState.getCountryStates();
        Country targetCountry = currentCountryStates.get(targetCountryName);

        Map<Resource, Integer> minResourceFactorMap = calculateMinimumResourceFactor(
                resourceList, manufacturingInputManual);

        // Loop through all possible countries
        currentCountryStates
                .entrySet()
                .parallelStream()
                .forEach(countryStateEntry -> {

                    // Skip trade with itself
                    if (countryStateEntry.getKey() == targetCountry.getName()) {
                        return;
                    }

                    Country originCountry = countryStateEntry.getValue();
                    originCountry
                            .getResourceMap()
                            .entrySet()
                            .forEach(resourceEntry -> {
                                Resource resource = resourceEntry.getKey();
                                int maxAmount = resourceEntry.getValue();

                                int amount = minResourceFactorMap.getOrDefault(resource, 1);
                                int additionalAmount = amount;

                                // Prune by max resources owned
                                while (amount <= maxAmount) {
                                    Operation newOperation = new Operation(
                                            OperationType.TRANSFER,
                                            targetCountry.getName(),
                                            originCountry.getName(),
                                            resource,
                                            amount
                                    );
                                    try {
                                        State newState = trade.takeOperation(
                                                currentState,
                                                newOperation,
                                                manufacturingInputManual,
                                                manufacturingOutputManual
                                        );
                                        currentState.addChild(newState);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    amount += additionalAmount;
                                }
                            });
                });
    }

    private Map<Resource, Integer> calculateMinimumResourceFactor(
            List<Resource> resourceList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual
    ) {
        Map<Resource, Integer> res = resourceList
                .stream()
                .collect(Collectors.toMap(resource -> resource, resource -> Integer.MAX_VALUE));

        manufacturingInputManual
                .values()
                .stream()
                .forEach(inputManual ->
                        inputManual
                                .entrySet()
                                .stream()
                                .forEach(entry -> {
                                    Resource resource = entry.getKey();
                                    int currentValue = res.get(resource);
                                    int amount = Integer.min(entry.getValue(), currentValue);
                                    res.put(resource, amount);
                                })
                );

        return res;
    }

    // Loop through possible future states with quality as score
    // Operation - transform
    private void getTransformSuccessors(
            String targetCountryName,
            State currentState,
            List<Resource> resourceList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) {
        Map<String, Country> currentCountryStates = currentState.getCountryStates();
        Country targetCountry = currentCountryStates.get(targetCountryName);

        resourceList
                // Stream
                .parallelStream()
                // Go through each creatable resource
                .forEach(targetResource -> {
                    // Return if the resource is not creatable
                    if (targetResource.getType() != ResourceType.CREATED) {
                        return;
                    }

                    // Check if the country has required resources available
                    // Get the maximum possible
                    int maxMultiple = this.calculateMaxMultiple(targetCountry, targetResource, manufacturingInputManual);

                    // Prune by multiple
                    IntStream
                            .range(1, maxMultiple)
                            .boxed()
                            .forEach(multiple -> {
                                        Operation newOperation = new Operation(
                                                OperationType.TRANSFORM,
                                                targetCountry.getName(),
                                                targetCountry.getName(),
                                                targetResource,
                                                multiple
                                        );
                                        try {
                                            State newState = trade.takeOperation(
                                                    currentState,
                                                    newOperation,
                                                    manufacturingInputManual,
                                                    manufacturingOutputManual
                                            );
                                            currentState.addChild(newState);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                            );
                });
    }

    // Check if the country has required resources available
    // Get the maximum possible
    private int calculateMaxMultiple(
            Country targetCountry,
            Resource targetResource,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual
    ) {
        // Get most current country resource state
        Map<Resource, Integer> parentResourceMap = targetCountry.getResourceMap();
        Map<Resource, Integer> requiredResources = manufacturingInputManual.get(targetResource);

        return requiredResources
                // Get entry set
                .entrySet()
                // Stream
                .stream()
                // Divide current resource by required amount of resource
                // If greater than 0, meaning there are resources available
                // Else resources are not enough
                .map(entry -> {
                    Resource key = entry.getKey();
                    int currentAmount = parentResourceMap.get(key);
                    int requiredAmount = entry.getValue();
                    return currentAmount / requiredAmount;
                })
                // Get the min resource factor to know at most how many can be created
                .min(Integer::compare)
                .get();
    }
}
