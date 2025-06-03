module com.htu.tankgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires com.almasb.fxgl.all;
    requires annotations;

    opens com.htu.tankgame to javafx.fxml;
    exports com.htu.tankgame;
}