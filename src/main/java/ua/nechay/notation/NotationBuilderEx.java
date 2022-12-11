package ua.nechay.notation;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author anechaev
 * @since 04.12.2022
 */
public class NotationBuilderEx extends FoldingBuilderEx {
    private static final String JAVA_NAME = "JAVA";
    public NotationBuilderEx() {
    }

    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!JAVA_NAME.equalsIgnoreCase(root.getLanguage().getID())) {
            return new FoldingDescriptor[0];
        }
        if (PsiTreeUtil.hasErrorElements(root)) {
            return new FoldingDescriptor[0];
        }
        List<PsiCodeBlock> blocks = findChildren(root, PsiClass.class)
                .map(PsiClass::getAllMethods)
                .flatMap(Arrays::stream)
                .map(PsiMethod::getBody)
                .filter(Objects::nonNull)
                .toList();
        return new FoldingDescriptor[0];
    }

    @NotNull
    private <T extends PsiElement> Stream<T> findChildren(@NotNull PsiElement element, @NotNull Class<T> type) {
        return PsiTreeUtil.findChildrenOfType(element, type).stream();
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
