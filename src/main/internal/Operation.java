package main.internal;

import java.util.PriorityQueue;
import java.util.Queue;

public class Operation {
    private final OperationType type;
    private final Country originCountry;
    private final Country targetCountry;
    private final Resource resource;
    private final int multiple;
    private final Operation parentOperation;
    private final int depth;
    private final Queue<Operation> childOperations = new PriorityQueue<>();

    public Operation(Country originCountry) {
        this.originCountry = originCountry;
        this.targetCountry = null;
        this.type = OperationType.ROOT;
        this.resource = null;
        this.multiple = 0;
        this.parentOperation = null;
        this.depth = 0;
    }

    public Operation(
            OperationType type,
            Country originCountry,
            Country targetCountry,
            Resource resource,
            int multiple,
            Operation parentOperation
    ) {
        this.type = type;
        this.originCountry = originCountry;
        this.targetCountry = targetCountry;
        this.resource = resource;
        this.multiple = multiple;
        this.parentOperation = parentOperation;
        this.depth = parentOperation.getDepth() + 1;
    }

    public Operation(
            OperationType type,
            Country originCountry,
            Country targetCountry,
            Resource resource,
            int multiple,
            Operation parentOperation,
            int depth
    ) {
        this.type = type;
        this.originCountry = originCountry;
        this.targetCountry = targetCountry;
        this.resource = resource;
        this.multiple = multiple;
        this.parentOperation = parentOperation;
        this.depth = depth;
    }

    public OperationType getType() {
        return type;
    }

    public Country getOriginCountry() {
        return originCountry;
    }

    public Country getTargetCountry() {
        return targetCountry;
    }

    public Resource getResource() {
        return resource;
    }

    public int getMultiple() {
        return multiple;
    }

    public Operation getParentOperation() {
        return parentOperation;
    }

    public int getDepth() {
        return depth;
    }

    public Queue<Operation> getChildOperations() {
        return childOperations;
    }

    @Override
    public Operation clone() {
        return new Operation(
                this.type,
                this.originCountry.clone(),
                this.targetCountry != null ? this.targetCountry.clone() : null,
                this.resource != null ? this.resource.clone() : null,
                this.multiple,
                this.parentOperation != null ? this.parentOperation.clone() : null,
                this.depth
        );
    }

    @Override
    public String toString() {
        if (type == OperationType.TRANSFER) {
            return type.name() + " " +
                    multiple + " " +
                    resource.getName() + " from " +
                    originCountry.getName() + " to " +
                    targetCountry.getName();
        } else if (type == OperationType.TRANSFORM) {
            return type.name() + " " +
                    multiple + " " +
                    resource.getName() + " by " +
                    originCountry.getName();
        }

        return "Operation{" +
                "operation=" + type.name() +
                ", originCountry=" + originCountry.getName() +
                ", targetCountry=" + targetCountry.getName() +
                ", resource=" + resource.getName() +
                ", multiple=" + multiple +
                '}';
    }
}
