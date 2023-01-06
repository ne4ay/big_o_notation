package ua.nechay.notation.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.nechay.notation.domain.NotationCollectionMethod.ADD;
import static ua.nechay.notation.domain.NotationCollectionMethod.CONTAINS;
import static ua.nechay.notation.domain.NotationCollectionMethod.GET;
import static ua.nechay.notation.domain.NotationCollectionMethod.REMOVE;
import static ua.nechay.notation.domain.NotationComplexity.CONSTANT;
import static ua.nechay.notation.domain.NotationComplexity.LINEAR;

/**
 * @author anechaev
 * @since 11.12.2022
 */
public enum NotationDataStructure {
    ARRAY_LIST(
        NotationMethod.from(ADD, CONSTANT),
        NotationMethod.from(GET, CONSTANT),
        NotationMethod.from(REMOVE, LINEAR),
        NotationMethod.from(CONTAINS, LINEAR)
    ),
    LINKED_LIST(
        NotationMethod.from(ADD, CONSTANT),
        NotationMethod.from(GET, LINEAR),
        NotationMethod.from(REMOVE, CONSTANT),
        NotationMethod.from(CONTAINS, LINEAR)
    ),
    COPY_ON_WRITE_ARRAY_LIST(
        NotationMethod.from(ADD, LINEAR),
        NotationMethod.from(GET, CONSTANT),
        NotationMethod.from(REMOVE, LINEAR),
        NotationMethod.from(CONTAINS, LINEAR)
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
        Map.entry("ArrayList", ARRAY_LIST),
        Map.entry("LinkedList", LINKED_LIST),
        Map.entry("CopyOnWriteArrayList", COPY_ON_WRITE_ARRAY_LIST)
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
