package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class randomArtController implements Initializable {
    @FXML
    private ChoiceBox shapes;
    @FXML
    private ChoiceBox colors;
    @FXML
    private ChoiceBox numberOfShapes;

    private void possibleShapes(){
        ArrayList<String> shape = new ArrayList<String>(Arrays.asList("Square", "Circle", "Oval"));
        for (String i: shape){
            shapes.getItems().add(i);
        }
    }

    private void possibleColors(){
        ArrayList<String> color = new ArrayList<String>(Arrays.asList("Black", "Blue", "Yellow"));
        for (String i : color){
            colors.getItems().add(i);
        }

    }

    private void possibleNumberOfShapes(){
        ArrayList<Character> number = new ArrayList<Character>(Arrays.asList('1', '2', '3'));
        for (Character i: number){
            numberOfShapes.getItems().add(i);
        }
    }

    @FXML
    private void confirm(ActionEvent event){
        String shape = shapes.getValue().toString();
        String color = colors.getValue().toString();
        String numOfShapes = numberOfShapes.getValue().toString();

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Controller controller = loader.getController();
        controller.generatingTheArt(shape, numOfShapes, color);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        possibleShapes();
        possibleColors();
        possibleNumberOfShapes();

        shapes.setValue("Square");
        colors.setValue("Black");
        numberOfShapes.setValue('3');
    }

}
