package main.internal;

import main.internal.type.OperationType;

public class Operation {
    private final OperationType type;
    private final String destinationCountryName;
    private final String originCountryName;
    private final Resource resource;
    private final int multiple;

    public Operation(
            OperationType type,
            String destinationCountryName,
            String originCountryName,
            Resource resource,
            int multiple
    ) {
        this.type = type;
        this.destinationCountryName = destinationCountryName;
        this.originCountryName = originCountryName;
        this.resource = resource;
        this.multiple = multiple;
    }

    public OperationType getType() {
        return type;
    }

    public String getOriginCountryName() {
        return originCountryName;
    }

    public String getDestinationCountryName() {
        return destinationCountryName;
    }

    public Resource getResource() {
        return resource;
    }

    public int getMultiple() {
        return multiple;
    }

    @Override
    public Operation clone() {
        return new Operation(
                this.type,
                this.destinationCountryName,
                this.originCountryName,
                this.resource != null ? this.resource.clone() : null,
                this.multiple
        );
    }

    @Override
    public String toString() {
        if (type == OperationType.TRANSFER) {
            return type.name() + " " +
                    multiple + " " +
                    resource.getName() + " from " +
                    originCountryName + " to " +
                    destinationCountryName;
        } else if (type == OperationType.TRANSFORM) {
            return type.name() + " " +
                    multiple + " " +
                    resource.getName() + " by " +
                    destinationCountryName;
        }

        return "Operation{" +
                "operation=" + type.name() +
                ", destinationCountryName=" + destinationCountryName +
                ", targetCountry=" + originCountryName +
                ", resource=" + resource.getName() +
                ", multiple=" + multiple +
                '}';
    }
}
