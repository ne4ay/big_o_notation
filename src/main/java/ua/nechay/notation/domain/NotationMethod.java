package ua.nechay.notation.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * TODO: add args
 *
 * @author anechaev
 * @since 16.12.2022
 */
public class NotationMethod {
    private final String name;
    private final NotationComplexity complexity;

    public NotationMethod(@NotNull String name, @NotNull NotationComplexity complexity) {
        this.name = name;
        this.complexity = complexity;
    }

    @NotNull
    public static NotationMethod from(@NotNull String name, @NotNull NotationComplexity complexity) {
        return new NotationMethod(name, complexity);
    }

    public static NotationMethod from(@NotNull NotationCollectionMethod method, @NotNull NotationComplexity complexity) {
        return new NotationMethod(method.getPrintableName(), complexity);
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public NotationComplexity getComplexity() {
        return complexity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NotationMethod that))
            return false;
        return Objects.equals(getName(), that.getName()) && getComplexity() == that.getComplexity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getComplexity());
    }
}
