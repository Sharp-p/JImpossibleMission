module Controller {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires java.desktop;

    opens Controller to javafx.fxml;
    exports Controller;
}