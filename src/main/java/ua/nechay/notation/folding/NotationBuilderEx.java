package ua.nechay.notation.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ua.nechay.notation.NotationUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import static ua.nechay.notation.NotationUtils.JAVA_NAME;

/**
 * @author anechaev
 * @since 04.12.2022
 */
public class NotationBuilderEx extends FoldingBuilderEx {
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
        return NotationUtils.findChildren(root, PsiClass.class)
            .map(foundClass -> foundClass.getAllMethods())
            .flatMap(Arrays::stream)
            .map(method -> method.getBody())
            .filter(Objects::nonNull)
            .map(new NotationFoldingElementsCollector()::handleBlock)
            .flatMap(Collection::stream)
            .toList()
            .toArray(new FoldingDescriptor[0]);
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
