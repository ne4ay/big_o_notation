package ua.nechay.notation.domain.auxiliary;

import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import ua.nechay.notation.domain.NotationMethod;

import java.util.Objects;

/**
 * @author anechaev
 * @since 02.01.2023
 */
public class NotationHintInfo {

    private final PsiMethodCallExpression callExpressionElem;
    private final NotationMethod method;

    public NotationHintInfo(@NotNull PsiMethodCallExpression callExpressionElem, @NotNull NotationMethod method) {
        this.callExpressionElem = callExpressionElem;
        this.method = method;
    }

    @NotNull
    public PsiMethodCallExpression getCallExpressionElem() {
        return callExpressionElem;
    }

    @NotNull
    public NotationMethod getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NotationHintInfo that))
            return false;
        return Objects.equals(getCallExpressionElem(), that.getCallExpressionElem()) && Objects.equals(getMethod(),
            that.getMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCallExpressionElem(), getMethod());
    }
}
