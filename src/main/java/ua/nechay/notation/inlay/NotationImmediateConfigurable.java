package ua.nechay.notation.inlay;

import com.intellij.codeInsight.hints.ChangeListener;
import com.intellij.codeInsight.hints.ImmediateConfigurable;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * @author anechaev
 * @since 18.12.2022
 */
public class NotationImmediateConfigurable implements ImmediateConfigurable {
    @NotNull
    @Override
    public JComponent createComponent(@NotNull ChangeListener changeListener) {
        JPanel panel = new JPanel();
        panel.setVisible(false);
        return panel;
    }
}
