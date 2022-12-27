package ua.nechay.notation.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author anechaev
 * @since 07.05.2022
 */
public class Pair<FirstT, SecondT> {
    private final FirstT first;
    private final SecondT second;

    public Pair(@NotNull FirstT first, @NotNull SecondT second) {
        this.first = first;
        this.second = second;
    }

    @NotNull
    public static <FirstT, SecondT> Pair<FirstT, SecondT> of(FirstT first, SecondT second) {
        return new Pair<>(first, second);
    }

    @NotNull
    public Pair<SecondT, FirstT> swap() {
        return new Pair<>(second, first);
    }

    @NotNull
    public FirstT getFirst() {
        return first;
    }

    @NotNull
    public SecondT getSecond() {
        return second;
    }

    @NotNull
    public <ResultT> Pair<ResultT, SecondT> mapFirst(Function<FirstT, ResultT> firstFunction) {
        return Pair.of(firstFunction.apply(first), second);
    }

    @NotNull
    public <ResultT> Pair<FirstT, ResultT> mapSecond(Function<SecondT, ResultT> secondFunction) {
        return Pair.of(first, secondFunction.apply(second));
    }

    @NotNull
    public <ResultT> ResultT merge(BiFunction<FirstT, SecondT, ResultT> mergeFunction) {
        return mergeFunction.apply(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Pair<?, ?> pair))
            return false;
        return Objects.equals(getFirst(), pair.getFirst()) && Objects.equals(getSecond(), pair.getSecond());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond());
    }
}
