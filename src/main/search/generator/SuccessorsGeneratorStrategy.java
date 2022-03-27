package main.search.generator;

import main.internal.Country;
import main.internal.Operation;
import main.internal.Resource;

import java.util.List;
import java.util.Map;

public interface SuccessorsGeneratorStrategy {
    List<Operation> generate(
            Operation parentOperation,
            List<Resource> resourceList,
            List<Country> countryList,
            Map<Resource, Map<Resource, Integer>> manufacturingInputManual,
            Map<Resource, Map<Resource, Integer>> manufacturingOutputManual
    );
}
