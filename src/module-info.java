module HelloFX {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}