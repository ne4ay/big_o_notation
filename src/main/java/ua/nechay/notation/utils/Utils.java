package ua.nechay.notation.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author anechaev
 * @since 18.12.2022
 */
public final class Utils {

    private Utils() { }

    @Nullable
    public static String nullIfBlank(@NotNull String maybeBlankString) {
        return maybeBlankString.isBlank() ? null : maybeBlankString;
    }
}
