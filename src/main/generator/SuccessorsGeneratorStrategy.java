package main.generator;

import main.internal.Resource;
import main.internal.State;

import java.util.List;
import java.util.Map;

public interface SuccessorsGeneratorStrategy {
    List<State> execute(
            String targetCountryName,
            State currentState,
            List<Resource> resourceList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) throws Exception;
}
