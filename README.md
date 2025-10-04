# Windows Keyboard - Detailed Technical Documentation

## Table of Contents
1. [Application Overview](#application-overview)
2. [System Architecture](#system-architecture)
3. [Class Documentation](#class-documentation)
4. [Key Features Implementation](#key-features-implementation)
5. [Theme System](#theme-system)
6. [Event Handling](#event-handling)
7. [Installation & Deployment](#installation--deployment)
8. [Troubleshooting](#troubleshooting)

---

## Application Overview

**Application Name:** Windows Keyboard  
**Developer:** Fatherly P. Titus  
**Technology Stack:** Java, JavaFX, CSS  
**Version:** 1.0  
**License:** MIT License

### Purpose
Windows Keyboard is a virtual keyboard application designed for Windows desktop environments that provides:
- Alternative input method for users with accessibility needs
- Customizable interface with theme support
- Adjustable keyboard size for better usability
- Standard QWERTY layout with full functionality

---

## System Architecture

### High-Level Architecture
```
Windows Keyboard Application
├── Presentation Layer (JavaFX)
│   ├── VirtualKeyboardApp (Main Class)
│   ├── KeyboardPane (Keyboard Layout)
│   └── VirtualKeyButton (Individual Keys)
├── Business Logic Layer
│   ├── Theme Management
│   ├── Size Adjustment
│   └── Event Handling
├── Styling Layer (CSS)
│   ├── Light Theme
│   └── Dark Theme
└── Configuration
    ├── Module Configuration
    └── Build Configuration
```

### Dependencies
```xml
<!-- Required Dependencies -->
Java 11+
JavaFX SDK 11+
- javafx.controls
- javafx.fxml
- javafx.graphics
```

---

## Class Documentation

### 1. VirtualKeyboardApp (Main Application Class)

#### Responsibilities
- Application entry point and main window management
- UI component initialization and layout
- Theme switching coordination
- Event handling setup

#### Key Properties
```java
private static final double DEFAULT_WIDTH = 800;
private static final double DEFAULT_HEIGHT = 300;
private static final double MIN_WIDTH = 600;
private static final double MIN_HEIGHT = 200;

private VBox root;                    // Main layout container
private TextArea textArea;           // Text input area
private KeyboardPane keyboardPane;   // Virtual keyboard component
private boolean isDarkTheme = false; // Current theme state
```

#### Main Methods

**initializeUI(Stage stage)**
- Creates the main application window
- Sets up menu bar, text area, and keyboard
- Applies initial theme and styling

**createMenuBar()**
- Builds application menu structure
- Handles theme switching menu items
- Manages keyboard size adjustment options

**setupKeyboardEvents()**
- Configures virtual keyboard event listeners
- Sets up physical keyboard event filtering

**handleVirtualKeyPress(KeyEvent event)**
- Processes virtual keyboard button presses
- Determines appropriate action based on key type
- Updates text area accordingly

**simulateKeyPress(KeyCode keyCode, String keyText)**
- Implements key press simulation logic
- Handles special keys (Backspace, Enter, Tab, Space)
- Manages text insertion and deletion

### 2. KeyboardPane Class

#### Responsibilities
- Keyboard layout management and rendering
- Key organization in standard QWERTY layout
- Size adjustment functionality
- Caps Lock and Shift key state management

#### Key Properties
```java
private static final double BASE_FONT_SIZE = 14;
private static final double SIZE_INCREMENT = 2;
private static final double MIN_FONT_SIZE = 10;
private static final double MAX_FONT_SIZE = 24;

private double currentFontSize = BASE_FONT_SIZE;
private List<VirtualKeyButton> allKeys;
private boolean capsLock = false;
private boolean shiftPressed = false;
```

#### Layout Methods

**createNumberRow()**
- Creates top row with number keys and Backspace
- Keys: `~, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, -, =, Backspace`

**createFirstLetterRow()**
- First letter row with Tab key
- Keys: `Tab, Q, W, E, R, T, Y, U, I, O, P, [, ], \`

**createSecondLetterRow()**
- Second letter row with Caps Lock and Enter
- Keys: `Caps, A, S, D, F, G, H, J, K, L, ;, ', Enter`

**createThirdLetterRow()**
- Third letter row with Shift keys
- Keys: `Shift, Z, X, C, V, B, N, M, ,, ., /, Shift`

**createBottomRow()**
- Bottom row with modifier keys and Space
- Keys: `Ctrl, Win, Alt, Space, Alt, Win, Menu, Ctrl`

#### State Management Methods

**toggleCapsLock()**
- Toggles Caps Lock state
- Updates all letter key labels
- Maintains visual state indication

**toggleShift()**
- Toggles Shift key state
- Updates letter case for all keys
- Temporary state (resets after key press)

**updateKeyLabels()**
- Updates key text based on Caps Lock and Shift states
- Converts between uppercase and lowercase
- Maintains consistency across all keys

### 3. VirtualKeyButton Class

#### Responsibilities
- Individual key representation and behavior
- Visual styling and theme application
- Click event handling and simulation

#### Key Properties
```java
private final String keyText;    // Original key text
private final KeyCode keyCode;   // JavaFX KeyCode representation
private boolean isDarkTheme = false; // Current theme state
```

#### Key Methods

**initializeButton()**
- Sets up button appearance and dimensions
- Configures event handlers
- Applies initial styling

**applyTheme(boolean isDarkTheme)**
- Applies theme-specific styling
- Updates background and text colors
- Maintains visual consistency

---

## Key Features Implementation

### 1. Theme System

#### Light Theme (Default)
```css
/* light-theme.css */
.root {
    -fx-background-color: #f0f0f0;
    -fx-text-fill: black;
}

.text-area {
    -fx-background-color: white;
    -fx-text-fill: black;
    -fx-border-color: #cccccc;
}

.menu-bar {
    -fx-background-color: #e0e0e0;
}
```

#### Dark Theme
```css
/* dark-theme.css */
.root {
    -fx-background-color: #2b2b2b;
    -fx-text-fill: white;
}

.text-area {
    -fx-background-color: #3c3c3c;
    -fx-text-fill: white;
    -fx-border-color: #555555;
}

.menu-bar {
    -fx-background-color: #3c3c3c;
}
```

#### Theme Switching Logic
```java
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
```

### 2. Size Adjustment System

#### Size Management
```java
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
```

### 3. Event Handling System

#### Virtual Key Press Handling
```java
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
```

#### Key Simulation Logic
```java
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
            textArea.appendText("    "); // 4 spaces for tab
            break;
        case SPACE:
            textArea.appendText(" ");
            break;
        case SHIFT:
        case CONTROL:
        case ALT:
            // Modifier keys handled by keyboard pane
            break;
        default:
            if (keyText.length() == 1) {
                textArea.appendText(keyText);
            }
            break;
    }
}
```

---

## Theme System Detailed Breakdown

### CSS Architecture

#### Component-Based Styling
```css
/* Base components */
.root, .text-area, .menu-bar

/* State-based styling */
.menu-item:hover, .button:pressed

/* Theme-specific variables */
-light-color: #f0f0f0;
-dark-color: #2b2b2b;
-primary-color: #3498db;
```

#### Inheritance Structure
```
Base Styles
├── Light Theme (default)
│   ├── Background: #f0f0f0
│   ├── Text: #333333
│   └── Borders: #cccccc
└── Dark Theme
    ├── Background: #2b2b2b
    ├── Text: #ffffff
    └── Borders: #555555
```

---

## Event Handling Detailed Flow

### Virtual Keyboard Event Flow
```
User Click → VirtualKeyButton → KeyEvent Generation → 
Event Filter → Key Press Simulation → Text Area Update
```

### Physical Keyboard Integration
```java
// Physical keyboard events are passed through to text area
root.addEventFilter(KeyEvent.KEY_PRESSED, this::handlePhysicalKeyPress);
```

### Modifier Key State Management
```java
// Shift key handling
private void toggleShift() {
    shiftPressed = !shiftPressed;
    updateKeyLabels();
}

// Caps Lock handling  
private void toggleCapsLock() {
    capsLock = !capsLock;
    updateKeyLabels();
}
```

---

## Installation & Deployment

### System Requirements
- **OS:** Windows 10/11 (64-bit)
- **Java:** OpenJDK 11 or later
- **JavaFX:** Version 11 or later
- **RAM:** 2GB minimum, 4GB recommended
- **Storage:** 50MB available space

### Build Process
```bash
# 1. Clone repository
git clone https://github.com/Fatherly-P-Titus/windows-keyboard.git

# 2. Navigate to project directory
cd windows-keyboard

# 3. Build with Maven
mvn clean compile

# 4. Package application
mvn package

# 5. Run application
java -jar target/windows-keyboard-1.0.jar
```

### Module Configuration
```java
module VirtualKeyboard {
    requires javafx.controls;
    requires javafx.fxml;
    
    exports com.virtualkeyboard;
    
    opens com.virtualkeyboard to javafx.fxml;
}
```

### Directory Structure
```
windows-keyboard/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── virtualkeyboard/
│       │           ├── VirtualKeyboardApp.java
│       │           ├── KeyboardPane.java
│       │           └── VirtualKeyButton.java
│       └── resources/
│           ├── dark-theme.css
│           └── light-theme.css
├── target/
│   └── windows-keyboard-1.0.jar
└── pom.xml
```

---

## Troubleshooting Guide

### Common Issues

#### 1. JavaFX Not Found
**Symptoms:** Application fails to start with ClassNotFoundException
**Solution:** 
```bash
java --module-path /path/to/javafx-sdk/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar windows-keyboard.jar
```

#### 2. Theme Not Applying
**Symptoms:** UI remains in default theme after switching
**Solution:** Check CSS file paths and ensure resources are in classpath

#### 3. Keys Not Responding
**Symptoms:** Clicking keys doesn't update text area
**Solution:** Verify event handlers are properly connected and KeyCode mappings are correct

#### 4. Size Adjustment Not Working
**Symptoms:** Keyboard size doesn't change with menu commands
**Solution:** Check font size constraints and CSS application

### Debug Mode
Add debug logging to track application state:
```java
// Enable debug logging
System.setProperty("javafx.debug", "true");

// Add state logging
private void logState(String message) {
    if (DEBUG) {
        System.out.println("DEBUG: " + message);
    }
}
```

---

## Performance Considerations

### Memory Management
- VirtualKeyButton objects are cached for reuse
- CSS styles are applied efficiently without recreation
- Event listeners are properly managed to prevent leaks

### Responsiveness
- Key presses are handled asynchronously
- UI updates are batched where possible
- Large text areas are managed with pagination

### Scalability
- Keyboard layout can be extended with additional rows
- New themes can be added without code changes
- Key mappings are configurable for international layouts

---

## Extension Points

### Adding New Themes
1. Create new CSS file in resources
2. Add theme switching method in VirtualKeyboardApp
3. Update menu creation to include new theme

### Custom Key Layouts
1. Extend KeyboardPane class
2. Override layout creation methods
3. Implement custom key arrangement

### Additional Features
- **Audio Feedback:** Add key press sounds
- **Visual Effects:** Ripple animations on key press
- **Multi-language:** Support for international keyboard layouts
- **Presets:** Save custom theme and size combinations

---

## Contact & Support

**Developer:** Fatherly P. Titus  
**Email:** [Available on GitHub profile]  
**Website:** https://fatherly-p-titus.github.io/fatherlytitus.github.io/  
**Phone:** +234-901-806-3499  
**GitHub:** github.com/Fatherly-P-Titus

For bug reports, feature requests, or contributions, please use the GitHub repository issues section.

---

*Documentation Version: 1.0 | Last Updated: December 2023*  
*© 2023 Fatherly P. Titus. All Rights Reserved.*
