package com.example.university_score_tracking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private ListView<String> courseList;

    @FXML
    public void addCourse() {
        String courseName = CourseField.getText();

        double grade = Double.parseDouble(GradeField.getText());

        int unit = Integer.parseInt(UnitField.getText());

        CourseStorage courseStorage = new CourseStorage(courseName,grade,unit);

        gpaCalculator.addCourse(courseStorage);

        Double gpa = gpaCalculator.calculateGPA();

        gpaLabel.setText(String.format("Your New GPA: %.2f", gpa));

        courseList.getItems().add(String.format("%-10s Grade %.2f Unit %d", courseName, grade, unit));

        CourseField.clear();
        GradeField.clear();
        UnitField.clear();
    }

}
