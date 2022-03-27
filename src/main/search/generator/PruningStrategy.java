package main.search.generator;

import main.internal.Country;
import main.internal.Operation;
import main.internal.Resource;
import main.internal.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PruningStrategy implements SuccessorsGeneratorStrategy {
    @Override
    public List<Operation> generate(
            Operation parentOperation,
            List<Resource> resourceList,
            List<Country> countryList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) {
        List<Operation> res = new ArrayList<>();

        // Get most current country state
        Map<Resource, Integer> parentResourceMap = parentOperation.getOriginCountry().getResourceMap();

        return res;
    }

    // Loop through possible future states with quality as score
    // Operation - transform
    private List<Operation> getTransformSuccessors(
            Operation parentOperation,
            List<Resource> resourceList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) {
        return resourceList
                // Stream
                .stream()
                // Go through each creatable resource
                .map(targetResource -> {
                    // Return if the resource is not creatable
                    if (targetResource.getType() != ResourceType.CREATED) {
                        return null;
                    }

                    // Check if the country has required resources available
                    // Get the maximum possible
                    int maxMultiple = this.calculateMaxMultiple(parentOperation, targetResource, manufacturingInputManual);

                    List<Operation> possibleOperations = IntStream
                            .range(0, maxMultiple)
                            .boxed()
                            .map(multiple -> getChildOperation(
                                    parentOperation,
                                    multiple,
                                    targetResource,
                                    manufacturingInputManual,
                                    manufacturingOutputManual)
                            )
                            .collect(Collectors.toList());

                    return possibleOperations;
                })
                // Flat out each list
                .flatMap(list -> list.stream())
                // Collect them into one
                .collect(Collectors.toList());
    }

    // Check if the country has required resources available
    // Get the maximum possible
    private int calculateMaxMultiple(
            Operation parentOperation,
            Resource targetResource,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual
    ) {
        // Get most current country resource state
        Map<Resource, Integer> parentResourceMap = parentOperation.getOriginCountry().getResourceMap();
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

    private Operation getChildOperation(
            Operation parentOperation,
            int multiple,
            Resource targetResource,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) {
        // Get most current country resource state
        Operation childOperation = parentOperation.clone();
        Map<Resource, Integer> currentResourceMap = childOperation.getOriginCountry().getResourceMap();

        // Remove used resource to current resource map
        manufacturingInputManual
                .get(targetResource)
                .entrySet()
                .stream()
                .forEach(entry -> {
                    currentResourceMap.compute(
                            entry.getKey(),
                            (k, v) -> v - entry.getValue()
                    );
                });

        // Add new resource to current resource map
        manufacturingOutputManual
                .get(targetResource)
                .entrySet()
                .stream()
                .forEach(entry -> {
                    currentResourceMap.compute(
                            entry.getKey(),
                            (k, v) -> v + entry.getValue()
                    );
                });

        return childOperation;
    }
}
