package main.operations;

import main.contant.Constant;
import main.internal.Country;
import main.internal.Resource;
import main.internal.ResourceType;

import java.util.Map;

public class TradeOperation {
    public static void transform(Country country, Resource resource, int multiple) throws Exception {
        if (resource.getType() != ResourceType.CREATED) {
            throw new Exception("[ERROR] has to transform an creatable resource");
        }

        Map<Resource, Integer> countryResourceMap = country.getResourceMap();
        Map<Resource, Integer> inputManual = Constant.manufacturingInputManual.get(resource);
        Map<Resource, Integer> outputManual = Constant.manufacturingOutputManual.get(resource);

        for (Map.Entry<Resource, Integer> entry : inputManual.entrySet()) {
            Resource requiredResource = entry.getKey();
            int requiredAmount = entry.getValue() * multiple;
            if (countryResourceMap.getOrDefault(requiredResource, -1) < requiredAmount) {
                throw new Exception("[ERROR] origin country resource insufficient");
            }
            countryResourceMap
                    .computeIfPresent(
                            requiredResource,
                            (originalResource, origianlAmount) -> origianlAmount - requiredAmount
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
    };

    public static void transfer(Country origin, Country destination, Resource resource, int amount) throws Exception {
        Map<Resource, Integer> originResourceMap = origin.getResourceMap();
        Map<Resource, Integer> destinationResourceMap = destination.getResourceMap();

        if (originResourceMap.getOrDefault(resource, -1) <= 0) {
            throw new Exception("[ERROR] origin country resource insufficient");
        }

        originResourceMap.compute(resource, (k, originalAmount) -> originalAmount - amount);
        destinationResourceMap.compute(resource, (k, originalAmount) -> originalAmount + amount);
    };
}
