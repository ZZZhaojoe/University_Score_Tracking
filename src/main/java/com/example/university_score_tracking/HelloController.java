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
    private TextField UnitField;

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
    private ProgressBar progressBar;


    public void updateProgressBar() {
        int completed = degreePlanner.completedCourse();
        int total = degreePlanner.getRequirements().size();

        double progress =
                (double) completed / total;

        progressBar.setProgress(progress);

        System.out.println("Complete " + completed + " total " + total + " progress " + progress);
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

        try {
            String letterGrade = GradeBox.getValue();

            if (letterGrade == null) {
                gpaLabel.setText("Please choose a grade");
                return;
            }

            double grade = convertLetterTOGPA(letterGrade);

            int unit = Integer.parseInt(UnitField.getText());

            if (unit <= 0) {
                gpaLabel.setText("Units must be greater than 0.");
                return;
            } else if (unit > 5) {
                gpaLabel.setText("Unit can not be larger than 5");
                return;
            }

            CourseStorage courseStorage = new CourseStorage(courseName,letterGrade,grade,unit,semester);
            gpaCalculator.addCourse(courseStorage);
            boolean requireCourse = degreePlanner.markCompleted(courseName.trim().toUpperCase());

            requirementTable.refresh();
            updateProgressBar();

            if (requireCourse) {
                gpaLabel.setText(String.format(
                        "GPA: %.2f | Required course completed: %d" ,
                        gpaCalculator.calculateGPA(),
                        degreePlanner.completedCourse()
                ));
            }
            else {
                gpaLabel.setText(String.format(
                        "Your New GPA: %.2f",
                        gpaCalculator.calculateGPA()
                ));
            }

            courseTable.getItems().add(courseStorage);
            CourseField.clear();
            GradeBox.setValue(null);
            UnitField.clear();
            semesterBox.setValue(null);
        } catch (NumberFormatException e) {
            gpaLabel.setText("Invalid input for unit");
        }

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

        double gpa = gpaCalculator.calculateGPA();
        gpaLabel.setText(String.format("Your New GPA: %.2f",gpa));
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

            gpaLabel.setText(
                    String.format(
                            "Loaded %d courses | GPA: %.2f",
                            gpaCalculator.returnCourses().size(),
                            gpaCalculator.calculateGPA()
                    )
            );
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

            double gpa = gpaCalculator.calculateGPA();

            gpaLabel.setText(String.format("Your New GPA: %.2f",gpa));
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

            double gpa = gpaCalculator.calculateGPA();

            gpaLabel.setText(
                    String.format("Your New GPA: %.2f",gpa));
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
