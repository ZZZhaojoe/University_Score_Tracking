package com.example.university_score_tracking;

import java.util.ArrayList;

public class GPA_Calculator {
    private ArrayList<CourseStorage> courses;

    public GPA_Calculator() {
        courses = new ArrayList<>();
    }


    public double calculateGPA() {

        double totalPoint = 0;
        int totalUnit = 0;

        for (CourseStorage courseStorage: courses) {
            totalPoint += courseStorage.returnGrade() * courseStorage.returnUnit();
            totalUnit += courseStorage.returnUnit();
        }

        if (totalUnit == 0) {
            return 0;
        }

        return totalPoint/totalUnit;
    }

    public void addCourse(CourseStorage courseStorage) {
        courses.add(courseStorage);
    }
}
