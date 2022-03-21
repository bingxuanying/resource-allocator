package main.internal;

import java.util.HashMap;
import java.util.Map;

public class Country {
    private final String name;
    private Map<Resource, Integer> resourceMap = new HashMap<>();

    public Country(String name, Map<Resource, Integer> resourceMap) {
        this.name = name;
        this.resourceMap = resourceMap;
    }

    public String getName() {
        return name;
    }

    public Map<Resource, Integer> getResourceMap() {
        return resourceMap;
    }
}
