package ua.nechay.notation;

import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.annotations.NotNull;
import ua.nechay.notation.domain.NotationDataStructure;
import ua.nechay.notation.domain.auxiliary.NotationMethodCallInfo;
import ua.nechay.notation.domain.auxiliary.Pair;
import ua.nechay.notation.utils.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ua.nechay.notation.utils.NotationUtils.extractClassRef;

/**
 * @author anechaev
 * @since 30.12.2022
 */
public abstract class AbstractNotationElementsCollector<T> {
    @NotNull
    public Collection<T> handleBlock(@NotNull PsiCodeBlock codeBlock) {
        Map<String, NotationDataStructure> varToDataStructure = new HashMap<>();
        return Arrays.stream(codeBlock.getChildren())
            .map(statement -> handleStatement(statement, varToDataStructure))
            .flatMap(List::stream)
            .toList();
    }

    @NotNull
    private List<T> handleStatement(
        @NotNull PsiElement element,
        @NotNull Map<String, NotationDataStructure> varAccumulator)
    {
        if (element instanceof PsiDeclarationStatement declaration) {
            return handleDeclaration(declaration, varAccumulator);
        } else if (element instanceof PsiExpressionStatement expression) {
            return handleExpression(expression, varAccumulator);
        }
        return Collections.emptyList();
    }

    private List<T> handleExpression(
        @NotNull PsiExpressionStatement expression,
        @NotNull Map<String, NotationDataStructure> varAccumulator)
    {
        return Arrays.stream(expression.getChildren())
            .filter(child -> child instanceof PsiMethodCallExpression)
            .map(child -> (PsiMethodCallExpression) child)
            .map(callExpr -> Pair.of(callExpr, List.of(callExpr.getChildren())))
            .map(callWithChildren -> determineDescriptorForMethodCall(callWithChildren, varAccumulator))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }

    @NotNull
    private Optional<T> determineDescriptorForMethodCall(
        @NotNull Pair<PsiMethodCallExpression, List<PsiElement>> callWithChildren,
        @NotNull Map<String, NotationDataStructure> varAccumulator)
    {
        Pair<PsiMethodCallExpression, Optional<NotationMethodCallInfo>> callInfo = callWithChildren.mapSecond(children -> children
            .stream()
            .filter(elem -> elem instanceof PsiReferenceExpression)
            .findFirst()
            .map(elem -> (PsiReferenceExpression) elem)
            .flatMap(AbstractNotationElementsCollector::determineMethodCallInfo));
        if (callInfo.getSecond().isEmpty()) {
            return Optional.empty();
        }
        return callInfo
            .mapSecond(Optional::get)
            .merge((callElem, info) -> merge(callElem, info, varAccumulator));
    }

    @NotNull
    protected abstract Optional<T> merge(
        @NotNull PsiMethodCallExpression callExpressionElem,
        @NotNull NotationMethodCallInfo callInfo,
        @NotNull Map<String, NotationDataStructure> varAccumulator);

    @NotNull
    private static Optional<NotationMethodCallInfo> determineMethodCallInfo(
        @NotNull PsiReferenceExpression callRefExpression)
    {
        String varName = null;
        String methodName = null;
        for (var child : callRefExpression.getChildren()) {
            if (child instanceof PsiReferenceExpression varExpression) {
                varName = Arrays.stream(varExpression.getChildren())
                    .filter(varElem -> varElem instanceof PsiIdentifier)
                    .findFirst()
                    .map(PsiElement::getText)
                    .orElse(null);
            } else if (child instanceof PsiIdentifier callIdentifier) {
                methodName = Utils.nullIfBlank(callIdentifier.getText());
            }
        }
        return NotationMethodCallInfo.maybeInfo(varName, methodName);
    }


    @NotNull
    private List<T> handleDeclaration(
        @NotNull PsiDeclarationStatement declarationStatement,
        @NotNull Map<String, NotationDataStructure> varToDataStructure)
    {
        return Arrays.stream(declarationStatement.getDeclaredElements())
            .map(element -> handleDeclaredElement(element, varToDataStructure))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }

    @NotNull
    private Optional<T> handleDeclaredElement(
        @NotNull PsiElement element,
        @NotNull Map<String, NotationDataStructure> varToDataStructure)
    {
        if (element instanceof PsiLocalVariable localVariable) {
            return handleLocalVariable(localVariable, varToDataStructure);
        }
        return Optional.empty();
    }

    @NotNull
    private Optional<T> handleLocalVariable(
        @NotNull PsiLocalVariable localVariable,
        @NotNull Map<String, NotationDataStructure> varToDataStructure)
    {
        PsiIdentifier variableIdentifier = null;
        NotationDataStructure dataStructureRef = null;
        for (var child : localVariable.getChildren()) {
            if (child instanceof PsiIdentifier identifier) {
                variableIdentifier = identifier;
            } else if (child instanceof PsiNewExpression newExpression) {
                dataStructureRef = extractClassRef(newExpression)
                    .map(PsiIdentifier::getText)
                    .map(NotationDataStructure::fromName)
                    .orElse(null);
            }
            if (variableIdentifier != null && dataStructureRef != null) { //just for optimization
                varToDataStructure.put(variableIdentifier.getText(), dataStructureRef);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
