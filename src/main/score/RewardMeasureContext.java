package main.score;

import main.internal.State;

public class RewardMeasureContext {
    private final RewardMeasureStrategy strategy;

    public RewardMeasureContext(RewardMeasureStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeStrategy(State currentState) {
        return this.strategy.measure(currentState);
    }
}
