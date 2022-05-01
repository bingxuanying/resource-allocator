package main.search;

import main.generator.SuccessorsGeneratorContext;
import main.generator.SuccessorsGeneratorStrategy;
import main.internal.Resource;
import main.internal.State;
import main.score.RewardMeasureContext;
import main.score.RewardMeasureStrategy;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class MasterCountrySearch implements ScheduleSearchStrategy {

    private final SuccessorsGeneratorContext successorsGeneratorContext;
    private final RewardMeasureContext rewardMeasureContext;
    private final List<Resource> resourceList;
    private final Map<Resource, Map<Resource, Integer>> manufacturingInputManual;
    private final Map<Resource, Map<Resource, Integer>> manufacturingOutputManual;

    public MasterCountrySearch(
            SuccessorsGeneratorStrategy successorsGeneratorStrategy,
            RewardMeasureStrategy rewardMeasureStrategy,
            List<Resource> resourceList, Map<Resource,
            Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    ) {
        this.successorsGeneratorContext = new SuccessorsGeneratorContext(successorsGeneratorStrategy);
        this.rewardMeasureContext = new RewardMeasureContext(rewardMeasureStrategy);
        this.resourceList = resourceList;
        this.manufacturingInputManual = manufacturingInputManual;
        this.manufacturingOutputManual = manufacturingOutputManual;
    }

    @Override
    public State search(State root, int maxDepth, String masterCountryName) throws Exception {

        Queue<State> solutions = new PriorityQueue<>();
        Queue<State> frontier = new PriorityQueue<>() {{
            add(root);
        }};

        while (!frontier.isEmpty()) {
            State state = frontier.poll();
            if (maxDepth <= state.getDepth()) {
                solutions.add(state);
            } else {
                List<State> successors = successorsGeneratorContext.executeStrategy(
                        masterCountryName,
                        state,
                        resourceList,
                        manufacturingInputManual,
                        manufacturingOutputManual
                );
                frontier.addAll(successors);
            }
        }

        State solution = solutions.peek();

        return solution;
    }
}
