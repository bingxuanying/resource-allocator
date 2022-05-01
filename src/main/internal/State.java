package main.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class State implements Comparable<State> {
    private final Map<String, Country> countryStates;
    private final Operation operation;
    private final int depth;
    private final State root;
    private final State parent;
    private final List<State> children;
    private double stateQuality = 0D;
    private double finalDiscountedReward = 0D;

    public State(Map<String, Country> countryStates) {
        this.countryStates = countryStates;
        this.operation = null;
        this.depth = 0;
        this.root = null;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public State(Map<String, Country> countryStates, Operation operation, State parent) {
        this.countryStates = countryStates;
        this.operation = operation;
        this.depth = parent.getDepth() + 1;
        this.root = parent.getDepth() == 0 ? parent : parent.getRoot();
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public State(Map<String, Country> countryStates, Operation operation, State parent, List<State> children) {
        this.countryStates = countryStates;
        this.operation = operation;
        this.depth = parent.getDepth() + 1;
        this.root = parent.getDepth() == 0 ? parent : parent.getRoot();
        this.parent = parent;
        this.children = children;
    }

    public int printHistorySteps() {
        if (parent == null) {
            return 0;
        }
        int step = this.parent.printHistorySteps() + 1;
        System.out.println("[Step #" + step + "] " + this.operation.toString());
        return step;
    }

    @Override
    public int compareTo(State theOtherState) {
        return theOtherState.getFinalDiscountedReward() > this.getFinalDiscountedReward() ? 1 : -1;
    }

    public int getDepth() {
        return depth;
    }

    public Map<String, Country> getCountryStates() {
        return countryStates;
    }

    public Operation getOperation() {
        return operation;
    }

    public State getRoot() {
        return root;
    }

    public State getParent() {
        return parent;
    }

    public List<State> getChildren() {
        return children;
    }

    public double getStateQuality() {
        return stateQuality;
    }

    public void setStateQuality(double stateQuality) {
        this.stateQuality = stateQuality;
    }

    public void addChild(State state) {
        this.children.add(state);
    }

    public void addChildren(List<State> stateList) {
        this.children.addAll(stateList);
    }

    public double getFinalDiscountedReward() {
        return finalDiscountedReward;
    }

    public void setFinalDiscountedReward(double finalDiscountedReward) {
        this.finalDiscountedReward = finalDiscountedReward;
    }
}
