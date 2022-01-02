package tr.edu.ku.devnull.needforspear.Model.UIModels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

// Taken from https://stackoverflow.com/a/44006131/14497846.

/**
 * FocusableJTextField is a class which prevents the text inside the JTextFields to disappear
 * FocusableJTextField extends JTextField and implements FocusListener.
 */
public class FocusableJTextField extends JTextField implements FocusListener {

    private final String placeholder;
    private boolean changedText = false;

    /**
     * Constructor of FocusableJTextField.
     *
     * @param placeholder Takes argument to set display text on the text field.
     */
    public FocusableJTextField(String placeholder) {
        super();
        this.placeholder = Objects.requireNonNull(placeholder);
        this.addFocusListener(this);
        super.setText(placeholder);
        super.setForeground(Color.GRAY);
    }

    /**
     * Gets current text on the field.
     *
     * @return Display text of the field.
     */
    @Override
    public String getText() {
        if (this.changedText) {
            return super.getText();
        } else {
            return "";
        }
    }

    /**
     * Sets placeholder if empty, otherwise returns what it is written already.
     *
     * @param t String to be set field to.
     */
    @Override
    public void setText(String t) {
        if (t == null || t.isEmpty()) {
            super.setText(this.placeholder);
            this.changedText = false;
        } else {
            super.setText(t);
            this.changedText = true;
        }
    }

    /**
     * When user clicks on the field this method is triggered.
     *
     * @param e Data about focus event.
     */
    @Override
    public void focusGained(FocusEvent e) {
        if (!this.changedText) {
            super.setText("");
            super.setForeground(Color.BLACK);
            this.changedText = true;
        }
    }

    /**
     * When user clicks outside of the field this method is triggered.
     *
     * @param e Data about focus event.
     */
    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setForeground(Color.GRAY);
            super.setText(this.placeholder);
            this.changedText = false;
        } else {
            this.changedText = true;
        }
    }
}
