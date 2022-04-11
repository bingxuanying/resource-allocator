package main.state;

import main.internal.Country;
import main.internal.Operation;
import main.internal.Resource;
import main.internal.State;
import main.internal.type.OperationType;
import main.internal.type.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class Trade {

    public State takeOperation(
            State currentState,
            Operation operation,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) throws Exception {
        Map<String, Country> newCountryState = cloneCurrentCountryState(currentState);

        if (operation != null && operation.getType() == OperationType.TRANSFORM) {
            newCountryState = this.transform(
                    operation, newCountryState, manufacturingInputManual, manufacturingOutputManual);
        } else if (operation != null && operation.getType() == OperationType.TRANSFER) {
            newCountryState = this.transfer(operation, newCountryState);
        }

        return new State(newCountryState, operation, currentState);

    }

    private Map<String, Country> cloneCurrentCountryState(State currentState) {
        Map<String, Country> res = new HashMap<>();
        currentState
                .getCountryStates()
                .values()
                .stream()
                .forEach(country -> res.put(country.getName(), country.clone()));
        return res;
    }

    private Map<String, Country> transform(
            Operation operation,
            Map<String, Country> countryStates,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) throws Exception {
        String countryName = operation.getOriginCountryName();
        Resource resource = operation.getResource();
        int multiple = operation.getMultiple();

        Country country = countryStates.get(countryName);

        if (resource.getType() != ResourceType.CREATED) {
            throw new Exception("[ERROR] has to transform an creatable resource");
        }

        Map<Resource, Integer> countryResourceMap = country.getResourceMap();
        Map<Resource, Integer> inputManual = manufacturingInputManual.get(resource);
        Map<Resource, Integer> outputManual = manufacturingOutputManual.get(resource);

        for (Map.Entry<Resource, Integer> entry : inputManual.entrySet()) {
            Resource requiredResource = entry.getKey();
            int requiredAmount = entry.getValue() * multiple;
            if (countryResourceMap.getOrDefault(requiredResource, -1) < requiredAmount) {
                throw new Exception("[ERROR] origin country resource insufficient");
            }
            countryResourceMap
                    .computeIfPresent(
                            requiredResource,
                            (originalResource, originalAmount) -> originalAmount - requiredAmount
                    );
        }

        for (Map.Entry<Resource, Integer> entry : outputManual.entrySet()) {
            Resource producedResource = entry.getKey();
            int producedAmount = entry.getValue() * multiple;
            countryResourceMap
                    .compute(
                            producedResource,
                            (originalResource, originalAmount) -> originalAmount + producedAmount
                    );
        }

        // Update country states
        countryStates.put(countryName, country);

        return countryStates;
    }

    private Map<String, Country> transfer(Operation operation, Map<String, Country> countryStates) throws Exception {
        String originCountryName = operation.getOriginCountryName();
        String destinationCountryName = operation.getDestinationCountryName();
        Resource resource = operation.getResource();
        int amount = operation.getMultiple();

        Country originCountry = countryStates.get(originCountryName);
        Country destinationCountry = countryStates.get(destinationCountryName);

        Map<Resource, Integer> originResourceMap = originCountry.getResourceMap();
        Map<Resource, Integer> destinationResourceMap = destinationCountry.getResourceMap();

        if (originResourceMap.getOrDefault(resource, -1) <= 0) {
            throw new Exception("[ERROR] origin country resource insufficient");
        }

        originResourceMap.compute(resource, (k, originalAmount) -> originalAmount - amount);
        destinationResourceMap.compute(resource, (k, originalAmount) -> originalAmount + amount);

        // Update country states
        countryStates.put(originCountryName, originCountry);
        countryStates.put(destinationCountryName, destinationCountry);

        return countryStates;
    }

}
