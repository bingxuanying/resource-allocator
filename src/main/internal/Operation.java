package main.internal;

public class Operation {
    private final OperationType operation;
    private final Country country;
    private final Country targetCountry;
    private final Resource resource;
    private final int multiple;


    public Operation(OperationType operation, Country country, Country targetCountry, Resource resource, int multiple) {
        this.country = country;
        this.targetCountry = targetCountry;
        this.operation = operation;
        this.resource = resource;
        this.multiple = multiple;
    }

    public Country getCountry() {
        return country;
    }

    public OperationType getOperation() {
        return operation;
    }

    public Resource getResource() {
        return resource;
    }

    public int getMultiple() {
        return multiple;
    }

    @Override
    public String toString() {
        if (operation != OperationType.TRANSFER) {
            return operation.name() + " " +
                    multiple + " " +
                    resource.getName() + " from " +
                    country.getName() + " to " +
                    targetCountry.getName();
        } else if (operation != OperationType.TRANSFORM) {
            return operation.name() + " " +
                    multiple + " " +
                    resource.getName() + " by " +
                    country.getName();
        }

        return "Operation{" +
                "operation=" + operation.name() +
                ", country=" + country.getName() +
                ", targetCountry=" + targetCountry.getName() +
                ", resource=" + resource.getName() +
                ", multiple=" + multiple +
                '}';
    }
}
