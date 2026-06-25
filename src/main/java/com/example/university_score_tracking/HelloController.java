package com.example.university_score_tracking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    private GPA_Calculator gpaCalculator = new GPA_Calculator();

    @FXML
    private TextField CourseField;

    @FXML
    private TextField GradeField;

    @FXML
    private TextField UnitField;

    @FXML
    private Label gpaLabel;

    @FXML
    public void addCourse() {
        System.out.println("Button clicked");
    }

}
