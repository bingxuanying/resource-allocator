package main.internal;

public class OperationFactory {
    public static Operation createRootOperation(Country country) throws Exception {
        return new Operation(country);
    }

    public static Operation createChildTransformOperation(
            Country country,
            Resource resource,
            int multiple,
            Operation parentOperation
    ) throws Exception {
        return new Operation(OperationType.TRANSFORM, country, null, resource, multiple, parentOperation);
    }

    public static Operation createChildTransferOperation(
            Country country,
            Country targetCountry,
            Resource resource,
            int multiple,
            Operation parentOperation
    ) throws Exception {
        return new Operation(OperationType.TRANSFER, country, targetCountry, resource, multiple, parentOperation);
    }
}
