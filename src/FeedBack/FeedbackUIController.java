/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeedBack;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author icom
 */
public class FeedbackUIController {

    @FXML
    private TableView<Feedback> tvFeedback;

    @FXML
    private JFXButton btnInsert;

    @FXML
    private TableColumn<Feedback, Integer> tcFbID;

    @FXML
    private TableColumn<Feedback, String> tcContent;

    @FXML
    private TableColumn<Feedback, Integer> tcProductID;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    void btnInsertClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToEdit(null);
    }

 
    public void initialize() {

        System.out.println("#FeedBackController initialized!");

        tvFeedback.setItems(Feedback.selectAll());

        tcFbID.setCellValueFactory((feedback) -> {
            return feedback.getValue().getFbIDProperty();
        });

        tcContent.setCellValueFactory((feedback) -> {
            return feedback.getValue().getContentProperty();
        });

        tcProductID.setCellValueFactory((feedback) -> {
            return feedback.getValue().getProductIDProperty();
        });
    }

    @FXML
    void btnUpdateClick(ActionEvent event) throws IOException {
        Feedback updatefeedback = tvFeedback.getSelectionModel().getSelectedItem();

        if (updatefeedback == null) { //no book is selected
            selectFeedbackWarning();
        } else { //a book is selected
            Navigator.getInstance().goToEdit(updatefeedback);
        }
    }

    @FXML
    void btnDeleteClick(ActionEvent event) {
        Feedback selectedfeedback = tvFeedback.getSelectionModel().getSelectedItem();

        if (selectedfeedback == null) { //no book is selected
            selectFeedbackWarning();
        } else { //a book is selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete the selected feedback?");
            alert.setTitle("Deleting a feedback");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                Feedback deletefeedback = tvFeedback.getSelectionModel().getSelectedItem();
                boolean result = Feedback.delete(deletefeedback);

                if (result) {
                    tvFeedback.getItems().remove(deletefeedback); //update TableView
                    System.out.println("feedback is deleted");
                } else {
                    System.err.println("No feedback is deleted");
                }
            }
        }

    }

    private void selectFeedbackWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Please select a feedback");
        alert.setHeaderText("A feedback must be selected for the operation");
        alert.showAndWait();
    }

}
