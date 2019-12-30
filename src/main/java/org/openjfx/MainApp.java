package org.openjfx;

import Data.Data;
import GUI.MainScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{


    public static void main(String[] args){
        launch(args);
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage){
        start1(primaryStage);
        //start2();
    }

    private void start1(Stage primaryStage){
        MainScene mainScene = new MainScene();
        primaryStage.setOnCloseRequest(event -> {
            Data.getInstance().saveConfig();
        });
        primaryStage.setTitle("Shaepr");
        primaryStage.setScene(mainScene.makeScene());
        primaryStage.show();
    }

    private void start2(){
        Tests tests = new Tests();
        //tests.testData();
        //tests.testJSON();
        //tests.testSave();
        //tests.testLoad();
        //tests.printData();
        //tests.testValidator();
    }


}