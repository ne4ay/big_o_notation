package ua.nechay.notation.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * @author anechaev
 * @since 11.12.2022
 */
public enum NotationCollectionMethod {
    ADD("add"),
    ADD_ALL("addAll"),
    GET("get"),
    REMOVE("remove"),
    REMOVE_ALL("removeAll"),
    CONTAINS("contains"),
    CONTAINS_ALL("containsAll"),
    SET("set"),
    SORT("sort")
    ;

    private final String printableName;

    NotationCollectionMethod(@NotNull String printableName) {
        this.printableName = printableName;
    }

    @NotNull
    public String getPrintableName() {
        return printableName;
    }
}
