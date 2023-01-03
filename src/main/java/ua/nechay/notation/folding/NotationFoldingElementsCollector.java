package ua.nechay.notation.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import ua.nechay.notation.AbstractNotationElementsCollector;
import ua.nechay.notation.domain.NotationDataStructure;
import ua.nechay.notation.domain.NotationMethod;
import ua.nechay.notation.domain.auxiliary.NotationMethodCallInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author anechaev
 * @since 30.12.2022
 */
public class NotationFoldingElementsCollector extends AbstractNotationElementsCollector<FoldingDescriptor> {
    @Override
    protected @NotNull Optional<FoldingDescriptor> merge(
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
        String placeholderText = method.getComplexity().getPrintableName();
        if (callExpressionElem instanceof ASTNode astNode) {
            return Optional.of(new FoldingDescriptor(astNode, callExpressionElem.getTextRange(),
                null, placeholderText));
        }
        return Optional.empty();
    }
}
