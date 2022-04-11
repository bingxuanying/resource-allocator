package main.search;

import main.generator.SuccessorsGeneratorContext;
import main.generator.SuccessorsGeneratorStrategy;
import main.internal.Resource;
import main.internal.State;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class MasterCountrySearch implements ScheduleSearchStrategy {

    private final SuccessorsGeneratorContext successorsGeneratorContext;
    private final List<Resource> resourceList;
    private final Map<Resource, Map<Resource, Integer>> manufacturingInputManual;
    private final Map<Resource, Map<Resource, Integer>> manufacturingOutputManual;

    public MasterCountrySearch(
            SuccessorsGeneratorStrategy strategy,
            List<Resource> resourceList, Map<Resource,
            Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) {
        this.successorsGeneratorContext = new SuccessorsGeneratorContext(strategy);
        this.resourceList = resourceList;
        this.manufacturingInputManual = manufacturingInputManual;
        this.manufacturingOutputManual = manufacturingOutputManual;
    }

    @Override
    public List<State> search(State root, int depth, String masterCountryName) throws Exception {
        List<State> res = successorsGeneratorContext.executeStrategy(
                masterCountryName,
                root,
                resourceList,
                manufacturingInputManual,
                manufacturingOutputManual
        );


        Queue<String> solutions = new PriorityQueue<>();
        Queue<String> frontier = new PriorityQueue<>();
        return null;
    }
}
