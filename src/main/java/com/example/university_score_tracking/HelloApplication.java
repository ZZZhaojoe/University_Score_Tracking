package com.example.university_score_tracking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 900);
        scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());


        stage.setTitle("University GPA & Degree Planner");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(750);
        stage.show();
    }
}
