package ua.nechay.notation.inlay;

import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import ua.nechay.notation.AbstractNotationElementsCollector;
import ua.nechay.notation.domain.NotationDataStructure;
import ua.nechay.notation.domain.NotationMethod;
import ua.nechay.notation.domain.NotationHintInfo;
import ua.nechay.notation.utils.NotationMethodCallInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author anechaev
 * @since 30.12.2022
 */
public class NotationInlayElementsCollector extends AbstractNotationElementsCollector<NotationHintInfo> {
    @Override
    protected @NotNull Optional<NotationHintInfo> merge(
        @NotNull PsiMethodCallExpression callExpressionElem,
        @NotNull NotationMethodCallInfo callInfo,
        @NotNull Map<String, NotationDataStructure> varAccumulator)
    {
        NotationDataStructure dataStructure = varAccumulator.get(callInfo.variableName());
        if (dataStructure == null) {
            return Optional.empty();
        }
        List<NotationMethod> methods = dataStructure.getMethods().get(callInfo.methodName());
        if (methods == null || methods.isEmpty()) {
            return Optional.empty();
        }
        //TODO: distinguish by args
        NotationMethod method = methods.iterator().next();
        return Optional.of(new NotationHintInfo(callExpressionElem, method));
    }
}
