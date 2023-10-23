package splendor.client.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UserPane extends Pane {

  @FXML
  Label name;

  @FXML
  PasswordField passwordField;
  @FXML
  Button passwordButton;

  @FXML
  Label type;

  @FXML
  ColorPicker colorPicker;
  @FXML
  Button colorButton;

  @FXML
  Button deleteButton;

  User user;

  /**
   * Constructor for UserPane class.
   *
   * @param user registered user obtained from the Lobby Service from which to create the
   *               UserPane
   */
  public UserPane(User user) {

    this.user = user;

    this.setPrefHeight(40);

    Border border = new Border(new BorderStroke(
        javafx.scene.paint.Color.BLACK,
        BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY,
        BorderWidths.DEFAULT
    ));

    //this.setBorder(border);

    Font fontLabels = new Font(16);
    Font fontButtons = new Font(16);

    // Add name label
    String nameStr = user.getName();
    name = new Label(nameStr);
    name.setFont(fontLabels);
    name.relocate(0,5);

    // Add password field
    passwordField = new PasswordField();
    passwordField.setFont(fontLabels);
    passwordField.relocate(140,0);

    // Add password button
    passwordButton = new Button("Update");
    passwordButton.setFont(fontButtons);
    passwordButton.relocate(340, 0);

    // Add type label
    String typeStr = user.getRole();
    type = new Label(typeStr);
    type.setFont(fontLabels);
    type.relocate(450,5);

    // Add colour picker
    colorPicker = new ColorPicker();
    String colorStr = "#".concat(user.getPreferredColour());
    colorPicker.setValue(Color.web(colorStr));
    colorPicker.setPrefWidth(35);
    colorPicker.setPrefHeight(35);
    colorPicker.relocate(560,0);

    // Add colour button
    colorButton = new Button("Update");
    colorButton.setFont(fontButtons);
    colorButton.relocate(600,0);

    // Add delete button
    deleteButton = new Button("Delete");
    deleteButton.setFont(fontButtons);
    deleteButton.relocate(700,0);

    passwordButton.setOnAction(passwordEvent);
    colorButton.setOnAction(colourEvent);
    deleteButton.setOnAction(deleteEvent);

    getChildren().addAll(name, passwordField, passwordButton, type,
        colorPicker, colorButton, deleteButton);

  }

  // Event handler for the password button
  EventHandler<ActionEvent> passwordEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      // Send request to change the password of the user
      AdminRequest.changePassword(user.getName(), passwordField.getText());

      // Update the ui to reflect the new changes
      AdminController.getInstance().updateAdminMenu();
    }
  };

  // Event handler for the colour button
  EventHandler<ActionEvent> colourEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      // Send request to change the preferred colour of the user
      String colourStr = colorPicker.getValue().toString().substring(2,8).toUpperCase();
      AdminRequest.changeColour(user.getName(), colourStr);

      // Update the ui to reflect the new changes
      AdminController.getInstance().updateAdminMenu();
    }
  };

  // Event handler for the delete button
  EventHandler<ActionEvent> deleteEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      // Send request to delete the specified user
      AdminRequest.deleteUser(user.getName());

      // Update the ui to reflect the new changes
      AdminController.getInstance().updateAdminMenu();
    }
  };



}
