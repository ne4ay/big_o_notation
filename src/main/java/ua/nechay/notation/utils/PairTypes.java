package ua.nechay.notation.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author anechaev
 * @since 18.12.2022
 */
public final class PairTypes {

    private PairTypes() { }

    public static class PairMethodCall extends Pair<PsiMethodCallExpression, List<PsiElement>> {
        public PairMethodCall(@Nullable PsiMethodCallExpression first, @Nullable List<PsiElement> second) {
            super(first, second);
        }
    }
}
