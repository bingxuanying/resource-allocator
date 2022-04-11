package main.score;

import main.internal.State;

public interface RewardMeasureStrategy {
    double measure(State currentState);
}
