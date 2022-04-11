package main.internal;

import main.internal.type.ResourceType;

public class Resource {

    private final String name;
    private final ResourceType type;
    private final double weight;
    private final boolean transferable;
    private final boolean renewable;

    public Resource(String name, ResourceType type, double weight) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.transferable = true;
        this.renewable = false;
    }

    public Resource(String name, ResourceType type, double weight, boolean transferable, boolean renewable) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.transferable = transferable;
        this.renewable = renewable;
    }

    public String getName() {
        return name;
    }

    public ResourceType getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isTransferable() {
        return transferable;
    }

    public boolean isRenewable() {
        return renewable;
    }

    @Override
    protected Resource clone() {
        return this;
    }
}
