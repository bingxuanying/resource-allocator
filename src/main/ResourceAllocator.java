package main;

import main.operations.TradeOperation;
import main.contant.Constant;
import main.internal.Country;
import main.internal.CountryFactory;

public class ResourceAllocator {
    public static void main(String[] args) {
        System.out.println("Hello world");

        CountryFactory countryFactory = new CountryFactory();
        Country self = countryFactory.create("self");
        Country us = countryFactory.create("us");
        Country uk = countryFactory.create("uk");

        try {
            TradeOperation.transform(self, Constant.METALLIC_ALLOYS, 2);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
