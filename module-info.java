module VirtualKeyboard {
    requires javafx.controls;
    requires javafx.fxml;
    
    exports com.virtualkeyboard;
    
    opens com.virtualkeyboard to javafx.fxml;
}