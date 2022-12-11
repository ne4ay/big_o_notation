package ua.nechay.notation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author anechaev
 * @since 11.12.2022
 */
public enum NotationDataStructure {
    ARRAY_LIST
    ;


    //TODO: Make a new enum with methods+
    private final NotationComplexity


    private static final Map<String, NotationDataStructure> structures = Map.ofEntries(
        Map.entry("ArrayList", ARRAY_LIST)
    );
}
