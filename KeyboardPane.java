package com.virtualkeyboard;
// KeyboardPane.java
//package com.virtualkeyboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class KeyboardPane extends VBox {
    private static final double BASE_FONT_SIZE = 14;
    private static final double SIZE_INCREMENT = 2;
    private static final double MIN_FONT_SIZE = 10;
    private static final double MAX_FONT_SIZE = 24;
    
    private double currentFontSize = BASE_FONT_SIZE;
    private List<VirtualKeyButton> allKeys;
    private boolean capsLock = false;
    private boolean shiftPressed = false;
    
    public KeyboardPane() {
        super(5);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        this.allKeys = new ArrayList<>();
        initializeKeyboard();
    }
    
    private void initializeKeyboard() {
        // Row 1: Function keys and numbers
        HBox row1 = createNumberRow();
        
        // Row 2: First letter row
        HBox row2 = createFirstLetterRow();
        
        // Row 3: Second letter row
        HBox row3 = createSecondLetterRow();
        
        // Row 4: Third letter row
        HBox row4 = createThirdLetterRow();
        
        // Row 5: Space bar and special keys
        HBox row5 = createBottomRow();
        
        this.getChildren().addAll(row1, row2, row3, row4, row5);
    }
    
    private HBox createNumberRow() {
        HBox row = new HBox(3);
        row.setAlignment(Pos.CENTER);
        
        String[] keys = {"`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "Backspace"};
        KeyCode[] keyCodes = {KeyCode.BACK_QUOTE, KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, 
                             KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7,
                             KeyCode.DIGIT8, KeyCode.DIGIT9, KeyCode.DIGIT0, KeyCode.MINUS,
                             KeyCode.EQUALS, KeyCode.BACK_SPACE};
        
        for (int i = 0; i < keys.length; i++) {
            VirtualKeyButton button = new VirtualKeyButton(keys[i], keyCodes[i]);
            if (keys[i].equals("Backspace")) {
                button.setPrefWidth(120);
            }
            row.getChildren().add(button);
            allKeys.add(button);
        }
        
        return row;
    }
    
    private HBox createFirstLetterRow() {
        HBox row = new HBox(3);
        row.setAlignment(Pos.CENTER);
        
        String[] keys = {"Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\"};
        KeyCode[] keyCodes = {KeyCode.TAB, KeyCode.Q, KeyCode.W, KeyCode.E, KeyCode.R, 
                             KeyCode.T, KeyCode.Y, KeyCode.U, KeyCode.I, KeyCode.O, 
                             KeyCode.P, KeyCode.OPEN_BRACKET, KeyCode.CLOSE_BRACKET, KeyCode.BACK_SLASH};
        
        for (int i = 0; i < keys.length; i++) {
            VirtualKeyButton button = new VirtualKeyButton(keys[i], keyCodes[i]);
            if (keys[i].equals("Tab")) {
                button.setPrefWidth(80);
            }
            row.getChildren().add(button);
            allKeys.add(button);
        }
        
        return row;
    }
    
    private HBox createSecondLetterRow() {
        HBox row = new HBox(3);
        row.setAlignment(Pos.CENTER);
        
        String[] keys = {"Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "Enter"};
        KeyCode[] keyCodes = {KeyCode.CAPS, KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.F, 
                             KeyCode.G, KeyCode.H, KeyCode.J, KeyCode.K, KeyCode.L, 
                             KeyCode.SEMICOLON, KeyCode.QUOTE, KeyCode.ENTER};
        
        for (int i = 0; i < keys.length; i++) {
            VirtualKeyButton button = new VirtualKeyButton(keys[i], keyCodes[i]);
            if (keys[i].equals("Caps")) {
                button.setPrefWidth(90);
                button.setOnAction(e -> toggleCapsLock());
            } else if (keys[i].equals("Enter")) {
                button.setPrefWidth(100);
            }
            row.getChildren().add(button);
            allKeys.add(button);
        }
        
        return row;
    }
    
    private HBox createThirdLetterRow() {
        HBox row = new HBox(3);
        row.setAlignment(Pos.CENTER);
        
        String[] keys = {"Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "Shift"};
        KeyCode[] keyCodes = {KeyCode.SHIFT, KeyCode.Z, KeyCode.X, KeyCode.C, KeyCode.V, 
                             KeyCode.B, KeyCode.N, KeyCode.M, KeyCode.COMMA, 
                             KeyCode.PERIOD, KeyCode.SLASH, KeyCode.SHIFT};
        
        for (int i = 0; i < keys.length; i++) {
            VirtualKeyButton button = new VirtualKeyButton(keys[i], keyCodes[i]);
            if (keys[i].equals("Shift")) {
                button.setPrefWidth(100);
                button.setOnAction(e -> toggleShift());
            }
            row.getChildren().add(button);
            allKeys.add(button);
        }
        
        return row;
    }
    
    private HBox createBottomRow() {
        HBox row = new HBox(3);
        row.setAlignment(Pos.CENTER);
        
        // Control, Windows, Alt, Space, Alt, Windows, Menu, Control
        VirtualKeyButton ctrl1 = new VirtualKeyButton("Ctrl", KeyCode.CONTROL);
        VirtualKeyButton win1 = new VirtualKeyButton("Win", KeyCode.WINDOWS);
        VirtualKeyButton alt1 = new VirtualKeyButton("Alt", KeyCode.ALT);
        VirtualKeyButton space = new VirtualKeyButton(" ", KeyCode.SPACE);
        VirtualKeyButton alt2 = new VirtualKeyButton("Alt", KeyCode.ALT);
        VirtualKeyButton win2 = new VirtualKeyButton("Win", KeyCode.WINDOWS);
        VirtualKeyButton menu = new VirtualKeyButton("Menu", KeyCode.CONTEXT_MENU);
        VirtualKeyButton ctrl2 = new VirtualKeyButton("Ctrl", KeyCode.CONTROL);
        
        space.setPrefWidth(200);
        ctrl1.setPrefWidth(70);
        win1.setPrefWidth(70);
        alt1.setPrefWidth(70);
        alt2.setPrefWidth(70);
        win2.setPrefWidth(70);
        menu.setPrefWidth(70);
        ctrl2.setPrefWidth(70);
        
        row.getChildren().addAll(ctrl1, win1, alt1, space, alt2, win2, menu, ctrl2);
        
        allKeys.add(ctrl1);
        allKeys.add(win1);
        allKeys.add(alt1);
        allKeys.add(space);
        allKeys.add(alt2);
        allKeys.add(win2);
        allKeys.add(menu);
        allKeys.add(ctrl2);
        
        return row;
    }
    
    private void toggleCapsLock() {
        capsLock = !capsLock;
        updateKeyLabels();
    }
    
    private void toggleShift() {
        shiftPressed = !shiftPressed;
        updateKeyLabels();
    }
    
    private void updateKeyLabels() {
        for (VirtualKeyButton key : allKeys) {
            String originalText = key.getKeyText();
            if (originalText.length() == 1 && Character.isLetter(originalText.charAt(0))) {
                if (capsLock || shiftPressed) {
                    key.setText(originalText.toUpperCase());
                } else {
                    key.setText(originalText.toLowerCase());
                }
            }
        }
    }
    
    public void increaseSize() {
        if (currentFontSize < MAX_FONT_SIZE) {
            currentFontSize += SIZE_INCREMENT;
            applyFontSize();
        }
    }
    
    public void decreaseSize() {
        if (currentFontSize > MIN_FONT_SIZE) {
            currentFontSize -= SIZE_INCREMENT;
            applyFontSize();
        }
    }
    
    public void resetSize() {
        currentFontSize = BASE_FONT_SIZE;
        applyFontSize();
    }
    
    private void applyFontSize() {
        for (VirtualKeyButton key : allKeys) {
            key.setStyle(String.format("-fx-font-size: %.1fpt;", currentFontSize));
        }
    }
    
    public void applyTheme(boolean isDarkTheme) {
        for (VirtualKeyButton key : allKeys) {
            key.applyTheme(isDarkTheme);
        }
    }
}