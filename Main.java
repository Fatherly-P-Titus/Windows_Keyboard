// VirtualKeyboardApp.java
package com.virtualkeyboard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double DEFAULT_WIDTH = 800;
    private static final double DEFAULT_HEIGHT = 300;
    private static final double MIN_WIDTH = 600;
    private static final double MIN_HEIGHT = 200;
    
    private VBox root;
    private TextArea textArea;
    private KeyboardPane keyboardPane;
    private boolean isDarkTheme = false;
    
    @Override
    public void start(Stage primaryStage) {
        initializeUI(primaryStage);
    }
    
    private void initializeUI(Stage stage) {
        // Create main layout
        root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(15));
        
        // Create menu bar
        MenuBar menuBar = createMenuBar();
        
        // Create text area
        textArea = new TextArea();
        textArea.setPromptText("Type using the virtual keyboard or your physical keyboard...");
        textArea.setWrapText(true);
        textArea.setPrefHeight(150);
        
        // Create keyboard
        keyboardPane = new KeyboardPane();
        
        // Add components to root
        root.getChildren().addAll(menuBar, textArea, keyboardPane);
        
        // Create scene
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        applyTheme(scene);
        
        // Set up stage
        stage.setTitle("Virtual Keyboard");
        stage.setScene(scene);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        
        // Handle keyboard events
        setupKeyboardEvents();
        
        stage.show();
    }
    
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());
        fileMenu.getItems().addAll(exitItem);
        
        // View menu
        Menu viewMenu = new Menu("View");
        
        MenuItem lightThemeItem = new MenuItem("Light Theme");
        lightThemeItem.setOnAction(e -> switchToLightTheme());
        
        MenuItem darkThemeItem = new MenuItem("Dark Theme");
        darkThemeItem.setOnAction(e -> switchToDarkTheme());
        
        MenuItem increaseSizeItem = new MenuItem("Increase Size");
        increaseSizeItem.setOnAction(e -> increaseKeyboardSize());
        
        MenuItem decreaseSizeItem = new MenuItem("Decrease Size");
        decreaseSizeItem.setOnAction(e -> decreaseKeyboardSize());
        
        MenuItem resetSizeItem = new MenuItem("Reset Size");
        resetSizeItem.setOnAction(e -> resetKeyboardSize());
        
        viewMenu.getItems().addAll(lightThemeItem, darkThemeItem, 
                                 new SeparatorMenuItem(), 
                                 increaseSizeItem, decreaseSizeItem, resetSizeItem);
        
        menuBar.getMenus().addAll(fileMenu, viewMenu);
        return menuBar;
    }
    
    private void setupKeyboardEvents() {
        // Handle virtual keyboard button clicks
        keyboardPane.setOnKeyPressed(this::handleVirtualKeyPress);
        
        // Handle physical keyboard events
        root.addEventFilter(KeyEvent.KEY_PRESSED, this::handlePhysicalKeyPress);
    }
    
    private void handleVirtualKeyPress(KeyEvent event) {
        if (event.getTarget() instanceof VirtualKeyButton) {
            VirtualKeyButton button = (VirtualKeyButton) event.getTarget();
            String keyText = button.getKeyText();
            KeyCode keyCode = button.getKeyCode();
            
            if (keyCode != null) {
                simulateKeyPress(keyCode, keyText);
            }
        }
    }
    
    private void handlePhysicalKeyPress(KeyEvent event) {
        // You can add specific handling for physical keyboard if needed
        // For now, we'll just let the text area handle it normally
    }
    
    private void simulateKeyPress(KeyCode keyCode, String keyText) {
        switch (keyCode) {
            case BACK_SPACE:
                if (textArea.getLength() > 0) {
                    textArea.deleteText(textArea.getLength() - 1, textArea.getLength());
                }
                break;
            case ENTER:
                textArea.appendText("\n");
                break;
            case TAB:
                textArea.appendText("    ");
                break;
            case SPACE:
                textArea.appendText(" ");
                break;
            case SHIFT:
            case CONTROL:
            case ALT:
                // Modifier keys - handled by the keyboard pane
                break;
            default:
                if (keyText.length() == 1) {
                    textArea.appendText(keyText);
                }
                break;
        }
    }
    
    private void switchToDarkTheme() {
        isDarkTheme = true;
        applyTheme(root.getScene());
    }
    
    private void switchToLightTheme() {
        isDarkTheme = false;
        applyTheme(root.getScene());
    }
    
    private void applyTheme(Scene scene) {
        if (isDarkTheme) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());
            root.setStyle("-fx-background-color: #2b2b2b;");
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/light-theme.css").toExternalForm());
            root.setStyle("-fx-background-color: #f0f0f0;");
        }
        keyboardPane.applyTheme(isDarkTheme);
    }
    
    private void increaseKeyboardSize() {
        keyboardPane.increaseSize();
    }
    
    private void decreaseKeyboardSize() {
        keyboardPane.decreaseSize();
    }
    
    private void resetKeyboardSize() {
        keyboardPane.resetSize();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}