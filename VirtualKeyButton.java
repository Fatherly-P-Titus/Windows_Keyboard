package com.virtualkeyboard;
// VirtualKeyButton.java
//package com.virtualkeyboard;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class VirtualKeyButton extends Button {
    private final String keyText;
    private final KeyCode keyCode;
    private boolean isDarkTheme = false;
    
    public VirtualKeyButton(String text, KeyCode keyCode) {
        super(text);
        this.keyText = text;
        this.keyCode = keyCode;
        
        initializeButton();
    }
    
    private void initializeButton() {
        this.setPrefSize(40, 40);
        this.setMinSize(30, 30);
        this.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");
        
        // Set up mouse click event
        this.setOnAction(e -> {
            fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, 
                null, null, keyCode, 
                false, false, false, false));
        });
        
        applyTheme(false); // Start with light theme
    }
    
    public void applyTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
        if (isDarkTheme) {
            this.setStyle(this.getStyle() + 
                "-fx-background-color: #555555; -fx-text-fill: white; -fx-border-color: #777777;");
        } else {
            this.setStyle(this.getStyle() + 
                "-fx-background-color: #ffffff; -fx-text-fill: black; -fx-border-color: #cccccc;");
        }
    }
    
    public String getKeyText() {
        return keyText;
    }
    
    public KeyCode getKeyCode() {
        return keyCode;
    }
}