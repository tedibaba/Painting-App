package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private Button insertShape;

    GraphicsContext gc;
    Color color;
    Double lineWidth = 1.0d;
    boolean isErase = false;
    boolean isShape = false;
    boolean isClear = false;
    Random random = new Random();
    ArrayList<Double> positionOfShape = new ArrayList<>();


    @FXML
    private void drawOrShape(MouseEvent event){
        if (!isShape){
            draw(event);
        } else {
            drawShape(event);
        }
    }

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
    private void erase(){
        if (!isErase){
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

    @FXML
    private void randomArt() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("randomArt.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
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

    //Generating random art or random things

    public void generatingTheArt(String shape, String number, String color){
        invoke(shape, number, color);
    }

    private void invoke(String shape, String number, String color){
        gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < Integer.getInteger(number); i++){
            gc.setFill(Color.BLACK);
            gc.strokeOval(random.nextInt() * canvas.getWidth(), random.nextInt() * canvas.getHeight(), 5, 5);
        }
    }

    //Changing the canvas from drawing to drawing shapes
    @FXML
    private void shape(){
        if (!isShape){
            isShape = true;
        } else {
            isShape = false;
        }
    }

    @FXML
    private void drawShape(MouseEvent event){
        double widthSquare;
        double heightSquare;
        double posX;
        double posY;

        if (positionOfShape.isEmpty()){
            positionOfShape.add(event.getX());
            positionOfShape.add(event.getY());
            System.out.println(positionOfShape.get(0) + " " + positionOfShape.get(1));
        } else {
            Double x = event.getX();
            Double y = event.getY();
            gc = canvas.getGraphicsContext2D();
            gc.setStroke(color);
            gc.setLineWidth(lineWidth);
            double distance = EuclideanDistance(positionOfShape.get(0), positionOfShape.get(1), x, y);
//            gc.strokeOval(positionOfShape.get(0), positionOfShape.get(1), distance, distance);

            if (positionOfShape.get(0) < x){
                widthSquare = x - positionOfShape.get(0);
                posX = positionOfShape.get(0);
            } else {
                widthSquare = positionOfShape.get(0) - x;
                posX = x;
            }

            if (positionOfShape.get(1) < y){
                heightSquare = y - positionOfShape.get(1);
                posY = positionOfShape.get(1);
            } else {
                heightSquare = positionOfShape.get(1) - y ;
                posY = y;
            }
            gc.strokeRect(posX, posY, widthSquare, heightSquare);
            System.out.println(x + " " + y);
            positionOfShape.remove(0);
            positionOfShape.remove(0);
        }
    }

    private double EuclideanDistance(double x2, double y2, double x1, double y1){
        double y = y2 - y1;
        double x = x2 - x1;
        return Math.sqrt((Math.pow(x, 2)) + (Math.pow(y, 2)));
    }

    @FXML
    private void clearBoard(){
        gc = canvas.getGraphicsContext2D();
        color = Color.WHITE;
        gc.setFill(color);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

}
