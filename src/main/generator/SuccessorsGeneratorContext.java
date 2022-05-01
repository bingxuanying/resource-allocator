package main.generator;

import main.internal.Resource;
import main.internal.State;

import java.util.List;
import java.util.Map;

public class SuccessorsGeneratorContext {
    private final SuccessorsGeneratorStrategy strategy;

    public SuccessorsGeneratorContext(SuccessorsGeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    public List<State> executeStrategy(
            String targetCountryName,
            State currentState,
            List<Resource> resourceList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) throws Exception {
        return this.strategy.execute(
                targetCountryName,
                currentState,
                resourceList,
                manufacturingInputManual,
                manufacturingOutputManual
        );
    }
}
