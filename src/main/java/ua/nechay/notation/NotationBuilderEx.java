package ua.nechay.notation;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author anechaev
 * @since 04.12.2022
 */
public class NotationBuilderEx extends FoldingBuilderEx {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!root.getLanguage().is(JavaLanguage.INSTANCE)) {
            return new FoldingDescriptor[0];
        }
        if (!PsiTreeUtil.hasErrorElements(root)) {
            return new FoldingDescriptor[0];
        }
        findChildren(root, PsiClass.class)
            .flatMap(psiClass -> findChildren(psiClass, PsiMethod.class))
            .flatMap(psiMethod -> findChildren(psiMethod, PsiCodeBlock.class))
            .collect(Collectors.toList());
        return new FoldingDescriptor[0];
    }

    private <T extends PsiElement> Stream<T> findChildren(@NotNull PsiElement element, @NotNull Class<T> type) {
        return PsiTreeUtil.findChildrenOfType(element, type).stream();
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        return null;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
