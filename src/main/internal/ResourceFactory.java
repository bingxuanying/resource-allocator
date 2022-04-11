package main.internal;

import main.internal.type.ResourceType;

public class ResourceFactory {
    public Resource create(String name, ResourceType type, int weight) {
        return new Resource(name, type, weight, true, false);
    }

    public Resource create(String name, ResourceType type, int weight, boolean transferable) {
        return new Resource(name, type, weight, transferable, false);
    }
}
