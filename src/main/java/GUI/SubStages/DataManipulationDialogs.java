package GUI.SubStages;

import Data.Dancer;
import Data.Data;
import Data.Pose;
import Data.Shape;
import GUI.MainScene;
import Utils.Notifier;
import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Vector;

import Data.Sex;

public class DataManipulationDialogs {

    public static void showEditShapeDialog(double posX, double posY, Shape item){
        Stage editShapeStage = new Stage();
        editShapeStage.setResizable(false);
        editShapeStage.setX(posX);
        editShapeStage.setY(posY);
        Label shapeNamelbl = new Label("Name: ");
        TextField shapeNametxt = new TextField(item.getName());
        Button finnishButton = new Button("apply");
        finnishButton.setMaxWidth(Double.MAX_VALUE);
        finnishButton.setOnAction(event1 -> {
            item.setName(shapeNametxt.getText());
            Notifier.notify("Shape updated");
            editShapeStage.close();
        });

        shapeNametxt.setOnKeyPressed(event -> {
            if(KeyCode.ENTER == event.getCode()){
                finnishButton.fire();
            }
        });

        GridPane editShapeGrid = new GridPane();
        editShapeGrid.add(shapeNamelbl, 0, 0);
        editShapeGrid.add(shapeNametxt, 1, 0);
        editShapeGrid.add(finnishButton, 0, 1, 2, 1);

        Scene editShapeScene = new Scene(editShapeGrid);
        editShapeStage.setScene(editShapeScene);
        editShapeStage.setAlwaysOnTop(true);
        editShapeStage.show();
    }

    public static void showEditDancerDialog(double posX, double posY, Dancer item){

        //System.out.println("edit Dancer");

        Pose p = MainScene.getSelectedShape().getPoseOf(item);
        //System.out.println("P = "+p);
        //System.out.println("MainScene.getSelectedShape() = " + MainScene.getSelectedShape());;

        Stage editStage = new Stage();
        GridPane editGrid = new GridPane();
        editStage.setResizable(false);
        editStage.setX(posX);
        editStage.setY(posY);
        editStage.setAlwaysOnTop(true);

        if(p == null){
            // if no pose available, just open dialog for editing dancer name

            editStage.setTitle(item.getName());
            Label dancerNamelbl = new Label("Name: ");
            TextField dancerNametxt = new TextField(item.getName());
            editGrid.add(dancerNamelbl, 0, 0);
            editGrid.add(dancerNametxt, 1, 0);
            dancerNametxt.textProperty().addListener((observable, oldValue, newValue) -> {
                item.setName(observable.getValue());
            });

        }else {
            // if pose available, open dialog to edit all data of pose

            editStage.setTitle(p.getDancerNamesConcat(","));

            //Labels
            Label xLbl = new Label("X: ");
            Label yLbl = new Label("Y: ");
            Label thetaLbl = new Label("\u03F4");

            //Textfields
            TextField xTxt = new TextField(String.valueOf(p.getX()));
            TextField yTxt = new TextField(String.valueOf(p.getY()));
            TextField thetaTxt = new TextField(String.valueOf(p.getTheta()));

            //change Listeners
            xTxt.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    p.setX(Double.valueOf(xTxt.getText()), false);
                } catch (Exception e) {
                    System.out.println("wrong X-format");
                }
            });
            yTxt.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    p.setY(Double.valueOf(yTxt.getText()), false);
                } catch (Exception e) {
                    System.out.println("wrong Y-format");
                }
            });
            thetaTxt.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    p.setTheta(Double.valueOf(thetaTxt.getText()));
                } catch (Exception e) {
                    System.out.println("wrong theta-format");
                }
            });


            //Slider
            Slider thetaSlider = new Slider();
            thetaSlider.setMin(0);
            thetaSlider.setMax(360);
            thetaSlider.setValue(p.getTheta());
            //change Listener
            thetaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                p.setTheta(thetaSlider.getValue());
            });

            //adding components
            int rowIterator = 0;
            for (Dancer dancer : p.getDancers()) {
                Label dancerNamelbl = new Label("Name" + (rowIterator + 1) + ": ");
                TextField dancerNametxt = new TextField(dancer.getName());

                dancerNametxt.textProperty().addListener((observable, oldValue, newValue) -> {
                    dancer.setName(dancerNametxt.getText());
                });
                editGrid.add(dancerNamelbl, 0, rowIterator);
                editGrid.add(dancerNametxt, 1, rowIterator);
                rowIterator++;
            }
            editGrid.add(xLbl, 0, rowIterator);
            editGrid.add(xTxt, 1, rowIterator);
            rowIterator++;
            editGrid.add(yLbl, 0, rowIterator);
            editGrid.add(yTxt, 1, rowIterator);
            rowIterator++;
            editGrid.add(thetaLbl, 0, rowIterator);
            editGrid.add(thetaTxt, 1, rowIterator);
            rowIterator++;
            editGrid.add(thetaSlider, 1, rowIterator);

            //if more than one dancer is associated to this Pose, add a button for disconnecting all dancers from each other
            if(p.getDancers().size() > 1){
                Button disconnectButton = new Button("disconnect");
                disconnectButton.setOnAction(event -> {
                    //todo
                    Vector<Dancer> disconnectedDancers = p.disconnectDancers();
                    MainScene.getActiveView().buildView();
                });
                rowIterator++;
                editGrid.add(disconnectButton, 1, rowIterator);
            }
        }


        Scene editScene = new Scene(editGrid);
        editStage.setScene(editScene);
        editStage.show();
    }

    public static void showAddDancerDialog(List<Dancer> list){
        Label dancerNamelbl = new Label("Name: ");
        Label dancerSexlbl = new Label("Sex: ");
        TextField dancerNametxt = new TextField("dancer"+list.size());
        Vector<Sex> sexVector = new Vector<>();
        //Data.sex male = Data.sex.male, female = Data.sex.female;
        sexVector.add(Sex.Male);
        sexVector.add(Sex.Female);
        ComboBox<Sex> dancerSexComBox = new ComboBox<>(FXCollections.observableList(sexVector));
        dancerSexComBox.getSelectionModel().select(0);
        Button finnishButton = new Button("add");
        finnishButton.setMaxWidth(Double.MAX_VALUE);
        finnishButton.setOnAction(event1 -> {
            list.add(new Dancer(dancerNametxt.getText(), dancerSexComBox.getValue()));
            dancerNametxt.setText("dancer"+list.size());
        });

        GridPane addDancerGrid = new GridPane();
        addDancerGrid.add(dancerNamelbl, 0, 0);
        addDancerGrid.add(dancerNametxt, 1, 0);
        addDancerGrid.add(dancerSexlbl, 0, 1);
        addDancerGrid.add(dancerSexComBox, 1, 1);
        addDancerGrid.add(finnishButton, 0, 2, 2, 1);

        Scene addDancerScene = new Scene(addDancerGrid);
        Stage addDancerStage = new Stage();
        addDancerStage.setScene(addDancerScene);
        addDancerStage.setAlwaysOnTop(true);
        addDancerStage.show();
    }

    public static void showAddShapeDialog(List<Shape> list){
        Label shapeNamelbl = new Label("Name: ");
        TextField shapeNametxt = new TextField("shape"+list.size());
        Button finnishButton = new Button("add");
        finnishButton.setMaxWidth(Double.MAX_VALUE);
        finnishButton.setOnAction(event1 -> {
            Shape shape = new Shape();
            shape.setName(shapeNametxt.getText());
            list.add(shape);
            shapeNametxt.setText("shape"+list.size());
        });

        GridPane addShapeGrid = new GridPane();
        addShapeGrid.add(shapeNamelbl, 0, 0);
        addShapeGrid.add(shapeNametxt, 1, 0);
        addShapeGrid.add(finnishButton, 0, 1, 2, 1);

        Scene addShapeScene = new Scene(addShapeGrid);
        Stage addShapeStage = new Stage();
        addShapeStage.setScene(addShapeScene);
        addShapeStage.setAlwaysOnTop(true);
        addShapeStage.show();
    }


}

