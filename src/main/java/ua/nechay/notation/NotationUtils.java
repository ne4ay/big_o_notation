package ua.nechay.notation;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author anechaev
 * @since 04.12.2022
 */
public final class NotationUtils {

    private NotationUtils() {}

    public static boolean hasErrors(@NotNull PsiElement element) {
        if (element instanceof StubBasedPsiElement<?>) {
            var stub = (StubBasedPsiElement<?>) element;
            return stub.getStub() == null && PsiTreeUtil.hasErrorElements(element);
        }
        return PsiTreeUtil.hasErrorElements(element);
    }
}
