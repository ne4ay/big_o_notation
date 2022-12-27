package ua.nechay.notation.inlay;

import com.intellij.codeInsight.hints.ImmediateConfigurable;
import com.intellij.codeInsight.hints.InlayHintsCollector;
import com.intellij.codeInsight.hints.InlayHintsProvider;
import com.intellij.codeInsight.hints.InlayHintsSink;
import com.intellij.codeInsight.hints.NoSettings;
import com.intellij.codeInsight.hints.SettingsKey;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TODO: settings
 *
 * @author anechaev
 * @since 18.12.2022
 */
public class NotationInlayHintsProvider implements InlayHintsProvider<NoSettings> {
    private static final SettingsKey<NoSettings> KEY = new SettingsKey<>("ComplexityNotation");

    @NotNull
    @Override
    public SettingsKey<NoSettings> getKey() {
        return KEY;
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getName() {
        return "Complexity notation";
    }

    @Nullable
    @Override
    public String getPreviewText() {
        return null;
    }

    @NotNull
    @Override
    public ImmediateConfigurable createConfigurable(@NotNull NoSettings noSettings) {
        return new NotationImmediateConfigurable();
    }

    @NotNull
    @Override
    public NoSettings createSettings() {
        return new NoSettings();
    }

    @Nullable
    @Override
    public InlayHintsCollector getCollectorFor(@NotNull PsiFile psiFile, @NotNull Editor editor, @NotNull NoSettings noSettings,
        @NotNull InlayHintsSink inlayHintsSink)
    {
        return new NotationInlayHintsCollector();
    }
}
