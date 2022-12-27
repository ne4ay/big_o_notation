package ua.nechay.notation;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author anechaev
 * @since 04.12.2022
 */
public final class NotationUtils {
    public static final String JAVA_NAME = "JAVA";
    private NotationUtils() {}

    @NotNull
    public static  <T extends PsiElement> Stream<T> findChildren(@NotNull PsiElement element, @NotNull Class<T> type) {
        return PsiTreeUtil.findChildrenOfType(element, type).stream();
    }

    @NotNull
    public static Optional<PsiIdentifier> extractClassRef(@NotNull PsiNewExpression newExpression) {
        return Optional.ofNullable(newExpression.getClassReference())
            .map(PsiElement::getChildren)
            .map(Arrays::stream)
            .flatMap(elems -> elems
                .filter(element -> element instanceof PsiIdentifier)
                .map(element -> (PsiIdentifier) element)
                .findAny());
    }
}
