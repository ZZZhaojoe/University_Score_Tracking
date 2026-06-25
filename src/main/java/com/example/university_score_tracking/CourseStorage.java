package com.example.university_score_tracking;

import java.util.ArrayList;

public class CourseStorage {
    private String courseName;
    private double grade;
    private int unit;

    public CourseStorage(String courseName, double grade, int unit) {
        this.courseName = courseName;
        this.grade = grade;
        this.unit = unit;
    }

    public String returnCourse() {
        return courseName;
    }

    public double returnGrade() {
        return grade;
    }

    public int returnUnit() {
        return unit;
    }


}
