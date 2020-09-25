package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Canvas canvas;
    @FXML
    private Slider slider;
    @FXML
    private Slider red;
    @FXML
    private Slider green;
    @FXML
    private Slider blue;
    @FXML
    private Canvas colorChosen;
    @FXML
    private Label chosenWidth;

    GraphicsContext gc;
    Color color;
    Double lineWidth = 5.0d;
    GraphicsContext combination;

    //Drawing on the canvas
    @FXML
    private void draw(MouseEvent event){
        gc = canvas.getGraphicsContext2D();
        System.out.println(color);
        gc.setStroke(color);
        gc.beginPath();
        gc.setLineWidth(lineWidth);
        gc.moveTo(event.getX(), event.getY());
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
    }

    //Choosing which color to use
    @FXML
    private void chooseColor(){
        double redValue = red.getValue();
        double greenValue = green.getValue();
        double blueValue = blue.getValue();
        color = Color.color(redValue / 255, greenValue / 255, blueValue/ 255);
        combination = colorChosen.getGraphicsContext2D();
        combination.setFill(color);
        combination.fillRect(0,0, colorChosen.getWidth(), colorChosen.getHeight());
        System.out.println(color);

    }

    //Changing the thickness of the brush
    @FXML
    private void ChangeTheThickness(MouseEvent event){
        double thickness = slider.getValue();
        lineWidth = thickness;
        String value = String.format("%.2f", thickness);
        chosenWidth.setText(value);
    }

    //making the canvas white
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        combination = colorChosen.getGraphicsContext2D();
        Color color =  Color.WHITE;
        gc.setFill(color);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        combination.setFill(Color.BLACK);
        combination.fillRect(0,0, colorChosen.getWidth(), colorChosen.getHeight());
    }
}
