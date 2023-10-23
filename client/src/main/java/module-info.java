module splendor.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires unirest.java;
    requires org.apache.commons.codec;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.net.http;

    opens splendor.client to javafx.fxml;
    exports splendor.client;
  exports splendor.client.admin;
  opens splendor.client.admin to javafx.fxml;
}