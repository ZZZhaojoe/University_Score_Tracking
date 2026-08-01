package com.example.university_score_tracking;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.ProgressBar;

public class HelloController {
    private final GPA_Calculator gpaCalculator = new GPA_Calculator();
    private final IO io = new IO();
    private final DegreePlanner degreePlanner = new DegreePlanner();

    @FXML
    private TextField CourseField;

    @FXML
    private ComboBox<String> GradeBox;

    @FXML
    private ComboBox<Integer> UnitBox;

    @FXML
    private Label gpaLabel;

    @FXML
    private ComboBox<String> semesterBox;

    @FXML
    private TableView<CourseStorage> courseTable;

    @FXML
    private TableColumn<CourseStorage,String> CourseCol;

    @FXML
    private TableColumn<CourseStorage,String> GradeCol;

    @FXML
    private TableColumn<CourseStorage,Integer> UnitCol;

    @FXML
    private TableColumn<CourseStorage, String> semesterCol;

    @FXML
    private TableView<DegreeRequirement> requirementTable;

    @FXML
    private TableColumn<DegreeRequirement,String> requiredCourseCol;

    @FXML
    private TableColumn<DegreeRequirement,Boolean> completedCourse;

    @FXML
    private Label progressTextLabel;

    @FXML
    private Label gpaValueLabel;

    @FXML
    private Label courseCountLabel;

    @FXML
    private Label unitCountLabel;

    @FXML
    private Label completedCountLabel;

    @FXML
    private ProgressBar progressBar;

    private void updateStatistics() {

        gpaValueLabel.setText(
                String.format("%.2f", gpaCalculator.calculateGPA())
        );

        courseCountLabel.setText(
                String.valueOf(gpaCalculator.returnCourses().size())
        );

        unitCountLabel.setText(
                String.valueOf(gpaCalculator.calculateUnit())
        );

        completedCountLabel.setText(
                degreePlanner.completedCourse()
                        + " / "
                        + degreePlanner.getRequirements().size()
        );
    }

    public void updateProgressBar() {
        int completed = degreePlanner.completedCourse();
        int total = degreePlanner.getRequirements().size();

        double progress;

        if (total == 0) {
            return;
        }
        else {
            progress = (double) completed / total;
        }

        progressBar.setProgress(progress);

        int percentage = (int) Math.round(progress * 100);

        progressTextLabel.setText(
                percentage + "% Complete — "
                        + completed + " of "
                        + total + " required courses"
        );
    }

    private void refreshDegreeRequirements() {

        degreePlanner.resetRequirement();

        for (CourseStorage course : gpaCalculator.returnCourses()) {
            degreePlanner.markCompleted(
                    course.getCourseName().trim().toUpperCase()
            );
        }

        requirementTable.refresh();
        updateProgressBar();
        updateStatistics();
    }

    @FXML
    public void addCourse() {
        String courseName = CourseField.getText();
        String semester = semesterBox.getValue();

        if (semester == null) {
            gpaLabel.setText("Please choose a semester");
            return;
        }

        if (courseName.trim().isEmpty()) {
            gpaLabel.setText("Course name can not be empty");
            return;
        }

        String letterGrade = GradeBox.getValue();

        if (letterGrade == null) {
            gpaLabel.setText("Please choose a grade");
               return;
        }

        double grade = convertLetterTOGPA(letterGrade);

        Integer unit = UnitBox.getValue();

        if (unit == null) {
            gpaLabel.setText("Please choose the number of units");
            return;
        }

        CourseStorage courseStorage = new CourseStorage(courseName,letterGrade,grade,unit,semester);
        gpaCalculator.addCourse(courseStorage);
        courseTable.getItems().add(courseStorage);
        boolean requireCourse = degreePlanner.markCompleted(courseName.trim().toUpperCase());

        requirementTable.refresh();
        updateProgressBar();

        updateStatistics();

        if (requireCourse) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Congratulation");
            alert.setContentText("Required course complete!");
            alert.showAndWait();
        }

        CourseField.clear();
        GradeBox.setValue(null);
        UnitBox.setValue(null);
        semesterBox.setValue(null);

    }

    @FXML
    public void removeCourse() {
        CourseStorage selectedCourse = courseTable.getSelectionModel().getSelectedItem();

        if (selectedCourse == null) {
            gpaLabel.setText("Please add a selected course to remove");
            return;
        }

        courseTable.getItems().remove(selectedCourse);
        gpaCalculator.removeCourse(selectedCourse);

        refreshDegreeRequirements();
        gpaLabel.setText(
                selectedCourse.getCourseName() + " removed"
        );
    }

    @FXML
    public void loadCourse() {

        GPA_Calculator storage = new GPA_Calculator();
        boolean success = io.loadFile(storage);

        degreePlanner.resetRequirement();

        if (success) {
            courseTable.getItems().clear();
            gpaCalculator.clearCourses();

            for (CourseStorage courseStorage: storage.returnCourses()) {
                gpaCalculator.addCourse(courseStorage);
                courseTable.getItems().add(courseStorage);

                degreePlanner.markCompleted(
                        courseStorage.getCourseName().trim().toUpperCase());
            }

            requirementTable.refresh();
            updateProgressBar();

            updateStatistics();
        }
        else {
            gpaLabel.setText("Failed to load");
        }
    }

    @FXML
    public void saveCourse() {
        boolean success = io.saveFile(gpaCalculator);

        if (success) {
            gpaLabel.setText(String.format(
                    "Saved %d courses",
                    gpaCalculator.returnCourses().size()
            ));
        } else {
            gpaLabel.setText("Failed to save");
        }

    }

    @FXML
    public void initialize() {
        CourseCol.setCellValueFactory(
                new PropertyValueFactory<>("courseName"));

        GradeCol.setCellValueFactory(
                new PropertyValueFactory<>("letterGrade"));

        UnitCol.setCellValueFactory(
                new PropertyValueFactory<>("unit"));

        semesterCol.setCellValueFactory(
                new PropertyValueFactory<>("semester"));

        GradeBox.getItems().addAll(
                "A+","A", "A-"
                ,"B+","B","B-"
                ,"C+","C","C-"
                ,"D+","D","D-"
                ,"F");

        UnitBox.getItems().addAll(
                1,2,3,4,5);

        courseTable.setEditable(true);

        GradeCol.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                "A+","A", "A-"
                ,"B+","B","B-"
                ,"C+","C","C-"
                ,"D+","D","D-"
                ,"F"
                )
        );

        GradeCol.setOnEditCommit(event -> {
            CourseStorage course = event.getRowValue();
            String newLetterGrade = event.getNewValue();

            course.setLetterGrade(newLetterGrade);

            double newGrade = convertLetterTOGPA(newLetterGrade);
            course.setGrade(newGrade);

            updateStatistics();
        });

        UnitCol.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                        1,
                        2,
                        3,
                        4,
                        5
                ));

        UnitCol.setOnEditCommit(event -> {
            CourseStorage unit = event.getRowValue();
            int newUnit = event.getNewValue();

            unit.setUnit(newUnit);

            updateStatistics();
        });

        semesterCol.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                        "Fall",
                        "Winter",
                        "Spring",
                        "Summer")
        );

        semesterCol.setOnEditCommit(event -> {
            CourseStorage courseStorage = event.getRowValue();
            courseStorage.setSemester(event.getNewValue());
        });

        semesterBox.getItems().addAll(
                "Fall",
                "Winter",
                "Spring",
                "Summer"
        );

        requiredCourseCol.setCellValueFactory(
                new PropertyValueFactory<>("courseCode")
        );

        completedCourse.setCellValueFactory(
                new PropertyValueFactory<>("completed"));

        completedCourse.setCellFactory(column -> new TableCell<>() {

            @Override
            protected void updateItem(Boolean completed, boolean empty) {
                super.updateItem(completed,empty);
                if (empty || completed == null) {
                    setText(null);
                    setStyle("");
                }
                else if (completed) {
                    setText("✓");
                    setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                }
                else {
                    setText("X");
                    setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });

        requirementTable.getItems().addAll(
                degreePlanner.getRequirements());

        updateProgressBar();
        updateStatistics();
    }

    private double convertLetterTOGPA(String letter) {
        return switch (letter) {
            case "A+","A" -> 4.0;
            case "A-" -> 3.7;
            case "B+" -> 3.3;
            case "B" -> 3.0;
            case "B-" -> 2.7;
            case "C+" -> 2.3;
            case "C" -> 2.0;
            case "C-" -> 1.7;
            case "D+" -> 1.3;
            case "D" -> 1.0;
            case "D-" -> 0.5;
            case "F" -> 0.0;
            default -> 0.0;
        };
    }

}
