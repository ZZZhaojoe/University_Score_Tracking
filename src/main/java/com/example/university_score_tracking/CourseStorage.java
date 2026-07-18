package com.example.university_score_tracking;


public class CourseStorage {
    private String courseName;
    private double grade;
    private int unit;
    private String letterGrade;
    private String semester;

    public CourseStorage(String courseName, String letterGrade, double grade, int unit, String semester) {
        this.courseName = courseName;
        this.letterGrade = letterGrade;
        this.grade = grade;
        this.unit = unit;
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getGrade() {
        return grade;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public int getUnit() {
        return unit;
    }

    public String getSemester() {
        return semester;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

}
