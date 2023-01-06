package ua.nechay.notation.presentation;

import com.intellij.codeInsight.hints.presentation.InlayPresentation;
import com.intellij.codeInsight.hints.presentation.PresentationFactory;
import com.intellij.codeInsight.hints.presentation.PresentationListener;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import ua.nechay.notation.domain.auxiliary.NotationHintInfo;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author anechaev
 * @since 02.01.2023
 */
public class NotationInlayHintPresentation implements InlayPresentation {

    private final InlayPresentation inlayPresentation;
    private final int offset;

    public NotationInlayHintPresentation(@NotNull InlayPresentation inlayPresentation, int offset) {
        this.inlayPresentation = inlayPresentation;
        this.offset = offset;
    }

    @NotNull
    public static NotationInlayHintPresentation fromHintInfo(@NotNull PresentationFactory factory, @NotNull NotationHintInfo hintInfo) {
        //TODO: add args instead of n or k
        PsiMethodCallExpression callExpression = hintInfo.getCallExpressionElem();
        var textPresentation = factory.text(hintInfo.getMethod().getComplexity().getPrintableName());
        var psiRefPresentation = factory.psiSingleReference(textPresentation, () -> callExpression);
        var roundedPresentation = factory.roundWithBackground(psiRefPresentation);
        int offset = callExpression.getTextOffset();
        int length = callExpression.getText().length();
        return new NotationInlayHintPresentation(roundedPresentation, offset + length);
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public int getHeight() {
        return inlayPresentation.getHeight();
    }

    @Override
    public int getWidth() {
        return inlayPresentation.getWidth();
    }

    @Override
    public void addListener(@NotNull PresentationListener presentationListener) {
        inlayPresentation.addListener(presentationListener);
    }

    @Override
    public void fireContentChanged(@NotNull Rectangle rectangle) {
        inlayPresentation.fireContentChanged(rectangle);
    }

    @Override
    public void fireSizeChanged(@NotNull Dimension dimension, @NotNull Dimension dimension1) {
        inlayPresentation.fireSizeChanged(dimension, dimension1);
    }

    @Override
    public void paint(@NotNull Graphics2D graphics2D, @NotNull TextAttributes textAttributes) {
        inlayPresentation.paint(graphics2D, textAttributes);
    }

    @Override
    public void removeListener(@NotNull PresentationListener presentationListener) {
        inlayPresentation.removeListener(presentationListener);
    }
}
