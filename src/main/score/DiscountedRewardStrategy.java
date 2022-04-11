package main.score;

import main.internal.Country;
import main.internal.Operation;
import main.internal.Resource;
import main.internal.State;
import main.internal.type.OperationType;

import java.util.Map;

public class DiscountedRewardStrategy implements RewardMeasureStrategy {

    private final double gamma;

    public DiscountedRewardStrategy(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public double measure(State currentState) {
        Operation currentOperation = currentState.getOperation();
        String masterCountryName = currentOperation.getDestinationCountryName();
        double masterCountryDiscountedReward = getDiscountedRewardScore(currentState, masterCountryName);
        double transferSuccessProbability = 1D;

        if (currentOperation.getType() == OperationType.TRANSFER) {
            String slaveCountry = currentOperation.getOriginCountryName();
            transferSuccessProbability = calculateTransferSuccessProbability(currentState, slaveCountry);
        }

        return masterCountryDiscountedReward * transferSuccessProbability;
    }

    // Trade origin country possibility of accepting the trade
    private double calculateTransferSuccessProbability(State currentState, String targetCountryName) {
        double discountedReward = getDiscountedRewardScore(currentState, targetCountryName);
        return sigmoid(discountedReward);
    }

    private double sigmoid(double x) {
        return (1 / (1 + Math.pow(Math.E, (-1 * x))));
    }

    // Process and get final discounted reward
    private double getDiscountedRewardScore(State currentState, String targetCountryName) {
        double stateQuality = calculateStateQuality(currentState, targetCountryName);
        double undiscountedReward = calculateUndiscountedReward(currentState, stateQuality);
        double discountedReward = calculateDiscountedReward(currentState, undiscountedReward);
        return discountedReward;
    }

    // Current state quality minus initial state quality
    private double calculateDiscountedReward(State currentState, double undiscountedReward) {
        return undiscountedReward * Math.pow(gamma, currentState.getDepth());
    }

    // Current state quality minus initial state quality
    private double calculateUndiscountedReward(State currentState, double currentStateQuality) {
        return currentStateQuality - currentState.getRoot().getStateQuality();
    }

    //  w_Ri * A_Ri / A_Population
    private double calculateStateQuality(State currentState, String targetCountryName) {
        if (currentState.getStateQuality() != 0D) {
            return currentState.getStateQuality();
        }

        Map<String, Country> countryStates = currentState.getCountryStates();
        Country targetCountry = countryStates.get(targetCountryName);
        Map<Resource, Integer> resourceMap = targetCountry.getResourceMap();
        double totalScore = 0D;
        int population = 0;

        for (Map.Entry<Resource, Integer> resourceEntry : resourceMap.entrySet()) {
            Resource resource = resourceEntry.getKey();
            int amount = resourceEntry.getValue();

            if (resource.getName() == "Population") {
                population = amount;
            }

            totalScore += resource.getWeight() * amount;
        }

        double stateQuality = totalScore / population;
        currentState.setStateQuality(stateQuality);
        return stateQuality;
    }
}