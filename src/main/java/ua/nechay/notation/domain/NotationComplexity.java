package ua.nechay.notation.domain;

import org.jetbrains.annotations.NotNull;

/**
 * @author anechaev
 * @since 11.12.2022
 */
public enum NotationComplexity {
    //Todo: put into extra args (for k and m)
    CONSTANT("O(1)"),
    LOG_N("O(log(n))"),
    LINEAR("O(n)"),
    N_LOG_N("O(n*log(n))"),
    POWER("O(n^k)"),
    EXPONENTIAL("O(k^n)"),
    FACTORIAL("O(n!)")
    ;

    private final String printableName;

    NotationComplexity(@NotNull String printableName) {
        this.printableName = printableName;
    }

    public String getPrintableName() {
        return printableName;
    }
}
