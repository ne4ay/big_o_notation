package ua.nechay.notation.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.nechay.notation.domain.NotationCollectionMethod.ADD;

/**
 * @author anechaev
 * @since 11.12.2022
 */
public enum NotationDataStructure {
    ARRAY_LIST(
        NotationMethod.from(ADD, NotationComplexity.CONSTANT)
    )
    ;

    //TODO: take into account methods' args
    private final Map<String, List<NotationMethod>>  methods;

    NotationDataStructure(@NotNull Collection<NotationMethod> methods) {
        this.methods = methods.stream()
            .collect(Collectors.groupingBy(
                NotationMethod::getName
            ));
    }

    NotationDataStructure(NotationMethod ... methods) {
        this(Arrays.asList(methods));
    }

    private static final Map<String, NotationDataStructure> structures = Map.ofEntries(
        Map.entry("ArrayList", ARRAY_LIST)
    );

    @NotNull
    public Map<String, List<NotationMethod>> getMethods() {
        return methods;
    }

    @Nullable
    public static NotationDataStructure fromName(@NotNull String name) {
        return structures.get(name);
    }
}
