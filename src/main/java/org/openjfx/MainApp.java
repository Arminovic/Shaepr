package org.openjfx;

import Data.Data;
import GUI.MainScene;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Starting point for the application.
 * every JavaFX application starts at a class extending the Application class and calling the launch(args) method
 * from there the start(Stage) Method is called, which must me overwritten to build up the stage, set the scene and then
 * show the stage.
 */

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
        System.out.println("test");
        primaryStage.show();
    }

    /**
     * for filling the application with test data
     */
    private void start2(){
        Tests tests = new Tests();
        tests.testData();
        tests.testJSON();
        tests.testSave();
        tests.testLoad();
        tests.printData();
        //tests.testValidator();
    }


}