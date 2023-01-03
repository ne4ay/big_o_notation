package ua.nechay.notation.inlay;

import com.intellij.codeInsight.hints.InlayHintsCollector;
import com.intellij.codeInsight.hints.InlayHintsSink;
import com.intellij.codeInsight.hints.presentation.InlayTextMetricsStorage;
import com.intellij.codeInsight.hints.presentation.PresentationFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import ua.nechay.notation.utils.NotationUtils;
import ua.nechay.notation.presentation.NotationInlayHintPresentation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static com.intellij.util.ObjectUtils.tryCast;
import static ua.nechay.notation.utils.NotationUtils.JAVA_NAME;

/**
 * @author anechaev
 * @since 18.12.2022
 */
@SuppressWarnings("UnstableApiUsage")
public class NotationInlayHintsCollector implements InlayHintsCollector {
    private static final Key<InlayTextMetricsStorage> TEXT_METRICS_STORAGE
        = new Key<>("InlayTextMetricsStorage");
    @Override
    public boolean collect(@NotNull PsiElement element, @NotNull Editor editor, @NotNull InlayHintsSink inlayHintsSink) {
        PsiJavaFile root = tryCast(element.getContainingFile(), PsiJavaFile.class);
        if (root == null) return true;
        if (!JAVA_NAME.equalsIgnoreCase(root.getLanguage().getID())) {
          return false;
        }
        if (PsiTreeUtil.hasErrorElements(root)) {
            return true;
        }
        if (!(editor instanceof EditorImpl)) {
            return false;
        }
        PresentationFactory factory = new PresentationFactory((EditorImpl)editor);
        NotationUtils.findChildren(root, PsiClass.class)
            .map(foundClass -> foundClass.getAllMethods())
            .flatMap(Arrays::stream)
            .map(method -> method.getBody())
            .filter(Objects::nonNull)
            .map(new NotationInlayElementsCollector()::handleBlock)
            .flatMap(Collection::stream)
            .map(info -> NotationInlayHintPresentation.fromHintInfo(factory, info))
            .forEach(presentation -> addHint(inlayHintsSink, presentation));
        return false;
    }

    private void addHint(InlayHintsSink inlayHintsSink, NotationInlayHintPresentation presentation) {
        inlayHintsSink.addInlineElement(presentation.getOffset(), true, presentation, false);
    }

    private InlayTextMetricsStorage getTextMetricStorage(EditorImpl editor) {
        InlayTextMetricsStorage storage = editor.getUserData(TEXT_METRICS_STORAGE);
        if (storage != null) {
            return storage;
        }
        InlayTextMetricsStorage newStorage = new InlayTextMetricsStorage(editor);
        editor.putUserData(TEXT_METRICS_STORAGE, newStorage);
        return newStorage;
    }
}
