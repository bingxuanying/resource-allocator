package main.internal.contant;

import main.internal.Resource;
import main.internal.type.ResourceType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Constant {
    public static final Resource POPULATION = new Resource("Population", ResourceType.BASIC, 0);
    public static final Resource METALLIC_ELEMENTS = new Resource("MetallicElements", ResourceType.BASIC, Math.random() + 0.5);
    public static final Resource TIMBER = new Resource("Timber", ResourceType.BASIC, Math.random() + 0.5);

    public static final Resource METALLIC_ALLOYS = new Resource("MetallicAlloys", ResourceType.CREATED, Math.random() + 1);
    public static final Resource ELECTRONICS = new Resource("Electronics", ResourceType.CREATED, Math.random() + 1.5);
    public static final Resource HOUSING = new Resource("Housing", ResourceType.CREATED, Math.random() + 1.5);

    public static final Resource METALLIC_ALLOYS_WASTE = new Resource("MetallicAlloysWaste", ResourceType.WASTE, -1 * Math.random());
    public static final Resource ELECTRONICS_WASTE = new Resource("ElectronicsWaste", ResourceType.WASTE, -1 * Math.random());
    public static final Resource HOUSING_WASTE = new Resource("HousingWaste", ResourceType.WASTE, -1 * Math.random());

    public static final List<Resource> DEFAULT_RESOURCE_LIST = Arrays.asList(
            POPULATION,
            METALLIC_ELEMENTS,
            TIMBER,
            METALLIC_ALLOYS,
            METALLIC_ALLOYS_WASTE,
            ELECTRONICS,
            ELECTRONICS_WASTE,
            HOUSING,
            HOUSING_WASTE
    );

    public static final Map<Resource, Integer> HOUSING_INPUT_TEMPLATE =
            Map.of(
                    POPULATION, 5,
                    METALLIC_ELEMENTS, 1,
                    TIMBER, 5,
                    METALLIC_ALLOYS, 3
            );

    public static final Map<Resource, Integer> ALLOYS_INPUT_TEMPLATE =
            Map.of(
                    POPULATION, 1,
                    METALLIC_ELEMENTS, 2
            );

    public static final Map<Resource, Integer> ELECTRONICS_INPUT_TEMPLATE =
            Map.of(
                    POPULATION, 1,
                    METALLIC_ELEMENTS, 3,
                    METALLIC_ALLOYS, 2
            );

    public static final Map<Resource, Map<Resource, Integer>> MANUFACTURING_INPUT_MANUAL =
            Map.of(
                    HOUSING, HOUSING_INPUT_TEMPLATE,
                    METALLIC_ALLOYS, ALLOYS_INPUT_TEMPLATE,
                    ELECTRONICS, ELECTRONICS_INPUT_TEMPLATE
            );

    public static final Map<Resource, Integer> HOUSING_OUTPUT_TEMPLATE =
            Map.of(
                    HOUSING, 1,
                    HOUSING_WASTE, 1,
                    POPULATION, 5
            );

    public static final Map<Resource, Integer> ALLOYS_OUTPUT_TEMPLATE =
            Map.of(
                    POPULATION, 1,
                    METALLIC_ALLOYS, 1,
                    METALLIC_ALLOYS_WASTE, 1
            );

    public static final Map<Resource, Integer> ELECTRONICS_OUTPUT_TEMPLATE =
            Map.of(
                    POPULATION, 1,
                    ELECTRONICS, 2,
                    ELECTRONICS_WASTE, 1
            );

    public static final Map<Resource, Map<Resource, Integer>> MANUFACTURING_OUTPUT_MANUAL =
            Map.of(
                    HOUSING, HOUSING_OUTPUT_TEMPLATE,
                    METALLIC_ALLOYS, ALLOYS_OUTPUT_TEMPLATE,
                    ELECTRONICS, ELECTRONICS_OUTPUT_TEMPLATE
            );
}

