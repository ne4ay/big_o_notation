package ua.nechay.notation.domain;

import org.jetbrains.annotations.NotNull;

/**
 * @author anechaev
 * @since 11.12.2022
 */
public enum NotationCollectionMethod {
    ADD("add")
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
