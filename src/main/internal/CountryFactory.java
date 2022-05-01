package main.internal;

import main.internal.contant.Constant;
import main.internal.type.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryFactory {
    public Country create(String name) {
        Map<Resource, Integer> resourceMap = this.initResourceMap();
        return new Country(name, resourceMap);
    }

    private Map<Resource, Integer> initResourceMap() {
        Map<Resource, Integer> resourceMap = new HashMap<>();
        List<Resource> resourcesList = Constant.DEFAULT_RESOURCE_LIST;

        resourcesList
                .forEach(resource -> {
                    if (resource.getType() != ResourceType.BASIC) {
                        resourceMap.put(resource, 0);
                    } else {
                        // random the resource from 0 to 20
                        resourceMap.put(resource, (int) (Math.random() * 20));
                    }
                });

        return resourceMap;
    }
}
