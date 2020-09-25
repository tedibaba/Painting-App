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
import java.util.Random;
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
    @FXML
    private Button eraseButton;
    @FXML
    private Canvas width;

    GraphicsContext gc;
    Color color;
    Double lineWidth = 1.0d;
    boolean isErase = false;

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
        gc = colorChosen.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0,0, colorChosen.getWidth(), colorChosen.getHeight());
        System.out.println(color);

    }

    //Changing the thickness of the brush
    @FXML
    private void changeTheThickness(){
        double thickness = slider.getValue();
        lineWidth = thickness;
        String value = String.format("%.2f", thickness);
        chosenWidth.setText(value);
        showChosenWidth(thickness);
    }

    //When the user clicks on the button, a random color will be chosen
    @FXML
    private void randomColor(){
        Random random = new Random();
        double redValue = random.nextDouble() * 255;
        double greenValue = random.nextDouble() * 255;
        double blueValue = random.nextDouble() * 255;
        red.adjustValue(redValue);
        green.adjustValue(greenValue);
        blue.adjustValue(blueValue);
        chooseColor();
    }

    //Erase button
    @FXML
    private void erase(ActionEvent event){
        if (isErase == false){
            eraseButton.getStylesheets().add(getClass().getResource("eraseCss.css").toExternalForm());
            changeTheThickness();
            color = Color.WHITE;
            gc = colorChosen.getGraphicsContext2D();
            gc.setFill(color);
            gc.fillRect(0,0, colorChosen.getWidth(), colorChosen.getHeight());
            isErase = true;
        } else {
            eraseButton.getStylesheets().clear();
            chooseColor();
            isErase = false;
        }
    }

    //Showing the width that the user has chosen
    @FXML
    private void showChosenWidth(Double lineWidth){
        gc = width.getGraphicsContext2D();
        gc.clearRect(0,0, width.getWidth(), width.getHeight());
        gc.setFill(color);
        gc.fillOval(0, 0, lineWidth, lineWidth);
    }

    //making the canvas white and the color chosen black
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        GraphicsContext combination = colorChosen.getGraphicsContext2D();
        Color color =  Color.WHITE;
        gc.setFill(color);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        combination.setFill(Color.BLACK);
        combination.fillRect(0,0, colorChosen.getWidth(), colorChosen.getHeight());
        chosenWidth.setText("1.00");
    }
}
