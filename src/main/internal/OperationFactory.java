package main.internal;

public class OperationFactory {
    public Operation createTransformOperation(
            OperationType operation,
            Country country,
            Resource resource,
            int multiple
    ) throws Exception {
        if (operation != OperationType.TRANSFORM) {
            throw new Exception("This operation has e to TRANSFORM");
        }
        return new Operation(operation, country, null, resource, multiple);
    }

    public Operation createTransferOperation(
            OperationType operation,
            Country country,
            Country targetCountry,
            Resource resource,
            int multiple
    ) throws Exception {
        if (operation != OperationType.TRANSFER) {
            throw new Exception("This operation has e to TRANSFER");
        }
        return new Operation(operation, country, targetCountry, resource, multiple);
    }
}
