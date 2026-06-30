package com.example.university_score_tracking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HelloController {
    private GPA_Calculator gpaCalculator = new GPA_Calculator();
    IO io = new IO();

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

        if (courseName.trim().isEmpty()) {
            gpaLabel.setText("Course name can not be empty");
            return;
        }

        try {
            double grade = Double.parseDouble(GradeField.getText());

            if (grade < 0 || grade> 4.0) {
                gpaLabel.setText("Grade must between 0 to 4.0");
                return;
            }

            int unit = Integer.parseInt(UnitField.getText());

            if (unit <= 0) {
                gpaLabel.setText("Units must be greater than 0.");
                return;
            }

            CourseStorage courseStorage = new CourseStorage(courseName,grade,unit);
            gpaCalculator.addCourse(courseStorage);
            double gpa = gpaCalculator.calculateGPA();
            gpaLabel.setText(String.format("Your New GPA: %.2f", gpa));
            courseList.getItems().add(String.format("%-10s Grade %.2f Unit %d", courseName, grade, unit));
            CourseField.clear();
            GradeField.clear();
            UnitField.clear();
        } catch (NumberFormatException e) {
            gpaLabel.setText("Invalid input for grade or unit");
        }

    }

    @FXML
    public void removeCourse() {
        int selectedCourse = courseList.getSelectionModel().getSelectedIndex();

        if (selectedCourse == -1) {
            gpaLabel.setText("Please add a remove selection");
            return;
        }

        courseList.getItems().remove(selectedCourse);
        gpaCalculator.removeCourse(selectedCourse);

        double gpa = gpaCalculator.calculateGPA();
        gpaLabel.setText(String.format("Your New GPA: %.2f",gpa));
    }

    @FXML
    public void loadCourse() {

        courseList.getItems().clear();
        gpaCalculator.clearCourses();

        io.loadFile(gpaCalculator);

        for (CourseStorage courseStorage: gpaCalculator.returnCourses()) {
            courseList.getItems().add(String.format(
                    "%-10s Grade %.2f Unit %d",
                    courseStorage.returnCourse(),
                    courseStorage.returnGrade(),
                    courseStorage.returnUnit()

            ));
        }

        gpaLabel.setText(
                String.format(
                        "Loaded %d courses | GPA: %.2f",
                        gpaCalculator.returnCourses().size(),
                        gpaCalculator.calculateGPA()
                )
        );
    }

    @FXML
    public void saveCourse() {

        io.saveFile(gpaCalculator);
        gpaLabel.setText(String.format(
                "Saved %d courses",
                gpaCalculator.returnCourses().size()
                ));
    }

}
