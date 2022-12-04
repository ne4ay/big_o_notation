package ua.nechay.notation;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author anechaev
 * @since 04.12.2022
 */
public class NotationBuilderEx extends FoldingBuilderEx {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (NotationUtils.hasErrors(root)) {
            return new FoldingDescriptor[0];
        }
        PsiClass psiClass = PsiTreeUtil.findChildOfType(root, PsiClass.class);
        return new FoldingDescriptor[0];
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
