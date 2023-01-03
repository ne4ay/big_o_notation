package ua.nechay.notation.domain.auxiliary;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * @author anechaev
 * @since 18.12.2022
 */
public record NotationMethodCallInfo(@NotNull String variableName, @NotNull String methodName) {

    public static Optional<NotationMethodCallInfo> maybeInfo(@Nullable String variableName, @Nullable String methodName) {
        if (variableName == null) {
            return Optional.empty();
        }
        if (methodName == null) {
            return Optional.empty();
        }
        return Optional.of(new NotationMethodCallInfo(variableName, methodName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NotationMethodCallInfo that))
            return false;
        return variableName.equals(that.variableName) && methodName.equals(that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variableName, methodName);
    }
}
